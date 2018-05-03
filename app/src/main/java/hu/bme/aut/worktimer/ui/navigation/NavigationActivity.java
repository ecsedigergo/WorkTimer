package hu.bme.aut.worktimer.ui.navigation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import hu.bme.aut.worktimer.R;
import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.ui.about.AboutActivity;
import hu.bme.aut.worktimer.ui.login.LoginActivity;

public class NavigationActivity extends AppCompatActivity
        implements INavigationScreen, NavigationView.OnNavigationItemSelectedListener {

    //private String userName = "admin";

    private String mUserEmail = "admin@admin.com";
    
    @Inject
    NavigationPresenter navigationPresenter;
    
    @Override
    protected void onStart() {
        super.onStart();

        //userName = getIntent().getStringExtra(LoginActivity.USERNAME);
        navigationPresenter.attachScreen(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigationPresenter.detachScreen();
    }
    Calendar myCalendar = Calendar.getInstance();
    private void addWorkDay() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        //TODO create a workday and add it to the list also
        Toast.makeText(getApplicationContext(), sdf.format(myCalendar.getTime()), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOnMain);
        setSupportActionBar(toolbar);

        WorkTimerApplication.injector.inject(this);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                addWorkDay();
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addWD);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NavigationActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mUserEmail = getIntent().getStringExtra(LoginActivity.USEREMAIL);
        if (navigationView.getHeaderCount() == 1) {
            View headerView = navigationView.getHeaderView(0);
            TextView t = (TextView) headerView.findViewById(R.id.textViewOnNavEmail);
            t.setText(mUserEmail);
            TextView navigationNameText = (TextView) headerView.findViewById(R.id.textViewOnNavName);
            navigationNameText.setText(mUserEmail.substring(0, mUserEmail.indexOf('@')));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_checks) {
            // Handle the camera action
        } else if (id == R.id.nav_checks) {

        } else if (id == R.id.nav_about) {
            navigateToAboutPage();
        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToAboutPage() {
        Intent aboutIntent = new Intent(NavigationActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }
}
