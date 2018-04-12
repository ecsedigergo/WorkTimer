package hu.bme.aut.worktimer.interactor;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.worktimer.interactor.event.EmployeeInteractor;

@Module
public class InteractorModule {
    @Provides
    public EmployeeInteractor provideEmployeeInteractor() {
        return new EmployeeInteractor();
    }

}