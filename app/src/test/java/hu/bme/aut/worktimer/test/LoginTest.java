package hu.bme.aut.worktimer.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.annotation.Config;

import hu.bme.aut.worktimer.BuildConfig;
import hu.bme.aut.worktimer.ui.login.ILoginScreen;
import hu.bme.aut.worktimer.ui.login.LoginPresenter;
import hu.bme.aut.worktimer.utils.RobolectricDaggerTestRunner;

import static hu.bme.aut.worktimer.TestHelper.setTestInjector;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginTest {
    private LoginPresenter presenter;

    @Before
    public void setup() throws Exception {
        setTestInjector();
        presenter = new LoginPresenter();
    }

    @Test
    public void test() {
        ILoginScreen screen = mock(ILoginScreen.class);
        presenter.attachScreen(screen);
        presenter.login("user@example.com", "abcd1234");

        verify(screen, times(1)).showLoginSuccessful("user@example.com");
    }

    @After
    public void tearDown() {
        presenter.detachScreen();
    }
}
