package com.huanqiu.travelsafe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.activities.BaseActivity;
import com.huanqiu.travelsafe.display.Display;

/**
 * Created by Administrator on 2016/7/7.
 */
public class BaseTravelSafeFragment extends Fragment{

    private Toolbar mToolbar;
    protected static String PARAM_ID = "param_id";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    protected void setSupportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar, true);
    }

    protected final void setSupportActionBar(Toolbar toolbar, boolean handleBackground) {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar, handleBackground);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected Display getDisplay() {
        return ((BaseActivity) getActivity()).getDisplay();
    }

}
