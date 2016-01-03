package com.android.thinkr.adapters;

import android.databinding.BaseObservable;

import com.android.thinkr.R;
import com.android.thinkr.databinding.ListItemAssignmentBinding;
import com.android.thinkr.viewmodels.AssignmentItemViewModel;
import com.android.thinkr.viewmodels.RecyclerItemViewModel;

import java.security.InvalidParameterException;

/**
 * Created by M. Silva on 1/3/16.
 */
public class AssignmentRecyclerAdapter extends BaseRecyclerAdapter {

    @Override
    protected int getViewType(BaseObservable item) {
        if (item instanceof RecyclerItemViewModel) {
            return ((RecyclerItemViewModel) item).getLayoutId();
        } else { // Unsupported instances will result in an error
            throw new InvalidParameterException();
        }
    }

    @Override
    protected int getLayoutIdByViewType(int viewType) {
        if (viewType > 0) return viewType;
        return -1;
    }

    @Override
    protected void onBindViewHolderByViewType(int viewType, ViewBindingHolder holder, BaseObservable item) {
        switch (viewType) {
            case R.layout.list_item_assignment:
                ListItemAssignmentBinding binding = (ListItemAssignmentBinding) holder.binding;
                binding.setViewModel((AssignmentItemViewModel) item);
                break;
            default: // Unsupported view types are not handled
                throw new InvalidParameterException();
        }
    }
}
