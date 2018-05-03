package hu.bme.aut.worktimer.interactor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.interactor.User.UserInteractor;

@Module
public class InteractorModule {
    //@Singleton
    @Provides
    public UserInteractor provideUserInteractor(){return new UserInteractor();}
}
