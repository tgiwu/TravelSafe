package com.huanqiu.travelsafe.event;

/**
 * Created by Administrator on 2016/7/19.
 */
public class RequestTranslationEvent implements IEvent {

    private int callingId;
    private String q, from, to;

    public RequestTranslationEvent(String q, String from, String to, int callingId) {
        this.q = q;
        this.from = from;
        this.to = to;
        this.callingId = callingId;
    }

    public String getQ() {
        return q;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public int getCallingId() {
        return callingId;
    }
}
