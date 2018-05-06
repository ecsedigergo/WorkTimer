package hu.bme.aut.worktimer.interactor.user;


import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.interactor.user.event.AddWorkDayEvent;
import hu.bme.aut.worktimer.interactor.user.event.WorkDayQueryEvent;
import hu.bme.aut.worktimer.interactor.user.event.auth.LoginEvent;
import hu.bme.aut.worktimer.interactor.user.event.auth.LogoutEvent;
import hu.bme.aut.worktimer.interactor.user.event.auth.RegisterEvent;
import hu.bme.aut.worktimer.mock.interceptors.MockHelper;
import hu.bme.aut.worktimer.network.api.UserApi;
import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.network.model.WorkDay;
import hu.bme.aut.worktimer.network.model.WorkDays;
import hu.bme.aut.worktimer.repository.Repository;
import hu.bme.aut.worktimer.utils.GsonHelper;
import retrofit2.Call;
import retrofit2.Response;

/**
 * User related calls
 * Created by ecsedigergo on 2018. 04. 21..
 */

public class UserInteractor {
    @Inject
    UserApi userApi;

    @Inject
    Repository repository;

    @Inject
    EventBus bus;

    public UserInteractor() {
        WorkTimerApplication.injector.inject(this);
    }

    public void login(User user) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setUser(user);
        try {
            Call<Void> loginCall = userApi.loginUser(user);
            Response<Void> loginResponse = loginCall.execute();

            loginEvent.setCode(loginResponse.code());
        } catch (Exception e) {
            loginEvent.setThrowable(e);
        }
        bus.post(loginEvent);

    }

    public void logout(User user) {
        LogoutEvent logoutEvent = new LogoutEvent();
        logoutEvent.setUser(user);
        try {
            Call<Void> logoutCall = userApi.logoutUser(user);
            Response<Void> logoutResponse = logoutCall.execute();
            logoutEvent.setCode(logoutResponse.code());
        } catch (Exception e) {
            logoutEvent.setThrowable(e);
        }
        bus.post(logoutEvent);
    }

    public void register(User user) {
        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.setUser(user);
        try {
            Call<Void> registerCall = userApi.createUser(user);
            Response<Void> registerResponse = registerCall.execute();
            registerEvent.setCode(registerResponse.code());
        } catch (Exception e) {
            registerEvent.setThrowable(e);
        }
        bus.post(registerEvent);
    }

    public void getAllWorkDays(String username) {
        WorkDayQueryEvent event = new WorkDayQueryEvent();

        try {
            Call<WorkDays> call = userApi.getAllWorkdays(username);
            Response<WorkDays> response = call.execute();
            event.setCode(response.code());
            event.setWorkDays(response.body());
        } catch (Exception ioe) {
            event.setThrowable(ioe);
        }
        bus.post(event);
    }

    public void addWorkDay(User user, WorkDay workDay) {

        AddWorkDayEvent event = new AddWorkDayEvent();
        if (checkIsValidWorkDay(workDay)) {
            try {
                Call<Void> call = userApi.addWorkDay(user.getUsername(), workDay);
                Response<Void> response = call.execute();
                event.setCode(response.code());
            } catch (Exception ioe) {
                event.setThrowable(ioe);
            }
        } else {
            //put some->validation error in the event!
            event.setThrowable(new Throwable("Validation: Workday check times are not valid."));
        }
        bus.post(event);
    }

    private boolean checkIsValidWorkDay(WorkDay workDay) {
        if (workDay.getCheckin().compareTo(workDay.getCheckout()) <= 0) {
            //OK
            return true;
        } else {
            //NOT OK
            return false;
        }
    }
}
