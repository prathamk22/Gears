package com.example.gears.Fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gears.R;
import com.example.gears.databinding.FragmentGetStartedBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetStartedFragment extends Fragment {


    FragmentGetStartedBinding binding;

    public GetStartedFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_get_started,container,false);

        binding.getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new LoginFragment());
            }
        });

        return binding.getRoot();
    }

    public void setFragment(Fragment fragment){

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.frameLayout,fragment)
                .commit();
    }

}
