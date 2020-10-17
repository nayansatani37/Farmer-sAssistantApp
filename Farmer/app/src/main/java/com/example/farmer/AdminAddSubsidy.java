package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminAddSubsidy extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    EditText edSubname,edSubDetails,edWhocanget,edRequiredform,edLimitperyear,edStartingdate,edEndingdate;
    Button addSub;

    DatePickerDialog.OnDateSetListener date,date2;
    Calendar myCalendar;

    String sDate,eDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_subsidy);

        addSub=(Button)findViewById(R.id.btnaddsub);
        myCalendar= Calendar.getInstance();

        edSubname=(EditText)findViewById(R.id.subname);
        edSubDetails=(EditText)findViewById(R.id.subDetails);
        edWhocanget=(EditText)findViewById(R.id.whocanget);
        edRequiredform=(EditText)findViewById(R.id.requiredform);
        edLimitperyear=(EditText)findViewById(R.id.limitperyear);
        edStartingdate=(EditText)findViewById(R.id.startingdate);
        edEndingdate=(EditText)findViewById(R.id.endingdate);

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

        edStartingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminAddSubsidy.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edEndingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AdminAddSubsidy.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edStartingdate.setText(sdf.format(myCalendar.getTime()));
        sDate=edStartingdate.getText().toString();
    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edEndingdate.setText(sdf.format(myCalendar.getTime()));
        eDate=edStartingdate.getText().toString();
    }

    public void addSubClick(View view)
    {
//        if (sDate.compareTo(eDate)<0)
//        {
//            Toast.makeText(AdminAddSubsidy.this,"Date Error",Toast.LENGTH_SHORT).show();
//        }
//        else if (sDate.compareTo(eDate)==0)
//        {
//            Toast.makeText(AdminAddSubsidy.this,"Date Must not be same",Toast.LENGTH_SHORT).show();
//
//        }
//        else
//        {
//            Toast.makeText(AdminAddSubsidy.this,"Date ok",Toast.LENGTH_SHORT).show();
//
//        }
        String sname=edSubname.getText().toString();
        String sdetails=edSubDetails.getText().toString();
        String swhocanget=edWhocanget.getText().toString();
        String srequiredform=edRequiredform.getText().toString();
        String slimitperyear=edLimitperyear.getText().toString();
        String sstartingdate=edStartingdate.getText().toString();
        String sendingdate=edEndingdate.getText().toString();

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Subsidy");

        AdminSubHelperClass helperClass = new AdminSubHelperClass(sname,sdetails,swhocanget,srequiredform,
                slimitperyear,sstartingdate,sendingdate);

        if (TextUtils.isEmpty(sname)||TextUtils.isEmpty(sdetails)||TextUtils.isEmpty(swhocanget)||TextUtils.isEmpty(srequiredform)
                || TextUtils.isEmpty(slimitperyear)||TextUtils.isEmpty(sstartingdate)||TextUtils.isEmpty(sendingdate))
        {
            Toast.makeText(AdminAddSubsidy.this, "Field must not be empty", Toast.LENGTH_LONG).show();
        }
        else {
            reference.child(sname).setValue(helperClass);

            Toast.makeText(AdminAddSubsidy.this, "Subsidy Added Succsessfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AdminAddSubsidy.this, AdminHome.class);
            startActivity(intent);
            finish();
        }
    }
}