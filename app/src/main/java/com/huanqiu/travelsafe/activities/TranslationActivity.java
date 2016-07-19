package com.huanqiu.travelsafe.activities;

import android.content.Intent;
import android.os.Bundle;

import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.display.Display;

/**
 * Created by Administrator on 2016/7/18.
 */
public class TranslationActivity extends BaseActivity implements MainController.MainControllerUi {
    private MainController.MainControllerUiCallback mUiCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTransparent(true);
    }

    @Override
    public int getLayout() {
        return R.layout.translation_activity_layout;
    }

    @Override
    public boolean needToolsBar() {
        return false;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getMainController().attachUi(this);
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        getMainController().detachUi(this);
//    }

    @Override
    protected void handleIntent(Intent intent, Display display) {
        display.showTranslationFragment();
    }

    @Override
    public void setCallbacks(MainController.MainControllerUiCallback callbacks) {
        mUiCallbacks = callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }
}
