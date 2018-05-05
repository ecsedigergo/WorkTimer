package hu.bme.aut.worktimer.ui.about;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.ui.Presenter;

/**
 * Presenter class for about screen
 * Created by ecsedigergo on 2018. 05. 03..
 */

public class AboutPresenter extends Presenter<IAboutScreen> {
//    @Inject
//    UserInteractor userInteractor;


    @Override
    public void attachScreen(IAboutScreen aboutScreen){
        super.attachScreen(aboutScreen);
        WorkTimerApplication.injector.inject(this);
    }

    @Override
    public void detachScreen(){
        super.detachScreen();
    }
}
