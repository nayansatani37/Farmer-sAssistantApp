package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminManageTipsHome extends AppCompatActivity {

    Button addtipbtn,viewtipbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_tips_home);

        addtipbtn=(Button)findViewById(R.id.btnaddtips);
        viewtipbtn=(Button)findViewById(R.id.btnviewetips);

        addtipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageTipsHome.this,AdminAddGetTips.class);
                startActivity(intent);
            }
        });

        viewtipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageTipsHome.this,AdminRemoveTips.class);
                startActivity(intent);
            }
        });
    }
}