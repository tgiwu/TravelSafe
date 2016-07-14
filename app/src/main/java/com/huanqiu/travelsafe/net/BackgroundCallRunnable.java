package com.huanqiu.travelsafe.net;

/**
 * Created by Administrator on 2016/7/13.
 */
public abstract class BackgroundCallRunnable<R> {
    public void preExecute(){}

    public abstract R runAsync();

    public void postExecute(R result){}
}
