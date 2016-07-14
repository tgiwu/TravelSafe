package com.huanqiu.travelsafe;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.dagger.DGraph;
import com.huanqiu.travelsafe.dagger.DaggerDGraph;
import com.huanqiu.travelsafe.dagger.SystemServicesModule;
import com.huanqiu.travelsafe.event.RxBus;
import com.huanqiu.travelsafe.net.HttpClientManager;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/7/4.
 */
public class App extends Application {

    public DGraph graph;

    private static App instance;

    public static App from(Context context) {
        return (App) context.getApplicationContext();
    }

    @Inject
    public SharedPreferences sharedPreferences;

    @Inject
    public MainController mMainController;

    @Inject
    RxBus rxBus;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        HttpClientManager.initRetrofit();
        graph = DaggerDGraph.builder()
                .systemServicesModule(new SystemServicesModule(this)).build();

        graph.inject(this);

//        getMainController().inject();
    }

    public DGraph getGraph() {
        return graph;
    }

    public MainController getMainController() {
        return mMainController;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
