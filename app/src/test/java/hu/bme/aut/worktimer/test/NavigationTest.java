package hu.bme.aut.worktimer.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.annotation.Config;

import java.util.Date;

import hu.bme.aut.worktimer.BuildConfig;
import hu.bme.aut.worktimer.network.model.WorkDays;
import hu.bme.aut.worktimer.ui.navigation.INavigationScreen;
import hu.bme.aut.worktimer.ui.navigation.NavigationPresenter;
import hu.bme.aut.worktimer.utils.RobolectricDaggerTestRunner;

import static hu.bme.aut.worktimer.TestHelper.setTestInjector;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class NavigationTest {
    private NavigationPresenter presenter;

    @Before
    public void setup() throws Exception {
        setTestInjector();
        presenter = new NavigationPresenter();
    }

    @Test
    public void testQuery() {
        INavigationScreen screen = mock(INavigationScreen.class);
        presenter.attachScreen(screen);
        presenter.getWorkDays("user@example.com");

        ArgumentCaptor<WorkDays> captor = ArgumentCaptor.forClass(WorkDays.class);
        verify(screen, times(1)).workDaysQueried(captor.capture());

        WorkDays captured = captor.getValue();
        //Dates copied from MemoryRepository
        assertEquals(new Date(2018 - 1900, 4, 4, 12, 00), captured.getWorkdays().get(0).getCheckin());
        assertEquals(new Date(2018 - 1900, 4, 5, 8, 00), captured.getWorkdays().get(1).getCheckin());
    }

    @Test
    public void testAddWorkDay() {
        INavigationScreen screen = mock(INavigationScreen.class);
        presenter.attachScreen(screen);
        presenter.addWorkDay("user@example.com", 2018, 5, 1, 12, 00, 16, 00);

        verify(screen, times(1)).workDayAdded();
    }

    @Test
    public void testLogout(){
        INavigationScreen screen = mock(INavigationScreen.class);
        presenter.attachScreen(screen);
        presenter.logout();

        verify(screen, times(1)).logout();
    }

    @After
    public void tearDown() {
        presenter.detachScreen();
    }
}
