package com.huanqiu.travelsafe.net;

import android.util.Log;

import com.huanqiu.travelsafe.BuildConfig;
import com.huanqiu.travelsafe.model.RequestVerificationCodeModel;
import com.huanqiu.travelsafe.model.TokenModel;
import com.huanqiu.travelsafe.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/7/4.
 */
public class TravelSafeApi {

    public static Observable<String> requestVerification(String type, String phoneNumber) {
        Observable<RequestVerificationCodeModel> call = HttpClientManager.mApiService.requestVerification(BuildConfig.SERVER_VERSION, Utils.buildUserAgent(), type, phoneNumber);
        return call.subscribeOn(Schedulers.newThread())
                .throttleFirst(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new FuncGetCode());
        }

    public static Observable<String> registry(String name, String mobile, String password, String captcha) {
        Observable<RequestVerificationCodeModel> observable = HttpClientManager.mApiService.registry(BuildConfig.SERVER_VERSION,
                Utils.buildUserAgent(), name, mobile, password, captcha);
        return observable.subscribeOn(Schedulers.newThread())
                .throttleFirst(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new FuncGetCode());
    }

    public static Observable<String> retrievePass(String mobile, String newPassword, String captcha) {
        Observable<RequestVerificationCodeModel> observable = HttpClientManager.mApiService.retrievePass(BuildConfig.SERVER_VERSION,Utils.buildUserAgent(),mobile,newPassword, captcha);
        return observable.subscribeOn(Schedulers.newThread())
                .throttleFirst(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new FuncGetCode());
    }

    public static Observable<TokenModel> fetchToken(String auth, String username, String password, String type) {
        Observable<TokenModel> observable = HttpClientManager.mApiService.fetchTkoen(auth, username, password, type);
        return observable.subscribeOn(Schedulers.newThread())
                .throttleFirst(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<TokenModel> refreshToken(String auth, String refreshToken, String type) {
        Observable<TokenModel> observable = HttpClientManager.mApiService.refreshTkoen(auth, refreshToken, type);
        return observable.subscribeOn(Schedulers.newThread())
                .throttleFirst(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<String> logout(String auth) {
        Observable<String> observable = HttpClientManager.mApiService.logout(auth);
        return observable.subscribeOn(Schedulers.newThread())
                .throttleFirst(10 * 1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class FuncGetCode implements Func1<RequestVerificationCodeModel, String> {

        @Override
        public String call(RequestVerificationCodeModel requestVerificationCodeModel) {
            return requestVerificationCodeModel.code;
        }
    }
}
