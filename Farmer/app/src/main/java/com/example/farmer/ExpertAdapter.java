package com.example.farmer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.data.model.User;

import java.util.List;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {

    private Context context;
    private List<AdminAddExpertHelper> mExperts;


    public ExpertAdapter(Context context, List<AdminAddExpertHelper> mExperts) {
        this.context = context;
        this.mExperts = mExperts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);

        return new ExpertAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final AdminAddExpertHelper experts=mExperts.get(position);
        holder.username.setText(experts.getExpertFirstname()+" "+experts.getExpertLastname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MessageActivityFarmer.class);
                i.putExtra("expertfname",experts.getExpertFirstname());
                i.putExtra("expertMno",experts.getExpertMobile());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mExperts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView username;

        public ViewHolder(View itemView)
        {
            super(itemView);
            username=itemView.findViewById(R.id.txtuname);
        }
    }
}
