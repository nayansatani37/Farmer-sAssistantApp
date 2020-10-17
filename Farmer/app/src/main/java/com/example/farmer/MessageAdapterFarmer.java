package com.example.farmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MessageAdapterFarmer extends RecyclerView.Adapter<MessageAdapterFarmer.ViewHolder> {

    private Context context;
    private List<ChatsFarmer> mChatFarmer;


    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    SharedPreferences sharedPreferences;

    FirebaseUser firebaseUser;

    public MessageAdapterFarmer(Context context, List<ChatsFarmer> mChatFarmer) {
        this.context = context;
        this.mChatFarmer = mChatFarmer;
    }

    @NonNull
    @Override
    public MessageAdapterFarmer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapterFarmer.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapterFarmer.ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapterFarmer.ViewHolder holder, int position) {

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

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child(SessionManagement.KEY_MOBILE);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();


        if (mChatFarmer.get(position).getSender().equals(SessionManagement.KEY_MOBILE))
        {
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}

