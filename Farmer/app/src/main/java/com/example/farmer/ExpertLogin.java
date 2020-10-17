package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ExpertLogin extends AppCompatActivity {

    TextInputLayout mobile,password;
    CheckBox rememberMe;
    Button btnlogin,btnforget;
    TextInputEditText expertm,expertp;
    SharedPreferences sharedFarmerMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_login);

        rememberMe=(CheckBox)findViewById(R.id.checkRememberexpert);
        mobile=(TextInputLayout) findViewById(R.id.emobile);
        password=(TextInputLayout) findViewById(R.id.epass);

        expertm=findViewById(R.id.expertnumber);
        expertp=findViewById(R.id.expertpassword);

        btnlogin=(Button)findViewById(R.id.loginbuttonexpert);
        btnforget=(Button)findViewById(R.id.forgetbuttonexpert);

        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExpertLogin.this,ForgetPasswordExpert.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateMobile()
    {
        String val = mobile.getEditText().getText().toString();

        if (val.isEmpty()) {
            mobile.setError("Field can't be empty");
            return false;
        }
        else {
            mobile.setError(null);
            mobile.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword()
    {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(true);
            return true;
        }
    }

    public void loginExpert(View view)
    {
        if(!isConnected(this))
        {
            showCustomeDialog();
        }

        if (!validateMobile() | !validatePassword())
        {
            return;
        }
        else
        {
            isExpert();

            sharedFarmerMobile=getSharedPreferences("LoggedInExpertMobile",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedFarmerMobile.edit();
            editor1.putString("SHARED_EXPERT_MOBILE",mobile.getEditText().getText().toString());
            editor1.apply();
        }
    }

    private void isExpert() {
        final String expertEnteredMobile=mobile.getEditText().getText().toString().trim();
        final String expertEnteredPassword=password.getEditText().getText().toString().trim();


        //Remember Me
//        if (rememberMe.isChecked())
//        {
//            SessionManagement sessionManagement=new SessionManagement(Login.this,SessionManagement.SESSION_REMEMBERME);
//            sessionManagement.createRememberMeSession(userEnteredMobile,userEnteredPassword);
//        }

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Experts");

        Query checkUser=reference.orderByChild("expertMobile").equalTo(expertEnteredMobile);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {

                    mobile.setError(null);
                    mobile.setErrorEnabled(false);

                    String passwordFromExpertDB=dataSnapshot.child(expertEnteredMobile).child("expertPassword").getValue(String.class);

                    if (passwordFromExpertDB.equals(expertEnteredPassword))
                    {
                        String firstnameFromExpertDB=dataSnapshot.child(expertEnteredMobile).child("expertFirstname").getValue(String.class);
                        String lastnameFromExpertDB=dataSnapshot.child(expertEnteredMobile).child("expertLastname").getValue(String.class);
                        String emailFromExpertDB=dataSnapshot.child(expertEnteredMobile).child("expertEmail").getValue(String.class);
                        String mobileFromExpertDB=dataSnapshot.child(expertEnteredMobile).child("expertMobile").getValue(String.class);
                        String addressFromExpertDB=dataSnapshot.child(expertEnteredMobile).child("expertAddress").getValue(String.class);


                        //for session management of the user

                        SessionManagementExperts sessionExpert=new SessionManagementExperts(ExpertLogin.this,SessionManagementExperts.SESSION_EXPERTSESSION);
                        sessionExpert.createLoginSession(firstnameFromExpertDB,lastnameFromExpertDB,emailFromExpertDB,mobileFromExpertDB,passwordFromExpertDB);

                        Intent intent=new Intent(getApplicationContext(),ExpertHome.class);
                        intent.putExtra("firstnameFromExpertDB",firstnameFromExpertDB);
                        intent.putExtra("lastnameFromExpertDB",lastnameFromExpertDB);
                        startActivity(intent);

                    }
                    else
                    {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else
                {
                    mobile.setError("No such User Exists");
                    mobile.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean isConnected(ExpertLogin login) {

        ConnectivityManager connectivityManager=(ConnectivityManager)login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn !=null && wifiConn.isConnected()) || (mobileConn !=null && mobileConn.isConnected()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private void showCustomeDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(ExpertLogin.this);
        builder.setMessage("Please check the Internet Connection")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //startActivity(new Intent(getApplicationContext(),Login.class));
                        dialogInterface.cancel();
                        Toast.makeText(ExpertLogin.this,"Please connect with Internet",Toast.LENGTH_LONG).show();
                    }
                });
        builder.create();
        builder.show();
    }

}