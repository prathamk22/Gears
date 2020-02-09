package com.example.gears.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.gears.Fragment.GetStartedFragment;
import com.example.gears.R;
import com.example.gears.Utilities.UserPreferanceManager;
import com.example.gears.databinding.ActivityGetStartedBinding;

public class GetStarted extends AppCompatActivity {

    ActivityGetStartedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_started);
        GetStartedFragment getStartedFragment = new GetStartedFragment();
        setFragment(getStartedFragment);
    }

    public void setFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout,fragment)
                .commit();
    }


}
