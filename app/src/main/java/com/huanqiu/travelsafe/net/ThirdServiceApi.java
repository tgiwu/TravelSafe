package com.huanqiu.travelsafe.net;

import com.huanqiu.travelsafe.model.TranslationModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface ThirdServiceApi {
    @GET("/api/trans/vip/translate")
    Observable<TranslationModel> requestTransliton(@Query("q") String q,
                                                   @Query("from") String from,
                                                   @Query("to") String to,
                                                   @Query("appid") String appid,
                                                   @Query("salt") String salt,
                                                   @Query("sign") String sign);
}
