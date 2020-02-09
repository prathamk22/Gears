package com.example.gears.Fragment;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gears.R;
import com.example.gears.ViewModel.SignUpViewModel;
import com.example.gears.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding signUpBinding;
    private FirebaseAuth mAuth;
    private SignUpViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

        LiveData<Boolean> signUp = viewModel.getInitialSignUp();
        signUp.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setFragment(new OwnerDetailsFragment());
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        signUpBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(signUpBinding.emailText.length()!=0){
                    if(signUpBinding.passwordText.length()!=0){
                        if(signUpBinding.confirmPassword.length()!=0){
                            if(signUpBinding.confirmPassword.getText().toString().matches(signUpBinding.passwordText.getText().toString())){
                                viewModel.SignUpUser(getContext(),signUpBinding.emailText.getText().toString(),signUpBinding.passwordText.getText().toString());
                                signUpBinding.signUpText.setText("creating...");
                            }else
                                Toast.makeText(getContext(), "Please enter the same password to confirm it", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getContext(), "Enter Confirm Password field to continue", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getContext(), "Enter Password field to continue", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Enter Email to continue", Toast.LENGTH_SHORT).show();
            }
        });

        return signUpBinding.getRoot();
    }

    public void setFragment(Fragment fragment){
        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameLayout,fragment)
                    .commit();
        }
    }


}
