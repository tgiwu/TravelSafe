package com.huanqiu.travelsafe.net;

import com.huanqiu.travelsafe.model.TokenModel;

import rx.Observable;

/**
 * Created by Administrator on 2016/7/13.
 */
public class TokenNetworkCallRunnable extends NetworkCallRunnable<TokenModel> {
    private final int mCallingId;
    private final String auth,username,password,type;

    public TokenNetworkCallRunnable(int callingId, String auth, String username, String password, String type) {
        this.mCallingId = callingId;
        this.auth = auth;
        this.username = username;
        this.password = password;
        this.type = type;
    }
    @Override
    public Observable<TokenModel> doBackgroundCall() {

        return TravelSafeApi.fetchToken(auth, username, password, type);
    }

    @Override
    public void onSuccess(TokenModel result) {

    }

    @Override
    public void onError(Throwable e) {

    }

}
