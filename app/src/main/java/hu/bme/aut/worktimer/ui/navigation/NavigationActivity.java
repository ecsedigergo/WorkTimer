package hu.bme.aut.worktimer.ui.navigation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.worktimer.R;
import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.model.WorkDay;
import hu.bme.aut.worktimer.model.orm.UserORMModel;
import hu.bme.aut.worktimer.model.orm.WorkDayORMModel;
import hu.bme.aut.worktimer.network.model.WorkDays;
import hu.bme.aut.worktimer.repository.Repository;
import hu.bme.aut.worktimer.ui.about.AboutActivity;
import hu.bme.aut.worktimer.ui.login.LoginActivity;

public class NavigationActivity extends AppCompatActivity
        implements INavigationScreen, NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_USERNAME = "username";

    private class WorkDayAdapter extends BaseAdapter {

        private List<WorkDay> elements = Collections.emptyList();

        public void setElements(List<WorkDay> elements) {
            this.elements = elements;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public WorkDay getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(NavigationActivity.this).inflate(R.layout.workday_list_item, null);
            }
            TextView date = convertView.findViewById(R.id.date_text);
            TextView start = convertView.findViewById(R.id.start_time_text);
            TextView end = convertView.findViewById(R.id.end_time_text);
            WorkDay item = getItem(position);
            date.setText(String.format("%d.%02d.%02d", item.getCheckin().getYear() + 1900, item.getCheckin().getMonth() + 1, item.getCheckin().getDate()));
            start.setText(String.format("%02d:%02d", item.getCheckin().getHours(), item.getCheckin().getMinutes()));
            end.setText(String.format("%02d:%02d", item.getCheckout().getHours(), item.getCheckout().getMinutes()));
            return convertView;
        }
    }

    private String mUserEmail = "admin@admin.com";
    private ListView list;
    private WorkDayAdapter adapter;
    private DrawerLayout drawer;
    private Calendar calendar = Calendar.getInstance();

    private UserORMModel ormUser;

    @Inject
    NavigationPresenter navigationPresenter;

    @Inject
    Repository repository;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigationPresenter.detachScreen();

        repository.close();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WorkTimerApplication.injector.inject(this);
        mUserEmail = getIntent().getStringExtra(EXTRA_USERNAME);

        setContentView(R.layout.activity_navigation);

        list = findViewById(R.id.workdays_list);
        adapter = new WorkDayAdapter();

        list.setAdapter(adapter);

        repository.open(getApplicationContext());

        //set datepicker
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Date currentdate = new Date();

                TimePickerDialog starttimepicker = new TimePickerDialog(NavigationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int starthourOfDay, int startminute) {
                        TimePickerDialog endtimepicker = new TimePickerDialog(NavigationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int endhourOfDay, int endminute) {
                                addWorkDay(year, monthOfYear, dayOfMonth, starthourOfDay, startminute, endhourOfDay, endminute);
                            }
                        }, (starthourOfDay + 8) % 24, startminute, true);
                        endtimepicker.setTitle("End time");
                        endtimepicker.show();
                    }
                }, currentdate.getHours(), currentdate.getMinutes(), true);
                starttimepicker.setTitle("Start time");
                starttimepicker.show();
            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addWD);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NavigationActivity.this, date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set username email and name on navigation tab bar
        if (navigationView.getHeaderCount() == 1) {
            View headerView = navigationView.getHeaderView(0);
            TextView t = (TextView) headerView.findViewById(R.id.textViewOnNavEmail);
            t.setText(mUserEmail);
            TextView navigationNameText = (TextView) headerView.findViewById(R.id.textViewOnNavName);
            navigationNameText.setText(mUserEmail.substring(0, mUserEmail.indexOf('@')));
        }

        navigationPresenter.attachScreen(this);
        if (!setCachedWorkDays()) {
            navigationPresenter.getWorkDays(mUserEmail);
        }
    }

    //Determines if there is a cached workday list for the users and sets that to the adapter
    private boolean setCachedWorkDays() {
        for (UserORMModel u : repository.getUsers()) {
            if (mUserEmail.equals(u.getUsername())) {
                ormUser = u;
                List<WorkDay> days = new ArrayList<>();
                for (WorkDayORMModel wd : u.getWorkdays()) {
                    WorkDay d = new WorkDay();
                    d.setCheckin(wd.getCheckindate());
                    d.setCheckout(wd.getCheckoutdate());
                    days.add(d);
                }
                adapter.setElements(days);
                return true;
            }
        }
        ormUser = new UserORMModel(mUserEmail, "*", new ArrayList<>());
        return false;
    }

    private void addWorkDay(int year, int month, int day, int starthour, int startminute, int endhour, int endminute) {
        navigationPresenter.addWorkDay(mUserEmail, year, month, day, starthour, startminute, endhour, endminute);
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (!drawer.isDrawerOpen(drawer)) {
                drawer.openDrawer(drawer);
            }
            return true;
        }
        return super.onKeyDown(keyCode, e);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_checks) {
            item.setChecked(false);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        } else if (id == R.id.nav_about) {
            navigateToAboutPage();
        } else if (id == R.id.nav_logout) {
            navigationPresenter.logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void navigateToAboutPage() {
        Intent aboutIntent = new Intent(NavigationActivity.this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    @Override
    public void logout() {
        Intent loginIntent = new Intent(NavigationActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void workDayQueryFailed(String message) {
        networkError(message);
    }

    private void networkError(String message) {
        Toast.makeText(getApplicationContext(), "Network error: " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addWorkDayFailed(String message) {
        networkError(message);
    }

    //Reload the workdays for the ORMUser and for the adapter also
    @Override
    public void workDaysQueried(WorkDays workDays) {
        List<WorkDayORMModel> ormdays = ormUser.getWorkdays();
        ormdays.clear();
        List<WorkDay> days = new ArrayList<>();
        for (hu.bme.aut.worktimer.network.model.WorkDay wd : workDays.getWorkdays()) {
            days.add(new WorkDay(wd.getCheckin(), wd.getCheckout()));
            ormdays.add(new WorkDayORMModel(wd.getCheckin(), wd.getCheckout()));
        }
        adapter.setElements(days);
        repository.saveUser(ormUser);
    }

    @Override
    public void workDayAdded() {
        navigationPresenter.getWorkDays(mUserEmail);
    }
}
