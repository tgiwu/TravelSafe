package com.huanqiu.travelsafe.event;

import android.support.v4.app.Fragment;

import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.fragment.StartPageFragment;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/7.
 */
public class StartFragmentTransformEvent implements IEvent {
    private final StartController.FRAGMENTS frament;
    private final String targetId;

    private Map<String, Integer> param;

    private int callingId;

    public int getCallingId() {
        return callingId;
    }

    public void setCallingId(int callingId) {
        this.callingId = callingId;
    }

    public Map<String, Integer> getParam() {
        return param;
    }

    public StartFragmentTransformEvent(String targetId, StartController.FRAGMENTS fragment, Map param) {
        this.targetId = targetId;
        this.frament = fragment;
        this.param = param;
    }

    public StartController.FRAGMENTS getFramentClass() {
        return frament;
    }

    public String getTargetId() {
        return targetId;
    }
}
