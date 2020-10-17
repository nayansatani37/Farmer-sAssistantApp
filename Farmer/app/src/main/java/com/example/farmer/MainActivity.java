package com.example.farmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CardView cvSubsidy,cvMarketRate,cvPostQuery,cvGetTips;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtusername,txtun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtusername=(TextView)findViewById(R.id.textwelcome);
        txtun=(TextView)findViewById(R.id.txtuname);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        //Toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSession username
        SessionManagement sessionManagement=new SessionManagement(this,SessionManagement.SESSION_USERSESSION);
        HashMap<String,String> userDetails=sessionManagement.getUsersDetailFromSession();

        //String fname=userDetails.get(SessionManagement.KEY_FIRSTNAME);
        Intent i=getIntent();
        String firstnamemsg=i.getStringExtra("firstnameFromDB");
        String lastnamemsg=i.getStringExtra("lastnameFromDB");
        txtusername.setText(getText(R.string.Welcome_msg).toString()+" \n " +firstnamemsg+ " "+lastnamemsg);
        //txtun.setText(firstnamemsg+ " "+lastnamemsg);


        //Menu

        Menu menu=navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        //Cardview Click
        cvSubsidy=(CardView)findViewById(R.id.cardsubsidy);
        cvMarketRate=(CardView)findViewById(R.id.cardmarketrate);
        cvPostQuery=(CardView)findViewById(R.id.cardpostquery);
        cvGetTips=(CardView)findViewById(R.id.cardgettips);

        cvSubsidy.setOnClickListener(this);
        cvMarketRate.setOnClickListener(this);
        cvPostQuery.setOnClickListener(this);
        cvGetTips.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Alert !");
            builder.setMessage(getText(R.string.app_close_alert_msg).toString());

            builder.setPositiveButton(getText(R.string.alert_yes).toString(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this,"Thank you ! Visit again",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            builder.setNegativeButton(getText(R.string.alert_no).toString(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)     {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                break;
            case R.id.nav_check_subsydies:
                Intent intent=new Intent(MainActivity.this,subsidy.class);
                startActivity(intent);
                break;
            case R.id.nav_market_rate:
                intent=new Intent(MainActivity.this,FarmerMarketRate.class);
                startActivity(intent);
                break;
            case R.id.nav_query:
                intent=new Intent(MainActivity.this,Queries.class);
                startActivity(intent);
                break;
            case R.id.nav_get_tips:
                intent=new Intent(MainActivity.this,GetTips.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View layout= null;
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);



                layout = inflater.inflate(R.layout.rating, null);
                final RatingBar ratingBar = (RatingBar)layout.findViewById(R.id.ratingBar);
                builder.setTitle(R.string.rate_us);
                builder.setMessage(R.string.Thankrate);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Float value = ratingBar.getRating();
                        Toast.makeText(MainActivity.this,"Rating is : "+value,Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton(R.string.nothanks, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.setView(layout);
                builder.show();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
                return true;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId())
        {
            case R.id.cardsubsidy:i=new Intent(this,subsidy.class);startActivity(i); break;
            case R.id.cardmarketrate:i=new Intent(this,FarmerMarketRate.class);startActivity(i);break;
            case R.id.cardpostquery:i=new Intent(this,ChatTabsHome.class);startActivity(i);break;
            case R.id.cardgettips:i=new Intent(this,GetTips.class);startActivity(i);break;
            default:break;
        }
    }


}