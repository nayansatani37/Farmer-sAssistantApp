package com.example.farmer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FarmersFragment extends Fragment {

    private FarmerAdapter farmerAdapter;
    private List<UserHelperClass> mFarmers;
    private RecyclerView recyclerView;

    public FarmersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_farmers,container,false);
        recyclerView=view.findViewById(R.id.recycler_view102);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFarmers=new ArrayList<>();

        ReadFarmers();
        return view;
    }
    private void ReadFarmers() {

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mFarmers.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    UserHelperClass farmers=snapshot1.getValue(UserHelperClass.class);


//                    assert farmers !=null;
//                    if (!experts.getId().equals(firebaseUser.getUid()))
//                   {
                    mFarmers.add(farmers);
//                    }
                }
                farmerAdapter=new FarmerAdapter(getContext(),mFarmers);
                recyclerView.setAdapter(farmerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}