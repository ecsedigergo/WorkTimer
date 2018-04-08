package hu.bme.aut.worktimer.model;

import java.util.Date;

/**
 * Represents a working day in the application with checkin and checkout times.
 * Created by ecsedigergo on 2018. 04. 08..
 */

public class WorkDay {
    private Date checkin;

    private Date checkout;

    public void setCheckin(Date in) {
        checkin = in;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckout(Date out) {
        checkout = out;
    }

    public Date getCheckout() {
        return checkout;
    }
}
