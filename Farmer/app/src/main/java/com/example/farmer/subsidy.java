package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class subsidy extends AppCompatActivity implements myAdapter.OnCardListener {

    Toolbar toolbar_back;
    RecyclerView recyclerView;
    DatabaseReference reference;
    myAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidy);

        reference= FirebaseDatabase.getInstance().getReference().child("Subsidy");
        reference.keepSynced(true);

        recyclerView=(RecyclerView)findViewById(R.id.subsidyRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //toolbar
        toolbar_back=findViewById(R.id.toolbar_back);
        setSupportActionBar(toolbar_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseRecyclerOptions<AdminSubHelperClass> options =
                new FirebaseRecyclerOptions.Builder<AdminSubHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Subsidy"), AdminSubHelperClass.class)
                        .build();

        adapter=new myAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void onCardClick(int position)
    {
        //Toast.makeText(subsidy.this,"Oncardclick clicked : "+position,Toast.LENGTH_LONG).show();
//        Intent intent=new Intent(subsidy.this,Subsidy_Desciption_Activity.class);
//
//        intent.putExtra("Subname","Nayan Satani");
//        startActivity(intent);
    }
}