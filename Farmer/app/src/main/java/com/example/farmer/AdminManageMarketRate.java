package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminManageMarketRate extends AppCompatActivity {

    Button addcombtn,viewcomratebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_market_rate);

        addcombtn=(Button)findViewById(R.id.btnaddcommain);
        viewcomratebtn=(Button)findViewById(R.id.btnviewecomrate);

        addcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageMarketRate.this,AdminAddMarketRate.class);
                startActivity(intent);
            }
        });

        viewcomratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageMarketRate.this,AdminAddPrice.class);
                startActivity(intent);
            }
        });
    }
}