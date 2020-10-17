package com.example.farmer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

public class ChatsFragment extends Fragment {

    private ExpertAdapter expertAdapter;
    private List<AdminAddExpertHelper> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<ChatList> expertList;

    RecyclerView recyclerView;
    AdminAddExpertHelper experts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_chats,container,false);

        recyclerView=view.findViewById(R.id.recycler_view22);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser= FirebaseAuth.getInstance().getCurrentUser();

        expertList=new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("ChatList").child(SessionManagement.KEY_MOBILE);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                expertList.clear();
                //loops for all users
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    ChatList chatlst=snapshot1.getValue(ChatList.class);
                    expertList.add(chatlst);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void chatList()
    {
        //Getting all users/previus users
        mUsers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Experts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mUsers.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    experts=snapshot1.getValue(AdminAddExpertHelper.class);
                    for (ChatList chatLis:expertList)
                    {
                        if (experts.getExpertMobile().equals(chatLis.getMnumber()))
                        {
                            mUsers.add(experts);
                        }
                    }
                }
                expertAdapter=new ExpertAdapter(getContext(),mUsers);
                recyclerView.setAdapter(expertAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}