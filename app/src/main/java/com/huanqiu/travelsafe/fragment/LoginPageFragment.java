package com.huanqiu.travelsafe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.BuildConfig;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.event.CheckInputEvent;
import com.huanqiu.travelsafe.event.DoActiveEvent;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.event.StartFragmentTransformEvent;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LoginPageFragment extends BaseStartPageFragment implements StartController.LoginUi{

    private static String PARAM_ID = "param_id";
    private byte flag = 3;

    @BindView(R.id.login_head_layout)
    RelativeLayout loginHeadLayout;
    @BindView(R.id.login_mobile_edt)
    EditText loginMobileEdt;
    @BindView(R.id.login_password_edt)
    EditText loginPasswordEdt;
    @BindView(R.id.auth_code_input)
    EditText authCodeInput;
    @BindView(R.id.send_auth_code)
    TextView sendAuthCode;
    @BindView(R.id.auth_code_layout)
    LinearLayout authCodeLayout;
    @BindView(R.id.login_forget_password_txt)
    TextView loginForgetPasswordTxt;
    @BindView(R.id.chk_show_password)
    CheckBox chkShowPassword;
    @BindView(R.id.login_btn_txt)
    TextView loginBtnTxt;
    @BindView(R.id.login_rl)
    RelativeLayout loginRl;
    @BindView(R.id.login_fragment_layout)
    LinearLayout loginFragmentLayout;
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.previous_and_login_layout)
    LinearLayout previousAndLoginLayout;

    @Inject
    RxBus rxBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
        App.getInstance().getGraph().inject(this);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static LoginPageFragment newInstance(String id) {
        LoginPageFragment fragment = new LoginPageFragment();
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

        loginHeadLayout.setBackground(getResources().getDrawable(R.drawable.login_head_bg));
        authCodeLayout.setVisibility(View.GONE);
        chkShowPassword.setVisibility(View.GONE);
        loginTitle.setText(R.string.login_title);
        loginBtnTxt.setText(R.string.login_title);
        previousAndLoginLayout.setVisibility(View.GONE);

        buildToast(R.string.login_toast);
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

    @OnClick({R.id.login_rl, R.id.login_forget_password_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget_password_txt:
                Map<String, Integer> map = new HashMap<>();
                map.put("forget", 1);
                StartFragmentTransformEvent event = new StartFragmentTransformEvent("retrieve", StartController.FRAGMENTS.RETRIEVE_ONE, map);
                event.setCallingId(this.hashCode());
                rxBus.send(event);
                break;
            case R.id.login_rl:
                Map<String, StartController.CHECK_TYPE> checkMap = new HashMap<>();
                checkMap.put("username", StartController.CHECK_TYPE.PHONE);
                checkMap.put("password", StartController.CHECK_TYPE.PASSWORD);

                CheckInputEvent checkEvent = new CheckInputEvent(this.hashCode());
                checkEvent.setTypeMap(checkMap);
                rxBus.send(checkEvent);
                break;
        }
    }

    @Override
    public void setCallbacks(StartController.StartUiCallback callbacks) {

    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void showError(int s) {
        if (BuildConfig.DEBUG)
        Log.i("zhy", "flag = " + Integer.toBinaryString(flag));
    }

    @Override
    public void doNext() {
        DoActiveEvent doActiveEvent = new DoActiveEvent(StartController.ACTIVITIES.LOGIN, this.hashCode());
        rxBus.send(doActiveEvent);
    }

    @Override
    public Map<String, String> getInputParam() {
        Map<String, String> param = new HashMap<>();
        param.put("username", loginMobileEdt.getText().toString());
        param.put("password", loginPasswordEdt.getText().toString());
        return param;
    }
}
