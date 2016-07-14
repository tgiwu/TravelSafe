package com.huanqiu.travelsafe.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/1.
 */
public class RequestVerificationCodeModel {
    @SerializedName("code")public String code;

    @Override
    public String toString() {
        return "RequestVerificationCodeModel{" +
                "code='" + code + '\'' +
                '}';
    }
}
