package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Subsidy_Desciption_Activity extends AppCompatActivity {

    Toolbar toolbar_back;
    TextView subtexttitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidy__desciption_);

        subtexttitle=(TextView)findViewById(R.id.sub_titletxt);

        //toolbar
        toolbar_back=findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Toast.makeText(Subsidy_Desciption_Activity.this,"Cardview clicked",Toast.LENGTH_LONG).show();
//        Intent intent=getIntent();
//        String namesub=intent.getStringExtra("Subname");
//
//        subtexttitle.setText(namesub);


    }
}