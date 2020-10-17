package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddNewAdmin extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    EditText fname, lname, email, password, mobile;
    Button btnaddadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_admin);

        fname = (EditText) findViewById(R.id.add_adminfirstname);
        lname = (EditText) findViewById(R.id.add_adminlastname);
        email = (EditText) findViewById(R.id.add_adminemail);
        password = (EditText) findViewById(R.id.add_adminpassword);
        mobile = (EditText) findViewById(R.id.add_adminmobile);

        btnaddadmin = (Button) findViewById(R.id.btnaddnewadmin);

    }

    public void addAdminClick(View v)
    {
        String afirstname = fname.getText().toString();
        String alastname = lname.getText().toString();
        String aemail = email.getText().toString();
        String amobile = mobile.getText().toString();
        String apassword = password.getText().toString();


        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Admin");

        AdminAddAdminHelper helperClassadd = new AdminAddAdminHelper(afirstname, alastname, aemail, amobile, apassword);

        ref.child(amobile).setValue(helperClassadd);

        Toast.makeText(AdminAddNewAdmin.this, "Admin Added Succsessfully", Toast.LENGTH_LONG).show();

//        Intent intent=new Intent(AdminAddNewAdmin.this,AdminManageExpertHome.class);
//        startActivity(intent);
    }
}