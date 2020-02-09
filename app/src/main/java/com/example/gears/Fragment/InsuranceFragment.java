package com.example.gears.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gears.ViewModel.InsuranceViewModel;
import com.example.gears.R;
import com.example.gears.databinding.FragmentInsuranceBinding;

public class InsuranceFragment extends Fragment {

    private InsuranceViewModel toolsViewModel;
    private FragmentInsuranceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_insurance,container,false);
        toolsViewModel =
                ViewModelProviders.of(this)
                        .get(InsuranceViewModel.class);

        toolsViewModel.getInsuranceDetailsFromFirebase(getContext());

        toolsViewModel.insurance.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.insurance.setText(s);
                binding.progressCircular.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
}