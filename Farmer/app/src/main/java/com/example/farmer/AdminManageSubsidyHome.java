package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminManageSubsidyHome extends AppCompatActivity {

    Button addsubsidybtn,viewsubsidybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_subsidy_home);

        addsubsidybtn=(Button)findViewById(R.id.btnaddsubsidy);
        viewsubsidybtn=(Button)findViewById(R.id.btnviewesubsidy);

        addsubsidybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageSubsidyHome.this,AdminAddSubsidy.class);
                startActivity(intent);
            }
        });

        viewsubsidybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminManageSubsidyHome.this,AdminRemoveSubsidy.class);
                startActivity(intent);
            }
        });
    }
}