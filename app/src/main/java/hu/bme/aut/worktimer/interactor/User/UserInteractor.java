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
import hu.bme.aut.worktimer.repository.Repository;

/**
 * Created by ecsedigergo on 2018. 04. 21..
 */

public class UserInteractor {
    @Inject
    UserApi userApi;

    @Inject
    Repository repository;

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

    public WorkDays getAllWorkDays(User user) {
        WorkDays workDays = null;
        try {
            workDays = userApi.getAllWorkdays(user.getUsername()).execute().body();
        } catch (IOException ioe) {
            //TODO handle exception
        }
        return workDays;
    }

    public WorkDay getWorkDay(User user, Date checkinDate) throws IOException {
        return userApi.getWorkDayByCheckinDate(user.getUsername(), checkinDate).execute().body();
    }

    public void addWorkDay(User user, WorkDay workDay) throws IOException {
        userApi.addWorkDay(user.getUsername(), workDay);
    }

    //TODO convert model classes to each other (network/model, model/ORM, model/Android
}
