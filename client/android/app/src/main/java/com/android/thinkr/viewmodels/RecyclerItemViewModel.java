package com.android.thinkr.viewmodels;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

/**
 * Created by M. Silva on 12/15/15.
 */
public abstract class RecyclerItemViewModel extends BaseObservable {

    private String mTag;

    public abstract int getLayoutId();

    @NonNull
    public String getTag() {
        if (mTag == null) mTag = "";
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public boolean hasDataToDisplay() {
        return true;
    }
}
