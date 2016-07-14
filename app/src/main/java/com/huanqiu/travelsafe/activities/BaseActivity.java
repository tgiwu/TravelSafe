package com.huanqiu.travelsafe.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.display.AndroidDisplay;
import com.huanqiu.travelsafe.display.Display;

public abstract class BaseActivity extends AppCompatActivity implements MainController.HostCallBacks{

    private MainController mMainController;
    private Display mDisplay;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (needToolsBar()) setToolbar();

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDisplay = new AndroidDisplay(this, null);
        mMainController = App.from(this).getMainController();

        handleIntent(getIntent(), getDisplay());
    }

    public abstract int getLayout();

    @Override
    protected void onResume() {
        super.onResume();
        mMainController.attachDisplay(mDisplay);
        mMainController.setHostCallbacks(this);
        mMainController.init();
    }

    @Override
    protected void onPause() {
        mMainController.suspend();
        mMainController.setHostCallbacks(null);
        mMainController.detachDisplay(mDisplay);
        super.onPause();
    }

    public abstract boolean needToolsBar();

    public void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    protected void setStatusBarTransparent(boolean shouldTransparent) {
        if (shouldTransparent) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisplay = null;
    }

    protected void handleIntent(Intent intent, Display display) {
    }
    protected final MainController getMainController() {
        return mMainController;
    }

    public Display getDisplay() {
        return mDisplay;
    }


    @Override
    public void setAccountAuthenticatorResult(String name, String token, String accountType) {

    }

    public void setSupportActionBar(@Nullable Toolbar toolbar, boolean handleBackground) {
        setSupportActionBar(toolbar);
        getDisplay().setSupportActionBar(toolbar, handleBackground);
    }
}