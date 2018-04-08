package hu.bme.aut.worktimer.network;

import java.util.List;

import hu.bme.aut.worktimer.model.WorkDay;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Getting employee work datas
 * Created by ecsedigergo on 2018. 04. 08..
 */

public interface EmployeeApi {
    //TODO add dta access and query methods
    @GET("getWorkDays")
    Call<List<WorkDay>> getWorkDays(@Header("Authorization") String authorisation);
}
