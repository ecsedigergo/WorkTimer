package hu.bme.aut.worktimer.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.network.api.UserApi;

@Module
public class NetworkModule {
    @Singleton
    @Provides
    public UserApi provideUserApi(){
        return new ApiClient().createService(UserApi.class);
    }
}
