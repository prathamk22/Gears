package com.example.gears.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.Utilities.UserPreferanceManager;

public class HomeViewModel extends ViewModel implements FirebaseClass.HomeListener {

    public MutableLiveData<String> emergencyLiveData = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumberLiveData = new MutableLiveData<>();

    public void getDetailsfromDatabase(Context context){
        UserPreferanceManager preferanceManager = new UserPreferanceManager(context);
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.getHomeFragmentDetails(preferanceManager.getUsername(context),this);
    }

    @Override
    public void onCompleteListener(String emergency, String contact) {
        emergencyLiveData.setValue(emergency);
        phoneNumberLiveData.setValue(contact);
    }
}