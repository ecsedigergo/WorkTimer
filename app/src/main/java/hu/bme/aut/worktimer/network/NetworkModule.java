package hu.bme.aut.worktimer.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.network.api.UserApi;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().client(client).baseUrl(NetworkConfig.SERVICE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create()).build();

    }

    @Provides
    @Singleton
    public UserApi provideUserApi(){
        return new ApiClient().createService(UserApi.class);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        return builder.build();
    }

    @Provides
    @Singleton
    public OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient().newBuilder();
    }
}
