package com.huanqiu.travelsafe.controllers;

import android.util.Log;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.event.CheckInputEvent;
import com.huanqiu.travelsafe.event.DoActiveEvent;
import com.huanqiu.travelsafe.event.IEvent;
import com.huanqiu.travelsafe.event.StartFragmentTransformEvent;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.utils.Utils;

import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/7/7.
 */
public class StartController extends BaseUiController<StartController.StartUi, StartController.StartUiCallback> implements Action1<Object>{

    public enum FRAGMENTS {
        START, LOGIN, RETRIEVE_ONE, RETRIEVE_TWO, REGISTRY_ONE, REGISTRY_TWO
    }

    public enum ACTIVITIES {
        LOGIN, RESET_PASS, REGISTRY
    }

    public enum CHECK_TYPE {
        PHONE, PASSWORD
    }
    @Inject
    RxBus rxBus;

    public StartController() {
        App.getInstance().getGraph().inject(this);
    }

    @Override
    protected void onInited() {
        super.onInited();
        setSubscriptions(new CompositeSubscription());
        rxBus = Preconditions.checkNotNull(rxBus, "rxBus cannot be null");
        getSubscriptions().add(rxBus.toObserverable()
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
            }
        } else if (event instanceof CheckInputEvent) {
            switch (((CheckInputEvent) event).getType()) {
                case PHONE:
                    if (!Utils.checkPhoneNumber(((CheckInputEvent) event).getInput())) {
                        findUi(((CheckInputEvent) event).getCallingId()).showError("phone");
                    } else {
                        findUi(((CheckInputEvent) event).getCallingId()).showError("phone_right");
                    }
                    break;
                case PASSWORD:
                    if (!Utils.checkPassword(((CheckInputEvent) event).getInput())) {
                        findUi(((CheckInputEvent) event).getCallingId()).showError("password");
                    } else {
                        findUi(((CheckInputEvent) event).getCallingId()).showError("password_right");
                    }
                    break;
            }
        }
    }

    private void populateLoginUi(LoginUi ui) {

    }

    private void populateRetrieveUi(RetrieveUi ui) {

    }

    private void populateRegistryUi(RegistryUi ui) {

    }

    public interface StartUi extends BaseUiController.Ui<StartUiCallback> {
        void showError(String s);
    }

    public interface StartUiCallback{
        void showToast(String s);
    }

    public interface LoginUi extends StartUi{
        Map<String, String> getLoginParam();
    }

    public interface RetrieveUi extends StartUi{
        Map<String, String> getRetrieveParam();
    }

    public interface RegistryUi extends StartUi {
        Map<String, String> getRegistryParam();
    }
}
