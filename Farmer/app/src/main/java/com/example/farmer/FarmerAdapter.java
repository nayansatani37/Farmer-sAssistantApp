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

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.ViewHolderFarmer> {

    private Context context;
    private List<UserHelperClass> mFarmers;


    public FarmerAdapter(Context context, List<UserHelperClass> mFarmers) {
        this.context = context;
        this.mFarmers = mFarmers;
    }


    @NonNull
    @Override
    public ViewHolderFarmer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new FarmerAdapter.ViewHolderFarmer(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFarmer holder, int position) {

        final UserHelperClass userHelperClass=mFarmers.get(position);
        holder.farmerName.setText(userHelperClass.getFirstname()+" "+userHelperClass.getLastame());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MessageActivityExpert.class);
                i.putExtra("farmerFname",userHelperClass.getFirstname());
                i.putExtra("farmerMno",userHelperClass.getMobile());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFarmers.size();
    }

    public class ViewHolderFarmer extends RecyclerView.ViewHolder
    {
        public TextView farmerName;

        public ViewHolderFarmer(@NonNull View itemView) {
            super(itemView);

            farmerName=itemView.findViewById(R.id.txtuname);

        }
    }
}
