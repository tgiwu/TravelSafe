package com.huanqiu.travelsafe.event;

import com.huanqiu.travelsafe.controllers.StartController;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class CheckInputEvent implements IEvent {
    private int callingId;

    private Map<String, StartController.CHECK_TYPE> typeMap;

    public Map<String, StartController.CHECK_TYPE> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, StartController.CHECK_TYPE> typeMap) {
        this.typeMap = typeMap;
    }

    public CheckInputEvent(int callingId) {
        this.callingId = callingId;
    }

    @Override
    public int getCallingId() {
        return callingId;
    }
}
