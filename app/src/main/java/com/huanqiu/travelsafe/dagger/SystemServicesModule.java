package com.huanqiu.travelsafe.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.utils.AppConfig;
import com.huanqiu.travelsafe.utils.AppManager;
import com.huanqiu.travelsafe.utils.BackgroundExecutor;
import com.huanqiu.travelsafe.utils.TravelSafeBackgroundExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/6/13.
 */

@Module
public class SystemServicesModule {
    private final Application application;
    private StartController startController;

    public SystemServicesModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideContext() {return application;}

    @Provides
    @Singleton
    public SharedPreferences providePreferenceManager() {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    public ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

//    @Provides
//    @Singleton
//    public IApiServices provideTravelSafeApi() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://ngrok.zhangxd.cn/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        return retrofit.create(IApiServices.class);
//    }

    @Provides
    @Singleton
    public AppConfig providerAppConfig() {
        return AppConfig.getAppConfig(application);
    }

    @Provides
    @Singleton
    public AppManager providerAppManager() {
        return AppManager.getAppManager();
    }

//    @Provides
//    @Singleton
//        //Method parameter injected by Dagger2
//    NetworkStateManager provideNetworkStateManager(ConnectivityManager connectivityManagerCompat) {
//        return new NetworkStateManager(connectivityManagerCompat);
//    }

    @Provides
    @Singleton
    public RxBus providerRxBus() {
        return new RxBus();
    }

    @Provides
    @Singleton
    public MainController providerMainController() {
        return new MainController();
    }

    @Provides
    @Singleton
    public StartController providerStartController() {
        return new StartController();
    }

    @Provides @Singleton
    public BackgroundExecutor provideMultiThreadExecutor() {
        final int numberCores = Runtime.getRuntime().availableProcessors();
        return new TravelSafeBackgroundExecutor(Executors.newFixedThreadPool(numberCores * 2 + 1));
    }

    @Provides @Singleton
    public BackgroundExecutor provideDatabaseThreadExecutor() {
        return new TravelSafeBackgroundExecutor(Executors.newSingleThreadExecutor());
    }

}
