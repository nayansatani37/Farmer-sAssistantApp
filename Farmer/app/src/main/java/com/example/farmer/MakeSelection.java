
package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MakeSelection extends AppCompatActivity {

    TextView mobileDisc;
    ImageView imgback2;
    Button btncontinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);

        mobileDisc=(TextView)findViewById(R.id.mobile_discription);
        imgback2=(ImageView)findViewById(R.id.imgback2);
        btncontinue=(Button)findViewById(R.id.forgetcontinue);

        final Intent i=getIntent();
        final String mobilenumber=i.getStringExtra("fogetmobilenumber");

        mobileDisc.setText(mobilenumber);

        imgback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MakeSelection.this,ForgetPasswordFarmer.class);
                startActivity(intent);
            }
        });

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MakeSelection.this,OTPActivityForgetPassword.class);
                intent.putExtra("forgetmobilenumber",mobilenumber);
                startActivity(intent);
            }
        });
    }
}