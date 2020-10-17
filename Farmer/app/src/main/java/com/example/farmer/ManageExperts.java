package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageExperts extends AppCompatActivity {

    Button manageebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_experts);

        manageebtn=(Button)findViewById(R.id.mexpertbtn);

        manageebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManageExperts.this,AdminAddNewUser.class);
                startActivity(intent);
            }
        });
    }
}