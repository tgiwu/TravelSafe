package com.huanqiu.travelsafe.controllers;

import android.content.Intent;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.display.Display;
import com.huanqiu.travelsafe.event.RxBus;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/7/6.
 */
public abstract class BaseController {
    private Display mDisplay;
    private boolean mInited;
    @Inject
    RxBus rxBus;

    public CompositeSubscription getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(CompositeSubscription subscriptions) {
        this.subscriptions = subscriptions;
    }

    private CompositeSubscription subscriptions;

    public final void init() {
        Preconditions.checkState(mInited == false, "Already inited");
        mInited = true;
        onInited();
    }

    public final void suspend() {
        Preconditions.checkState(mInited == true, "Not inited");
        onSuspended();
        mInited = false;
    }

    public final boolean ismInited() {
        return mInited;
    }

    protected void onInited() {}

    protected void onSuspended() {}

    public boolean handleIntent(Intent intent) {
        return false;
    }

    protected void setDisplay(Display display) {
        mDisplay = display;
    }

    protected final Display getDisplay() {
        return mDisplay;
    }

    protected final void assertInited() {
        Preconditions.checkState(mInited, "Must be init'ed to perform action");
    }

}
