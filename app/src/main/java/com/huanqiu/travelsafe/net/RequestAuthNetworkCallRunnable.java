package com.huanqiu.travelsafe.net;

import com.huanqiu.travelsafe.model.RequestVerificationCodeModel;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/16.
 */
public class RequestAuthNetworkCallRunnable extends NetworkCallRunnable<String> {
    private String type;

    private String phoneNumber;

    public RequestAuthNetworkCallRunnable(String type, String phoneNumber) {
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Observable<String> doBackgroundCall() {
        return TravelSafeApi.requestAuthCode(type, phoneNumber);
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
