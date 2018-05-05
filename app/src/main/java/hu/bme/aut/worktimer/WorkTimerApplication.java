package hu.bme.aut.worktimer;

import android.app.Application;

import javax.inject.Inject;

import hu.bme.aut.worktimer.repository.Repository;
import hu.bme.aut.worktimer.repository.RepositoryModule;
import hu.bme.aut.worktimer.ui.UIModule;

/**
 * Application
 * Created by ecsedigergo on 2018. 04. 09..
 */

public class WorkTimerApplication extends Application {
    public static WorkTimerApplicationComponent injector;


    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerWorkTimerApplicationComponent.builder().
                uIModule(new UIModule(this))
                .build();
        injector.inject(this);
    }

    public void setInjector(WorkTimerApplicationComponent appComponent) {
        injector = appComponent;
        injector.inject(this);
    }
}
