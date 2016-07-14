package com.huanqiu.travelsafe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.controllers.StartController;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/7.
 */
public abstract class BaseStartPageFragment extends BaseTravelSafeFragment implements StartController.StartUi, View.OnFocusChangeListener{

    private StartController.StartUiCallback mCallBack;
    private SuperCardToast mToast;

    @Override
    public void setCallbacks(StartController.StartUiCallback callbacks) {
        this.mCallBack = callbacks;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract void initView();

    @Override
    public boolean isModal() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        cancelToast();
        getController().detachUi(this);
        super.onPause();
    }

    protected final void cancelToast() {
        if (mToast != null) {
            mToast.dismiss();
        }
    }

    protected final void showToast(int text, Style style) {
        cancelToast();

        mToast = SuperCardToast.create(
                getActivity(), getText(text), SuperToast.Duration.MEDIUM, style);
        mToast.setIcon(SuperToast.Icon.Dark.INFO, SuperToast.IconPosition.LEFT);
        mToast.show();
    }

    protected final StartController.StartUiCallback getCallbacks() {
        return mCallBack;
    }


    private StartController getController() {
        return App.from(getActivity()).getMainController().getStartController();
    }

    @Override
    public void showError(String s) {
        showToast(R.string.app_name, Style.getStyle(Style.RED));
    }

    protected void buildToast(String message) {
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 20, 0);
        View toastRoot = getLayoutInflater(new Bundle()).inflate(R.layout.start_toast, null, false);
        ((TextView) toastRoot.findViewById(R.id.toast_info)).setText(message);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void buildToast(int res) {
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 10);
        View toastRoot = getLayoutInflater(new Bundle()).inflate(R.layout.start_toast, null, false);
        ((TextView) toastRoot.findViewById(R.id.toast_info)).setText(res);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
