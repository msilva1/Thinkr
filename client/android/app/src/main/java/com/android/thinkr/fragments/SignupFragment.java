package com.android.thinkr.fragments;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.thinkr.R;
import com.android.thinkr.common.Preferences;
import com.android.thinkr.databinding.FragmentSignupBinding;
import com.android.thinkr.service.ThinkrServiceImpl;
import com.bytes.hack.model.account.Account;
import com.bytes.hack.model.account.User;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, Callback<Account> {

    private FragmentSignupBinding mBinding;
    private Spinner mSpinner;

    private String mEmail;
    private String mPassword;
    private User.Type mType = User.Type.Student;
    private String mUserId;

    public SignupFragment() {
    }

    @Override
    public int getFragmentTitle() {
        return R.string.action_sign_up;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_signup;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.bind(view);
        mBinding.signUpButton.setOnClickListener(this);

        mSpinner = mBinding.userTypeSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.user_types_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_button:
                if (isValidUser()) {
                    User user = new User();
                    user.setEmail(mEmail);
                    user.setUserId(mUserId);
                    user.setPassword(mPassword);
                    user.setUserType(mType);

                    final Call<Account> call = ThinkrServiceImpl.getService().create(user);
                    call.enqueue(this);
                }
                break;
        }
    }

    private boolean isValidUser() {
        mEmail = mBinding.email.getText().toString();
        if (!isEmailValid(mEmail)) {
            mBinding.email.setError("Please enter a valid email");
            mBinding.email.requestFocus();
            return false;
        }

        mUserId = mBinding.userId.getText().toString();
        if (mUserId.isEmpty()) {
            mBinding.userId.setError("Username can't be empty");
            mBinding.userId.requestFocus();
            return false;
        }

        mPassword = mBinding.password.getText().toString();
        if (mPassword.isEmpty()) {
            mBinding.password.setError("Password can't be empty");
            mBinding.password.requestFocus();
            return false;
        }

        if (mPassword.length() < 8) {
            mBinding.password.setError("Password must be at least 8 characters");
            mBinding.password.requestFocus();
            return false;
        }
        String validation = mBinding.passwordValidation.getText().toString();
        if (!mPassword.equals(validation)) {
            mBinding.passwordValidation.setError("Passwords do not match");
            mBinding.passwordValidation.requestFocus();
            return false;
        }

        return true;

    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String userTypeString = parent.getItemAtPosition(position).toString();
        mType = Enum.valueOf(User.Type.class, userTypeString);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResponse(Response<Account> response, Retrofit retrofit) {
        if (response.isSuccess() && response.body() != null) {
            Preferences.setUser(response.body().getUser());
            Preferences.signUserIn(); // Need to make sure server is logged in too, currently, it might not be.
            Toast.makeText(getActivity(), "Account created", Toast.LENGTH_SHORT).show();
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Unable to create account", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
