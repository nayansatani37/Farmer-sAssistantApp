package com.example.farmer;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MessageAdapterExpert extends RecyclerView.Adapter<MessageAdapterExpert.ViewHolder>{

    private Context context;
    private List<ChatsFarmer> mChatFarmer;

    String number=SessionManagementExperts.KEY_EXMOBILE;

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;


    FirebaseUser firebaseUser;

    public MessageAdapterExpert(Context context, List<ChatsFarmer> mChatFarmer) {
        this.context = context;
        this.mChatFarmer = mChatFarmer;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapterExpert.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapterExpert.ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapterExpert.ViewHolder holder, int position) {

        ChatsFarmer chatsFarmer=mChatFarmer.get(position);
        holder.show_message.setText(chatsFarmer.getMessage());

    }

    @Override
    public int getItemCount() {
        return mChatFarmer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView show_message;

        public ViewHolder(View itemView)
        {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {

        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Experts");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        String number=firebaseUser.getPhoneNumber().toString();


        if (mChatFarmer.get(position).getSender().equals(ref))
        {
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }

}
