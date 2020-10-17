package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminRemoveSubsidy extends AppCompatActivity {

    DatabaseReference databaseSubsidy;
    ListView listViewSubsidy;
    List<AdminSubHelperClass> subsidyList;
    FirebaseDatabase ref;

    DatePickerDialog.OnDateSetListener date,date2;
    Calendar myCalendar;
    EditText eedEndingDate;
    EditText edStartingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_subsidy);


        listViewSubsidy=(ListView)findViewById(R.id.listsubsidy);
        subsidyList=new ArrayList<>();

        databaseSubsidy=FirebaseDatabase.getInstance().getReference("Subsidy");


        listViewSubsidy.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                AdminSubHelperClass subsidy=subsidyList.get(i);
                showUpdateDeleteDialog(subsidy.getSubname(),subsidy.getSubdetails(),subsidy.getSubrequiredform(),subsidy.getSubwhocanget(),subsidy.getSublimitperyear(),subsidy.getSubstartingdate(),subsidy.getSubendingdate());
                return false;
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseSubsidy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                subsidyList.clear();
                for (DataSnapshot subsidySnapshot:snapshot.getChildren())
                {
                    AdminSubHelperClass subsidy=subsidySnapshot.getValue(AdminSubHelperClass.class);
                    subsidyList.add(subsidy);
                }
                SubsidyList adapter=new SubsidyList(AdminRemoveSubsidy.this,subsidyList);
                listViewSubsidy.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String subsidyName, final String subsidydetails,
                                        final String requiredform,final String whocanget,final String limitperyear,
                                        final String startingdate,final String endingdate) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_subsidy, null);
        dialogBuilder.setView(dialogView);

        final EditText edSubName = (EditText) dialogView.findViewById(R.id.editTextSubsidyName);
        final EditText edSubDetails = (EditText) dialogView.findViewById(R.id.editTextSubsidyDetails);
        final EditText edRequiredForm = (EditText) dialogView.findViewById(R.id.editTextRequiredForm);
        final EditText edWhocanget = (EditText) dialogView.findViewById(R.id.editTextWhocanGet);
        final EditText edLimitperyear = (EditText) dialogView.findViewById(R.id.editTextLimitPerYear);
        edStartingDate= (EditText) dialogView.findViewById(R.id.editTextStartingDate);
        eedEndingDate = (EditText) dialogView.findViewById(R.id.editTextEndingDate);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateSubsidy);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteSubsidy);


        edSubName.setText(subsidyName);
        edSubDetails.setText(subsidydetails);
        edRequiredForm.setText(requiredform);
        edLimitperyear.setText(limitperyear);
        edWhocanget.setText(whocanget);
        edStartingDate.setText(startingdate);
        eedEndingDate.setText(endingdate);

        //Datepicker dialog


        dialogBuilder.setTitle("Update " + subsidyName+ "'s Information");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSubname = subsidyName;
                String sSubdetails = edSubDetails.getText().toString().trim();
                String sRequiredForm = edRequiredForm.getText().toString().trim();
                String sWhocanget = edWhocanget.getText().toString().trim();
                String sLimitperyear = edLimitperyear.getText().toString().trim();
                String sStartingDate = edStartingDate.getText().toString().trim();
                String sEndingDate = eedEndingDate.getText().toString().trim();

                if (TextUtils.isEmpty(sSubdetails)||TextUtils.isEmpty(sRequiredForm)||TextUtils.isEmpty(sWhocanget)||
                        TextUtils.isEmpty(sLimitperyear)|| TextUtils.isEmpty(sStartingDate)||TextUtils.isEmpty(sEndingDate))
                {
                    Toast.makeText(AdminRemoveSubsidy.this, "Field must not be empty", Toast.LENGTH_LONG).show();
                }
                else {
                    updateSubsidy(subsidyName, sSubdetails, sRequiredForm, sWhocanget, sLimitperyear, sStartingDate, sEndingDate);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSubsidy(subsidyName);
                b.dismiss();
            }
        });

        myCalendar= Calendar.getInstance();
        date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
            }
        };
        date2=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel2();
            }
        };

        edStartingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminRemoveSubsidy.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        eedEndingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminRemoveSubsidy.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    private boolean updateSubsidy( String suname, String sudetails, String surequired, String suwhocan,String sulimitper, String sustarting,String suending)
    {
        DatabaseReference dbReference=FirebaseDatabase.getInstance().getReference("Subsidy").child(suname);
        AdminSubHelperClass adminSubHelperClass = new AdminSubHelperClass(suname,sudetails,surequired,suwhocan,sulimitper,sustarting,suending);
        dbReference.setValue(adminSubHelperClass);
        Toast.makeText(getApplicationContext(), "Subsidy Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void deleteSubsidy(String sSubsidyName)
    {
        DatabaseReference drExpert=FirebaseDatabase.getInstance().getReference("Subsidy").child(sSubsidyName);
        drExpert.removeValue();
        Toast.makeText(this,"Subsidy Deleted",Toast.LENGTH_LONG).show();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edStartingDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eedEndingDate.setText(sdf.format(myCalendar.getTime()));
    }
}