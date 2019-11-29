package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AndroidIOHandler implements FileIOHandler {
    Context mContext;
    AssetManager mAssets;
    String mExternalStoragePath;

    public AndroidIOHandler(Context context) {
        this.mContext = context;
        this.mAssets = context.getAssets();
        this.mExternalStoragePath = context.getExternalFilesDir(null)
                .getAbsolutePath() + File.separator;
    }

    public InputStream readAsset(String fileName) throws IOException {
        return mAssets.open(fileName);
    }

    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(mExternalStoragePath + fileName);
    }

    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(mExternalStoragePath + fileName);
    }

    public SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}
