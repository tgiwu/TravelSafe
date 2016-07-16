package com.huanqiu.travelsafe.fragment;

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

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.event.CheckInputEvent;
import com.huanqiu.travelsafe.event.RxBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/8.
 */
public class RegistryTwoFragment extends BaseStartPageFragment implements StartController.StartUi {
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
    @BindView(R.id.login_nickname_edt)
    EditText loginNicknamEdt;

    @Inject
    RxBus rxBus;

    @Override
    protected void initView() {
        loginTitle.setText(R.string.title_registry);
        loginHeadLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.start_bg_2));
        loginBtnTxt.setText(R.string.btn_finish_registry);
        loginMobileEdt.setVisibility(View.INVISIBLE);
        loginNicknamEdt.setVisibility(View.VISIBLE);
        loginForgetPasswordTxt.setVisibility(View.GONE);
        loginPasswordEdt.setVisibility(View.INVISIBLE);
        chkShowPassword.setVisibility(View.VISIBLE);
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

    public static RegistryTwoFragment newInstance(String id) {
        RegistryTwoFragment fragment = new RegistryTwoFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.send_auth_code, R.id.save_and_login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_auth_code:
                break;
            case R.id.save_and_login_btn:
                Map<String, StartController.CHECK_TYPE> checkMap = new HashMap<>();
                checkMap.put("nickname", StartController.CHECK_TYPE.NICKNAME);
                checkMap.put("password", StartController.CHECK_TYPE.PASSWORD);
                CheckInputEvent checkEvent = new CheckInputEvent(this.hashCode());
                checkEvent.setTypeMap(checkMap);
                rxBus.send(checkEvent);
                break;
        }
    }

    @Override
    public void showError(int error) {

    }

    @Override
    public void doNext() {

    }

    @Override
    public Map<String, String> getInputParam() {
        Map<String, String> param = new HashMap<>();
        param.put("nickname", loginNicknamEdt.getText().toString());
        param.put("password", loginPasswordEdt.getText().toString());
        return param;
    }
}
