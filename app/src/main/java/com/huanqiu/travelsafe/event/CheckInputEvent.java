package com.huanqiu.travelsafe.event;

import com.huanqiu.travelsafe.controllers.StartController;

/**
 * Created by Administrator on 2016/7/14.
 */
public class CheckInputEvent implements IEvent {
    private int callingId;

    private StartController.CHECK_TYPE type;
    private String input;

    public StartController.CHECK_TYPE getType() {
        return type;
    }

    public void setType(StartController.CHECK_TYPE type) {
        this.type = type;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public CheckInputEvent(int callingId) {
        this.callingId = callingId;
    }

    @Override
    public int getCallingId() {
        return callingId;
    }
}
