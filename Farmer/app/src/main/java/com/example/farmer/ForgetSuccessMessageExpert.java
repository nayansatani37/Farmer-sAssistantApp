package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgetSuccessMessageExpert extends AppCompatActivity {

    Button goExpertLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_success_message_expert);

        goExpertLogin=(Button)findViewById(R.id.gotologinexp);

        goExpertLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ForgetSuccessMessageExpert.this,ExpertLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}