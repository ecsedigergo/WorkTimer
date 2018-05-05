package hu.bme.aut.worktimer.model.orm;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.Date;

/**
 * Workday model for SugarORM database
 * Created by ecsedigergo on 2018. 04. 22..
 */
@Table
public class WorkDayORMModel extends SugarRecord {
    private Long user;
    private Date checkindate;
    private Date checkoutdate;

    public WorkDayORMModel() {
    }

    public WorkDayORMModel(Date checkindate, Date checkoutdate) {
        this.checkindate = checkindate;
        this.checkoutdate = checkoutdate;
    }

    public void setUser(Long user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "WorkDayORMModel{" +
                "user=" + user +
                ", checkindate=" + checkindate +
                ", checkoutdate=" + checkoutdate +
                '}';
    }
}
