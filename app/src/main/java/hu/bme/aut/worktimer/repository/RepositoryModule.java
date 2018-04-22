package hu.bme.aut.worktimer.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for database by dagger
 * Created by ecsedigergo on 2018. 04. 22..
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public Repository provideRepository(){return new SugarORMRepository();}
}
