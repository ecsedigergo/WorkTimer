package hu.bme.aut.worktimer.network.model;

import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class WorkDay {

    @SerializedName("checkin")
    private Date checkin = null;

    @SerializedName("checkout")
    private Date checkout = null;


    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }


    /**
     **/
    @ApiModelProperty(value = "")
    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkDay workDay = (WorkDay) o;
        return Objects.equals(checkin, workDay.checkin) &&
                Objects.equals(checkout, workDay.checkout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkin, checkout);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WorkDay {\n");

        sb.append("    checkin: ").append(toIndentedString(checkin)).append("\n");
        sb.append("    checkout: ").append(toIndentedString(checkout)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
