package com.example.gears.Fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.example.gears.ViewModel.DetailsViewModel;
import com.example.gears.R;
import com.example.gears.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {

    private DetailsViewModel sendViewModel;
    private FragmentDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false);
        sendViewModel =
                ViewModelProviders.of(this).get(DetailsViewModel.class);

        sendViewModel.getDetailsfromDatabase(getContext());

        sendViewModel.phoneLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.personPhoneNumber.setText(s);
                binding.progressCircular.setVisibility(View.GONE);
            }
        });

        sendViewModel.nameLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.personName.setText(s);
            }
        });

        sendViewModel.addLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.personAddress.setText(s);
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }
}