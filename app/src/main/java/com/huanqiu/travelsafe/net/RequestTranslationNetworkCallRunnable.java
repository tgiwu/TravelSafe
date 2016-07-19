package com.huanqiu.travelsafe.net;

import android.util.Log;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.model.TranslationModel;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RequestTranslationNetworkCallRunnable extends NetworkCallRunnable<TranslationModel> {

    String q, from, to;
    private final int mCallingId;

    public RequestTranslationNetworkCallRunnable(String q, String from, String to, int callingId) {
        this.q = q;
        this.from =from;
        this.to = to;
        this.mCallingId = callingId;
    }

    @Override
    public Observable<TranslationModel> doBackgroundCall() {
        return TravelSafeApi.requestTranslation(q, from, to);
    }

    @Override
    public void onSuccess(TranslationModel result) {
        App.getInstance().getMainController().getTranslationController().translateCompleted(result, mCallingId);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
