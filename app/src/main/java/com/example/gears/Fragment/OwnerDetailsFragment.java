package com.example.gears.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gears.Activity.MainActivity;
import com.example.gears.R;
import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.databinding.FragmentOwnerDetailsBinding;

public class OwnerDetailsFragment extends Fragment {

    private FragmentOwnerDetailsBinding binding;
    private static final String REDCOLOR = "#E44652";
    private boolean isText = false;
    private boolean isAddress = false;
    private boolean isPhoneNumber = false;
    private boolean isCarModel = false;
    private boolean isCarRegistration = false;
    private boolean isInsurance = false;
    private boolean isEmergency = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_details, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FirebaseClass firebaseClass = new FirebaseClass(getContext());
        binding.next.setEnabled(false);

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseClass.setAllFields(getContext(),
                        binding.nameEditText.getText().toString(),
                        binding.addressEditText.getText().toString(),
                        binding.phoneNumberEditText.getText().toString(),
                        binding.carModelEditText.getText().toString(),
                        binding.carRegistrationEditText.getText().toString(),
                        binding.insuranceEditText.getText().toString(),
                        binding.emergencyEditText.getText().toString());
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        editTextChangeListner();
    }

    private void editTextChangeListner(){
        binding.nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.nameEditText.getText().toString().length() > 1){
                    isText = true;
                }else
                    isText = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.addressEditText.getText().toString().length() > 10){
                    isAddress = true;
                }else
                    isAddress = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.phoneNumberEditText.getText().toString().length() > 0){
                    isPhoneNumber = true;
                }else
                    isPhoneNumber = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.carModelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.carModelEditText.getText().toString().length() > 5){
                    isCarModel = true;
                }else
                    isCarModel = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.carRegistrationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.carRegistrationEditText.getText().toString().length() > 5){
                    isCarRegistration = true;
                }else
                    isCarRegistration = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.emergencyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.emergencyEditText.getText().toString().length() > 0){
                    isEmergency = true;
                }else
                    isEmergency = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.insuranceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.insuranceEditText.getText().toString().length() > 5){
                    isInsurance = true;
                }else
                    isInsurance = false;
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void checkAllFields(){
        if(isAddress && isInsurance && isText && isPhoneNumber && isEmergency && isCarRegistration && isCarModel){
            binding.next.setEnabled(true);
            binding.next.setTextColor(Color.parseColor(REDCOLOR));
        }
    }
}
