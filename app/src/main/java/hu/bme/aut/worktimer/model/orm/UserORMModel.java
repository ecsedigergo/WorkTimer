package hu.bme.aut.worktimer.model.orm;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * SugarORM database model for users
 * Created by ecsedigergo on 2018. 04. 22..
 */
@Table
public class UserORMModel extends SugarRecord {
    private String username;
    private String password;
    @Ignore
    private List<WorkDayORMModel> workdays;

    public UserORMModel() {
    }

    public UserORMModel(String username, String password, List<WorkDayORMModel> workdays) {
        this.username = username;
        this.password = password;
        this.workdays = workdays;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<WorkDayORMModel> getWorkdays() {
        return workdays;
    }

    public void setWorkdays(List<WorkDayORMModel> workdays) {
        this.workdays = workdays;
    }

}
