package com.huanqiu.travelsafe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.display.Display;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends BaseActivity implements MainController.MainControllerUi {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMainController().attachUi(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMainController().detachUi(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean needToolsBar() {
        return false;
    }

    @Override
    protected void handleIntent(Intent intent, Display display) {
        display.showStartPage();
    }

    @Override
    public void setCallbacks(MainController.MainControllerUiCallback callbacks) {

    }

    @Override
    public boolean isModal() {
        return false;
    }
}
