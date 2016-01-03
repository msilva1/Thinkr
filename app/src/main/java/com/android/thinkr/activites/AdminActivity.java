package com.android.thinkr.activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.thinkr.R;
import com.android.thinkr.common.Preferences;
import com.android.thinkr.databinding.NavHeaderAdminBinding;
import com.android.thinkr.fragments.BaseFragment;
import com.android.thinkr.fragments.CommFragment;
import com.android.thinkr.fragments.LoginFragment;
import com.android.thinkr.fragments.SignupFragment;
import com.android.thinkr.service.ThinkrServiceImpl;
import com.android.thinkr.viewmodels.NavHeaderViewModel;
import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.User;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AdminActivity extends BaseFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected BaseFragment getFragment() {
        return new SignupFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final NavHeaderAdminBinding headerBinding =
                NavHeaderAdminBinding.inflate(getLayoutInflater());
        headerBinding.imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swapFragment(new LoginFragment());
                        DrawerLayout drawer = getBinding().drawerLayout;
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
        );
        headerBinding.setViewModel(NavHeaderViewModel.getInstance());
        getBinding().navView.addHeaderView(headerBinding.getRoot());

        // app:menu="@menu/activity_admin_drawer"
        // Dynamically inflate drawer menu based on user type
        User user = Preferences.getUser();
        switch (user.getUserType()) {
            case Admin:
                getBinding().navView.inflateMenu(R.menu.activity_admin_drawer);
                break;

            case Teacher:
                getBinding().navView.inflateMenu(R.menu.activity_teacher_drawer);
                break;

            case Parent:
                getBinding().navView.inflateMenu(R.menu.activity_parent_drawer);
                break;

            case Student:
                getBinding().navView.inflateMenu(R.menu.activity_student_drawer);
                break;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        User user = Preferences.getUser();
        switch (user.getUserType()) {
            case Admin:
                getMenuInflater().inflate(R.menu.admin, menu);
                break;

            case Teacher:
                getMenuInflater().inflate(R.menu.teacher, menu);
                break;

            case Parent:
                getMenuInflater().inflate(R.menu.parent, menu);
                break;

            case Student:
                getMenuInflater().inflate(R.menu.student, menu);
                break;
        }

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
        } else if (id == R.id.action_signout) {

            signout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void signout() {

        User user = Preferences.getUser();
        final Call<Account> call = ThinkrServiceImpl.getService()
                .logOut(user.getUserId(), user.getPassword());
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Response<Account> response, Retrofit retrofit) {
                if (response.isSuccess() && response.body() != null) {
                    Preferences.setUser(response.body().getUser());
                    Preferences.signUserOut(); // Need to make sure server is logged in too, currently, it might not be.
                    Toast.makeText(getBaseContext(), "User logged out", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);

                } else {
                    Toast.makeText(getBaseContext(), "Unable to log user out", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_admin_approve) {
        } else if (id == R.id.nav_admin_assign_student) {
        } else if (id == R.id.nav_admin_create_account) {
            assignToStudent();

        } else if (id == R.id.nav_admin_approve) {
            approveAssignment();

        } else if (id == R.id.nav_teacher_approve_assignment) {
            approve();
        } else if (id == R.id.nav_teacher_assign_student) {
        } else if (id == R.id.nav_teacher_create_account) {
            createAccount();
        } else if (id == R.id.nav_teacher_create_assignment) {
        } else if (id == R.id.nav_teacher_view_assignment) {
        } else if (id == R.id.nav_common_send) {
        } else if (id == R.id.nav_common_talk) {
        } else if (id == R.id.nav_student_view_assignment) {
        } else if (id == R.id.nav_parent_approve) {
        } else if (id == R.id.nav_parent_assign_to_student) {
        } else if (id == R.id.nav_parent_view_assignment) {
        }

        DrawerLayout drawer = getBinding().drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void approve() {
        swapFragment(new CommFragment());
    }

    private void assignToStudent() {

    }

    private void approveAssignment() {

    }

    private void createAssignment() {

    }

    private void createAccount() {

        // Show user fragment
        startActivity(new Intent(this, SignupActivity.class));

    }

}
