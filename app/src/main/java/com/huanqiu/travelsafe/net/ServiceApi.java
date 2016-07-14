package com.huanqiu.travelsafe.net;

import com.huanqiu.travelsafe.model.RequestVerificationCodeModel;
import com.huanqiu.travelsafe.model.TokenModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface ServiceApi {

    @FormUrlEncoded
    @Headers({"Accept : application/json;charset=UTF-8"})
    @POST("/{version}/sms/captcha")
    Observable<RequestVerificationCodeModel> requestVerification(@Path("version") String version,
                                                                 @Header("User-agent") String agent,
                                                                 @Field("type") String type,
                                                                 @Field("mobile") String mobile);

    @FormUrlEncoded
    @Headers({"Accept : application/json;charset=UTF-8"})
    @POST("/{version}/users")
    Observable<RequestVerificationCodeModel> registry(@Path("version") String version,
                                                      @Header("User-Agent") String agent,
                                                     @Field("nickname") String nickname,
                                                     @Field("mobile") String mobile,
                                                     @Field("password") String password,
                                                     @Field("captcha") String captcha);

    @FormUrlEncoded
    @Headers({"Accept : application/json;charset=UTF-8"})
    @POST("/{version}/users/password")
    Observable<RequestVerificationCodeModel> retrievePass(@Path("version") String version,
                                                            @Header("User-Agent") String agent,
                                                            @Field("mobile") String mobile,
                                                            @Field("password") String newPassword,
                                                            @Field("captcha") String captcha);

    @FormUrlEncoded
    @Headers({"Accept : application/json;charset=UTF-8"})
    @POST("/oauth/token")
    Observable<TokenModel> fetchTkoen(@Header("Authorization") String auth,
                                      @Field("username") String username,
                                      @Field("password") String password,
                                      @Field("grant_type") String type);

    @FormUrlEncoded
    @Headers({"Accept : application/json;charset=UTF-8"})
    @POST("/oauth/token")
    Observable<TokenModel> refreshTkoen(@Header("Authorization") String auth,
                                      @Field("refresh_token") String refreshToken,
                                      @Field("grant_type") String type);

    @FormUrlEncoded
    @Headers({"Accept : application/json;charset=UTF-8"})
    @POST("/oauth/logout")
    Observable<String> logout(@Header("Authorization") String accessToken);
}
