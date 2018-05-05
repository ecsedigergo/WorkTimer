package hu.bme.aut.worktimer.ui.login;


public interface ILoginScreen {
    void showLoginFailed();

    void showNetworkError(String message);

    void showLoginSuccessful(String username);

    void showRegisterSuccessful(String username);
}
