package com.example.google.sabaikobiruwa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ConnectBiruwa extends AppCompatActivity {
    Button fbCommunity,contactSkill;
    String usersname,phone,address;
    LinearLayout marketLayoutz,homeLayoutz,myGardenLayoutz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_biruwa);
        fbCommunity = findViewById(R.id.fbCommunity);
        Intent intents = getIntent();
        usersname = intents.getStringExtra("name");
        phone = intents.getStringExtra("phone");
        address = intents.getStringExtra("address");
        marketLayoutz = findViewById(R.id.marketLayoutZ);
        homeLayoutz = findViewById(R.id.homeLayoutZ);
        myGardenLayoutz = findViewById(R.id.myGardenLayoutZ);
        contactSkill = findViewById(R.id.contactSkill);
        contactSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9861488830"));
                startActivity(intent);            }
        });
        marketLayoutz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(ConnectBiruwa.this,BuySale.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        homeLayoutz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(ConnectBiruwa.this,MainActivity.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        myGardenLayoutz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(ConnectBiruwa.this,MyGarden.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        fbCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/767083436999318/"));
                startActivity(browserIntent);
            }
        });
    }
}
