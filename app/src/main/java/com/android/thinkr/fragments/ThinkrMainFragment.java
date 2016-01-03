package com.android.thinkr.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.thinkr.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThinkrMainFragment extends Fragment {

    public ThinkrMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thinkr_main, container, false);
    }
}
