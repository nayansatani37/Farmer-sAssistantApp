package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextInputLayout firstname, lastname, email, mobile, password;
    MaterialButton backlogbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = (TextInputLayout) findViewById(R.id.firstname);
        lastname = (TextInputLayout) findViewById(R.id.lastname);
        email = (TextInputLayout) findViewById(R.id.email);
        mobile = (TextInputLayout) findViewById(R.id.mobile);
        password = (TextInputLayout) findViewById(R.id.password);

        backlogbtn = (MaterialButton) findViewById(R.id.backloginbtn);

        backlogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });
    }

    public void regUser(View view) {

        if (!validateFirstname() || !validateLastname() || !validateEmail() || !validateMobile() || !validatePassword())
        {
            return;
        }
        String sfname = firstname.getEditText().getText().toString();
        String slname = lastname.getEditText().getText().toString();
        String semail = email.getEditText().getText().toString();
        String smobile = mobile.getEditText().getText().toString();
        String spassword = password.getEditText().getText().toString();
        String completeMobileNumber="+91"+smobile;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        UserHelperClass helperClass = new UserHelperClass(sfname, slname, semail, smobile, spassword);

        reference.child(smobile).setValue(helperClass);
        //Toast.makeText(this,"Registred !",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Register.this,OTPActivity.class);

        intent.putExtra("fullmobile",completeMobileNumber);
        startActivity(intent);
    }

    private Boolean validateFirstname() {
        String val = firstname.getEditText().getText().toString();
        String noWhiteSpace="\\A\\w{3,20}\\z";

        if (val.isEmpty()) {
            firstname.setError("Field can't be empty");
            return false;
        }
        else if (!val.matches(noWhiteSpace))
        {
            firstname.setError("WhiteSpaces are not allowed");
            return false;
        }
        else {
            firstname.setError(null);
            firstname.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateLastname() {
        String val = lastname.getEditText().getText().toString();

        if (val.isEmpty()) {
            lastname.setError("Field can't be empty");
            return false;
        } else {
            lastname.setError(null);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        }
        else if (!val.matches(emailPattern))
        {
            email.setError("Invalid Email");
            return false;
        }
        else {
            email.setError(null);
            firstname.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateMobile() {
        String val = mobile.getEditText().getText().toString();

        if (val.isEmpty())
        {
            mobile.setError("Field can't be empty");
            return false;
        }
//        else if(isUser())
//        {
//            mobile.setError("User Already Exists !");
//            return false;
//        }
        else
        {
            mobile.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordPattern="^"+
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        }
        else if (!val.matches(passwordPattern))
        {
            password.setError("Password format not matched");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(true);
            return true;
        }
    }

    //public boolean isUser()
//    {
//        final String userEnteredMobile=mobile.getEditText().getText().toString().trim();
//
//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
//
//        Query checkUser=reference.orderByChild("mobile").equalTo(userEnteredMobile);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists())
//                {
//                    String mobileFromDB=dataSnapshot.child(userEnteredMobile).child("mobile").getValue(String.class);
//
//                    if (mobileFromDB.equals(userEnteredMobile))
//                    {
//                        String firstnameFromDB=dataSnapshot.child(userEnteredMobile).child("firstname").getValue(String.class);
//                        String lastnameFromDB=dataSnapshot.child(userEnteredMobile).child("lastame").getValue(String.class);
//                        String emailFromDB=dataSnapshot.child(userEnteredMobile).child("email").getValue(String.class);
//                        //String mobileFromDB=dataSnapshot.child(userEnteredMobile).child("mobile").getValue(String.class);
//                    }
//                    else
//                    {
//                        mobile.setError(null);
//                        mobile.setErrorEnabled(false);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
}