package hu.bme.aut.worktimer.interactor.event;

import javax.inject.Inject;

import hu.bme.aut.worktimer.network.EmployeeApi;
import hu.bme.aut.worktimer.network.TokenApi;

/**
 * Call back to network and repo for datas
 * Created by ecsedigergo on 2018. 04. 08..
 */

public class EmployeeInteractor {
    @Inject
    EmployeeApi employeeApi;

    @Inject
    TokenApi tokenApi;

    public EmployeeInteractor(){
        //TODO implement class for getting employee classes
    }

//    Methods from Employee and token APIs
}
