package com.huanqiu.travelsafe.controllers;

import android.util.Log;
import android.view.View;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.display.Display;
import com.huanqiu.travelsafe.event.CheckInputEvent;
import com.huanqiu.travelsafe.event.RxBus;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/7/6.
 */
public class MainController extends BaseUiController<MainController.MainControllerUi, MainController.MainControllerUiCallback> implements Action1<Object> {

    private HostCallBacks mHostCallbacks;

    @Inject
    StartController mStartController;

    @Inject
    RxBus rxBus;

    public MainController() {
        App.getInstance().getGraph().inject(this);
//        mStartController = Preconditions.checkNotNull(startController, "start cannot be null");
    }

    @Override
    protected MainControllerUiCallback createUiCallbacks(MainControllerUi ui) {
        return new MainControllerUiCallback() {
//            @Override
//            public void showRegistry() {
//                Display display = getDisplay();
//                if (null != display) {
//                    display.showRegistryOneFragment();
//                }
//            }
//
//            @Override
//            public void showRetrievePass() {
//                Display display = getDisplay();
//                if (null != display) {
//                    display.showRetrievePassOneFragment();
//                }
//            }
//
//            @Override
//            public void showHeader() {
//                Display display = getDisplay();
//                if (null != display) {
//                    display.showHeaderFragment();
//                }
//            }
        };
    }

    public interface HostCallBacks {
        void finish();
        void setAccountAuthenticatorResult(String name, String token, String accountType);
    }
    public interface MainControllerUi extends BaseUiController.Ui<MainControllerUiCallback>{};

    public interface SideMenuUi extends MainControllerUi {
//        void setSideMenuItems(SideMenuItem[] items, SideMenuItem selected);

//        void showUserProfile(PhilmUserProfile profile);

//        void showAddAccountButton();

//        void showMovieCheckin(WatchingMovie movie);

//        void hideMovieCheckin();
    }

    public interface MainUi extends MainControllerUi {

        void showLoginPrompt();

    }

    public interface MainControllerUiCallback {
//    public onSideMenuItemSelected(SideMenuItem item);

//        void showRegistry();
//        void showRetrievePass();
//        void showHeader();
//        void addAccountRequested();
//        void showMovieCheckin();
//        void setShownLoginPrompt();

    }

    @Override
    protected void onInited() {
        super.onInited();
        mStartController.init();
    }

    @Override
    public void call(Object o) {

    }

    public void attachDisplay(Display display) {
        Preconditions.checkNotNull(display, "display is null");
        Preconditions.checkState(getDisplay() == null, "we currently have a display");
        setDisplay(display);
    }

    public void detachDisplay(Display display) {
        Preconditions.checkNotNull(display, "display is null");
        Preconditions.checkState(getDisplay() == display, "display is not attached");
        setDisplay(null);
    }

    public void setHostCallbacks(HostCallBacks hostCallbacks) {
        mHostCallbacks = hostCallbacks;
    }

    public StartController getStartController() {
        return mStartController;
    }

    @Override
    protected void setDisplay(Display display) {
        super.setDisplay(display);
        mStartController.setDisplay(display);
    }
}
