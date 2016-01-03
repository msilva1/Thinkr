package com.android.thinkr.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.thinkr.R;
import com.android.thinkr.activites.SignupActivity;
import com.android.thinkr.common.Preferences;
import com.android.thinkr.databinding.FragmentLoginBinding;
import com.android.thinkr.service.ThinkrServiceImpl;
import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.AccountValidation;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by M. Silva on 1/3/16.
 */
public class LoginFragment extends BaseFragment {

    public static final int USER_REGISTRATION = 1;

    private FragmentLoginBinding mBinding;
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private ProgressBar mProgressView;
    private ScrollView mLoginFormView;

    @Override
    public int getFragmentTitle() {
        return R.string.title_activity_login;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding = DataBindingUtil.bind(view);
        mUserView = mBinding.userId;

        mPasswordView = mBinding.password;
        mPasswordView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                        if (id == R.id.login || id == EditorInfo.IME_NULL) {
                            attemptLogin();
                            return true;
                        }
                        return false;
                    }
                }
        );

        Button mEmailSignInButton = mBinding.signInButton;
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button signUpButton = mBinding.signUpButton;
        signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), SignupActivity.class);
                        startActivityForResult(intent, USER_REGISTRATION);
                    }
                }
        );

        mLoginFormView = mBinding.loginForm;
        mProgressView = mBinding.loginProgress;

    }

    private void attemptLogin() {

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }

//        else if (!isEmailValid(email)) {
//            mUserView.setError(getString(R.string.error_invalid_email));
//            focusView = mUserView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            final Call<Account> call = ThinkrServiceImpl.getService().login(username, password);
            call.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Response<Account> response, Retrofit retrofit) {
                    final Account account = response.body();
                    if (response.isSuccess() && account != null) {
                        if ((account.getValidation().getAccountStatus() == AccountValidation.Account.Valid)) {
                            Preferences.setUser(response.body().getUser());
                            Preferences.signUserIn();

                            Toast.makeText(getContext(), "Successful call! logged in as " + account.getUser().getUserId(), Toast.LENGTH_SHORT).show();
                        } else {
                            mUserView.setError("Account does not exist. Please try again.");
                        }
                    }
                    showProgress(false);
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), "Unsuccessful call!", Toast.LENGTH_SHORT).show();
                }
            });
        }


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

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
