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

public class TipsList extends ArrayAdapter<AdminTipHelperClass> {

    private Activity context;
    private List<AdminTipHelperClass> tipsList;

    public TipsList(Activity context,List<AdminTipHelperClass> tipsList )
    {
        super(context,R.layout.expert_info,tipsList);
        this.context=context;
        this.tipsList=tipsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.expert_info,null,true);

        TextView textViewName=(TextView)listViewItem.findViewById(R.id.expertInfo);
        //TextView textViewDate=(TextView)listViewItem.findViewById(R.id.expertNumber);

        AdminTipHelperClass tips=tipsList.get(position);

        textViewName.setText(tips.getTiptitle());
        //textViewDate.setText("Date : "+subsidy.getSubstartingdate() + " To " +subsidy.getSubendingdate());

        return listViewItem;
    }

}
