package hu.bme.aut.worktimer.ui;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.di.Network;
import hu.bme.aut.worktimer.ui.about.AboutPresenter;
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

    @Provides
    @Singleton
    public AboutPresenter provideAboutPresenter(){ return new AboutPresenter();}

    @Provides
    @Singleton
    @Network
    public Executor provideNetworkExecutor(){ return Executors.newFixedThreadPool(1);}

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
