package hu.bme.aut.worktimer.repository;

import android.content.Context;

import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.worktimer.model.orm.UserORMModel;
import hu.bme.aut.worktimer.model.orm.WorkDayORMModel;

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
        List<UserORMModel> result = SugarRecord.listAll(UserORMModel.class);
        for (UserORMModel user : result) {
            user.setWorkdays(SugarRecord.find(WorkDayORMModel.class, "user = ?", user.getId().toString()));
        }
        return result;
    }

    @Override
    public void saveUser(UserORMModel user) {
        if (user.getId() != null) {
            SugarRecord.deleteAll(WorkDayORMModel.class, "user = ?", user.getId().toString());
        }
        long userid = user.save();
        for (WorkDayORMModel wd : user.getWorkdays()) {
            WorkDayORMModel svd = new WorkDayORMModel(wd.getCheckindate(), wd.getCheckoutdate());
            svd.setUser(userid);
            svd.save();
        }
    }

    @Override
    public void removeUser(UserORMModel user) {
        SugarRecord.delete(user);
    }

    @Override
    public void updateUsers(List<UserORMModel> usersToUpdate) {
        List<UserORMModel> oldUsers = getUsers();
        List<UserORMModel> updateableUsers = new ArrayList<>(oldUsers.size());
        for (UserORMModel old : oldUsers) {
            for (UserORMModel update : usersToUpdate)
                if (update.getUsername() == old.getUsername()) {
                    updateableUsers.add(update);
                }
        }
        SugarRecord.saveInTx(updateableUsers);
    }

    @Override
    public boolean isInDB(UserORMModel user) {
        return SugarRecord.findById(UserORMModel.class, new String[]{user.getUsername()}) != null;
    }
}
