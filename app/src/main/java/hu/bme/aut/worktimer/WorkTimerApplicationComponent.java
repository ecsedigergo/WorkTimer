package hu.bme.aut.worktimer;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.worktimer.interactor.User.UserInteractor;
import hu.bme.aut.worktimer.network.NetworkModule;
import hu.bme.aut.worktimer.ui.UIModule;
import hu.bme.aut.worktimer.ui.login.LoginActivity;
import hu.bme.aut.worktimer.ui.navigation.NavigationActivity;

/**
 * Dagger injecter component
 * Created by ecsedigergo on 2018. 04. 09..
 */

@Singleton
@Component(modules = {NetworkModule.class, UIModule.class})
public interface WorkTimerApplicationComponent {
    void inject(LoginActivity loginActivity);
    void inject(NavigationActivity navigationActivity);
    void inject(UserInteractor userInteractor);
}
