package com.example.google.sabaikobiruwa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BuySale extends AppCompatActivity {
    Button salesBtn,buysBtns;
    String usersname,address,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sale);
        salesBtn = findViewById(R.id.sell);
        buysBtns = findViewById(R.id.buy);
        Intent intents =getIntent();
        usersname = intents.getStringExtra("name");
        address = intents.getStringExtra("address");
        phone = intents.getStringExtra("phone");
        buysBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySale.this,Market.class);
                startActivity(intent);
            }
        });
        salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySale.this,UploadItem.class);
                intent.putExtra("name",usersname);
                intent.putExtra("phone",phone);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
    }
}
