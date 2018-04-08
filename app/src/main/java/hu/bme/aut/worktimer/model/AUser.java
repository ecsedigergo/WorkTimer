package hu.bme.aut.worktimer.model;

import java.util.Date;
import java.util.List;

/**
 * Abstract class for users in the application
 * Created by ecsedigergo on 2018. 04. 08..
 */

public abstract class AUser implements IUser, ICheckable {
    private String userName;
    private String password;
    private List<WorkDay> workdays;

    @Override
    public void setUserName(String name) {
        this.userName = name;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void addWorkday(WorkDay day) {
        //TODO implement method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void addCheckin(WorkDay day, Date in) {
        //TODO implement method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void addCheckout(WorkDay day, Date out) {
        //TODO implement method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void removeWorkday(WorkDay day) {
        //TODO implement method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void removeCheckin(WorkDay day) {
        //TODO implement method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void removeCheckout(WorkDay day) {
        //TODO implement method
        throw new UnsupportedOperationException("Not implemented yet.");
    }


}
