package hu.bme.aut.worktimer;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.di.Network;
import hu.bme.aut.worktimer.ui.UIModule;
import hu.bme.aut.worktimer.ui.about.AboutPresenter;
import hu.bme.aut.worktimer.ui.login.LoginPresenter;
import hu.bme.aut.worktimer.ui.navigation.NavigationPresenter;

@Module
public class TestModule {

    private final UIModule uimodule;

    public TestModule(Context context) {
        this.uimodule = new UIModule(context);
    }

    @Provides
    public Context provideContext() {
        return uimodule.provideContext();
    }


    @Provides
    public LoginPresenter providePresenter() {
        return uimodule.provideLoginPresenter();
    }

    @Provides
    public AboutPresenter provideAboutPresenter() {
        return uimodule.provideAboutPresenter();
    }

    @Provides
    public NavigationPresenter provideRecipePresenter() {
        return uimodule.provideNavigation();
    }


    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    @Network
    public Executor provideNetworkExecutor() {
        return new UiExecutor();
    }


}