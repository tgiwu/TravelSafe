package com.huanqiu.travelsafe.net;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/13.
 */
public abstract class NetworkCallRunnable<R> {
    public void onPre(){}
    public abstract Observable<R> doBackgroundCall();
    public abstract void onSuccess(R result);
    public abstract void onError(Throwable e);
    public void onFinish(){}
}
