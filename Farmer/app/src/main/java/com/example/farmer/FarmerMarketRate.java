package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FarmerMarketRate extends AppCompatActivity {

    Spinner spinview,spincity;
    DatabaseReference reference,ref;
    FirebaseDatabase firebaseDatabase;
    List<String> commes;
    List<String> city;
    String selectedCom;
    Button btncheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_market_rate);

        spinview=(Spinner)findViewById(R.id.spinviewrate);
        //spincity=(Spinner)findViewById(R.id.spincity);
        btncheck=(Button)findViewById(R.id.btncheckrate);
        commes=new ArrayList<String>();
        city=new ArrayList<String>();
        reference=FirebaseDatabase.getInstance().getReference("Commodity");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    String spinner = snapshot1.child("comName").getValue(String.class);
                    commes.add(spinner);
                    ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(FarmerMarketRate.this, android.R.layout.simple_spinner_item, commes);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinview.setAdapter(arrayAdapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        spinview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCom=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(FarmerMarketRate.this,selectedCom,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FarmerMarketRate.this,MarketRate.class);
                intent.putExtra("ComSelected",selectedCom);
                startActivity(intent);
                //Toast.makeText(FarmerMarketRate.this,selectedCom,Toast.LENGTH_LONG).show();

            }
        });


    }
}