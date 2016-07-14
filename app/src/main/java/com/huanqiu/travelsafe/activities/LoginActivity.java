package com.huanqiu.travelsafe.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.display.Display;
import com.huanqiu.travelsafe.fragment.HeaderBarFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity implements
        HeaderBarFragment.OnFragmentInteractionListener, MainController.MainControllerUi {

//    @Inject
//    RxBus rxBus;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unbinder = ButterKnife.bind(this);
//        ((App)getApplication()).getGraph().inject(this);

    }

    @Override
    public int getLayout() {
        return R.layout.toolbar_with_drawer_layout;
//        return R.layout.start_fragment_layout;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        subscriptions = new CompositeSubscription();
//        subscriptions.add(rxBus.toObserverable()
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object s) {
//                        Toast.makeText(LoginActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }));
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
    public boolean needToolsBar() {
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        subscriptions.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        getDisplay().showRetrievePassOneFragment();
//        getSupportFragmentManager().beginTransaction()
//                .remove(mStartLoginContextFragment)
//                .add(R.id.start_context, mStartRetrievePassFragment)
//                .commit();
    }

    @Override
    protected void handleIntent(Intent intent, Display display) {
//        display.showHeaderFragment();
        display.showRegistryOneFragment();
    }

    @Override
    public void setCallbacks(MainController.MainControllerUiCallback callbacks) {

    }

    @Override
    public boolean isModal() {
        return false;
    }

//    @OnClick({R.id.login_register_btn, R.id.login_btn, R.id.login_forget_password_txt})
//    public void doClick(View view) {
//        switch (view.getId()) {
//            case R.id.login_register_btn:
//                if (rxBus != null) {
//                    rxBus.send("ssssssssss");
//                }
////            TravelSafeApi.requestVerification("start_registry_bg", "13466739594").subscribe(new Subscriber<String>() {
////                @Override
////                public void onCompleted() {
////                    Log.e("zhy", "onCompleted: ");
////                }
////
////                @Override
////                public void onError(Throwable e) {
////                    e.printStackTrace();
////                }
////
////                @Override
////                public void onNext(String s) {
////                    Log.e("zhy", "onNext: " + s);
////                }
////            });
//                break;
//            case R.id.login_btn:
//                break;
//            case R.id.login_forget_password_txt:
//                break;
//        }
//    }
}
