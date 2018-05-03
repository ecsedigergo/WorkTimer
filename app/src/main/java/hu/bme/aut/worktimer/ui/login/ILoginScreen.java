package hu.bme.aut.worktimer.ui.login;


public interface ILoginScreen {
    void navigateToMainMenu(String message);
    void showLoginFailed();
    void showSuccessfulRegistration();
    void showNetworkError(String message);
    void showLoginSuccessful();
}
