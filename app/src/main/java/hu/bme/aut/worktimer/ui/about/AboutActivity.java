package hu.bme.aut.worktimer.ui.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import hu.bme.aut.worktimer.R;
import hu.bme.aut.worktimer.WorkTimerApplication;

public class AboutActivity extends AppCompatActivity implements IAboutScreen {

    @Inject
    AboutPresenter aboutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WorkTimerApplication.injector.inject(this);

    }
}
