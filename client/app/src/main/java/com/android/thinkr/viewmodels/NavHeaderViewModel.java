package com.android.thinkr.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;

import com.android.thinkr.BR;
import com.android.thinkr.common.AccountController;
import com.bytes.hack.model.account.User;

/**
 * Created by M. Silva on 1/3/16.
 */
public class NavHeaderViewModel extends BaseObservable {
    private static NavHeaderViewModel ourInstance = new NavHeaderViewModel();
    private User mUser;

    private AccountController mAccountController;
    private OnPropertyChangedCallback mPropertyChangedCallback = new OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == BR.user) setUser(mAccountController.getUser());
        }
    };

    private NavHeaderViewModel() {
        mAccountController = AccountController.getInstance();
        mUser = mAccountController.getUser();
        mAccountController.addOnPropertyChangedCallback(mPropertyChangedCallback);
    }

    public static NavHeaderViewModel getInstance() {
        return ourInstance;
    }

    public void setUser(User user) {
        mUser = user;
        notifyPropertyChanged(BR.username);
        notifyPropertyChanged(BR.userEmail);
    }

    @Bindable
    public String getUsername() {
        final char upperCase = Character.toUpperCase(mUser.getUserId().charAt(0));
        final StringBuilder userId = new StringBuilder();
        userId.append(upperCase);
        userId.append(mUser.getUserId().substring(1));

        return userId.toString();
    }

    @Bindable
    public String getUserEmail() {
        return mUser.getEmail();
    }
}
