package com.android.thinkr.common;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.android.thinkr.BR;
import com.bytes.hack.model.account.User;

/**
 * Created by M. Silva on 1/3/16.
 */
public class AccountController extends BaseObservable {

    private static AccountController ourInstance = new AccountController();
    private User mUser;

    private AccountController() {
        mUser = Preferences.getUser();
    }

    public static AccountController getInstance() {
        return ourInstance;
    }

    @Bindable
    @NonNull
    public User getUser() {
        return mUser;
    }

    void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.user);
    }
}
