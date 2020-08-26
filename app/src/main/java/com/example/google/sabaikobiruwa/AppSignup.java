package com.example.google.sabaikobiruwa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AppSignup extends AppCompatActivity {
    EditText username,password,names,address,phones,photo;
    Button create_account;
    String insertUrl = "http://192.168.43.232/Php/insertData.php";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_signup);
        username = (EditText) findViewById(R.id.editText_username);
        password = (EditText) findViewById(R.id.editText_password);
        names = (EditText) findViewById(R.id.editText_name);
        address = (EditText) findViewById(R.id.editText_address);
        phones = (EditText) findViewById(R.id.editText_phone);

        create_account = (Button) findViewById(R.id.button_create);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest requests = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String,String>();
                        parameters.put("username",username.getText().toString());
                        parameters.put("password",password.getText().toString());
                        parameters.put("name",names.getText().toString());
                        parameters.put("address",address.getText().toString());
                        parameters.put("phone",phones.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(requests);
                Toast.makeText(AppSignup.this, "You are now a member of Mero Biruwa. Login to Continue", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AppSignup.this,SignupLogin.class);
                startActivity(intent);
            };
        });

    }
}
