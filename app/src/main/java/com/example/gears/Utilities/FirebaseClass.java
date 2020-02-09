package com.example.gears.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.gears.Activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class FirebaseClass {

    private static final String USERS = "Users";
    private static final String DATE = "Date";
    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String PHONE_NUMBER = "Phone Number";
    private static final String CAR_MODEL = "Car Model";
    private static final String CAR_REGISTATION = "Car Registration";
    private static final String INSURANCE = "Insurance";
    private static final String EMERGENCY = "Emergency";

    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private UserPreferanceManager userPreferanceManager;

    public FirebaseClass(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
    }

    private void initAuth(){
        mAuth = FirebaseAuth.getInstance();
    }

    private void initDatabase(){
        database = FirebaseDatabase.getInstance().getReference();
    }

    public FirebaseUser getCurrentUser(Context context){
        if(mAuth==null)
            initAuth();
        return mAuth.getCurrentUser();
    }

    public void signUp(final Context context, final String email, final String pass, final CompleteListener completeListener){
        if(mAuth==null)
            initAuth();
        userPreferanceManager = new UserPreferanceManager(context);
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            addUsertoDatabae(email);
                            userPreferanceManager.setLoggedinStatus(context,true);
                            userPreferanceManager.setUserNameField(context, email,pass);
                            completeListener.onCompleteCallBack(true);
                        }else{
                            Toast.makeText(context, "Some Error occured. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error occured:".concat(e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(final Activity activity, final Context context, final String username, final String password, final LoginListener loginListener){
        if(mAuth==null)
            initAuth();
        if(userPreferanceManager==null)
            userPreferanceManager = new UserPreferanceManager(context);

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userPreferanceManager.setLoggedinStatus(context,true);
                            userPreferanceManager.setUserNameField(context, username,password);
                            loginListener.onCompleteCallBack(true);
                        }else{
                            Toast.makeText(activity, "Error occured", Toast.LENGTH_SHORT).show();
                            loginListener.onCompleteCallBack(false);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        loginListener.onCompleteCallBack(false);

                    }
                });
    }

    private void addUsertoDatabae(String email){
        if(database==null)
            initDatabase();
        DatabaseReference emailRef= database.child(USERS).child(getusernamefromEmail(email));
        emailRef.child(DATE).setValue(java.text.DateFormat.getDateTimeInstance().format(new Date()));
    }

    private String getusernamefromEmail(String email){
        int atTheRate = email.indexOf("@");
        if(atTheRate!=-1)
            return email.substring(0,email.indexOf("@"));
        else
            return email;
    }

    public void getDetailFragmentDetails(final String email, final DetailFragmentistener detailFragmentistener){
        if(database==null)
            initDatabase();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot name = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(NAME);
                DataSnapshot address = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(ADDRESS);
                DataSnapshot phoneNumber = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(PHONE_NUMBER);
                detailFragmentistener.getFirebaseDetails(name.getValue().toString(),
                        address.getValue().toString(),
                        phoneNumber.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getModelFragmentDetails(final String email, final ModelListener modelListener){
        if(database==null)
            initDatabase();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot model = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(CAR_MODEL);
                modelListener.getFirebaseDetails(model.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getInsuranceFragmentDetails(final String email, final InsuranceListener modelListener){
        if(database==null)
            initDatabase();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot model = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(INSURANCE);
                modelListener.getFirebaseDetails(model.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getEmergencyFragmentDetails(final String email, final EmergencyListener modelListener){
        if(database==null)
            initDatabase();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot model = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(EMERGENCY);
                modelListener.getFirebaseDetails(model.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getHomeFragmentDetails(final String email, final HomeListener modelListener){
        if(database==null)
            initDatabase();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot emergency = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(EMERGENCY);
                DataSnapshot contact = dataSnapshot.child(USERS)
                        .child(getusernamefromEmail(email))
                        .child(PHONE_NUMBER);
                modelListener.onCompleteListener(emergency.getValue().toString(), contact.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setAllFields(Context context,
                             String name,
                             String address,
                             String phoneNumber,
                             String carModel,
                             String carRegistation,
                             String insurance,
                             String emergency){

        if(database==null)
            initDatabase();
        userPreferanceManager = new UserPreferanceManager(context);
        String username = getusernamefromEmail(userPreferanceManager.getUsername(context));
        Log.e("username", username);
        if(username!=null && username.length() > 0){
            DatabaseReference emailRef= database
                    .child("Users")
                    .child(getusernamefromEmail(getusernamefromEmail(username)));
            emailRef.child(NAME).setValue(name);
            emailRef.child(ADDRESS).setValue(address);
            emailRef.child(PHONE_NUMBER).setValue(phoneNumber);
            emailRef.child(INSURANCE).setValue(insurance);
            emailRef.child(CAR_MODEL).setValue(carModel);
            emailRef.child(CAR_REGISTATION).setValue(carRegistation);
            emailRef.child(EMERGENCY).setValue(emergency);
        }
    }

    //Interfaces

    public interface LoginListener{
        void onCompleteCallBack(boolean result);
    }

    public interface CompleteListener{
        void onCompleteCallBack(boolean result);
    }

    public interface ModelListener{
        void getFirebaseDetails(String model);
    }

    public interface DetailFragmentistener{
        void getFirebaseDetails(String name,String address, String phoneNumber);
    }

    public interface InsuranceListener{
        void getFirebaseDetails(String insurance);
    }

    public interface EmergencyListener{
        void getFirebaseDetails(String insurance);
    }

    public interface HomeListener{
        void onCompleteListener(String emergency, String contact);
    }

}
