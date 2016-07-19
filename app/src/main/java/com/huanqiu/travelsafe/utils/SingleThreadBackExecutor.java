package com.huanqiu.travelsafe.utils;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2016/7/19.
 */
public class SingleThreadBackExecutor extends TravelSafeBackgroundExecutor {
    public SingleThreadBackExecutor(ExecutorService executorService) {
        super(executorService);
    }
}
