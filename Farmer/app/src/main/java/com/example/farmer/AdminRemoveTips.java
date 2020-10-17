package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRemoveTips extends AppCompatActivity {

    DatabaseReference databaseTips;
    ListView listViewTips;
    List<AdminTipHelperClass> tipsList;
    FirebaseDatabase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_tips);

        listViewTips=(ListView)findViewById(R.id.listtips);
        tipsList=new ArrayList<>();

        databaseTips=FirebaseDatabase.getInstance().getReference("Tips");


        listViewTips.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AdminTipHelperClass tips=tipsList.get(i);
                showUpdateDeleteDialog(tips.getTiptitle(),tips.getTipdesc());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTips.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tipsList.clear();
                for (DataSnapshot tipsSnapshot:snapshot.getChildren())
                {
                    AdminTipHelperClass tips=tipsSnapshot.getValue(AdminTipHelperClass.class);
                    tipsList.add(tips);
                }
                TipsList adapter=new TipsList(AdminRemoveTips.this,tipsList);
                listViewTips.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String tipName, final String tipsdesc) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_tips, null);
        dialogBuilder.setView(dialogView);

        final EditText edtipsName = (EditText) dialogView.findViewById(R.id.editTextTipsName);
        final EditText edtipsDesc = (EditText) dialogView.findViewById(R.id.editTextTipsDetails);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateTips);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteTips);

        edtipsName.setText(tipName);
        edtipsDesc.setText(tipsdesc);

        dialogBuilder.setTitle("Update " + tipName+ "'s Information");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTipsname = edtipsName.getText().toString().trim();
                String sTipsdetails = edtipsDesc.getText().toString().trim();

                if (TextUtils.isEmpty(sTipsname)||TextUtils.isEmpty(sTipsdetails))
                {
                    Toast.makeText(AdminRemoveTips.this,"All Field Must not be Empty !",Toast.LENGTH_LONG).show();
                }
                else {
                    updateTips(tipName, sTipsdetails);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTips(tipName);
                b.dismiss();
            }
        });
    }

    private boolean updateTips( String tiname, String tidetails)
    {
        DatabaseReference dbReference=FirebaseDatabase.getInstance().getReference("Tips").child(tiname);
        AdminTipHelperClass adminTipHelperClass = new AdminTipHelperClass(tiname,tidetails);
        dbReference.setValue(adminTipHelperClass);
        Toast.makeText(getApplicationContext(), "Tit Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void deleteTips(String sTipsName)
    {
        DatabaseReference drExpert=FirebaseDatabase.getInstance().getReference("Tips").child(sTipsName);
        drExpert.removeValue();
        Toast.makeText(this,"Tips Deleted",Toast.LENGTH_LONG).show();
    }
}