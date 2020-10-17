package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ExpertHome extends AppCompatActivity implements View.OnClickListener {

    TextView txtexpertwelcome;
    private CardView cvChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_home);

        txtexpertwelcome=(TextView)findViewById(R.id.textwelcomeexpert);
        cvChat=(CardView)findViewById(R.id.cardexpertchat);

        String fullExpertFirstName=getIntent().getStringExtra("firstnameFromExpertDB");
        String fullExpertLastName=getIntent().getStringExtra("lastnameFromExpertDB");
        txtexpertwelcome.setText(getText(R.string.Welcome_msg)+" "+fullExpertFirstName+ " "+fullExpertLastName);
        cvChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,ExpertChatHome.class);
        startActivity(i);

    }
}