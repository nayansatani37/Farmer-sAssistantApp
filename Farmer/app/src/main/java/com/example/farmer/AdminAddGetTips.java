package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddGetTips extends AppCompatActivity {

    EditText txttiptitle,txttipdesc;
    Button addtip;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_get_tips);

        txttiptitle=(EditText)findViewById(R.id.add_tiptititle);
        txttipdesc=(EditText)findViewById(R.id.add_tipdesc);

        addtip=(Button)findViewById(R.id.btnadd_gettip);
    }

    public void addTipClick(View view)
    {
        String tname=txttiptitle.getText().toString();
        String ttitle=txttipdesc.getText().toString();


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Tips");

        AdminTipHelperClass helperClass = new AdminTipHelperClass(tname,ttitle);

        if (TextUtils.isEmpty(tname)||TextUtils.isEmpty(ttitle))
        {
            Toast.makeText(AdminAddGetTips.this,"All Field Must not be Empty !",Toast.LENGTH_LONG).show();
        }
        else {
            reference.child(tname).setValue(helperClass);

            Toast.makeText(AdminAddGetTips.this, "Tips Added Succsessfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AdminAddGetTips.this, AdminHome.class);
            startActivity(intent);
            finish();
        }
    }


}