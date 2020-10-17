package com.example.farmer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class intro_theme_activity extends AppCompatActivity {

    private static int SPLASH_SCREEN=3000;

    Animation topAnim,bottumAnim;
    ImageView imgview;
    TextView logo,slowgan;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_theme_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottumAnim= AnimationUtils.loadAnimation(this,R.anim.bottum_animation);

        imgview=(ImageView)findViewById(R.id.imageViewlogo);

        logo=(TextView)findViewById(R.id.textView4);
        slowgan=(TextView)findViewById(R.id.textView5);

        imgview.setAnimation(topAnim);
        logo.setAnimation(bottumAnim);
        slowgan.setAnimation(bottumAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(intro_theme_activity.this,AllLoginHome.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}