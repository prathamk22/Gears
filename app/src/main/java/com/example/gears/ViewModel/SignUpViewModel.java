package com.example.gears.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;

public class SignUpViewModel extends ViewModel implements FirebaseClass.CompleteListener {

    private boolean signUp = false;
    private MutableLiveData<Boolean> signUpData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getInitialSignUp(){
        signUpData.setValue(signUp);
        return signUpData;
    }

    public void SignUpUser(Context context, String email, String passoword){
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.signUp(context,email,passoword,this);
    }


    @Override
    public void onCompleteCallBack(boolean result) {
        signUp=result;
        signUpData.setValue(result);
    }
}
