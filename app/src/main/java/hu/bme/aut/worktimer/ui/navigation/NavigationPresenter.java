package hu.bme.aut.worktimer.ui.navigation;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.di.Network;
import hu.bme.aut.worktimer.interactor.User.UserInteractor;
import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.ui.Presenter;
import hu.bme.aut.worktimer.ui.login.LoginActivity;

/**
 * Navigation presenter for receiving information on sidebar
 * Created by ecsedigergo on 2018. 04. 09.
 */

public class NavigationPresenter extends Presenter<INavigationScreen> {
    @Inject
    UserInteractor userInteractor;

//    @Inject
//    @Network
//    Executor networkExecutor;
//
//    public void refreshWorkdays(User user){
//        networkExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                userInteractor.getAllWorkDays(user);
//            }
//        });
//    }


    public void logout() {
        userInteractor.logout(setupUser(LoginActivity.USEREMAIL, "*"));
    }

    private User setupUser(final String username, final String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        return u;
    }

    @Override
    public void attachScreen(INavigationScreen navigationScreen) {
        super.attachScreen(navigationScreen);
        WorkTimerApplication.injector.inject(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }
}
