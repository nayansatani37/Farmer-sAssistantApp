package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GetTips extends AppCompatActivity {

    Toolbar toolbar_back;
    RecyclerView recyclerViewtip;
    myAdapterTipsView adapterTipsView;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tips);

        reference= FirebaseDatabase.getInstance().getReference().child("Tips");
        reference.keepSynced(true);

        recyclerViewtip=(RecyclerView)findViewById(R.id.tipsRecycle);
        recyclerViewtip.setHasFixedSize(true);
        recyclerViewtip.setLayoutManager(new LinearLayoutManager(this));

        //toolbar back
        toolbar_back=findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseRecyclerOptions<AdminTipHelperClass> options1 =
                new FirebaseRecyclerOptions.Builder<AdminTipHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tips"), AdminTipHelperClass.class)
                        .build();

        adapterTipsView=new myAdapterTipsView(options1);
        recyclerViewtip.setAdapter(adapterTipsView);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterTipsView.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterTipsView.stopListening();
    }

}