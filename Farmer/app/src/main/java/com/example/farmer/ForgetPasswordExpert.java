package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgetPasswordExpert extends AppCompatActivity {

    ImageView imgback;
    TextInputEditText editNumber;
    TextInputLayout txtNumberLayout;
    Button btnnext;
    String mobileExp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_expert);

        txtNumberLayout=(TextInputLayout)findViewById(R.id.forgetmobileexp);
        editNumber=(TextInputEditText)findViewById(R.id.edtnumexp);
        btnnext=(Button)findViewById(R.id.fogetnextbtnexp);
        imgback=(ImageView) findViewById(R.id.imgbackexp);

        mobileExp=txtNumberLayout.getEditText().getText().toString();

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPasswordExpert.this,OTPActivityForgetPasswordExpert.class);
                intent.putExtra("forgetmobilenumberexp",editNumber.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPasswordExpert.this,ExpertLogin.class);
                startActivity(intent);
            }
        });
    }
}