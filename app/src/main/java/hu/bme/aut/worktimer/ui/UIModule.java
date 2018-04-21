package hu.bme.aut.worktimer.ui;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.ui.login.LoginPresenter;
import hu.bme.aut.worktimer.ui.navigation.NavigationPresenter;

/**
 * Dagger module element for UI classes
 * Created by ecsedigergo on 2018. 04. 09..
 */
@Module
public class UIModule {
    private Context context;

    public UIModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext(){ return context; }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter(){ return new LoginPresenter(); }

    @Provides
    @Singleton
    public NavigationPresenter provideNavigation(){ return new NavigationPresenter(); }


}
