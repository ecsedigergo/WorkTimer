package hu.bme.aut.worktimer.ui.navigation;

import hu.bme.aut.worktimer.network.model.WorkDays;

/**
 * Responsible for give informations for activity from presenter
 * Created by ecsedigergo on 2018. 04. 09..
 */

public interface INavigationScreen {
    void logout();

    void workDayQueryFailed(String message);

    void workDaysQueried(WorkDays workDays);

    void workDayAdded();

    void addWorkDayFailed(String s);
}
