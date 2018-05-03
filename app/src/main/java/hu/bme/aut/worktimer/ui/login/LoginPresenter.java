package hu.bme.aut.worktimer.ui.login;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.di.Network;
import hu.bme.aut.worktimer.interactor.User.UserInteractor;
import hu.bme.aut.worktimer.interactor.User.event.auth.LoginEvent;
import hu.bme.aut.worktimer.network.model.User;
import hu.bme.aut.worktimer.ui.Presenter;

@Singleton
public class LoginPresenter extends Presenter<ILoginScreen> {
    @Inject
    UserInteractor userInteractor;

    @Inject
    @Network
    Executor executor;

    @Override
    public void attachScreen(ILoginScreen loginScreen) {
        super.attachScreen(loginScreen);
        WorkTimerApplication.injector.inject(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
        EventBus.getDefault().unregister(this);
    }

    public void login(final String username, final String password) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userInteractor.login(setupUser(username, password));
            }
        });

    }

    public void register(final String username, final String password) {
        userInteractor.register(setupUser(username, password));
    }

    private User setupUser(final String username, final String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        return u;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final LoginEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkError(event.getThrowable().getMessage());
            }
        } else if (screen != null) {
            if (event.getCode() == 200) {
                screen.showLoginSuccessful(event.getUser().getUsername());
            } else {
                screen.showLoginFailed();
            }
        }
    }
}
