package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddNewUser extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    EditText expfirstname,explastname,expemail,exppassword,expmobile,expaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_user);

        expfirstname=(EditText)findViewById(R.id.add_expfirstname);
        explastname=(EditText)findViewById(R.id.add_explastname);
        expemail=(EditText)findViewById(R.id.add_expemail);
        exppassword=(EditText)findViewById(R.id.add_exppassword);
        expmobile=(EditText)findViewById(R.id.add_expmobile);
        expaddress=(EditText)findViewById(R.id.add_expaddress);
    }

    public void addExpertClick(View view)
    {
        String efirstname=expfirstname.getText().toString();
        String elastname=explastname.getText().toString();
        String eemail=expemail.getText().toString();
        String emobile=expmobile.getText().toString();
        String epassword=exppassword.getText().toString();
        String eaddress=expaddress.getText().toString();

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Experts");

        AdminAddExpertHelper helperClass = new AdminAddExpertHelper(efirstname,elastname,eemail,emobile,epassword,eaddress);

        if (TextUtils.isEmpty(efirstname)||TextUtils.isEmpty(elastname)||TextUtils.isEmpty(eemail)||TextUtils.isEmpty(emobile)||
                TextUtils.isEmpty(epassword)||TextUtils.isEmpty(eaddress))
        {
            Toast.makeText(AdminAddNewUser.this, "Field must not be empty", Toast.LENGTH_LONG).show();
        }
        else {
            reference.child(emobile).setValue(helperClass);

            Toast.makeText(AdminAddNewUser.this, "Expert Added Succsessfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AdminAddNewUser.this, AdminManageExpertHome.class);
            startActivity(intent);
            finish();
        }
    }
}