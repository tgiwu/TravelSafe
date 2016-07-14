package com.huanqiu.travelsafe.display;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.google.common.base.Preconditions;
import com.huanqiu.travelsafe.R;
import com.huanqiu.travelsafe.fragment.LoginPageFragment;
import com.huanqiu.travelsafe.fragment.RegistryOneFragment;
import com.huanqiu.travelsafe.fragment.RegistryTwoFragment;
import com.huanqiu.travelsafe.fragment.RetrievePasswordStepOnePageFragment;
import com.huanqiu.travelsafe.fragment.RetrievePasswordStepTwoPageFragment;
import com.huanqiu.travelsafe.fragment.StartPageFragment;

/**
 * Created by Administrator on 2016/7/6.
 */
public class AndroidDisplay implements Display, FragmentManager.OnBackStackChangedListener {

    private static final TypedValue sTypedValue = new TypedValue();
    private final AppCompatActivity mActivity;
    private final DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private boolean mCanChangeToolbarBackground;
//    private HeaderBarFragment mHeaderBarFragment;

//    private ColorScheme mColorScheme;
//    private int mColorPrimaryDark;

    public AndroidDisplay(AppCompatActivity activity, DrawerLayout mDrawerLayout) {
        this.mActivity = Preconditions.checkNotNull(activity, "activity cannot be null");
        this.mDrawerLayout = mDrawerLayout;
        mActivity.getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void showRetrievePassOneFragment() {
        showFragment(RetrievePasswordStepOnePageFragment.newInstance("retrievePassOne"));
    }

    @Override
    public void showRetrievePassTwoFragment() {
        showFragment(RetrievePasswordStepTwoPageFragment.newInstance("retrievePassTwo"));
    }

    @Override
    public void showRegistryOneFragment() {
        showFragment(RegistryOneFragment.newInstance("registry_one"));
    }

    @Override
    public void showRegistryTwoFragment() {
        showFragment(RegistryTwoFragment.newInstance("registry_two"));
    }

    @Override
    public void showHeaderFragment() {
//        mHeaderBarFragment = HeaderBarFragment.newInstance("","");
//        mActivity.getSupportFragmentManager().beginTransaction()
//                .add(R.id.start_header_bar, mHeaderBarFragment)
//                .commit();
    }

    @Override
    public void setActionBarTitle(CharSequence title) {

    }

    @Override
    public boolean popEntireFragmentBackStack() {
        final FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        final int backStackCount = fragmentManager.getBackStackEntryCount();
        //clear back stack
        for (int i = 0; i < backStackCount; i++) {
            fragmentManager.popBackStack();
        }
        return backStackCount > 0;
    }

    @Override
    public void showStartPage() {
        if (hasMainFragment())
            showFragment(StartPageFragment.newInstance("start_page"));
    }

    @Override
    public boolean hasMainFragment() {
        return mActivity.findViewById(R.id.fragment_main) != null;
    }

    @Override
    public void showLoginFragment() {
        if (hasMainFragment()) {
            showFragment(LoginPageFragment.newInstance("login_page"));
        }
    }

    @Override
    public void showRegisterFragment() {

    }

    @Override
    public void setSupportActionBar(Object toolbar, boolean handleBackground) {
        mToolbar = (Toolbar) toolbar;
        mCanChangeToolbarBackground = handleBackground;
    }

    @Override
    public void closeCurrentFragment() {
        mActivity.onBackPressed();
    }

    private void showFragment(Fragment fragment) {
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_main, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        if (mActivity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mActivity.finish();
        } else if (mActivity.getSupportFragmentManager().getBackStackEntryCount() == 1) {
//            mHeaderBarFragment.setRegisterBtnVisibility(View.VISIBLE);
        }
    }
}
