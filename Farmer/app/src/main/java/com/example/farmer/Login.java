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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    TextInputLayout mobile,password;
    CheckBox rememberMe;
    Button btnlogin,btnreg,btnforget;
    TextInputEditText userm,userp;
    FirebaseUser firebaseUser;

    SharedPreferences sharedFarmerMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        rememberMe=(CheckBox)findViewById(R.id.checkRemember);
        mobile=(TextInputLayout) findViewById(R.id.mobile);
        password=(TextInputLayout) findViewById(R.id.pass);

        userm=findViewById(R.id.usernumber);
        userp=findViewById(R.id.userpassword);

        btnlogin=(Button)findViewById(R.id.loginbutton);
        btnforget=(Button)findViewById(R.id.forgetbutton);
        btnreg=(Button)findViewById(R.id.regbutton);

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });

        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,ForgetPasswordFarmer.class);
                startActivity(intent);
            }
        });

        //checking for user existance
//        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//
//        if (firebaseUser !=null)
//        {
//            Intent i=new Intent(Login.this,MainActivity.class);
//            startActivity(i);
//            finish();
//        }


        //for remember me
        SessionManagement sessionManagement=new SessionManagement(Login.this,SessionManagement.SESSION_REMEMBERME);
        if (sessionManagement.checkRememberMe())
        {
            HashMap<String,String> rememberMeDetails=sessionManagement.getRememberMeDetailFromSession();
            userm.setText(rememberMeDetails.get(SessionManagement.KEY_SESSIONMOBILE));
            userp.setText(rememberMeDetails.get(SessionManagement.KEY_SESSIONPASSWORD));

        }
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

    public void loginUser(View view)
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
            isUser();

            sharedFarmerMobile=getSharedPreferences("LoggedInFarmerMobile",MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedFarmerMobile.edit();
            editor1.putString("SHARED_MOBILE",mobile.getEditText().getText().toString());
            editor1.apply();
        }
    }

    private void showCustomeDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(Login.this);
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
                Toast.makeText(Login.this,"Please connect with Internet",Toast.LENGTH_LONG).show();
            }
        });
        builder.create();
        builder.show();
    }

    private boolean isConnected(Login login) {

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

    public void isUser() {
        final String userEnteredMobile=mobile.getEditText().getText().toString().trim();
        final String userEnteredPassword=password.getEditText().getText().toString().trim();
        //Remember Me
        if (rememberMe.isChecked())
        {
            SessionManagement sessionManagement=new SessionManagement(Login.this,SessionManagement.SESSION_REMEMBERME);
            sessionManagement.createRememberMeSession(userEnteredMobile,userEnteredPassword);
        }

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser=reference.orderByChild("mobile").equalTo(userEnteredMobile);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    mobile.setError(null);
                    mobile.setErrorEnabled(false);
                    String passwordFromDB=dataSnapshot.child(userEnteredMobile).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword))
                    {
                        String firstnameFromDB=dataSnapshot.child(userEnteredMobile).child("firstname").getValue(String.class);
                        String lastnameFromDB=dataSnapshot.child(userEnteredMobile).child("lastame").getValue(String.class);
                        String emailFromDB=dataSnapshot.child(userEnteredMobile).child("email").getValue(String.class);
                        String mobileFromDB=dataSnapshot.child(userEnteredMobile).child("mobile").getValue(String.class);

                        //for session management of the user
                        SessionManagement sessionManagement=new SessionManagement(Login.this,SessionManagement.SESSION_USERSESSION);
                        sessionManagement.createLoginSession(firstnameFromDB,lastnameFromDB,emailFromDB,mobileFromDB,passwordFromDB);

                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("firstnameFromDB",firstnameFromDB);
                        intent.putExtra("lastnameFromDB",lastnameFromDB);
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

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences=getSharedPreferences("MyPreference",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("CHECKBOX_VALUE",rememberMe.isChecked());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences=getSharedPreferences("MyPreference",MODE_PRIVATE);

        Boolean check=sharedPreferences.getBoolean("CHECKBOX_VALUE",false);

        rememberMe.setChecked(check);
    }
}