package hu.bme.aut.worktimer.interactor.User;


import org.greenrobot.eventbus.EventBus;

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
            if (loginResponse.code() == 200) {
                EventBus.getDefault().post(loginEvent);

            } else {
                throw new Exception("Result code is not 200 (OK)");
            }
        } catch (Exception e) {
            loginEvent.setThrowable(e);
            EventBus.getDefault().post(loginEvent);
        }

    }

    public void logout(User user) {
        LogoutEvent logoutEvent = new LogoutEvent();
        logoutEvent.setUser(user);
        try {
            Call<Void> logoutCall = userApi.logoutUser(user);
            Response<Void> logoutResponse = logoutCall.execute();
            logoutEvent.setCode(logoutResponse.code());
            if (logoutResponse.code() == 200){
                EventBus.getDefault().post(logoutEvent);
            }else{
                throw new Exception("Result code in not ok (200)");
            }
        } catch (Exception e) {
            logoutEvent.setThrowable(e);
            EventBus.getDefault().post(logoutEvent);
        }
    }

    public void register(User user) {
        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.setUser(user);
        try{
            Call<Void> registerCall = userApi.createUser(user);
            Response<Void> resigsterResponse = registerCall.execute();
            if (resigsterResponse.code() == 200){
                EventBus.getDefault().post(registerEvent);
            }else{
                throw new Exception("Result code in not ok (200)");
            }
        }catch (Exception e){
            registerEvent.setThrowable(e);
            EventBus.getDefault().post(registerEvent);
        }
    }

    //TODO create workday events
    public WorkDays getAllWorkDays(User user) {
        WorkDays workDays = null;

        try {
            workDays = userApi.getAllWorkdays(user.getUsername()).execute().body();
        } catch (IOException ioe) {
            //EventBus.getDefault().post();
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
