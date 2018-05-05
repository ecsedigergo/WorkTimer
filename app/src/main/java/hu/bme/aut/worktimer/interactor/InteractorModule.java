package hu.bme.aut.worktimer.interactor;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.interactor.user.UserInteractor;

@Module
public class InteractorModule {
    //@Singleton
    @Provides
    public UserInteractor provideUserInteractor(){return new UserInteractor();}
}
