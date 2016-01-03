package com.android.thinkr.fragments;

import com.android.thinkr.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ThinkrMainFragment extends BaseFragment {

    @Override
    public int getFragmentTitle() {
        return R.string.app_name;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_thinkr_main;
    }
}
