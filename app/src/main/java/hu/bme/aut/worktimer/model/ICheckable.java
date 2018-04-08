package hu.bme.aut.worktimer.model;

import java.util.Date;

import hu.bme.aut.worktimer.model.WorkDay;

/**
 * Interface for users, who must do checks
 * Created by ecsedigergo on 2018. 04. 08..
 */

public interface ICheckable {
    void addWorkday(WorkDay day);

    void addCheckin(WorkDay day, Date in);

    void addCheckout(WorkDay day, Date out);

    void removeWorkday(WorkDay day);

    void removeCheckin(WorkDay day);

    void removeCheckout(WorkDay day);
}
