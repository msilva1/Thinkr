package com.android.thinkr.activites;

import android.app.Activity;
import android.databinding.Observable;
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

import com.android.thinkr.BR;
import com.android.thinkr.R;
import com.android.thinkr.common.AccountController;
import com.android.thinkr.common.Preferences;
import com.android.thinkr.databinding.NavHeaderAdminBinding;
import com.android.thinkr.fragments.BaseFragment;
import com.android.thinkr.fragments.CommFragment;
import com.android.thinkr.fragments.LoginFragment;
import com.android.thinkr.fragments.SignupFragment;
import com.android.thinkr.fragments.ThinkrMainFragment;
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
        if (!Preferences.isSignedIn()) return new LoginFragment();
        else return new ThinkrMainFragment();
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
                        if (!Preferences.isSignedIn()) swapFragment(new LoginFragment());
                        DrawerLayout drawer = getActivityBinding().drawerLayout;
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }
        );

        headerBinding.setViewModel(NavHeaderViewModel.getInstance());
        getActivityBinding().navView.addHeaderView(headerBinding.getRoot());

        inflateNavMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        AccountController.getInstance().addOnPropertyChangedCallback(
                new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable sender, int propertyId) {
                        if (propertyId == BR.user) updateNavMenu();
                    }
                }
        );
    }

    private void inflateNavMenu() {
        // app:menu="@menu/activity_admin_drawer"
        // Dynamically inflate drawer menu based on user type
        User user = AccountController.getInstance().getUser();
        switch (user.getUserType()) {
            case Admin:
                getActivityBinding().navView.inflateMenu(R.menu.activity_admin_drawer);
                break;

            case Teacher:
                getActivityBinding().navView.inflateMenu(R.menu.activity_teacher_drawer);
                break;

            case Parent:
                getActivityBinding().navView.inflateMenu(R.menu.activity_parent_drawer);
                break;

            case Student:
                getActivityBinding().navView.inflateMenu(R.menu.activity_student_drawer);
                break;
            default:
                getActivityBinding().navView.inflateMenu(R.menu.activity_admin_drawer);

        }
    }

    public void updateNavMenu() {
        getActivityBinding().navView.getMenu().clear();
        inflateNavMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        User user = AccountController.getInstance().getUser();
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
                .logout(user.getUserId(), user.getPassword());
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Response<Account> response, Retrofit retrofit) {
                if (response.isSuccess() && response.body() != null) {
                    Preferences.setUser(response.body().getUser());
                    // TODO: 1/3/16 Need to make sure server is logged in too, currently, it might not be.
                    Preferences.signUserOut();
                    Toast.makeText(getBaseContext(), "User logged out", Toast.LENGTH_SHORT).show();
                    AdminActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateNavMenu();
                        }
                    });
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

        switch (id) {
            case R.id.nav_admin_approve:
            case R.id.nav_teacher_approve_assignment:
            case R.id.nav_parent_approve:
                approve();
                break;
            case R.id.nav_teacher_assign_student:
            case R.id.nav_admin_assign_student:
            case R.id.nav_parent_assign_to_student:
                assignToStudent();
                break;
            case R.id.nav_admin_create_account:
            case R.id.nav_teacher_create_account:
                createAccount();
                break;

            case R.id.nav_teacher_create_assignment:
                createAssignment();
                break;
        }

        DrawerLayout drawer = getActivityBinding().drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void approve() {
        swapFragment(new CommFragment());
    }

    private void assignToStudent() {

    }

    private void createAssignment() {

    }

    private void createAccount() {
        // Show user fragment
//        startActivity(new Intent(this, SignupActivity.class));
        swapFragment(new SignupFragment());
    }

}
