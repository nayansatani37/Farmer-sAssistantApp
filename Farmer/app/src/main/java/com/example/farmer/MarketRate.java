package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarketRate extends AppCompatActivity {

    Toolbar toolbar_back;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    ListView listView;

    TextView ComName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_rate);

        listView=(ListView)findViewById(R.id.listcitycom);
        ComName=(TextView)findViewById(R.id.indicateCom);


        String selectedCom=getIntent().getStringExtra("ComSelected");
        //Toast.makeText(MarketRate.this,selectedCom,Toast.LENGTH_LONG).show();
        ComName.setText(selectedCom);

        final ArrayList<String> list=new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item_price,list);
        listView.setAdapter(adapter);

        ref= FirebaseDatabase.getInstance().getReference("Price").child(selectedCom);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    //AdminAddPrice city=snapshot1.getValue(AdminAddPrice.class);
                    list.add(snapshot1.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}