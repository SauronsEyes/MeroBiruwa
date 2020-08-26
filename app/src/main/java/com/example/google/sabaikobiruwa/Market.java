package com.example.google.sabaikobiruwa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Market extends AppCompatActivity {
    private static final String URL = "http://192.168.43.232/Php/getItems.php";
    String usersname,address,phone;
    List<Item> itemList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        recyclerView = findViewById(R.id.itemLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson =gsonBuilder.create();
                Log.d("RESPONSE<><><><>",response);
                Item[] items = gson.fromJson(response,Item[].class);
                recyclerView.setAdapter(new GithubAdapter(Market.this,items));
                gson.fromJson(response,Item[].class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Market.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}
