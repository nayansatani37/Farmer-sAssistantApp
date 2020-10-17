package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddMarketRate extends AppCompatActivity {

    EditText edtAddCom,edtAddCity;
    Button btnAddCom,btnAddCity;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_market_rate);

        edtAddCom=(EditText)findViewById(R.id.addcom);
        //edtAddCity=(EditText)findViewById(R.id.addcity);
        btnAddCom=(Button)findViewById(R.id.btnaddcom);
        //btnAddCity=(Button)findViewById(R.id.btnaddcity);
    }

    public void addComClick(View view)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Commodity");

        String coName = edtAddCom.getText().toString();

        CommodityHelper commodityHelper=new CommodityHelper(coName);

        if (TextUtils.isEmpty(coName))
        {
            Toast.makeText(AdminAddMarketRate.this,"Commondity Field Must not be Empty !",Toast.LENGTH_LONG).show();
        }
        else {
            reference.push().setValue(commodityHelper);
            Toast.makeText(this, "Commodity Added !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AdminAddMarketRate.this, AdminAddPrice.class);
            startActivity(intent);
            finish();
        }

    }

}