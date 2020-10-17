package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class MessageActivityFarmer extends AppCompatActivity {

    TextView expertName;
    ImageView imageView;
    FirebaseUser fUser;
    DatabaseReference reference;
    DatabaseReference reference1;
    Intent intent;

    RecyclerView recyclerView;
    EditText msg_editText;
    ImageButton sendBtn;
    String sendMobile;

    MessageAdapterFarmer messageAdapterFarmer;
    List<ChatsFarmer> mChatsFarmers;

    RecyclerView recyclerView1;
    String fuserNumber;
    SharedPreferences sharedPreferences;

    String expertMoNo;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_farmer);

        imageView=findViewById(R.id.imageview_profile);
        expertName=findViewById(R.id.expertName_profile);
        sendBtn=findViewById(R.id.btn_send);
        msg_editText=findViewById(R.id.text_send);


        //RecyclerView
        recyclerView1=findViewById(R.id.recycler_view);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager);



        intent=getIntent();
        final String expfirstname=intent.getStringExtra("expertfname");
        expertMoNo=intent.getStringExtra("expertMno");
        //expertName.setText(expertMoNo);

        fUser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Experts").child(expertMoNo);
//        reference1= FirebaseDatabase.getInstance().getReference("Users").child(sendMobile);

        sharedPreferences=getSharedPreferences("LoggedInFarmerMobile",MODE_PRIVATE);
        sendMobile=sharedPreferences.getString("SHARED_MOBILE"," ");

        //users number from session
//        SessionManagement sessionManagement=new SessionManagement(this,SessionManagement.SESSION_USERSESSION);
//        HashMap<String,String> userDetails=sessionManagement.getUsersDetailFromSession();
//
//        fuserNumber=userDetails.get(sessionManagement.KEY_SESSIONMOBILE);

//        sendMobile=fuserNumber;


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AdminAddExpertHelper experts=snapshot.getValue(AdminAddExpertHelper.class);
                //UserHelperClass uhelper=snapshot.getValue(UserHelperClass.class);
                expertName.setText(experts.getExpertFirstname()+" "+experts.getExpertLastname());
                //=uhelper.getMobile();

                readMessage(sendMobile,expertMoNo);
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
                    sendMessage(sendMobile,expertMoNo,msg);
                }
                else
                {
                    Toast.makeText(MessageActivityFarmer.this, getText(R.string.empty_send_toast).toString(), Toast.LENGTH_SHORT).show();
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
        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("ChatList")
                .child(sendMobile)
                .child(expertMoNo);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists())
                {
                    chatRef.child("mnumber").setValue(expertMoNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readMessage(final String myMobile, final String exMobile)
    {
        mChatsFarmers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChatsFarmers.clear();
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    ChatsFarmer chatsFarmer=snap.getValue(ChatsFarmer.class);

//                    String mmNo=chatsFarmer.getReceiver();
//                    Log.i("MyNumberIS",mmNo);

                    if (chatsFarmer.getReceiver().equals(myMobile) && chatsFarmer.getSender().equals(exMobile) ||
                    chatsFarmer.getReceiver().equals(exMobile) && chatsFarmer.getSender().equals(myMobile))
                    {
                        mChatsFarmers.add(chatsFarmer);
                    }
                    messageAdapterFarmer=new MessageAdapterFarmer(MessageActivityFarmer.this,mChatsFarmers);
                    recyclerView1.setAdapter(messageAdapterFarmer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}