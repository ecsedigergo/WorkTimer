package hu.bme.aut.worktimer.model;

/**
 * Interface for users in the application
 * Created by ecsedigergo on 2018. 04. 08..
 */

public interface IUser {
    void setUserName(String name);

    String getUserName();

    void setPassword(String password);

    String getPassword();

}
