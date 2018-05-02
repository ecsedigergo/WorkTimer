package hu.bme.aut.worktimer.model.orm;

import com.orm.dsl.Table;

import java.util.Date;

/**
 * Workday model for SugarORM database
 * Created by ecsedigergo on 2018. 04. 22..
 */
@Table
class WorkDayORMModel {
    private Date checkindate;
    private Date checkoutdate;

    public WorkDayORMModel(Date checkindate) {
        this.checkindate = checkindate;
    }

    public Date getCheckindate() {
        return checkindate;
    }

    public void setCheckindate(Date checkindate) {
        this.checkindate = checkindate;
    }

    public Date getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(Date checkoutdate) {
        this.checkoutdate = checkoutdate;
    }
}
