package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAddPrice extends AppCompatActivity{

    Spinner spinnerCom;
    DatabaseReference reference;
    List<String> commes;
    Button viewMarket;
    ArrayAdapter<String> arrayAdapter;
    String selectedCom;
    Button btnAddPrice;

    EditText ahmedabad1,rajkot1,surat1,vadodara1,amreli1,bharuch1,surendranagar1,vapi1,porbandar1,dang1,anand1,bhavnagar1,junagadh1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_price);


        //city
        ahmedabad1=(EditText)findViewById(R.id.ahmedabad);
        rajkot1=(EditText)findViewById(R.id.rajkot);
        surat1=(EditText)findViewById(R.id.surat);
        vadodara1=(EditText)findViewById(R.id.vadodara);
        amreli1=(EditText)findViewById(R.id.amreli);
        bharuch1=(EditText)findViewById(R.id.bharuch);
        surendranagar1=(EditText)findViewById(R.id.surendranagar);
        vapi1=(EditText)findViewById(R.id.vapi);
        porbandar1=(EditText)findViewById(R.id.porbandar);
        dang1=(EditText)findViewById(R.id.dang);
        anand1=(EditText)findViewById(R.id.anand);
        bhavnagar1=(EditText)findViewById(R.id.bhavnagar);
        junagadh1=(EditText)findViewById(R.id.junagadh);

        //viewMarket=(Button)findViewById(R.id.btnviewMarket);
        spinnerCom=(Spinner)findViewById(R.id.spinCom);
        btnAddPrice=(Button)findViewById(R.id.btnviewMarket);
        commes=new ArrayList<String>();

        reference=FirebaseDatabase.getInstance().getReference("Commodity");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    String spinner = snapshot1.child("comName").getValue(String.class);
                    commes.add(spinner);
                    arrayAdapter = new ArrayAdapter<>(AdminAddPrice.this, android.R.layout.simple_spinner_item, commes);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerCom.setAdapter(arrayAdapter);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spinnerCom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCom=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(AdminAddPrice.this,selectedCom,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnAddPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(AdminAddPrice.this,selectedCom,Toast.LENGTH_LONG).show();
                reference=FirebaseDatabase.getInstance().getReference("Price").child(selectedCom);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String ahmedabad =getText(R.string.Ahmedabad)+ahmedabad1.getText().toString();
                        String rajkot =getText(R.string.Rajkot)+rajkot1.getText().toString();
                        String surat = getText(R.string.Surat)+surat1.getText().toString();
                        String vadodara =getText(R.string.Vadodara)+vadodara1.getText().toString();
                        String amreli =getText(R.string.Amreli)+amreli1.getText().toString();
                        String bharuch =getText(R.string.Bharuch)+bharuch1.getText().toString();
                        String surendranagar =getText(R.string.Surendranagar)+surendranagar1.getText().toString();
                        String vapi =getText(R.string.Vapi)+ vapi1.getText().toString();
                        String porbandar =getText(R.string.Porbandar)+ porbandar1.getText().toString();
                        String dang =getText(R.string.Dang)+ dang1.getText().toString();
                        String anand =getText(R.string.Anand)+ anand1.getText().toString();
                        String bhavnagar =getText(R.string.Bhavnagar)+ bhavnagar1.getText().toString();
                        String junagadh =getText(R.string.Junagadh)+ junagadh1.getText().toString();

                        CityHelper cityHelper=new CityHelper(ahmedabad,rajkot,surat,vadodara,amreli,bharuch,surendranagar,vapi,
                                porbandar,dang,anand,bhavnagar,junagadh);

                        if (TextUtils.isEmpty(ahmedabad1.getText().toString())||(TextUtils.isEmpty(rajkot1.getText().toString())
                                ||(TextUtils.isEmpty(surat1.getText().toString())|| (TextUtils.isEmpty(vadodara1.getText().toString())
                                ||(TextUtils.isEmpty(amreli1.getText().toString())||(TextUtils.isEmpty(bharuch1.getText().toString())
                                ||(TextUtils.isEmpty(surendranagar1.getText().toString())||(TextUtils.isEmpty(vapi1.getText().toString())
                                ||(TextUtils.isEmpty(porbandar1.getText().toString())||(TextUtils.isEmpty(dang1.getText().toString())
                                ||(TextUtils.isEmpty(anand1.getText().toString())||(TextUtils.isEmpty(bhavnagar1.getText().toString())
                                ||(TextUtils.isEmpty(junagadh1.getText().toString()))))))))))))))
                        {
                            Toast.makeText(AdminAddPrice.this,"All Fields must not be empty !",Toast.LENGTH_LONG).show();
                        }
                        else {
                            reference.setValue(cityHelper);
                            Toast.makeText(AdminAddPrice.this, selectedCom+" Price Added !", Toast.LENGTH_LONG).show();
                        }
//                        Intent intent=new Intent(AdminAddMarketRate.this,AdminAddPrice.class);
//                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


}