package com.huanqiu.travelsafe.event;

import com.huanqiu.travelsafe.controllers.StartController;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/12.
 */
public class DoActiveEvent implements IEvent {
    private StartController.ACTIVITIES active;

    private int callingId;
    public DoActiveEvent(StartController.ACTIVITIES active, int callingId) {
        this.callingId = callingId;
        this.active = active;
    }

    public StartController.ACTIVITIES getActive() {
        return active;
    }

    public int getCallingId() {
        return callingId;
    }
}
