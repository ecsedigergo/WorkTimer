package hu.bme.aut.worktimer;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;

public class TestHelper {

	public static void setTestInjector() {
		ShadowLog.stream = System.out;
		WorkTimerApplication application = (WorkTimerApplication) RuntimeEnvironment.application;
        WorkTimerApplicationComponent injector = DaggerTestComponent.builder().testModule(new TestModule(application.getApplicationContext())).build();
		application.setInjector(injector);
	}
}