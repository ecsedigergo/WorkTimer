package hu.bme.aut.worktimer.interactor.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.interactor.User.event.auth.LoginEvent;
import hu.bme.aut.worktimer.interactor.User.event.auth.LogoutEvent;
import hu.bme.aut.worktimer.interactor.User.event.auth.RegisterEvent;
import hu.bme.aut.worktimer.network.api.UserApi;
import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.network.model.WorkDay;
import hu.bme.aut.worktimer.network.model.WorkDays;

/**
 * Created by ecsedigergo on 2018. 04. 21..
 */

public class UserInteractor {
    @Inject
    UserApi userApi;

    public UserInteractor() {
        WorkTimerApplication.injector.inject(this);
    }

    public void login(User user) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setUser(user);
    }

    public void logout() {
        LogoutEvent logoutEvent = new LogoutEvent();
    }

    public void register(User user) {
        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.setUser(user);
    }

    public WorkDays getAllWorkDays(User user) throws IOException {
        return userApi.getAllWorkdays(user.getUsername()).execute().body();
    }

    public WorkDay getWorkDay(User user, Date checkinDate) throws IOException {
        return userApi.getWorkDayByCheckinDate(user.getUsername(),checkinDate).execute().body();
    }

    public void addWorkDay(User user, WorkDay workDay)throws IOException{
        userApi.addWorkDay(user.getUsername(),workDay);
    }

}
