package com.example.farmer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ExpertList extends ArrayAdapter<AdminAddExpertHelper> {

    private Activity context;
    private List<AdminAddExpertHelper> expertsList;

    public ExpertList(Activity context,List<AdminAddExpertHelper> expertsList )
    {
        super(context,R.layout.expert_info,expertsList);
        this.context=context;
        this.expertsList=expertsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.expert_info,null,true);

        TextView textViewName=(TextView)listViewItem.findViewById(R.id.expertInfo);
        TextView textViewMobile=(TextView)listViewItem.findViewById(R.id.expertNumber);

        AdminAddExpertHelper experts=expertsList.get(position);

        textViewName.setText(experts.getExpertFirstname()+" "+experts.getExpertLastname());
        textViewMobile.setText("Mobile : "+experts.getExpertMobile());

        return listViewItem;
    }
}
