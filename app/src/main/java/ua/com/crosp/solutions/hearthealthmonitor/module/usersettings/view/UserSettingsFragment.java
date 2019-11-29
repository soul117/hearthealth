package ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.rm.rmswitch.RMSwitch;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.Gender;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.BaseFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.FitIconEditText;
import ua.com.crosp.solutions.hearthealthmonitor.di.named.NamedConstants;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.DateUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.Logger;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.StringUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ValidationUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.dialog.DoctorsListDialogFragment;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.presenter.UserSettingsPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.cotntract.view.UserSettingsViewContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.diassembly.UserSettingsComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.DoctorViewModel;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.UserSettingsViewModel;


/**
 * Created by Alexander Molochko on 2/19/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class UserSettingsFragment extends BaseFragment implements UserSettingsViewContract, AppBarLayout.OnOffsetChangedListener, DatePickerDialog.OnDateSetListener, Toolbar.OnMenuItemClickListener {
    public static final String DOCTORS_LINE_SEPARATOR = "\n";
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    public static final String DIALOG_DATE_OF_BIRTH = "DateOfBirthPickerDialog";
    private static final String FORMAT_USER_DETAILS = "%s | %s | %s y.o.";
    public static final String TAG_DIALOG_DOCTOR_LIST = "tag.dialog.doctor_list";

    // Views
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.linear_layout_title_container)
    LinearLayout mTitleContainerLinearLayout;
    // Text Views
    @BindView(R.id.text_view_user_details)
    TextView mTextViewUserDetails;
    @BindView(R.id.text_view_title_toolbar)
    TextView mTextViewTitleToolbar;
    @BindView(R.id.text_view_user_name)
    TextView mUserNameTextView;
    @BindView(R.id.rm_switch_gender)
    RMSwitch mRMSwitchGender;
    @BindView(R.id.text_view_selected_gender)
    TextView mSelectedGenderTextView;
    // Edit Texts
    @BindView(R.id.edit_text_date_of_birth)
    FitIconEditText mEditTextDateOfBirth;
    @BindView(R.id.edit_text_about)
    FitIconEditText mEditTextAbout;
    @BindView(R.id.edit_text_email)
    FitIconEditText mEditTextEmail;
    @BindView(R.id.edit_text_selected_doctor)
    FitIconEditText mEditTextSelectedDoctors;
    @BindView(R.id.edit_text_phone)
    FitIconEditText mEditTextPhone;
    @BindView(R.id.edit_text_last_name)
    FitIconEditText mEditTextLastName;
    @BindView(R.id.edit_text_first_name)
    FitIconEditText mEditTextFirstName;
    @BindView(R.id.edit_text_personal_id)
    FitIconEditText mEditTextPersonalId;
    // Component & modular dependencies
    @Inject
    UserSettingsPresenterContract mPresenter;
    @Inject
    @Named(NamedConstants.Fragment.DEFAULT_FRAGMENT_MANAGER)
    FragmentManager mFragmentManager;
    //region Variables
    private UserSettingsViewModel mUserSettings;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    //endregion

    //region Inline class implementations
    private AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent> mFilterEmptyStringsPredicate = new AppendOnlyLinkedArrayList.NonThrowingPredicate<TextViewTextChangeEvent>() {
        @Override
        public boolean test(TextViewTextChangeEvent textViewTextChangeEvent) {
            return !StringUtils.isBlank(textViewTextChangeEvent.text().toString());
        }
    };
    private Date mSelectedBirthDate;
    //endregion

    public UserSettingsFragment() {
    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_settings_user;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Injecting dependencies
        this.getComponentByType(UserSettingsComponent.class).inject(this);
        // Set presenter this as the presenter view
        mPresenter.setView(this);

        Logger.error("ON CREATE USER SETTINGS FRAGMENT");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    //region Lifecycle methods

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.onViewReady();
        setListeners();
        setupToolbar();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_settings: {
                saveUserSettings();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        setAppBarLayout();
        setInputListeners();
    }

    private void setInputListeners() {
        mEditTextDateOfBirth.setInputType(InputType.TYPE_NULL);
        mRMSwitchGender.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                String genderString = isChecked ? getString(R.string.switch_male) : getString(R.string.switch_female);
                mSelectedGenderTextView.setText(genderString);
                mUserSettings.setGender(genderString);
                displayUserHeaderInfo();
            }
        });
    }

    private void setAppBarLayout() {
        mAppBarLayout.addOnOffsetChangedListener(this);
        startAlphaAnimation(mTextViewTitleToolbar, 0, View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setupToolbar() {
        mToolbar.inflateMenu(R.menu.menu_user_settings);
        mToolbar.setTitle("");
        mToolbar.setOnMenuItemClickListener(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        destroyViews();
        super.onDestroy();
    }

    private void destroyViews() {
        if (mRMSwitchGender != null) {
            mRMSwitchGender.setLayoutTransition(null);
            mRMSwitchGender = null;
        }
    }

    //endregion
    //region Listeners
    @OnTextChanged(value = R.id.edit_text_first_name,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onFirstNameChange(Editable editText) {
        mUserSettings.setFirstName(editText.toString());
        displayUserHeaderInfo();
    }

    @OnTextChanged(value = R.id.edit_text_last_name,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onLastNameChange(Editable editText) {
        mUserSettings.setLastName(editText.toString());
        displayUserHeaderInfo();
    }

    @OnTextChanged(value = R.id.edit_text_personal_id,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onPersonalIdChange(Editable editText) {
        try {
            mUserSettings.setUserPersonalId(Long.parseLong(editText.toString()));
            displayUserHeaderInfo();
        } catch (Exception ex) {

        }
    }


    @OnClick(R.id.edit_text_date_of_birth)
    void onDateOfBirthInput(View editTextView) {
        Date selectedDate = mUserSettings.getDateOfBirth();
        showBirthDatePickDialog(selectedDate);
    }


    @OnClick(R.id.edit_text_selected_doctor)
    void onDoctorSelectionClicked(View editTextView) {
        showDoctorsList(mUserSettings.getDoctors());
    }

    private void saveUserSettings() {
        if (isValidInput()) {
            collectInputDate();
            mPresenter.saveUserSettings(mUserSettings);
        }
    }

    private boolean isValidInput() {
        if (!ValidationUtils.isValidFirstName(mEditTextFirstName.getText().toString())) {
            mEditTextFirstName.setError(getString(R.string.error_input_first_name));
            return false;
        }
        if (!ValidationUtils.isValidLastName(mEditTextLastName.getText().toString())) {
            mEditTextLastName.setError(getString(R.string.error_input_last_name));
            return false;
        }
        if (!ValidationUtils.isValidPhoneNumber(mEditTextPhone.getText().toString())) {
            mEditTextPhone.setError(getString(R.string.error_input_phone));
            return false;
        }
        if (!ValidationUtils.isValidEmail(mEditTextEmail.getText().toString())) {
            mEditTextEmail.setError(getString(R.string.error_input_email));
            return false;
        }
        if (ValidationUtils.isEmpty(mEditTextDateOfBirth.getText().toString())) {
            mEditTextDateOfBirth.setError(getString(R.string.error_input_date_of_birth));
            return false;
        }
        if (ValidationUtils.isEmpty(mEditTextPersonalId.getText().toString())) {
            mEditTextPersonalId.setError(getString(R.string.error_input_personal_id));
            return false;
        }
        if (ValidationUtils.isEmpty(mEditTextSelectedDoctors.getText().toString())) {
            mEditTextSelectedDoctors.setError(getString(R.string.error_input_doctor_at_least_one));
            return false;
        }
        return true;
    }

    private void collectInputDate() {
        mUserSettings.setFullName(mEditTextFirstName.getText().toString(), mEditTextLastName.getText().toString());
        mUserSettings.setEmail(mEditTextEmail.getText().toString());
        mUserSettings.setDateOfBirth(mSelectedBirthDate);
        mUserSettings.setPhone(mEditTextPhone.getText().toString());
        mUserSettings.setAbout(mEditTextAbout.getText().toString());
        mUserSettings.setGender(mRMSwitchGender.isChecked() ? Gender.GENDER_MALE : Gender.GENDER_FEMALE);
        mUserSettings.setUserPersonalId(Long.parseLong(mEditTextPersonalId.getText().toString()));

    }


    private void showDoctorsList(final DoctorViewModel.List userDoctors) {
        DoctorsListDialogFragment doctorListDialog = DoctorsListDialogFragment.newInstance(userDoctors.toIdList());
        doctorListDialog.setOnDoctorSelectionListener((DoctorsListDialogFragment.OnDoctorSelectionListener) mPresenter);
        doctorListDialog.show(mFragmentManager, TAG_DIALOG_DOCTOR_LIST);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (DIALOG_DATE_OF_BIRTH.contentEquals(view.getTag())) {
            mSelectedBirthDate = DateUtils.createDateFromValues(year, monthOfYear, dayOfMonth);
            if (DateUtils.dateInFuture(mSelectedBirthDate)) {
                ToastNotifications.showErrorMessage(getContext(), getString(R.string.error_message_birth_date_in_future));
            } else {
                setDateOfBirthText(mSelectedBirthDate);
                mUserSettings.setDateOfBirth(mSelectedBirthDate);
                displayUserHeaderInfo();
            }
        }
    }

    private void showBirthDatePickDialog(Date previousDateOfBirth) {
        Calendar now = Calendar.getInstance();
        now.setTime(previousDateOfBirth);
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.showYearPickerFirst(true);
        dpd.show(getActivity().getFragmentManager(), DIALOG_DATE_OF_BIRTH);

    }
    //endregion

    //region View contract implementation
    private void displayUserHeaderInfo() {
        String userName = mUserSettings.getFullName();
        this.mUserNameTextView.setText(mUserSettings.getFullName());
        this.mTextViewTitleToolbar.setText(userName);
        this.mTextViewUserDetails.setText(formatUserDetails());
    }

    private String formatUserDetails() {
        return String.format(FORMAT_USER_DETAILS,
                mUserSettings.getUserPersonalId().toString(),
                mUserSettings.getGender().contentEquals(Gender.GENDER_MALE) ? getString(R.string.male) : getString(R.string.female),
                DateUtils.calculateAge(mUserSettings.getDateOfBirth())
        );
    }

    @Override
    public void displayExistingUserSettings(UserSettingsViewModel userEntity) {
        mUserSettings = userEntity;
        mSelectedBirthDate = mUserSettings.getDateOfBirth();
        mEditTextEmail.setText(mUserSettings.getEmail());
        mEditTextFirstName.setText(mUserSettings.getFirstName());
        mEditTextLastName.setText(mUserSettings.getLastName());
        mEditTextAbout.setText(mUserSettings.getAbout());
        mEditTextPhone.setText(mUserSettings.getPhone());
        mEditTextDateOfBirth.setText(DateUtils.formatDate(mUserSettings.getDateOfBirth()));
        mEditTextPersonalId.setText(String.valueOf(mUserSettings.getUserPersonalId()));
        mEditTextSelectedDoctors.setText(mUserSettings.getDoctors().toSingleString(DOCTORS_LINE_SEPARATOR));
        mRMSwitchGender.setChecked(mUserSettings.getGender().contentEquals(Gender.GENDER_MALE));
    }

    @Override
    public void showErrorMessage(String message) {
        ToastNotifications.showErrorMessage(getContext(), message);
    }

    @Override
    public void updateSelectedDoctorsInfo(DoctorViewModel.List doctorEntities) {
        if (!doctorEntities.isEmpty()) {
            mUserSettings.setDoctors(doctorEntities);
            mEditTextSelectedDoctors.setText(doctorEntities.toSingleString(DOCTORS_LINE_SEPARATOR));
            mEditTextSelectedDoctors.setError(null);
        }
    }

    @Override
    public void showNotificationAndDestoryViews() {
        destroyViews();
        ToastNotifications.showSuccessMessage(getContext(), getString(R.string.message_user_settings_saved));
    }


    //================================================================================
    // Coordinator layout methods
    //================================================================================

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    //region Appbar methods
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTextViewTitleToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTextViewTitleToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainerLinearLayout, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainerLinearLayout, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    //endregion
    private void setDateOfBirthText(Date dateOfBirth) {
        mEditTextDateOfBirth.setText(DateUtils.formatDate(dateOfBirth));
    }


    // On Birth Date Selected

}
