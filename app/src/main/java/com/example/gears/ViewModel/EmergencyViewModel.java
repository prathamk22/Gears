package com.example.gears.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.Utilities.UserPreferanceManager;

public class EmergencyViewModel extends ViewModel implements FirebaseClass.EmergencyListener {

    public String emergency;
    public MutableLiveData<String> emergencyLiveData = new MutableLiveData<>();

    public void getDetailsfromDatabase(Context context){
        UserPreferanceManager preferanceManager = new UserPreferanceManager(context);
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.getEmergencyFragmentDetails(preferanceManager.getUsername(context),this);
    }

    @Override
    public void getFirebaseDetails(String model) {
        emergency = model;
        emergencyLiveData.setValue(model);
    }

}
