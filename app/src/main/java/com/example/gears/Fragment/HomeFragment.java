package com.example.gears.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gears.Activity.MainActivity;
import com.example.gears.Utilities.MyLocation;
import com.example.gears.ViewModel.HomeViewModel;
import com.example.gears.R;
import com.example.gears.databinding.FragmentHomeBinding;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment implements OnMapReadyCallback{

    private static final int REQUEST_CHECK_SETTINGS = 101;
    private HomeViewModel homeViewModel;
    FragmentHomeBinding binding;
    private GoogleMap mMap;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int CALL_PHONE = 101;
    private static final String TAG = HomeFragment.class.getSimpleName();

    private String EMERGENCY = null;
    private String PHONE_NUMBER = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container,false);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.getDetailsfromDatabase(getContext());

        homeViewModel.phoneNumberLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                PHONE_NUMBER = s;
            }
        });

        homeViewModel.emergencyLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                EMERGENCY = s;
            }
        });

        binding.emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EMERGENCY!=null){
                    String uri = "tel:" + EMERGENCY.trim() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                    Toast.makeText(getContext(), "Calling:".concat(EMERGENCY), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Getting data from server please wait.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PHONE_NUMBER!=null){
                    String uri = "tel:" + PHONE_NUMBER.trim() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                    Toast.makeText(getContext(), "Calling:".concat(PHONE_NUMBER), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Getting data from server please wait.", Toast.LENGTH_SHORT).show();
            }
        });

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(binding.linearLayoutBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(false);

        if (checkLocationPermission()) {
            displayLocationSettingsRequest(getContext());
            setUpMaps();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }

        if(!checkCallPermission()){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE);
        }

        return binding.getRoot();
    }

    private void setUpMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);


        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkCallPermission(){
        return ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location arg0) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 15.0f));
                binding.loadingContainer.setVisibility(View.GONE);
            }
        });
    }

    private void displayLocationSettingsRequest(final Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(context, "Location settings are inadequate, and cannot be fixed here. Dialog not created", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:{
                if(grantResults.length > 0 && grantResults[0]== PermissionChecker.PERMISSION_GRANTED) {
                    displayLocationSettingsRequest(getContext());
                    setUpMaps();
                }else{
                    getActivity().finish();
                }
            }
            break;
            case CALL_PHONE:{
                if(grantResults.length > 0 && grantResults[0]== PermissionChecker.PERMISSION_DENIED) {
                    getActivity().finish();
                }
            }
            break;
        }
    }
}