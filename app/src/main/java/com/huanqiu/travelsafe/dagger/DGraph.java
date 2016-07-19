package com.huanqiu.travelsafe.dagger;

import com.huanqiu.travelsafe.App;
import com.huanqiu.travelsafe.activities.LoginActivity;
import com.huanqiu.travelsafe.controllers.MainController;
import com.huanqiu.travelsafe.controllers.StartController;
import com.huanqiu.travelsafe.controllers.TranslationController;
import com.huanqiu.travelsafe.fragment.LoginPageFragment;
import com.huanqiu.travelsafe.fragment.RegistryOneFragment;
import com.huanqiu.travelsafe.fragment.RegistryTwoFragment;
import com.huanqiu.travelsafe.fragment.RetrievePasswordStepOnePageFragment;
import com.huanqiu.travelsafe.fragment.RetrievePasswordStepTwoPageFragment;
import com.huanqiu.travelsafe.fragment.StartPageFragment;
import com.huanqiu.travelsafe.fragment.TranslationFragment;
import com.huanqiu.travelsafe.utils.TravelSafeBackgroundExecutor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/13.
 */
@Singleton
@Component (modules = {SystemServicesModule.class})
public interface DGraph {

    void inject(App app);

    void inject(LoginActivity loginActivity);

    void inject(StartController controller);

    void inject(StartPageFragment startPageFragment);

    void inject(MainController mainController);

    void inject(RetrievePasswordStepOnePageFragment retrievePasswordStepOnePageFragment);

    void inject(RetrievePasswordStepTwoPageFragment retrievePasswordStepTwoPageFragment);

    void inject(RegistryOneFragment registryOneFragment);

    void inject(RegistryTwoFragment registryTwoFragment);

    void inject(LoginPageFragment loginPageFragment);

    void inject(TravelSafeBackgroundExecutor travelSafeBackgroundExecutor);

    void inject(TranslationController translationController);

    void inject(TranslationFragment translationFragment);
}
