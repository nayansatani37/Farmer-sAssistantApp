package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRemoveExpert extends AppCompatActivity {

    DatabaseReference databaseExperts;
    ListView listViewExperts;
    List<AdminAddExpertHelper> expertsList;
    FirebaseDatabase ref;

    String sfname,slname,semail,smobile,spassword,saddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_expert);

        listViewExperts=(ListView)findViewById(R.id.listexpert);
        expertsList=new ArrayList<>();

        databaseExperts=FirebaseDatabase.getInstance().getReference("Experts");


        listViewExperts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AdminAddExpertHelper experts=expertsList.get(i);
                showUpdateDeleteDialog(experts.getExpertMobile(),experts.getExpertFirstname(),experts.getExpertLastname(),experts.getExpertEmail(),experts.getExpertAddress(),experts.getExpertPassword());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseExperts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                expertsList.clear();
                for (DataSnapshot expertSnapshot:snapshot.getChildren())
                {
                    AdminAddExpertHelper experts=expertSnapshot.getValue(AdminAddExpertHelper.class);
                    expertsList.add(experts);
                }
                ExpertList adapter=new ExpertList(AdminRemoveExpert.this,expertsList);
                listViewExperts.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String expertMobile, final String expertFirstname,
                                        final String expertLastname,final String expertEmail,
                                        final String expertAddress,final String expertPassword) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edFirstName = (EditText) dialogView.findViewById(R.id.editTextFirstName);
        final EditText edLastName = (EditText) dialogView.findViewById(R.id.editTextLastName);
        final EditText edEmail = (EditText) dialogView.findViewById(R.id.editTextEmail);
        final EditText edMobile = (EditText) dialogView.findViewById(R.id.editTextMobile);
        final EditText edPassword = (EditText) dialogView.findViewById(R.id.editTextPassword);
        final EditText edAddress = (EditText) dialogView.findViewById(R.id.editTextAddress);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        edFirstName.setText(expertFirstname);
        edLastName.setText(expertLastname);
        edEmail.setText(expertEmail);
        edMobile.setText(expertMobile);
        edPassword.setText(expertPassword);
        edAddress.setText(expertAddress);

        dialogBuilder.setTitle("Update " + expertFirstname+ " "+expertLastname+ "'s Information");
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sfname = edFirstName.getText().toString().trim();
                slname = edLastName.getText().toString().trim();
                semail = edEmail.getText().toString().trim();
                smobile = edMobile.getText().toString().trim();
                spassword = edPassword.getText().toString().trim();
                saddress = edAddress.getText().toString().trim();

                if (TextUtils.isEmpty(sfname)||TextUtils.isEmpty(slname)||TextUtils.isEmpty(semail)||TextUtils.isEmpty(spassword)
                        || TextUtils.isEmpty(smobile)||TextUtils.isEmpty(saddress))
                {
                    Toast.makeText(AdminRemoveExpert.this, "Field must not be empty", Toast.LENGTH_LONG).show();
                }
                else {
                    updateExpert(expertMobile, sfname, slname, semail, spassword, saddress);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    deleteExpert(expertMobile);
                    b.dismiss();
            }
        });

    }

    private boolean updateExpert( String mobile, String fname, String lname, String email,String password, String address)
    {
        DatabaseReference dbReference=FirebaseDatabase.getInstance().getReference("Experts").child(mobile);
        AdminAddExpertHelper adminAddExpertHelper = new AdminAddExpertHelper(fname,lname,email,mobile,password,address);


            dbReference.setValue(adminAddExpertHelper);
            Toast.makeText(getApplicationContext(), "Expert Updated", Toast.LENGTH_LONG).show();
        return true;

    }

    private void deleteExpert(String expMobile)
    {
        DatabaseReference drExpert=FirebaseDatabase.getInstance().getReference("Experts").child(expMobile);
        drExpert.removeValue();
        Toast.makeText(this,"Expert Deleted",Toast.LENGTH_LONG).show();
    }
}