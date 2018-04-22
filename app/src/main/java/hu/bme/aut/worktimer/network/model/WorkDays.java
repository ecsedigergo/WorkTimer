package hu.bme.aut.worktimer.network.model;

import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


@ApiModel(description = "")
public class WorkDays {

    @SerializedName("workdays")
    private List<WorkDay> workdays = new ArrayList<WorkDay>();


    /**
     **/
    @ApiModelProperty(value = "")
    public List<WorkDay> getWorkdays() {
        return workdays;
    }

    public void setWorkdays(List<WorkDay> workdays) {
        this.workdays = workdays;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkDays workDays = (WorkDays) o;
        return Objects.equals(workdays, workDays.workdays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workdays);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WorkDays {\n");

        sb.append("    workdays: ").append(toIndentedString(workdays)).append("\n");
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
