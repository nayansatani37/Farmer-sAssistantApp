package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AllLoginHome extends AppCompatActivity implements View.OnClickListener {

    Button admin,expert,farmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_login_home);

        admin=(Button)findViewById(R.id.btnadmin);
        expert=(Button)findViewById(R.id.btnexpert);
        farmer=(Button)findViewById(R.id.btnfarmer);

        admin.setOnClickListener(this);
        expert.setOnClickListener(this);
        farmer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch(view.getId())
        {
            case R.id.btnadmin:i=new Intent(this,AdminLogin.class);startActivity(i); break;
            case R.id.btnexpert:i=new Intent(this,ExpertLogin.class);startActivity(i);break;
            case R.id.btnfarmer:i=new Intent(this, Login.class);startActivity(i);break;
            default:break;
        }
    }
}