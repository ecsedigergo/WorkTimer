package hu.bme.aut.worktimer.repository;

import android.content.Context;

import java.util.List;

import hu.bme.aut.worktimer.model.orm.UserORMModel;

/**
 * Database in Memory
 * Created by ecsedigergo on 2018. 04. 22..
 */

public class MemoryRepository implements Repository {
    private static List<UserORMModel> users;

    @Override
    public void open(Context context) {

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
                if(update.getUsername() == user.getUsername()){
                    updateUser(user,update);
                }
            }

        }
    }

    private void updateUser(UserORMModel userForUpdate, UserORMModel update){
        userForUpdate.setPassword(update.getPassword());
        userForUpdate.setWorkdays(update.getWorkdays());
    }

    @Override
    public boolean isInDB(UserORMModel user) {
        return users.contains(user);
    }
}
