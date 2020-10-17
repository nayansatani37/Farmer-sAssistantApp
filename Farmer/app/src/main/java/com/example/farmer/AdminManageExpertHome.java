package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminManageExpertHome extends AppCompatActivity {

    Button addexpertbtn,viewexpertbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_expert_home);

        addexpertbtn=(Button)findViewById(R.id.btnaddexpert);
        viewexpertbtn=(Button)findViewById(R.id.btnviewexpert);

        addexpertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageExpertHome.this,AdminAddNewUser.class);
                startActivity(intent);
            }
        });

        viewexpertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageExpertHome.this,AdminRemoveExpert.class);
                startActivity(intent);
            }
        });
    }
}