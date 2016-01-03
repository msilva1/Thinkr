package com.android.thinkr.viewmodels;

import android.databinding.Bindable;

import com.android.thinkr.R;
import com.bytes.hack.model.Assignment;

/**
 * Created by M. Silva on 1/3/16.
 */
public class AssignmentItemViewModel extends RecyclerItemViewModel {

    private Assignment mAssignment;

    public AssignmentItemViewModel(Assignment assignment) {
        mAssignment = assignment;
    }

    @Bindable
    public String getTitle() {
        return mAssignment.getName();
    }

    @Bindable
    public String getCategory() {
        return mAssignment.getCategory().name();
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_assignment;
    }
}
