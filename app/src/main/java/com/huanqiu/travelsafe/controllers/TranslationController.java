package com.huanqiu.travelsafe.controllers;

import android.util.Log;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.event.RequestTranslationEvent;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.fragment.TranslationFragment;
import com.huanqiu.travelsafe.model.TranslationModel;
import com.huanqiu.travelsafe.net.RequestTranslationNetworkCallRunnable;
import com.huanqiu.travelsafe.utils.BackgroundExecutor;

import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/7/19.
 */
public class TranslationController extends BaseUiController<TranslationController.TranslationUi, TranslationController.TranslationUiCallback> implements Action1<Object> {


    @Inject
    RxBus rxBus;
    @Inject
    BackgroundExecutor executor;

    private CompositeSubscription subscriptions;

    public TranslationController() {
        App.getInstance().getGraph().inject(this);
    }

    @Override
    protected void onInited() {
        super.onInited();

        subscriptions = new CompositeSubscription();

        rxBus = Preconditions.checkNotNull(rxBus, "rxBus cannot be null");
        subscriptions.add(rxBus.toObserverable()
                .subscribe(this));
    }

    @Override
    protected TranslationUiCallback createUiCallbacks(TranslationUi ui) {
        return new TranslationUiCallback() {
            @Override
            public void showToast(String s) {
                Log.e("zhy", "showToast");
            }
        };
    }

    @Override
    protected void onUiAttached(TranslationUi ui) {
        super.onUiAttached(ui);
    }

    @Override
    protected void onUiDetached(TranslationUi ui) {
        super.onUiDetached(ui);
    }

    @Override
    protected void populateUi(TranslationUi ui) {
        super.populateUi(ui);
    }

    @Override
    public void call(Object o) {
        if (o instanceof RequestTranslationEvent) {
            RequestTranslationEvent event = (RequestTranslationEvent) o;
            RequestTranslationNetworkCallRunnable runnable =
                    new RequestTranslationNetworkCallRunnable(event.getQ(), event.getFrom(), event.getTo(), event.getCallingId());
            executor.execute(runnable);
        }
    }

    public void translateCompleted(TranslationModel result, int callingId) {
        findUi(callingId).doNext(result);
    }

    public interface TranslationUi extends BaseUiController.Ui<TranslationUiCallback>{
        void showError(int error);
        void doNext(TranslationModel translationModel);
        Map<String, String> getInputParam();
    }

    public interface TranslationUiCallback{
        void showToast(String s);
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
        if (subscriptions.hasSubscriptions()) subscriptions.clear();
        subscriptions = null;
    }
}
