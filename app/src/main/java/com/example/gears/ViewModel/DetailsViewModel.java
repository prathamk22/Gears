package com.example.gears.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.Utilities.UserPreferanceManager;

public class DetailsViewModel extends ViewModel implements FirebaseClass.DetailFragmentistener {

    private String name = "";
    private String phoneNumber = "";
    private String Address = "";
    public MutableLiveData<String> nameLiveData = new MutableLiveData<>();
    public MutableLiveData<String> addLiveData = new MutableLiveData<>();
    public MutableLiveData<String> phoneLiveData = new MutableLiveData<>();

    public void getDetailsfromDatabase(Context context){
        UserPreferanceManager preferanceManager = new UserPreferanceManager(context);
        FirebaseClass firebaseClass = new FirebaseClass(context);
        firebaseClass.getDetailFragmentDetails(preferanceManager.getUsername(context),this);
    }

    @Override
    public void getFirebaseDetails(String name, String address, String phoneNumber) {
        nameLiveData.setValue(name);
        addLiveData.setValue(address);
        phoneLiveData.setValue(phoneNumber);
    }
}