package com.ddmeng.todorealm.home.add.list;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.utils.LogUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;


public class AddListDialogFragment extends BottomSheetDialogFragment implements AddListContract.View {

    public static final String TAG = "AddListDialogFragment";
    @BindView(R.id.input_layout)
    TextInputLayout inputLayout;
    @BindView(R.id.input_edit_text)
    TextInputEditText editText;

    private AddListContract.Presenter presenter;

    private BottomSheetBehavior.BottomSheetCallback bottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN: {
                    LogUtils.d("Bottom sheet hidden to dismiss");
                    dismiss();
                    break;
                }
                case BottomSheetBehavior.STATE_EXPANDED: {
                    LogUtils.d("expanded");
                    break;
                }
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    LogUtils.d("collapsed");
                    break;
                }

            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LogUtils.footPrint();
        return super.onCreateDialog(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        LogUtils.footPrint();
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_add_list_dialog, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            BottomSheetBehavior bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            bottomSheetBehavior.setBottomSheetCallback(bottomSheetBehaviorCallback);
        }

        ButterKnife.bind(this, contentView);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        presenter = new AddListPresenter(TodoRepository.getInstance());
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint(); // if onCreateView() return null, onViewCreated() will not be invoked
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.done_button)
    void onDoneButtonClicked() {
        final String currentInput = editText.getText().toString();
        presenter.onDoneButtonClick(currentInput);
    }

    @OnClick(R.id.cancel_button)
    void onCancelButtonClicked() {
        presenter.onCancelButtonClick();
    }

    @Override
    public void exit() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @OnTextChanged(R.id.input_edit_text)
    void onInputTextChanged(CharSequence text, int start, int count, int after) {
        if (text.length() == 0) {
            inputLayout.setError(getString(R.string.error_hint_title_can_not_be_empty));
            inputLayout.setErrorEnabled(true);
        } else {
            inputLayout.setErrorEnabled(false);
        }
    }


    @OnEditorAction(R.id.input_edit_text)
    boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            presenter.onDoneButtonClick(editText.getText().toString());
            return true;
        }
        return false;
    }
}
