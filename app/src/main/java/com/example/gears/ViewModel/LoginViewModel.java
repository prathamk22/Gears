package com.example.gears.ViewModel;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;

public class LoginViewModel extends ViewModel implements FirebaseClass.LoginListener {

    private boolean login = false;
    private MutableLiveData<Boolean> loginData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getInitialSignUp(){
        loginData.setValue(login);
        return loginData;
    }

    public void loginInUser(Activity activity,Context context, String email, String passoword){
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.login(activity,context,email,passoword,this);
    }

    @Override
    public void onCompleteCallBack(boolean result) {
        login=result;
        loginData.setValue(result);
    }
}
