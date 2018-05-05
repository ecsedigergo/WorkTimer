package hu.bme.aut.worktimer.ui.navigation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.di.Network;
import hu.bme.aut.worktimer.interactor.user.UserInteractor;
import hu.bme.aut.worktimer.interactor.user.event.AddWorkDayEvent;
import hu.bme.aut.worktimer.interactor.user.event.WorkDayQueryEvent;
import hu.bme.aut.worktimer.interactor.user.event.auth.LoginEvent;
import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.network.model.WorkDay;
import hu.bme.aut.worktimer.repository.Repository;
import hu.bme.aut.worktimer.ui.Presenter;

/**
 * Navigation presenter for receiving information on sidebar
 * Created by ecsedigergo on 2018. 04. 09.
 */

public class NavigationPresenter extends Presenter<INavigationScreen> {
    @Inject
    UserInteractor userInteractor;

    @Inject
    EventBus bus;

    @Inject
    @Network
    Executor executor;

    @Inject
    Repository repository;


    public void logout() {
        userInteractor.logout(setupUser(NavigationActivity.EXTRA_USERNAME, "*"));
        screen.logout();
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
        bus.register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        bus.unregister(this);
    }

    public void getWorkDays(String username) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userInteractor.getAllWorkDays(username);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final WorkDayQueryEvent event) {
        if (screen == null) {
            return;
        }
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            screen.workDayQueryFailed(event.getThrowable().toString());
        } else {
            if (event.getCode() == 200) {
                screen.workDaysQueried(event.getWorkDays());
            } else {
                screen.workDayQueryFailed("Invalid response code: " + event.getCode());
            }
        }
    }

    public void addWorkDay(String mUserEmail, int year, int month, int day, int starthour, int startminute, int endhour, int endminute) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                WorkDay wday = new WorkDay();
                wday.setCheckin(new Date(year - 1900, month, day, starthour, startminute));
                wday.setCheckout(new Date(year - 1900, month, day, endhour, endminute));
                userInteractor.addWorkDay(setupUser(mUserEmail, "*"), wday);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final AddWorkDayEvent event) {
        if (screen == null) {
            return;
        }
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            screen.addWorkDayFailed(event.getThrowable().toString());
        } else {
            if (event.getCode() == 200) {
                screen.workDayAdded();
            } else {
                screen.addWorkDayFailed("Invalid response code: " + event.getCode());
            }
        }
    }
}
