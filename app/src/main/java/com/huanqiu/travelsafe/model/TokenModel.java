package com.huanqiu.travelsafe.model;

/**
 * Created by Administrator on 2016/7/13.
 */
public class TokenModel {

    /**
     * access_token : 8ab07f3a-f1ae-4c58-a3ad-19247f757f08
     * token_type : bearer
     * refresh_token : af981dce-46a4-4a02-8cb4-270e86908b1c
     * expires_in : 43186
     * scope : read write
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
