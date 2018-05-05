package hu.bme.aut.worktimer.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hu.bme.aut.worktimer.R;
import hu.bme.aut.worktimer.WorkTimerApplication;
import hu.bme.aut.worktimer.repository.Repository;
import hu.bme.aut.worktimer.ui.navigation.NavigationActivity;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ILoginScreen {
    @Inject
    LoginPresenter loginPresenter;


    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.attachScreen(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachScreen();
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
        showProgress(false);
    }

    @Override
    public void showNetworkError(String message) {
        Toast.makeText(getApplicationContext(), "Network error: " + message, Toast.LENGTH_LONG).show();
        showProgress(false);
    }

    @Override
    public void showLoginSuccessful(String username) {
        Intent mainIntent = new Intent(this, NavigationActivity.class);
        mainIntent.putExtra(NavigationActivity.EXTRA_USERNAME, username);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void showRegisterSuccessful(String username) {
        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
        Intent mainIntent = new Intent(this, NavigationActivity.class);
        mainIntent.putExtra(NavigationActivity.EXTRA_USERNAME, username);
        startActivity(mainIntent);
        finish();
    }

    // UI references.
    private TextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WorkTimerApplication.injector.inject(this);

        // Set up the login form.
        mEmailView = (TextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptRegister() {
        if (mProgressView.getVisibility() == View.VISIBLE) {
            //already logging in
            return;
        }
        View focusview = checkValidInput();
        if (focusview != null) {
            focusview.requestFocus();
        } else {
            showProgress(true);
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            loginPresenter.register(email, password);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mProgressView.getVisibility() == View.VISIBLE) {
            //already logging in
            return;
        }

        View focusview = checkValidInput();
        if (focusview != null) {
            focusview.requestFocus();
        } else {
            showProgress(true);
            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();
            loginPresenter.login(email, password);
        }
    }

    /**
     * Checks the validity of the e-mail and password.
     *
     * @return A view to set the focus on in case of invalid input.
     * null if the inputs are valid and further progress can be made
     */
    private View checkValidInput() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            return focusView;
        }
        return null;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

