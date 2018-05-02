package hu.bme.aut.worktimer.ui.login;


public interface ILoginScreen {
    void navigateToMainMenu();
    void showLoginFailed();
    void showSuccessfulRegistration();
    void showNetworkError(String message);
    void showLoginSuccessful();
}
