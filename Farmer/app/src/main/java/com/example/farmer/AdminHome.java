package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity implements View.OnClickListener{

    private CardView cvSubsidy,cvMarketRate,cvPostQuery,cvGetTips,cvAddExpert;
    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        //cardview listner
        cvSubsidy=(CardView)findViewById(R.id.cardsubsidy);
        cvMarketRate=(CardView)findViewById(R.id.cardmarketrate);
        cvPostQuery=(CardView)findViewById(R.id.cardpostquery);
        cvGetTips=(CardView)findViewById(R.id.cardgettips);
        cvAddExpert=(CardView)findViewById(R.id.cardmanageexpert);
        txtWelcome=(TextView)findViewById(R.id.textwelcome);

        cvSubsidy.setOnClickListener(this);
        cvMarketRate.setOnClickListener(this);
        cvPostQuery.setOnClickListener(this);
        cvGetTips.setOnClickListener(this);
        cvAddExpert.setOnClickListener(this);

        String adfirstname=getIntent().getStringExtra("firstnameFromAdminDB");
        String adlastname=getIntent().getStringExtra("lastnameFromAdminDB");


        txtWelcome.setText("Welcome "+adfirstname + " " + adlastname);

    }
    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId())
        {
            case R.id.cardsubsidy:i=new Intent(this,AdminManageSubsidyHome.class);startActivity(i); break;
            case R.id.cardmarketrate:i=new Intent(this,AdminManageMarketRate.class);startActivity(i);break;
            case R.id.cardpostquery:i=new Intent(this,AdminViewFarmers.class);startActivity(i);break;
            case R.id.cardgettips:i=new Intent(this,AdminManageTipsHome.class);startActivity(i);break;
            case R.id.cardmanageexpert:i=new Intent(this,AdminManageExpertHome.class);startActivity(i);break;
            default:break;
        }
    }
}