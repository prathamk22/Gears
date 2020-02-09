package com.example.gears.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.Utilities.UserPreferanceManager;

public class InsuranceViewModel extends ViewModel implements FirebaseClass.InsuranceListener {

    public MutableLiveData<String> insurance = new MutableLiveData<>();

    public void getInsuranceDetailsFromFirebase(Context context){
        UserPreferanceManager preferanceManager = new UserPreferanceManager(context);
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.getInsuranceFragmentDetails(preferanceManager.getUsername(context),this);
    }

    @Override
    public void getFirebaseDetails(String model) {
        insurance.setValue(model);
    }
}