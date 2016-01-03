package com.android.thinkr.activites;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.thinkr.R;
import com.android.thinkr.databinding.ActivityAdminBinding;
import com.android.thinkr.fragments.BaseFragment;

/**
 * Created by M. Silva on 1/3/16.
 */
public abstract class BaseFragmentActivity extends AppCompatActivity {
    private ActivityAdminBinding mBinding;
    private FragmentManager mFragmentManager;
    private int mFrameLayoutId;
    private Toolbar mToolbar;

    protected abstract BaseFragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        mFragmentManager = getSupportFragmentManager();
        mFrameLayoutId = R.id.content_frame;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        swapFragment(getFragment());
    }

    public void swapFragment(BaseFragment fragment) {
        if (getToolbar() != null) {
            getToolbar().setTitle(fragment.getFragmentTitle());
            setSupportActionBar(getToolbar());
        }
        mFragmentManager
                .beginTransaction()
                .replace(mFrameLayoutId, fragment)
                .commit();
    }

    protected ActivityAdminBinding getBinding() {
        return mBinding;
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }
}
