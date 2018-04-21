package hu.bme.aut.worktimer.ui.login;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.interactor.User.UserInteractor;
import hu.bme.aut.worktimer.ui.Presenter;

@Singleton
public class LoginPresenter extends Presenter<ILoginScreen> {
    @Inject
    UserInteractor userInteractor;

    @Override
    public void attachScreen(ILoginScreen loginScreen){
        super.attachScreen(loginScreen);
        WorkTimerApplication.injector.inject(this);
    }

    @Override
    public void detachScreen(){
        super.detachScreen();
    }
}
