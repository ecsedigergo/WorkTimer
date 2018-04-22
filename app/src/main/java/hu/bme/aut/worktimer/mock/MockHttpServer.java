package hu.bme.aut.worktimer.mock;

import hu.bme.aut.worktimer.mock.interceptors.MockInterceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ecsedigergo on 2018. 04. 22..
 */

class MockHttpServer {
    public static Response call(Request req) {
        MockInterceptor mockInterceptor = new MockInterceptor();
        return mockInterceptor.process(req);
    }
}
