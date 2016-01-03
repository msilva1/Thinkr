package com.android.thinkr.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by M. Silva on 1/2/16.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If the hosting activity has a parent and actionbar display
        // the home button
        if (canNavigateUpFromSameTask() && getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * @return
     */
    public AppCompatActivity getSupportActivity() {
        return (AppCompatActivity) (getActivity());
    }

    /**
     * @return true if the activity has a parent defined.
     */
    protected boolean canNavigateUpFromSameTask() {
        return NavUtils.getParentActivityName(getSupportActivity()) != null;
    }

    @Nullable
    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }
}
