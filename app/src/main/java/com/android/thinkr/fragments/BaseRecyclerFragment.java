package com.android.thinkr.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.thinkr.R;
import com.android.thinkr.adapters.BaseRecyclerAdapter;
import com.android.thinkr.databinding.FragmentBaseRecyclerBinding;

/**
 * Created by M. Silva on 1/3/16.
 */
public abstract class BaseRecyclerFragment extends BaseFragment {

    private BaseRecyclerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    protected abstract BaseRecyclerAdapter getAdapter();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentBaseRecyclerBinding binding = DataBindingUtil.getBinding(view);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(binding.recyclerView.getContext()));
        binding.swipeRefreshLayout.setEnabled(false);

        binding.recyclerView.setAdapter(getAdapter());
    }
}
