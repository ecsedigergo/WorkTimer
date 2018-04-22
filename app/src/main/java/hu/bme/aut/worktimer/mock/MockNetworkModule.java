package hu.bme.aut.worktimer.mock;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.network.NetworkModule;
import hu.bme.aut.worktimer.network.api.UserApi;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by ecsedigergo on 2018. 04. 22..
 */
@Module
public class MockNetworkModule {
    private NetworkModule networkModule = new NetworkModule();

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        builder.interceptors().add(3, new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request req = chain.request();
                return MockHttpServer.call(req);
            }
        });
        return builder.build();
    }

    @Provides
    @Singleton
    public OkHttpClient.Builder provideOkHttpClientBuilder(){
        return networkModule.provideOkHttpClientBuilder();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client){
        return networkModule.provideRetrofit(client);
    }

    @Provides
    @Singleton
    public UserApi provideUserApi(){
        return networkModule.provideUserApi();
    }

}
