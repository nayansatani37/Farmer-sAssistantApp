package com.example.farmer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class myAdapterTipsView extends FirebaseRecyclerAdapter<AdminTipHelperClass,myAdapterTipsView.myViewHolder1>
{

    public myAdapterTipsView(@NonNull FirebaseRecyclerOptions<AdminTipHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder1 holder, int position, @NonNull AdminTipHelperClass model)
    {
        holder.tvTitle.setText(model.getTiptitle());
        holder.tvDesc.setText(model.getTipdesc());
    }

    @NonNull
    @Override
    public myViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gettips,parent,false);
        return new myViewHolder1(view);
    }

    class myViewHolder1 extends RecyclerView.ViewHolder
    {
        TextView tvTitle,tvDesc;
        public myViewHolder1(@NonNull View itemView1) {
            super(itemView1);
            tvTitle=(TextView)itemView1.findViewById(R.id.tip_title);
            tvDesc=(TextView)itemView1.findViewById(R.id.tip_desc);
        }
    }
}
