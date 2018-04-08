package hu.bme.aut.worktimer.network;

import android.support.annotation.RestrictTo;

import retrofit2.Retrofit;

import javax.inject.Singleton;

        import dagger.Module;
        import dagger.Provides;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());

    }

    @Provides
    @Singleton
    public EmployeeApi provideEmployeeApi(Retrofit.Builder builder){
        //TODO provide an employee
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Provides
    @Singleton
    public TokenApi provideTokenApi(Retrofit.Builder retrofitBuilder) {
        //TODO check correctness
        return retrofitBuilder.baseUrl(NetworkConfig.TOKEN_ENDPOINT_ADDRESS).build().create(TokenApi.class);
    }

}