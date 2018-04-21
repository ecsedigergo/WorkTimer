package hu.bme.aut.worktimer.ui.navigation;

import javax.inject.Inject;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.interactor.User.UserInteractor;
import hu.bme.aut.worktimer.ui.Presenter;

/**
 * Navigation presenter for receiving information on sidebar
 * Created by ecsedigergo on 2018. 04. 09.
 */

public class NavigationPresenter extends Presenter<INavigationScreen> {
    @Inject
    UserInteractor userInteractor;

    @Override
    public void attachScreen(INavigationScreen navigationScreen){
        super.attachScreen(navigationScreen);
        WorkTimerApplication.injector.inject(this);
    }

    @Override
    public void detachScreen(){
        super.detachScreen();
    }
}
