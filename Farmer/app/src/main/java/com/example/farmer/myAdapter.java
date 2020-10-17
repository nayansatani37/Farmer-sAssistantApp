package com.example.farmer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<AdminSubHelperClass,myAdapter.myViewHolder> {

    private OnCardListener onCardListener;

    public myAdapter(@NonNull FirebaseRecyclerOptions<AdminSubHelperClass> options,OnCardListener onCardListener) {
        super(options);
        this.onCardListener=onCardListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull AdminSubHelperClass model) {

        holder.txtsname.setText(model.getSubname());
        holder.txtsdesc.setText(model.getSubdetails());
        holder.txtslimit.setText(model.getSublimitperyear());
        holder.txtsstartdate.setText(model.getSubstartingdate());
        holder.txtsenddate.setText(model.getSubendingdate());
        holder.txtsrequired.setText(model.getSubrequiredform());
        holder.txtswhocanget.setText(model.getSubwhocanget());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subsidy,parent,false);
        return new myViewHolder(view,onCardListener);
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView img;
        TextView txtsname,txtsdesc,txtslimit,txtsstartdate,txtsenddate,txtsrequired,txtswhocanget;
        OnCardListener onCardListener;

        public myViewHolder(@NonNull View itemView,OnCardListener onCardListener) {
            super(itemView);

            txtsname=(TextView)itemView.findViewById(R.id.sub_title);
            txtsdesc=(TextView)itemView.findViewById(R.id.sub_desc);
            txtslimit=(TextView)itemView.findViewById(R.id.sub_limitperyear);
            txtsstartdate=(TextView)itemView.findViewById(R.id.sub_startingdate);
            txtsenddate=(TextView)itemView.findViewById(R.id.sub_endingdate);
            txtsrequired=(TextView)itemView.findViewById(R.id.sub_requiredform);
            txtswhocanget=(TextView)itemView.findViewById(R.id.sub_whocanget);
            this.onCardListener=onCardListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnCardListener
    {
        void onCardClick(int position);
    }

}
