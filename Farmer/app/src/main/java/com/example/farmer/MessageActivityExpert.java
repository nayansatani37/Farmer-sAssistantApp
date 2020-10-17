package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivityExpert extends AppCompatActivity {

    TextView farmerName;
    ImageView imageView;

    FirebaseUser fUser;
    DatabaseReference reference;
    DatabaseReference reference1;
    Intent intent;

    ImageButton sendBtn;
    String sendMobile;

    RecyclerView recyclerView;
    EditText msg_editText;
    RecyclerView recyclerView1;
    String fuserNumber;
    SharedPreferences sharedPreferences;

    MessageAdapterExpert messageAdapterExpert;
    List<ChatsFarmer> mChatsExpert;

    String farmerMoNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_expert);

        imageView=findViewById(R.id.imageview_profilefar);
        farmerName=findViewById(R.id.farmerName_profile);

        sendBtn=findViewById(R.id.btn_sendfar);
        msg_editText=findViewById(R.id.text_sendfar);


        //RecyclerView
        recyclerView1=findViewById(R.id.recycler_viewfarm);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager);

        //getting intent from previus page(Farmer Adapter)
        intent=getIntent();
        final String farfirstname=intent.getStringExtra("farmerFname");
        farmerMoNo=intent.getStringExtra("farmerMno");
        //expertName.setText(expertMoNo);

        fUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(farmerMoNo);
//        reference1= FirebaseDatabase.getInstance().getReference("Users").child(sendMobile);

        sharedPreferences=getSharedPreferences("LoggedInExpertMobile",MODE_PRIVATE);
        sendMobile=sharedPreferences.getString("SHARED_EXPERT_MOBILE"," ");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass uhelper=snapshot.getValue(UserHelperClass.class);
                //UserHelperClass uhelper=snapshot.getValue(UserHelperClass.class);
                farmerName.setText(uhelper.getFirstname()+" "+uhelper.getLastame());
                //=uhelper.getMobile();

                readMessage(sendMobile,farmerMoNo);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=msg_editText.getText().toString();

                if (!msg.equals(""))
                {
                    sendMessage(sendMobile,farmerMoNo,msg);
                }
                else
                {
                    Toast.makeText(MessageActivityExpert.this, getText(R.string.empty_send_toast).toString(), Toast.LENGTH_SHORT).show();
                }
                msg_editText.setText("");
            }
        });

    }
    private void sendMessage(String sender,String receiver,String message)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);

        //Adding User to chat fragment:Last/Letest chat
//        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("ChatList")
//                .child(sendMobile)
//                .child(farmerMoNo);
//
//        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (!snapshot.exists())
//                {
//                    chatRef.child("mnumber").setValue(farmerMoNo);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    private void readMessage(final String myMobile, final String farMobile)
    {
        mChatsExpert=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChatsExpert.clear();
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    ChatsFarmer chatsFarmer=snap.getValue(ChatsFarmer.class);

//                    String mmNo=chatsFarmer.getReceiver();
//                    Log.i("MyNumberIS",mmNo);

                    if (chatsFarmer.getReceiver().equals(myMobile) && chatsFarmer.getSender().equals(farMobile) ||
                            chatsFarmer.getReceiver().equals(farMobile) && chatsFarmer.getSender().equals(myMobile))
                    {
                        mChatsExpert.add(chatsFarmer);
                    }
                    messageAdapterExpert=new MessageAdapterExpert(MessageActivityExpert.this,mChatsExpert);
                    recyclerView1.setAdapter(messageAdapterExpert);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}