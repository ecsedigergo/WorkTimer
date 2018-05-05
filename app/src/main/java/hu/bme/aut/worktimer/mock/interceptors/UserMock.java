package hu.bme.aut.worktimer.mock.interceptors;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hu.bme.aut.worktimer.model.orm.UserORMModel;
import hu.bme.aut.worktimer.model.orm.WorkDayORMModel;
import hu.bme.aut.worktimer.network.NetworkConfig;
import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.network.model.WorkDay;
import hu.bme.aut.worktimer.network.model.WorkDays;
import hu.bme.aut.worktimer.repository.MemoryRepository;
import hu.bme.aut.worktimer.utils.GsonHelper;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by ecsedigergo on 2018. 04. 22..
 */

class UserMock {
    private static Pattern PATTERN_GETALLWORKDAYS = Pattern.compile(Pattern.quote(NetworkConfig.ENDPOINT_PREFIX + "user/") + "([^\\/]+)" + Pattern.quote("/WorkDays/All"));
    private static Pattern PATTERN_ADDWORKDAY = Pattern.compile(Pattern.quote(NetworkConfig.ENDPOINT_PREFIX + "user/") + "([^\\/]+)" + Pattern.quote("/WorkDay"));
    private static MemoryRepository repository = new MemoryRepository();

    static {
        repository.open(null);
    }

    public static Response process(Request request) {
        Uri uri = Uri.parse(request.url().toString());

        String responseString = "ERROR";
        int responseCode = 503;
        Headers headers = request.headers();
        try {

            String uripath = uri.getPath();
            if (request.method().equals("GET")) {
                //getallworkdays
                Matcher allwdm = PATTERN_GETALLWORKDAYS.matcher(uripath);
                if (allwdm.matches()) {
                    responseCode = 400;
                    responseString = "ERROR";
                    String username = allwdm.group(1);
                    for (UserORMModel user : repository.getUsers()) {
                        if (username.equals(user.getUsername())) {
                            responseCode = 200;
                            WorkDays days = new WorkDays();
                            List<WorkDay> dayslist = new ArrayList<>();
                            for (WorkDayORMModel workDayORMModel : user.getWorkdays()) {
                                WorkDay wd = new WorkDay();
                                wd.setCheckin(workDayORMModel.getCheckindate());
                                wd.setCheckout(workDayORMModel.getCheckoutdate());
                                dayslist.add(wd);
                            }
                            days.setWorkdays(dayslist);

                            responseString = GsonHelper.getGson().toJson(days);
                            break;
                        }
                    }
                } else if (uripath.startsWith(NetworkConfig.ENDPOINT_PREFIX + "user/")) {
                    //getuserbyname
                    String username = uripath.substring(NetworkConfig.ENDPOINT_PREFIX.length() + "user/".length());
                    for (UserORMModel user : repository.getUsers()) {
                        if (username.equals(user.getUsername())) {
                            responseString = GsonHelper.getGson().toJson(user);
                            responseCode = 200;
                            break;
                        }
                    }
                }

            } else if (request.method().equals("POST")) {
                Matcher addwdm = PATTERN_ADDWORKDAY.matcher(uripath);
                if (addwdm.matches()) {
                    responseCode = 400;
                    responseString = "ERROR";
                    String username = addwdm.group(1);
                    for (UserORMModel user : repository.getUsers()) {
                        if (username.equals(user.getUsername())) {
                            WorkDay param = GsonHelper.getGson().fromJson(MockHelper.bodyToString(request), WorkDay.class);
                            user.getWorkdays().add(new WorkDayORMModel(param.getCheckin(), param.getCheckout()));
                            responseCode = 200;
                            responseString = "OK";
                            break;
                        }
                    }
                } else if (uripath.equals(NetworkConfig.ENDPOINT_PREFIX + "user")) {
                    //register
                    User param = GsonHelper.getGson().fromJson(MockHelper.bodyToString(request), User.class);
                    responseCode = 200;
                    responseString = "OK";
                    for (UserORMModel user : repository.getUsers()) {
                        if (param.getUsername().equals(user.getUsername())) {
                            responseCode = 400;
                            responseString = "Invalid username";
                            break;
                        }
                    }
                    if (responseCode == 200) {
                        repository.saveUser(new UserORMModel(param.getUsername(), param.getPassword(), new ArrayList<>()));
                    }
                } else if (uripath.equals(NetworkConfig.ENDPOINT_PREFIX + "user/login")) {
                    //login
                    User param = GsonHelper.getGson().fromJson(MockHelper.bodyToString(request), User.class);
                    responseCode = 400;
                    responseString = "Invalid username or password";
                    for (UserORMModel user : repository.getUsers()) {
                        if (param.getUsername().equals(user.getUsername())) {
                            if (param.getPassword().equals(user.getPassword())) {
                                responseCode = 200;
                                responseString = "OK";
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            return MockHelper.makeResponse(request, headers, responseCode, responseString);
        } catch (Throwable t) {
            t.printStackTrace();
            return MockHelper.makeResponse(request, headers, 503, "ERROR");
        }


    }
}
