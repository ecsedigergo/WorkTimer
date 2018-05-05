package hu.bme.aut.worktimer.interactor.user.event;

import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.utils.NetworkEvent;


public class UserEvent extends NetworkEvent {
    private User user;

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
}
