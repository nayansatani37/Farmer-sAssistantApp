package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgetPasswordFarmer extends AppCompatActivity {

    ImageView imgback;
    TextInputEditText editNumber;
    TextInputLayout txtNumberLayout;
    Button btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_farmer);

        txtNumberLayout=(TextInputLayout)findViewById(R.id.forgetmobile);
        editNumber=(TextInputEditText)findViewById(R.id.edtnum);
        btnnext=(Button)findViewById(R.id.fogetnextbtn);
        imgback=(ImageView) findViewById(R.id.imgback);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPasswordFarmer.this,MakeSelection.class);
                intent.putExtra("fogetmobilenumber",editNumber.getText().toString());
                startActivity(intent);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetPasswordFarmer.this,Login.class);
                startActivity(intent);
            }
        });
    }
}