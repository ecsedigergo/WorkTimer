package hu.bme.aut.worktimer.ui.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import hu.bme.aut.worktimer.R;
import hu.bme.aut.worktimer.WorkTimerApplication;

public class AboutActivity extends AppCompatActivity implements IAboutScreen {

    @Inject
    AboutPresenter aboutPresenter;
    private Tracker mTracker;

    @Override
    protected void onStart() {
        super.onStart();


        mTracker.setScreenName("Image~" + "AboutActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onResume(){
        super.onResume();
        mTracker.setScreenName("Image~" + "AboutActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WorkTimerApplication.injector.inject(this);

        // Obtain the shared Tracker instance.
        WorkTimerApplication application = (WorkTimerApplication) getApplication();
        mTracker = application.getDefaultTracker();

    }
    public void forceCrash(View view){
        throw new RuntimeException("Crash forced! Thanks.");
    }
}
