package com.android.thinkr.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.thinkr.ThinkrApplication;
import com.bytes.hack.model.account.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Created by M. Silva on 1/2/16.
 */
public class Preferences {

    private static final String PREF_NAME = "thinkr";
    private static final Gson GSON = new GsonBuilder().create();

    public static User getUser() {
        User user = new User();
        final String userString = getPreferences().getString(Keys.user, "{}");
        try {
            user = GSON.fromJson(userString, User.class);
        } catch (JsonSyntaxException e){
            Log.e(Preferences.class.getSimpleName(), "Failed to deserialize user");
        }
        return user;
    }

    public static void setUser(User user) {
        final String userString = GSON.toJson(user);
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(Keys.user, userString);
        editor.apply();
    }

    public static void signUserIn() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(Keys.signedIn, true);
        editor.apply();
    }

    public static void signUserOut() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(Keys.signedIn, false);
        editor.apply();
    }

    public static boolean isSignedIn() {
        return getPreferences().getBoolean(Keys.signedIn, false);
    }

    private static SharedPreferences getPreferences() {
        return ThinkrApplication.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public interface Keys {
        String signedIn = "signedIn";
        String user = "user";
    }
}
