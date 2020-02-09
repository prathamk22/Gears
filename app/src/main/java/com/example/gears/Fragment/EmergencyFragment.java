package com.example.gears.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gears.R;
import com.example.gears.ViewModel.EmergencyViewModel;
import com.example.gears.ViewModel.ModelViewModel;
import com.example.gears.databinding.FragmentEmergencyBinding;

public class EmergencyFragment extends Fragment {

    private FragmentEmergencyBinding binding;
    private EmergencyViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_emergency, container,false);
        viewModel =
                ViewModelProviders.of(this)
                        .get(EmergencyViewModel.class);

        viewModel.getDetailsfromDatabase(getContext());

        viewModel.emergencyLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.emergencyEditText.setText(s);
                binding.progressCircular.setVisibility(View.GONE);
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
