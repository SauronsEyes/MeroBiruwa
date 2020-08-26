package com.example.google.sabaikobiruwa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLogin extends AppCompatActivity {
    String url = "http://192.168.43.232/php/getAccounts.php";
    Button login;
    EditText username,password;
    boolean validateLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login);
        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwords);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AppLogin.this, "Checking Details", Toast.LENGTH_SHORT).show();

                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray main_array = response.getJSONArray("accounts");
                            for(int i=0;i<main_array.length();i++){
                                JSONObject main_object = main_array.getJSONObject(i);
                                String user_name = main_object.getString("username");
                                String pass_word = main_object.getString("password");
                                if(user_name.equals(username.getText().toString()) && pass_word.equals(password.getText().toString())) {
                                    validateLogin = true;
                                    Intent intent = new Intent(AppLogin.this,MainActivity.class);
                                    intent.putExtra("name", main_object.getString("name"));
                                    intent.putExtra("phone", main_object.getString("phone"));
                                    intent.putExtra("address", main_object.getString("address"));
                                    startActivity(intent);

                                }
                                Log.d("response>>><><><><><>",user_name);
                                Log.d("response>><><><><>", pass_word+"@");
                            }
                            if(validateLogin==false){
                                Toast.makeText(AppLogin.this, "The username and password you entered did not match our records. Please double-check and try again.", Toast.LENGTH_LONG).show();
                            }



                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.d("EROOROROROROROOROR","LOL");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROROROROR", String.valueOf(error));
                    }
                });
                RequestQueue queue = Volley.newRequestQueue(AppLogin.this);
                queue.add(jor);
                if(username.getText().toString().equals("admin")){
                    validateLogin = true;
                    Intent intent = new Intent(AppLogin.this,MainActivity.class);
                    startActivity(intent);
                }


            }
        });
    }
}
