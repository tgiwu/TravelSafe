package com.huanqiu.travelsafe.controllers;

import android.text.TextUtils;
import android.util.Log;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.event.CheckInputEvent;
import com.huanqiu.travelsafe.event.DoActiveEvent;
import com.huanqiu.travelsafe.event.IEvent;
import com.huanqiu.travelsafe.event.StartFragmentTransformEvent;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.net.RequestAuthNetworkCallRunnable;
import com.huanqiu.travelsafe.utils.BackgroundExecutor;
import com.huanqiu.travelsafe.utils.Utils;

import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/7/7.
 */
public class StartController extends BaseUiController<StartController.StartUi, StartController.StartUiCallback> implements Action1<Object>{

    public enum FRAGMENTS {
        START,
        LOGIN,
        RETRIEVE_ONE,
        RETRIEVE_TWO,
        REGISTRY_ONE,
        REGISTRY_TWO
    }

    public enum ACTIVITIES {
        LOGIN,
        RESET_PASS,
        REGISTRY,
        REQUEST_AUTH
    }

    public enum CHECK_TYPE {
        PHONE,       //0x00000001
        PASSWORD, //0x00000010
        AUTH,         //0x00000100
        NICKNAME   //0x00001000
    }
    @Inject
    RxBus rxBus;
    @Inject
    BackgroundExecutor executor;

    CompositeSubscription compositeSubscription;

    public StartController() {
        App.getInstance().getGraph().inject(this);
    }

    @Override
    protected void onInited() {
        super.onInited();
        compositeSubscription = new CompositeSubscription();
        rxBus = Preconditions.checkNotNull(rxBus, "rxBus cannot be null");
        compositeSubscription.add(rxBus.toObserverable()
                .subscribe(this));
    }

    @Override
    protected StartUiCallback createUiCallbacks(StartUi ui) {
        return new StartUiCallback() {
            @Override
            public void showToast(String s) {
                Log.e("zhy", "showToast");
            }
        };
    }

    @Override
    protected void onUiAttached(StartUi ui) {
        super.onUiAttached(ui);
    }

    @Override
    protected void onUiDetached(StartUi ui) {
        super.onUiDetached(ui);

    }

    @Override
    protected void populateUi(StartUi ui) {
        super.populateUi(ui);
        if (ui instanceof LoginUi) {
            populateLoginUi((LoginUi) ui);
        } else if (ui instanceof RetrieveUi) {
            populateRetrieveUi((RetrieveUi) ui);
        } else if (ui instanceof RegistryUi) {
            populateRegistryUi((RegistryUi) ui);
        }
    }

    @Override
    public void call(Object event) {
        if (event instanceof StartFragmentTransformEvent) {
            switch (((StartFragmentTransformEvent) event).getFramentClass()) {
                case START:
                    break;
                case LOGIN:
                    getDisplay().showLoginFragment();
                    break;
                case RETRIEVE_ONE:
                    boolean isFromForget = false;
                    if (null != ((StartFragmentTransformEvent) event).getParam() &&
                            ((StartFragmentTransformEvent) event).getParam().containsKey("retrieve")) {
                        isFromForget = ((StartFragmentTransformEvent) event).getParam().get("retrieve") == 1;
                    }
                    if (isFromForget) {
                        populateUiFromEvent((StartFragmentTransformEvent) event);
                        getDisplay().closeCurrentFragment();
                    } else {
                        getDisplay().showRetrievePassOneFragment();
                    }
                    break;
                case RETRIEVE_TWO:
                    getDisplay().showRetrievePassTwoFragment();
                    break;
                case REGISTRY_ONE:
                    getDisplay().showRegistryOneFragment();
                    break;
                case REGISTRY_TWO:
                    getDisplay().showRegistryTwoFragment();
                    break;
            }
        } else if (event instanceof DoActiveEvent) {
            switch (((DoActiveEvent) event).getActive()) {
                case LOGIN:
                case RESET_PASS:
                case REGISTRY:
                    populateUiFromEvent(((DoActiveEvent) event));
                    break;
                case REQUEST_AUTH:
                    requestAuthCode((DoActiveEvent) event);
                    break;
            }
        } else if (event instanceof CheckInputEvent) {
            checkInput((CheckInputEvent) event);
        }
    }

    private void checkInput(CheckInputEvent event) {
        byte relust = 0;
        StartUi ui = findUi(event.getCallingId());
        Map<String, String> param = ui.getInputParam();
        for (Map.Entry<String, CHECK_TYPE> entry : event.getTypeMap().entrySet()) {
            switch (entry.getValue()) {
                case PHONE:
                    if (!Utils.checkPhoneNumber(param.get(entry.getKey()))) {
                        relust |= 0x00000001;
                    }
                    break;
                case PASSWORD:
                    if (!Utils.checkPassword(param.get(entry.getKey()))) {
                        relust |= 0x00000010;
                    }
                    break;
                case AUTH:
                    if (!Utils.checkAuth(param.get(entry.getKey()))) {
                        relust |= 0x00000100;
                    }
                    break;
                case NICKNAME:
                    if (!Utils.checkNickname(param.get(entry.getKey()))) {
                        relust |= 0x00001000;
                    }

            }
            if (entry.getKey().endsWith("confirm")) {
                String keyConfirm = entry.getKey();
                String key = keyConfirm.substring(0, keyConfirm.length() - "_confirm".length());
                if (TextUtils.isEmpty(param.get(keyConfirm)) || TextUtils.isEmpty(param.get(key))) {
                    if (!param.get(keyConfirm).equals(param.get(key)))
                        relust |= 0x00001000;
                }
            }
        }

        if (0 == relust) {
            ui.doNext();
        } else {
            ui.showError(relust);
        }
    }

    private void populateLoginUi(LoginUi ui) {

    }

    private void populateRetrieveUi(RetrieveUi ui) {

    }

    private void populateRegistryUi(RegistryUi ui) {

    }

    private void requestAuthCode(DoActiveEvent event) {
        StartUi ui = findUi(event.getCallingId());
        if (null == ui.getInputParam().get("username") && Utils.checkPhoneNumber(ui.getInputParam().get("username"))) {
            if (ui instanceof RetrieveUi) {
                executor.execute(new RequestAuthNetworkCallRunnable("forget", ui.getInputParam().get("username")));
            } else if (ui instanceof RegistryUi) {
                executor.execute(new RequestAuthNetworkCallRunnable("registry", ui.getInputParam().get("username")));
            }
        } else {
            ui.showError(0x00000001);
        }
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear();
        compositeSubscription = null;
    }

    public interface StartUi extends BaseUiController.Ui<StartUiCallback> {
        void showError(int error);
        void doNext();
        Map<String, String> getInputParam();
    }

    public interface StartUiCallback{
        void showToast(String s);
    }

    public interface LoginUi extends StartUi{
//        Map<String, String> getLoginParam();
    }

    public interface RetrieveUi extends StartUi{
//        Map<String, String> getRetrieveParam();
    }

    public interface RegistryUi extends StartUi {
//        Map<String, String> getRegistryParam();
    }
}
