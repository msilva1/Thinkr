package com.android.thinkr.activites;

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

import com.android.thinkr.R;
import com.android.thinkr.databinding.NavHeaderAdminBinding;
import com.android.thinkr.fragments.BaseFragment;
import com.android.thinkr.fragments.LoginFragment;
import com.android.thinkr.fragments.SignupFragment;
import com.android.thinkr.viewmodels.NavHeaderViewModel;

public class AdminActivity extends BaseFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

//        setContentView(R.layout.activity_admin);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
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

        if (id == R.id.nav_createAccount) {
            createAccount();

        } else if (id == R.id.nav_createAssignment) {
            createAssignment();

        } else if (id == R.id.nav_assignToStudent) {
            assignToStudent();

        } else if (id == R.id.nav_approveAssignment) {
            approveAssignment();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = getBinding().drawerLayout;
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    protected BaseFragment getFragment() {
        return new SignupFragment();
    }
}
