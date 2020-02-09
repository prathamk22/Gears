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

import com.example.gears.ViewModel.ModelViewModel;
import com.example.gears.R;
import com.example.gears.databinding.FragmentModelBinding;

public class ModelFragment extends Fragment {

    private ModelViewModel slideshowViewModel;
    private FragmentModelBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_model, container,false);
        slideshowViewModel =
                ViewModelProviders.of(this)
                        .get(ModelViewModel.class);

        slideshowViewModel.getDetailsfromDatabase(getContext());

        slideshowViewModel.modeliveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.carModel.setText(s);
                binding.progressCircular.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }
}