package hu.bme.aut.worktimer.mock.interceptors;

import android.net.Uri;

import java.io.IOException;

import hu.bme.aut.worktimer.network.NetworkConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ecsedigergo on 2018. 04. 22..
 */

public class MockInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)throws IOException{
        return process(chain.request());
    }

    public Response process(Request request) {
        Uri uri = Uri.parse(request.url().toString());

        //TODO create a response for the specific request throw okhttp3
        if(uri.getPath().startsWith(NetworkConfig.ENDPOINT_PREFIX + "User")){
            return UserMock.process(request);
        }

        return null;
    }

}
