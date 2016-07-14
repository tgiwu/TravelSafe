package com.huanqiu.travelsafe.utils;

import com.huanqiu.travelsafe.net.BackgroundCallRunnable;
import com.huanqiu.travelsafe.net.NetworkCallRunnable;

/**
 * Created by Administrator on 2016/7/13.
 */
public interface BackgroundExecutor {
    <R> void execute(NetworkCallRunnable<R> runnable);

    <R> void execute(BackgroundCallRunnable<R> runnable);
}
