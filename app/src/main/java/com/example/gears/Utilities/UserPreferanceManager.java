package com.example.gears.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class UserPreferanceManager {

    private static final String PREF_NAME = "GearsPref";
    private static final String LOGGEDIN = "isLoggedIn";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static SharedPreferences mSharedPreferences;
    Context mContext;

    private static void init(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public UserPreferanceManager(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn(Context mContext){
        if (mSharedPreferences == null) {
            init(mContext);
        }
        return mSharedPreferences.getBoolean(LOGGEDIN, false);
    }

    public void setLoggedinStatus(Context mContext, boolean loggedIn){
        if (mSharedPreferences == null) {
            init(mContext);
        }
        SharedPreferences.Editor mShEditor = mSharedPreferences.edit();
        mShEditor.putBoolean(LOGGEDIN, loggedIn);
        mShEditor.apply();
        mShEditor.commit();
    }

    public void setUserNameField(Context mContext,String username,String password){
        if (mSharedPreferences == null) {
            init(mContext);
        }
        SharedPreferences.Editor mShEditor = mSharedPreferences.edit();
        mShEditor.putString(EMAIL, username);
        mShEditor.putString(PASSWORD, password);
        mShEditor.apply();
        mShEditor.commit();
    }

    public String getUsername(Context context){
        if (mSharedPreferences == null) {
            init(context);
        }
        return mSharedPreferences.getString(EMAIL, null);
    }


}
