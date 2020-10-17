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

public class SubsidyList extends ArrayAdapter<AdminSubHelperClass> {

    private Activity context;
    private List<AdminSubHelperClass> subsidyList;

    public SubsidyList(Activity context,List<AdminSubHelperClass> subsidyList )
    {
        super(context,R.layout.expert_info,subsidyList);
        this.context=context;
        this.subsidyList=subsidyList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.expert_info,null,true);

        TextView textViewName=(TextView)listViewItem.findViewById(R.id.expertInfo);
        TextView textViewDate=(TextView)listViewItem.findViewById(R.id.expertNumber);

        AdminSubHelperClass subsidy=subsidyList.get(position);

        textViewName.setText(subsidy.getSubname());
        textViewDate.setText("Date : "+subsidy.getSubstartingdate() + " To " +subsidy.getSubendingdate());

        return listViewItem;
    }
}
