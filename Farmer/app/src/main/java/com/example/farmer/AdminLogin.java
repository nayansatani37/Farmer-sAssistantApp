package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    TextInputLayout mobile, password;
    CheckBox rememberMe;
    Button btnlogin;
    TextInputEditText adminm, adminp;
    //FirebaseUser firebaseUser;
    //ProgressBar buffering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        rememberMe = (CheckBox) findViewById(R.id.checkRemember);
        mobile = (TextInputLayout) findViewById(R.id.amobile);
        password = (TextInputLayout) findViewById(R.id.apass);
        //buffering=(ProgressBar)findViewById(R.id.progress);

        adminm = findViewById(R.id.adminnumber);
        adminp = findViewById(R.id.adminpassword);

        btnlogin = (Button) findViewById(R.id.loginbuttonadmin);
        //buffering.setVisibility(View.INVISIBLE);
    }

    public void loginAdmin(View v) {
        //buffering.setVisibility(View.VISIBLE);

        if (!isConnected(AdminLogin.this)) {
            showCustomeDialog();
        }

        if (!validateMobile() | !validatePassword()) {
            return;
        } else {
            isAdmin();
        }
    }

    //Internet connection checking
    private boolean isConnected(AdminLogin login) {

        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    //mobile  & password checking
    private boolean validateMobile() {
        String val = mobile.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            mobile.setError("Field can't be empty");
            return false;
        } else {
            mobile.setError(null);
            mobile.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(true);
            return true;
        }
    }

    private void showCustomeDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminLogin.this);
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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                });
        builder.create();
        builder.show();
    }

    public void isAdmin() {
        final String adminEnteredMobile=mobile.getEditText().getText().toString().trim();
        final String adminEnteredPassword=password.getEditText().getText().toString().trim();


        DatabaseReference refe= FirebaseDatabase.getInstance().getReference("Admin");

        Query checkadmin=refe.orderByChild("adminmobile").equalTo(adminEnteredMobile);

        checkadmin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    mobile.setError(null);
                    mobile.setErrorEnabled(false);

                    String passwordFromAdminDB=dataSnapshot.child(adminEnteredMobile).child("adminpassword").getValue(String.class);

                    if (passwordFromAdminDB.equals(adminEnteredPassword))
                    {
                        String firstnameFromAdminDB=dataSnapshot.child(adminEnteredMobile).child("adminfirstname").getValue(String.class);
                        String lastnameFromAdminDB=dataSnapshot.child(adminEnteredMobile).child("adminlastname").getValue(String.class);
                        String emailFromAdminDB=dataSnapshot.child(adminEnteredMobile).child("adminemail").getValue(String.class);
                        String mobileFromadminAdminDB=dataSnapshot.child(adminEnteredMobile).child("adminmobile").getValue(String.class);


                        //for session management of the user
//                        SessionManagement sessionManagement=new SessionManagement(Login.this,SessionManagement.SESSION_REMEMBERME);
//                        sessionManagement.createLoginSession(firstnameFromDB,lastnameFromDB,emailFromDB,mobileFromDB,passwordFromDB);
//
//
                        Intent intent=new Intent(getApplicationContext(),AdminHome.class);
                        intent.putExtra("firstnameFromAdminDB",firstnameFromAdminDB);
                        intent.putExtra("lastnameFromAdminDB",lastnameFromAdminDB);
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
}