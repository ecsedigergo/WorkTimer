package hu.bme.aut.worktimer.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hu.bme.aut.worktimer.model.orm.UserORMModel;
import hu.bme.aut.worktimer.model.orm.WorkDayORMModel;

/**
 * Database in Memory
 * Created by ecsedigergo on 2018. 04. 22..
 */

public class MemoryRepository implements Repository {
    private static List<UserORMModel> users = new ArrayList<>();

    @Override
    public void open(Context context) {

        UserORMModel user = new UserORMModel("user@example.com", "abcd1234", new ArrayList<>());
        users.add(user);
        user.getWorkdays().add(new WorkDayORMModel(new Date(2018 - 1900, 4, 4, 12, 00), new Date(2018 - 1900, 4, 4, 16, 00)));
        user.getWorkdays().add(new WorkDayORMModel(new Date(2018 - 1900, 4, 5, 8, 00), new Date(2018 - 1900, 4, 4, 11, 30)));
    }

    @Override
    public void close() {

    }

    @Override
    public List<UserORMModel> getUsers() {
        return users;
    }

    @Override
    public void saveUser(UserORMModel user) {
        users.add(user);
    }

    @Override
    public void removeUser(UserORMModel user) {
        users.remove(user);
    }

    @Override
    public void updateUsers(List<UserORMModel> usersToUpdate) {
        for (UserORMModel update : usersToUpdate) {
            for (UserORMModel user : users) {
                if (update.getUsername().equals(user.getUsername())) {
                    updateUser(user, update);
                    break;
                }
            }

        }
    }

    private void updateUser(UserORMModel userForUpdate, UserORMModel update) {
        userForUpdate.setPassword(update.getPassword());
        userForUpdate.setWorkdays(update.getWorkdays());
    }

    @Override
    public boolean isInDB(UserORMModel user) {
        return users.contains(user);
    }
}
