package com.android.thinkr.fragments;

import com.android.thinkr.R;

/**
 * Created by M. Silva on 1/3/16.
 */
public class LoginFragment extends BaseFragment {
    @Override
    public int getFragmentTitle() {
        return R.string.title_activity_login;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }
}
