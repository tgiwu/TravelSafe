package com.huanqiu.travelsafe.display;

/**
 * Created by Administrator on 2016/7/6.
 */
public interface Display {

    void showRetrievePassOneFragment();
    void showRetrievePassTwoFragment();
    void showRegistryOneFragment();
    void showRegistryTwoFragment();
    void showHeaderFragment();
    void setActionBarTitle(CharSequence title);
    boolean popEntireFragmentBackStack();

    void showStartPage();
    boolean hasMainFragment();

    void showLoginFragment();
    void showRegisterFragment();

    void setSupportActionBar(Object toolbar, boolean handleBackground);

    void closeCurrentFragment();

}
