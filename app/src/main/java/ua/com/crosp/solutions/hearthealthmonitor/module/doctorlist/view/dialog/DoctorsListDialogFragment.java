package ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import ua.com.crosp.solutions.hearthealthmonitor.R;
import ua.com.crosp.solutions.hearthealthmonitor.base.view.dialog.BaseDialogFragment;
import ua.com.crosp.solutions.hearthealthmonitor.common.notification.ToastNotifications;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.FitIconEditText;
import ua.com.crosp.solutions.hearthealthmonitor.common.view.RecyclerViewEmptySupport;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.CollectionUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ValidationUtils;
import ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util.ViewUtils;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.presenter.DoctorListPresenterContract;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.cotntract.view.DoctorListView;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.diassembly.DoctorListComponent;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.adapter.DoctorListAdapter;
import ua.com.crosp.solutions.hearthealthmonitor.module.doctorlist.view.viewmodel.DoctorListItem;
import ua.com.crosp.solutions.hearthealthmonitor.module.usersettings.view.viewmodel.DoctorViewModel;

/**
 * Created by Alexander Molochko on 10/18/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public class DoctorsListDialogFragment extends BaseDialogFragment implements DoctorListView {
    public static final int FLIP_INTERVAL = 1000;
    public static final String ARGUMENT_SELECTED_DOCTOR_IDS = "selected_doctor_ids";
    public static final int VIEW_FLIPPER_FIRST_VIEW = 0;
    private static final int VIEW_FLIPPER_SECOND_VIEW = 1;
    private static final String DOCTORS_LINE_SEPARATOR = ",";
    //Views
    @BindView(R.id.view_flipper_doctors)
    ViewFlipper mViewFlipperDoctor;
    // Doctor list views
    @BindView(R.id.recycler_view_doctor_list)
    RecyclerViewEmptySupport mRecyclerViewDoctorList;
    @BindView(R.id.floating_button_add)
    FloatingActionButton mFloatingButtonAddDoctor;

    // Add new doctor views
    @BindView(R.id.button_create_new_doctor)
    Button mButtonAddNewDoctor;
    @BindView(R.id.button_cancel_create_doctor)
    Button mButtonCancelAddDoctor;
    @BindView(R.id.edit_text_last_name)
    FitIconEditText mEditTextLastName;
    @BindView(R.id.edit_text_first_name)
    FitIconEditText mEditTextFirstName;
    @BindView(R.id.edit_text_email)
    FitIconEditText mEditTextEmail;
    @BindView(R.id.text_view_empty_recycler_view)
    TextView mTextViewEmptyList;

    @Inject
    DoctorListPresenterContract mPresenter;
    private DoctorListAdapter mDoctorsAdapter;
    private OnDoctorSelectionListener mOnDoctorSelectionListener;
    private long[] mAlreadySelectedUserIds;

    public static DoctorsListDialogFragment newInstance(List<Long> selectedDoctorsIds) {
        DoctorsListDialogFragment frag = new DoctorsListDialogFragment();
        Bundle args = new Bundle();
        args.putLongArray(ARGUMENT_SELECTED_DOCTOR_IDS, CollectionUtils.convertLongListToPrimitiveArray(selectedDoctorsIds));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_doctor_list, container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
        this.getComponentByType(DoctorListComponent.class).inject(this);
        mPresenter.setView(this);
    }

    private void parseArguments() {
        Bundle arguments = getArguments();
        if (null != arguments) {
            mAlreadySelectedUserIds = arguments.getLongArray(ARGUMENT_SELECTED_DOCTOR_IDS);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        Dialog dialog = getDialog();
        dialog.setTitle(R.string.title_dialog_doctor_selection);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setupViews();
        notifyPresenter();
    }

    private void setupViews() {
        mViewFlipperDoctor.setFlipInterval(FLIP_INTERVAL);
    }

    @Override
    public void dismiss() {
        if (null != mOnDoctorSelectionListener) {
            mOnDoctorSelectionListener.onDoctorsSelected(mDoctorsAdapter.getDoctorsList().toSelectedViewModelList());
        }
        super.dismiss();
    }

    private void notifyPresenter() {
        mPresenter.onViewReady();
    }

    //region Listeners

    // Doctor list specific
    @OnClick(R.id.floating_button_add)
    public void onAddNewDoctorClick(View button) {
        mPresenter.onAddNewDoctorRequest();
    }

    @Override
    public void displayDoctors(DoctorListItem.List doctorEntities) {
        if (CollectionUtils.isNotEmptyLongArray(mAlreadySelectedUserIds)) {
            doctorEntities.setSelectedDoctors(mAlreadySelectedUserIds);
        }
        mRecyclerViewDoctorList.setEmptyView(mTextViewEmptyList);
        mDoctorsAdapter = new DoctorListAdapter(doctorEntities, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewDoctorList.setLayoutManager(layoutManager);
        mRecyclerViewDoctorList.setAdapter(mDoctorsAdapter);
    }

    @Override
    public void showAddNewDoctorInputView() {
        slideRightToLeft(VIEW_FLIPPER_SECOND_VIEW);
    }

    // Doctor add specific
    @OnClick(R.id.button_create_new_doctor)
    public void onAcceptAddNewDoctorClick(View button) {
        if (validateDoctorInputFields()) {
            addNewDoctor();
        }
    }

    @OnClick(R.id.button_select_doctors)
    public void onSelectDoctors(View button) {
        if (null != mOnDoctorSelectionListener) {
            mOnDoctorSelectionListener.onDoctorsSelected(mDoctorsAdapter.getDoctorsList().toSelectedViewModelList());
            ToastNotifications.showErrorMessage(getContext(), mDoctorsAdapter.getDoctorsList().toSelectedViewModelList().toSingleString(DOCTORS_LINE_SEPARATOR));
            dismiss();
        }
    }

    @OnClick(R.id.button_cancel_select_doctor)
    public void onCancelListDialog(View button) {
        clearInputFields();
        dismiss();
    }

    @OnClick(R.id.button_cancel_create_doctor)
    public void onCancelCreateNewDoctorEntry(View button) {
        clearInputFields();
        slideLeftToRight(VIEW_FLIPPER_FIRST_VIEW);
    }


    private void clearInputFields() {
        ViewUtils.clearEditTexts(mEditTextEmail, mEditTextFirstName, mEditTextLastName);
    }

    private void addNewDoctor() {
        DoctorListItem doctorListItem = new DoctorListItem();
        doctorListItem.setDoctorName(mEditTextFirstName.getText().toString(), mEditTextLastName.getText().toString());
        doctorListItem.setDoctorEmail(mEditTextEmail.getText().toString());
        mPresenter.addNewDoctor(doctorListItem)
                .subscribe(new Consumer<DoctorListItem>() {
                    @Override
                    public void accept(DoctorListItem doctorListItem) throws Exception {
                        mDoctorsAdapter.addDoctor(doctorListItem);
                        clearInputFields();
                        slideLeftToRight(VIEW_FLIPPER_FIRST_VIEW);
                        ToastNotifications.showSuccessMessage(getContext(), getString(R.string.message_doctor_added_to_list));
                    }
                });
    }


    private boolean validateDoctorInputFields() {

        if (!ValidationUtils.isValidEmail(mEditTextEmail.getText().toString())) {
            mEditTextEmail.setError(getString(R.string.error_input_email));
            return false;
        }

        if (!ValidationUtils.isValidFirstName(mEditTextFirstName.getText().toString())) {
            mEditTextFirstName.setError(getString(R.string.error_input_first_name));
            return false;
        }
        if (!ValidationUtils.isValidLastName(mEditTextLastName.getText().toString())) {
            mEditTextLastName.setError(getString(R.string.error_input_last_name));
            return false;
        }
        return true;
    }


    public OnDoctorSelectionListener getOnDoctorSelectionListener() {
        return mOnDoctorSelectionListener;
    }

    public void setOnDoctorSelectionListener(OnDoctorSelectionListener onDoctorSelectionListener) {
        mOnDoctorSelectionListener = onDoctorSelectionListener;
    }

    public interface OnDoctorSelectionListener {

        void onDoctorsSelected(DoctorViewModel.List doctorEntities);
    }

    //region View flipper animations

    public void slideRightToLeft(int index) {
        mViewFlipperDoctor.setInAnimation(getContext(), R.anim.in_from_right);
        mViewFlipperDoctor.setOutAnimation(getContext(), R.anim.out_to_left);
        mViewFlipperDoctor.setDisplayedChild(index);
    }

    public void slideLeftToRight(int index) {
        mViewFlipperDoctor.setInAnimation(getContext(), R.anim.in_from_left);
        mViewFlipperDoctor.setOutAnimation(getContext(), R.anim.out_to_right);
        mViewFlipperDoctor.setDisplayedChild(index);
    }

    public void slideTopToBottom(int index) {
        mViewFlipperDoctor.setInAnimation(inFromTopAnimation(1000));
        mViewFlipperDoctor.setOutAnimation(outToBottomAnimation(1000));
        mViewFlipperDoctor.setDisplayedChild(index);
    }

    public void slideBottomToTop(int index) {
        mViewFlipperDoctor.setInAnimation(getContext(), R.anim.in_from_bottom);
        mViewFlipperDoctor.setOutAnimation(getContext(), R.anim.out_to_top);
        mViewFlipperDoctor.setDisplayedChild(index);
    }

    public Animation inFromTopAnimation(long durationMillis) {
        Animation inFromTop = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
        );
        inFromTop.setDuration(durationMillis);
        return inFromTop;
    }

    public Animation outToBottomAnimation(long durationMillis) {
        Animation outToBottom = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f
        );
        outToBottom.setDuration(durationMillis);
        return outToBottom;
    }

    //endregion


}
