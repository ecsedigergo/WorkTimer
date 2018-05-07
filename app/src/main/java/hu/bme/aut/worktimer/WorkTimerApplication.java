package hu.bme.aut.worktimer;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import hu.bme.aut.worktimer.repository.Repository;
import hu.bme.aut.worktimer.repository.RepositoryModule;
import hu.bme.aut.worktimer.ui.UIModule;

/**
 * Application
 * Created by ecsedigergo on 2018. 04. 09..
 */

public class WorkTimerApplication extends Application {
    public static WorkTimerApplicationComponent injector;
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        injector = DaggerWorkTimerApplicationComponent.builder().
                uIModule(new UIModule(this))
                .build();
        injector.inject(this);

        sAnalytics = GoogleAnalytics.getInstance(this);
        sAnalytics.setLocalDispatchPeriod(15);
    }

    public void setInjector(WorkTimerApplicationComponent appComponent) {
        injector = appComponent;
        injector.inject(this);
    }
    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
