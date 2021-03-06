package com.huanqiu.travelsafe.fragment;

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
import com.huanqiu.travelsafe.event.CheckInputEvent;
import com.huanqiu.travelsafe.event.StartFragmentTransformEvent;
import com.huanqiu.travelsafe.event.RxBus;
import com.nuance.speechkit.Audio;
import com.nuance.speechkit.Session;
import com.nuance.speechkit.Transaction;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/8.
 */
public class RegistryOneFragment extends BaseStartPageFragment implements StartController.StartUi {



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
    @BindView(R.id.previous_btn)
    TextView previousBtn;
    @BindView(R.id.save_and_login_btn)
    TextView saveAndLoginBtn;
    @BindView(R.id.previous_and_login_layout)
    LinearLayout previousAndLoginLayout;
    @BindView(R.id.login_fragment_layout)
    LinearLayout loginFragmentLayout;
    @BindView(R.id.login_title)
    TextView loginTitle;

    @Inject
    RxBus rxBus;

    @Override
    protected void initView() {
        loginTitle.setText(R.string.title_registry);
        loginHeadLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.start_bg_2));
        loginBtnTxt.setText(R.string.next_btn);
        loginForgetPasswordTxt.setVisibility(View.GONE);
        chkShowPassword.setVisibility(View.GONE);
        loginPasswordEdt.setVisibility(View.GONE);
        previousAndLoginLayout.setVisibility(View.GONE);
    }

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

    public static RegistryOneFragment newInstance(String id) {
        RegistryOneFragment fragment = new RegistryOneFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.login_rl, R.id.send_auth_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_rl:
                Map<String, StartController.CHECK_TYPE> checkMap = new HashMap<>();
                checkMap.put("username", StartController.CHECK_TYPE.PHONE);
                checkMap.put("captcha", StartController.CHECK_TYPE.AUTH);
                CheckInputEvent checkInputEvent = new CheckInputEvent(this.hashCode());
                checkInputEvent.setTypeMap(checkMap);
                rxBus.send(checkInputEvent);
                break;
            case R.id.send_auth_code:
                break;
        }
    }

    @Override
    public void showError(int error) {

    }

    @Override
    public void doNext() {
        rxBus.send(new StartFragmentTransformEvent("registry_two", StartController.FRAGMENTS.REGISTRY_TWO, null));
    }

    @Override
    public Map<String, String> getInputParam() {
        Map<String, String> param = new HashMap<>();
        param.put("username", loginMobileEdt.getText().toString());
        param.put("captcha", authCodeInput.getText().toString());
        return param;
    }


}
