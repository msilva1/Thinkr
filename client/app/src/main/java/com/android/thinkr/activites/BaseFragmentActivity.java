package com.android.thinkr.activites;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.android.thinkr.R;
import com.android.thinkr.databinding.ActivityAdminBinding;
import com.android.thinkr.fragments.BaseFragment;

/**
 * Created by M. Silva on 1/3/16.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityAdminBinding mBinding;
    private FragmentManager mFragmentManager;
    private int mFrameLayoutId;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;

    protected abstract BaseFragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin);

        mFragmentManager = getSupportFragmentManager();
        mFrameLayoutId = R.id.content_frame;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(getToolbar());
        mToggle = new ActionBarDrawerToggle(
                this,
                mBinding.drawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mBinding.drawerLayout.setDrawerListener(mToggle);
        mBinding.navView.setNavigationItemSelectedListener(this);

        swapFragment(getFragment());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mToggle.syncState();
    }

    public void swapFragment(BaseFragment fragment) {
        mFragmentManager
                .beginTransaction()
                .replace(mFrameLayoutId, fragment)
                .commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(fragment.getFragmentTitle());
            mToggle.syncState();
        }

        hideKeyboard();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        DrawerLayout drawer = mBinding.drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    protected ActivityAdminBinding getActivityBinding() {
        return mBinding;
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
