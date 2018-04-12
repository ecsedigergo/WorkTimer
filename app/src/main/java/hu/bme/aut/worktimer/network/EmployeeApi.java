package hu.bme.aut.worktimer.network;

import java.util.Date;
import java.util.List;

import hu.bme.aut.worktimer.model.WorkDay;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Getting employee work datas
 * Created by ecsedigergo on 2018. 04. 08..
 */

public interface EmployeeApi {
    @GET("getWorkDays")
    Call<List<WorkDay>> getWorkDays(@Header("Authorization") String authorisation);
    @GET("auth")
    Call<String> getAccesToken(@Field("username")String username, @Field("password")String password);
    @PUT("checkin")
    Call<Void> checkin(@Field("in")Date date);
    @PUT("checkout")
    Call<Void> checkout(@Field("out")Date date);
}
