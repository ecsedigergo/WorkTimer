package hu.bme.aut.worktimer.repository;

import android.content.Context;

import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.worktimer.model.orm.UserORMModel;

/**
 * Database by sugar orm
 * Created by ecsedigergo on 2018. 04. 22..
 */

class SugarORMRepository implements Repository {

    @Override
    public void open(Context context) {
        SugarContext.init(context);
    }

    @Override
    public void close() {
        SugarContext.terminate();
    }

    @Override
    public List<UserORMModel> getUsers() {
        return SugarRecord.listAll(UserORMModel.class);
    }

    @Override
    public void saveUser(UserORMModel user) {
        SugarRecord.save(user);
    }

    @Override
    public void removeUser(UserORMModel user) {
        SugarRecord.delete(user);
    }

    @Override
    public void updateUsers(List<UserORMModel> usersToUpdate) {
        List<UserORMModel> oldUsers = getUsers();
        List<UserORMModel> updateableUsers = new ArrayList<>(oldUsers.size());
        for (UserORMModel old: oldUsers ) {
            for(UserORMModel update: usersToUpdate)
                if (update.getUsername() == old.getUsername()){
                    updateableUsers.add(update);
                }
        }
        SugarRecord.saveInTx(updateableUsers);
    }

    @Override
    public boolean isInDB(UserORMModel user) {
        return SugarRecord.findById(UserORMModel.class, new String[]{user.getUsername()})!=null;
    }
}
