package hu.bme.aut.worktimer.repository;

import android.content.Context;

import java.util.List;

import hu.bme.aut.worktimer.model.orm.UserORMModel;

/**
 * Created by ecsedigergo on 2018. 04. 22..
 */

public interface Repository {
    void open(Context context);

    void close();

    List<UserORMModel> getUsers();

    void saveUser(UserORMModel user);

    void removeUser(UserORMModel user);

    void updateUsers(List<UserORMModel> usersToUpdate);

    boolean isInDB(UserORMModel user);
}
