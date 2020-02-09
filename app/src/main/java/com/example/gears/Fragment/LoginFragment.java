package com.example.gears.Fragment;


import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gears.Activity.MainActivity;
import com.example.gears.R;
import com.example.gears.Utilities.FirebaseClass;
import com.example.gears.ViewModel.LoginViewModel;
import com.example.gears.ViewModel.SignUpViewModel;
import com.example.gears.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding loginBinding;
    private LoginViewModel viewModel;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false);
        loginBinding.executePendingBindings();

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        LiveData<Boolean> logIn = viewModel.getInitialSignUp();
        logIn.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
            }
        });

        loginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginBinding.emailText.length() != 0){
                    if(loginBinding.passwordText.length() != 0){
                        viewModel.loginInUser(getActivity(),getContext(),loginBinding.emailText.getText().toString(),loginBinding.passwordText.getText().toString());
                    }else
                        Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Enter Username", Toast.LENGTH_SHORT).show();
            }
        });

        loginBinding.signUpContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        return loginBinding.getRoot();
    }

    public void setFragment(Fragment fragment){
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.frameLayout,fragment)
                .commit();
    }

}
