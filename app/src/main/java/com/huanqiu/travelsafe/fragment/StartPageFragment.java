package com.huanqiu.travelsafe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.event.StartFragmentTransformEvent;
import com.huanqiu.travelsafe.event.RxBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/6.
 */
public class StartPageFragment extends BaseStartPageFragment {

    private static String PARAM_ID = "param_id";
    @BindView(R.id.ignore_rl)
    RelativeLayout ignoreRl;
    @BindView(R.id.start_login_rl)
    RelativeLayout startLoginRl;
    @BindView(R.id.start_registry_rl)
    RelativeLayout startRegistryRl;

    @Inject
    RxBus rxBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_page_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.from(getActivity()).getGraph().inject(this);

        rxBus = Preconditions.checkNotNull(rxBus, "rxBus cannot be null");
    }

    public static StartPageFragment newInstance(String id) {
        StartPageFragment fragment = new StartPageFragment();
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

    @OnClick({R.id.ignore_rl, R.id.start_login_rl, R.id.start_registry_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ignore_rl:
                break;
            case R.id.start_login_rl:
                rxBus.send(new StartFragmentTransformEvent("login", StartController.FRAGMENTS.LOGIN, null));
                break;
            case R.id.start_registry_rl:
                rxBus.send(new StartFragmentTransformEvent("registryOne", StartController.FRAGMENTS.REGISTRY_ONE, null));
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}