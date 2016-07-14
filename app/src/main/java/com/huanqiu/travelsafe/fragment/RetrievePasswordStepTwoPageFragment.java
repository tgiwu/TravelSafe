package com.huanqiu.travelsafe.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.event.StartFragmentTransformEvent;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/8.
 */
public class RetrievePasswordStepTwoPageFragment extends BaseStartPageFragment implements StartController.RetrieveUi {
    @BindView(R.id.login_head_layout)
    RelativeLayout loginHeadLayout;
    @BindView(R.id.login_mobile_edt)
    EditText loginMobileEdt;
    @BindView(R.id.login_password_edt)
    EditText loginPasswordEdt;
    @BindView(R.id.auth_code_input)
    EditText authCodeInput;
    @BindView(R.id.auth_code_layout)
    LinearLayout authCodeLayout;
    @BindView(R.id.login_forget_password_txt)
    TextView loginForgetPasswordTxt;
    @BindView(R.id.chk_show_password)
    CheckBox chkShowPassword;
    @BindView(R.id.login_btn_txt)
    TextView loginBtnTxt;
    @BindView(R.id.login_fragment_layout)
    LinearLayout loginFragmentLayout;
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindString(R.string.next_btn)
    String next;
    @BindView(R.id.previous_and_login_layout)
    LinearLayout previousAndLoginLayout;
    @BindView(R.id.login_rl)
    RelativeLayout loginLayout;

    @Inject
    RxBus rxBus;
    @BindView(R.id.login_nickname_edt)
    EditText loginNicknameEdt;
    @BindView(R.id.send_auth_code)
    TextView sendAuthCode;
    @BindView(R.id.previous_btn)
    TextView previousBtn;
    @BindView(R.id.save_and_login_btn)
    TextView saveAndLoginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        App.getInstance().getGraph().inject(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RetrievePasswordStepTwoPageFragment newInstance(String id) {
        RetrievePasswordStepTwoPageFragment fragment = new RetrievePasswordStepTwoPageFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView() {
        loginTitle.setText(R.string.title_retrieve_password);
        loginForgetPasswordTxt.setVisibility(View.GONE);
        chkShowPassword.setVisibility(View.GONE);
        loginPasswordEdt.setVisibility(View.GONE);
        loginBtnTxt.setText(next);
        loginLayout.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void setCallbacks(StartController.StartUiCallback callbacks) {

    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void showError(String s) {
        buildToast(s);
    }

    @OnClick({R.id.previous_btn, R.id.save_and_login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous_btn:
                Map<String, Integer> param = new HashMap<>();
                param.put("retrieve", 1);
                StartFragmentTransformEvent startFragmentTransformEvent = new StartFragmentTransformEvent("retrievepass", StartController.FRAGMENTS.RETRIEVE_ONE, param);
                startFragmentTransformEvent.setCallingId(this.hashCode());
                rxBus.send(startFragmentTransformEvent);
                break;
            case R.id.save_and_login_btn:
                break;
        }
    }

    @Override
    public Map<String, String> getRetrieveParam() {
        return null;
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
