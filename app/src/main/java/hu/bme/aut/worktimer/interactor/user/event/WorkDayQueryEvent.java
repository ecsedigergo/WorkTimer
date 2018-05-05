package hu.bme.aut.worktimer.interactor.user.event;

import hu.bme.aut.worktimer.network.model.WorkDays;
import hu.bme.aut.worktimer.utils.NetworkEvent;

public class WorkDayQueryEvent extends NetworkEvent{
    private WorkDays workDays;

    public WorkDays getWorkDays() {
        return workDays;
    }

    public void setWorkDays(WorkDays workDays) {
        this.workDays = workDays;
    }
}
