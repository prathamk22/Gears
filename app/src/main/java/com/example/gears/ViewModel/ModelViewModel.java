package com.example.gears.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.Utilities.UserPreferanceManager;

public class ModelViewModel extends ViewModel implements FirebaseClass.ModelListener {


    public MutableLiveData<String> modeliveData = new MutableLiveData<>();

    public void getDetailsfromDatabase(Context context){
        UserPreferanceManager preferanceManager = new UserPreferanceManager(context);
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.getModelFragmentDetails(preferanceManager.getUsername(context),this);
    }

    @Override
    public void getFirebaseDetails(String model) {
        modeliveData.setValue(model);
    }

}