package hu.bme.aut.worktimer;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.aut.worktimer.interactor.InteractorModule;
import hu.bme.aut.worktimer.mock.MockNetworkModule;
import hu.bme.aut.worktimer.repository.TestRepositoryModule;

@Singleton
@Component(modules = {MockNetworkModule.class, TestModule.class, InteractorModule.class, TestRepositoryModule.class})
public interface TestComponent extends WorkTimerApplicationComponent {
}