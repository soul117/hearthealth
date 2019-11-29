package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.media;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.ECGViewOld;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HEART";
    private boolean isStarted;
    File recordingFile;
    RecordAudio recordTask;
    private static final int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        checkPermissions();
    }

    private void checkPermissions() {

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            startRecording();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {

                if (grantResults.length > 0) {
                    boolean allGranted = true;
                    for (int grantResult : grantResults) {
                        allGranted = allGranted && (grantResult == PackageManager.PERMISSION_GRANTED);
                    }
                    if (allGranted) {
                        startRecording();
                    } else {
                        Log.i(TAG, "Permission has been denied by user");
                    }
                } else {
                    Log.i(TAG, "Permission has been denied by user");
                }
            }
            break;
            default:
                break;
        }
    }

    private void startRecording() {
        isStarted = false;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.left);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    Snackbar.make(view, "STOP", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    stopRecord();
                } else {
                    startRecord();
                    Snackbar.make(view, "START", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        File path = new File(
                getFilesDir().getAbsolutePath()
                        + "/audio/");
        path.mkdirs();
        try {
            recordingFile = File.createTempFile("recording", ".pcm", path);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create file on SD card", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.content_activity_home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public void startRecord() {
        recordTask = new RecordAudio();
        recordTask.execute();
    }

    public void stopRecord() {
        isStarted = false;
    }

    private static int[] mSampleRates = new int[]{8000, 11025, 22050, 44100};

    public AudioRecord findAudioRecord() {
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[]{AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT}) {
                for (short channelConfig : new short[]{AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO}) {
                    try {
                        Log.d(TAG, "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, rate + "Exception, keep trying.", e);
                    }
                }
            }
        }
        return null;
    }

    /*
     * **************************
     *
     * Recorder Subclass
     *
     * **************************
     */
    private class RecordAudio extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            int sampleRate = 32000;
            int ecgSampleRate = 250;
            byte zeroPeroid = 20; // for sampleRate == 32000
            int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
            int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
            int audioSource = MediaRecorder.AudioSource.MIC;
            int lastCross = 0; // last zero cross
            int sampleCounter = 1;
            int ecgFrameSize = sampleRate / ecgSampleRate;
            short lastSample = 0;
            boolean isFirstSample = true;
            double ecgSample = 0;

            double averageSample = 0;
            double[] averageBuffer = new double[2 * ecgSampleRate];
            short averagePosition = 0;
            short ecgSample1 = 0;

            isStarted = true;

            try {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(recordingFile)));

                int bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfiguration, audioEncoding);

                AudioRecord audioRecord = new AudioRecord(
                        audioSource,
                        sampleRate,
                        channelConfiguration,
                        audioEncoding,
                        bufferSize);

                short[] buffer = new short[bufferSize];

                Thread.sleep(5000);
                audioRecord.startRecording();

                while (isStarted) {
                    int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                    if (isFirstSample && (bufferReadResult > 0)) {
                        isFirstSample = false;
                        lastSample = buffer[0];
                    }

                    for (int i = 0; i < bufferReadResult; i++) {

                        // find signal zero cross
                        if ((lastSample >= 0) && (buffer[i] < 0)) {
                            ecgSample = ecgSample + sampleCounter - lastCross - 20;
                            lastCross = sampleCounter;
                        }
                        lastSample = buffer[i];

                        // writeIntToRAFile ECG sample to file
                        if ((sampleCounter % ecgFrameSize) == 0) {

                            averageSample -= averageBuffer[averagePosition];
                            averageBuffer[averagePosition] = ecgSample;
                            averageSample += averageBuffer[averagePosition];
                            averagePosition = (short) ((averagePosition + 1) % averageBuffer.length);

                            ECGViewOld.data[ECGViewOld.viewPosition] = ecgSample - averageSample / averageBuffer.length;
                            ECGViewOld.viewPosition++;
                            if (ECGViewOld.viewPosition == ECGViewOld.data.length) {
                                ECGViewOld.viewPosition = 0;
                            }
                            ecgSample1 = (short) (Math.round(ecgSample - averageSample / averageBuffer.length));
//                            ecgSample1 = (short)(ecgSample);

                            dos.writeShort(ecgSample1);
                        }
                        sampleCounter++;
                    }
                }
                audioRecord.stop();
                dos.close();
            } catch (Throwable t) {
                Log.e("AudioRecord", "Recording Failed");
                Log.e("AudioRecord", t.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
//            statusText.setText(progress[0].toString());
        }

        protected void onPostExecute(Void result) {
            isStarted = false;
        }
    }
}
