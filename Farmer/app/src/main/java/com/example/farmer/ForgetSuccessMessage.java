package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetSuccessMessage extends AppCompatActivity {

    Button goLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_success_message);

        goLogin=(Button)findViewById(R.id.gotologinexp);

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetSuccessMessage.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}