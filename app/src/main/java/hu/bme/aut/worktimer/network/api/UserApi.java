package hu.bme.aut.worktimer.network.api;

import hu.bme.aut.worktimer.network.CollectionFormats;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;

import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.network.model.WorkDay;
import hu.bme.aut.worktimer.network.model.WorkDays;
import java.util.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserApi {
  
  /**
   * Create user
   * This can only be done by the not logged in user.
   * @param body Created user object
   * @return Call<Void>
   */
  
  @POST("user")
  Call<Void> createUser(
    @Body User body
  );

  
  /**
   * Logs user into
   * post query for logging in user
   * @param user User to be logged in
   * @return Call<Void>
   */
  
  @POST("user/login")
  Call<Void> loginUser(
    @Body User user
  );

  
  /**
   * Logs user out
   * Logs out a user, who&#39;s username is in the body
   * @param user User to be logged out
   * @return Call<Void>
   */
  
  @POST("user/logout")
  Call<Void> logoutUser(
    @Body User user
  );

  
  /**
   * Get user by user name
   * 
   * @param username The name that needs to be fetched. Use user1 for testing.
   * @return Call<User>
   */
  
  @GET("user/{username}")
  Call<User> getUserByName(
    @Path("username") String username
  );

  
  /**
   * Find workday by check in date
   * Returns a single workday
   * @param username The name that needs to be fetched. Use user1 for testing.
   * @param checkinDate checkin date of workday to return
   * @return Call<WorkDay>
   */
  
  @GET("user/{username}/WorkDay")
  Call<WorkDay> getWorkDayByCheckinDate(
    @Path("username") String username, @Query("checkinDate") Date checkinDate
  );

  
  /**
   * Add a new workday
   * 
   * @param username The name that needs to be fetched.
   * @param body WorkDay object that needs to be added to the store
   * @return Call<Void>
   */
  
  @POST("user/{username}/WorkDay")
  Call<Void> addWorkDay(
    @Path("username") String username, @Body WorkDay body
  );

  
  /**
   * Get all workdays for specific user
   * Returns user
   * @param username The name that needs to be fetched. Use user1 for testing.
   * @return Call<WorkDays>
   */
  
  @GET("user/{username}/WorkDays/All")
  Call<WorkDays> getAllWorkdays(
    @Path("username") String username
  );

  
}
