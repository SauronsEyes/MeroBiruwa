package com.example.google.sabaikobiruwa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyGarden extends AppCompatActivity {

    String usersname,phone,address;
    Button start_plantation,garden_states,soil_minerals;
    LinearLayout homeLayouts,marketLayouts,connectLayouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garden);
        start_plantation = findViewById(R.id.start_plant);
        garden_states = findViewById(R.id.garden_state);
        homeLayouts = findViewById(R.id.homeLayouts);
        soil_minerals = findViewById(R.id.soil_minerals);
        marketLayouts = findViewById(R.id.marketLayouts);
        connectLayouts = findViewById(R.id.connectLayouts);
        Intent intents = getIntent();
        usersname = intents.getStringExtra("name");
        phone = intents.getStringExtra("phone");
        address = intents.getStringExtra("address");
        connectLayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(MyGarden.this,MainActivity.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        soil_minerals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(MyGarden.this,SoilMinerals.class);
                startActivity(inten);
            }
        });
        homeLayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(MyGarden.this,MainActivity.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        marketLayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(MyGarden.this,BuySale.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        garden_states.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(MyGarden.this,GardenStatus.class);
                startActivity(ints);
            }
        });
        start_plantation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGarden.this,ControlBot.class);
                startActivity(intent);
            }
        });
    }
}
