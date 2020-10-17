package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    Button btnchange;
    TextInputLayout txtnewpass,txtconfpass;
    TextInputEditText edtnewpass,edtconfpass;
    String confirmMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        btnchange=(Button)findViewById(R.id.btnchangepassword);
        txtnewpass=(TextInputLayout) findViewById(R.id.txtnewpassword);
        txtconfpass=(TextInputLayout) findViewById(R.id.txtconfirmpassword);
        edtnewpass=(TextInputEditText)findViewById(R.id.edtnewpassword);
        edtconfpass=(TextInputEditText)findViewById(R.id.edtconfirmpassword);


        confirmMobile=getIntent().getStringExtra("mobileforconfirm");

    }
    private boolean validateNewPassword()
    {
        String val = txtnewpass.getEditText().getText().toString();

        if (val.isEmpty()) {
            txtnewpass.setError("Field can't be empty");
            return false;
        }
        else {
            txtnewpass.setError(null);
            txtnewpass.setErrorEnabled(true);
            return true;
        }
    }
    private boolean validateConfirmPassword()
    {
        String val = txtconfpass.getEditText().getText().toString();

        if (val.isEmpty()) {
            txtconfpass.setError("Field can't be empty");
            return false;
        }
        else {
            txtconfpass.setError(null);
            txtconfpass.setErrorEnabled(true);
            return true;
        }
    }
    private boolean samePassword()
    {
        String val1 = txtnewpass.getEditText().getText().toString();
        String val2 = txtconfpass.getEditText().getText().toString();
        if (!val1.equals(val2))
        {

            txtconfpass.setError("Both field must be same");
            txtnewpass.setError("Both field must be same");
            return false;
        }
        else
        {
            txtnewpass.setError(null);
            txtnewpass.setErrorEnabled(true);
            txtconfpass.setError(null);
            txtconfpass.setErrorEnabled(true);
            return true;
        }
    }

    public void setNewPassword(View view)
    {
        if (!validateNewPassword()||!validateConfirmPassword()||!samePassword())
        {
            return;
        }
        String _newPassword=txtnewpass.getEditText().getText().toString().trim();
        String _phoneNumber=confirmMobile;

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(),ForgetSuccessMessage.class));
        finish();
    }

}