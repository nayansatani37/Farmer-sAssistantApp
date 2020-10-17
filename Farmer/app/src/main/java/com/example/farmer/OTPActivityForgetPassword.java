package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.concurrent.TimeUnit;

public class OTPActivityForgetPassword extends AppCompatActivity {

    PinView fpinFromUser;
    String fcodeBySystem;
    String mobileforOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_forget_password);

        fpinFromUser=findViewById(R.id.otpPinView);

        mobileforOTP=getIntent().getStringExtra("forgetmobilenumber");
        sendVerificationCodeToUser(mobileforOTP);
    }

    private void sendVerificationCodeToUser(String mobilenoforOTP) {

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {

            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(mobilenoforOTP,"IN");
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164),
            //PhoneAuthProvider.getInstance().verifyPhoneNumber(
              //      mobilenoforOTP,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }
        catch (NumberParseException e)
        {

        }

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new
            PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    fcodeBySystem=s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code=phoneAuthCredential.getSmsCode();
                    if (code!=null)
                    {
                        fpinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(OTPActivityForgetPassword.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(fcodeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(OTPActivityForgetPassword.this,"Verification completed",Toast.LENGTH_LONG).show();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(OTPActivityForgetPassword.this,"Verification is not completed! Try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    public void callNextScreenFromOTP(View view)
    {
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();

        String code=fpinFromUser.getText().toString();
        if(code.isEmpty())
        {
            verifyCode(code);
        }
        Intent intent=new Intent(OTPActivityForgetPassword.this,SetNewPassword.class);
        intent.putExtra("mobileforconfirm",mobileforOTP);
        startActivity(intent);
        finish();
    }
}