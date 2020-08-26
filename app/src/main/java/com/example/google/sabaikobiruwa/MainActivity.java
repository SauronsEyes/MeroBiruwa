package com.example.google.sabaikobiruwa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String usersname,phone,address;
    TextView currentTemperature,tempDay,tempDay2,tempDay3,tempDay4,minimumTemp,maximumTemp,weatherDescription,currentHumidity;
    ImageView weatherIcon,weatherIcon2,weatherIcon3,weatherIcon4;
    LinearLayout marketLayout;
    ArrayList<Integer> indices=new ArrayList<Integer>();
    ArrayList<String> futureT= new ArrayList<String>();
    String url = "https://api.openweathermap.org/data/2.5/weather?id=1282931&appid=793d9bc5fa762cb1fd9928b1afbb013f&units=metric";
    String url2 = "https://api.openweathermap.org/data/2.5/forecast?id=1282931&appid=793d9bc5fa762cb1fd9928b1afbb013f&units=metric";
    int maxTemp = 0;
    int minTemp = 1000;
    final static int PERMISSION_SEND_SMS =111;
    LinearLayout gardenLayout,connectLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTemperature = (TextView) findViewById(R.id.currentTemp);
        tempDay = (TextView) findViewById(R.id.tempDay);
        tempDay2 = (TextView) findViewById(R.id.tempDay2);
        tempDay3 = (TextView) findViewById(R.id.tempDay3);
        tempDay4 = (TextView) findViewById(R.id.tempDay4);
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon1);
        weatherIcon2 = (ImageView) findViewById(R.id.weatherIcon2);
        weatherIcon3 = (ImageView) findViewById(R.id.weatherIcon3);
        weatherIcon4 = (ImageView) findViewById(R.id.weatherIcon4);
        minimumTemp = (TextView) findViewById(R.id.minimumTemp);
        maximumTemp = (TextView) findViewById(R.id.maximumTemp);
        connectLayout = findViewById(R.id.connectLayout);
        weatherDescription = (TextView) findViewById(R.id.weatherDescription);
        currentHumidity = (TextView) findViewById(R.id.currentHumidity);
        marketLayout = (LinearLayout) findViewById(R.id.marketLayout);
        gardenLayout = findViewById(R.id.myGardenLayout);
        find_weather();
        find_update();
        Intent intents = getIntent();
        usersname = intents.getStringExtra("name");
        phone = intents.getStringExtra("phone");
        address = intents.getStringExtra("address");
        connectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ConnectBiruwa.class);
                intent.putExtra("name",usersname);
                intent.putExtra("phone",phone);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
        marketLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BuySale.class);
                intent.putExtra("name",usersname);
                intent.putExtra("phone",phone);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
        gardenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ints = new Intent(MainActivity.this,MyGarden.class);
                ints.putExtra("name",usersname);
                ints.putExtra("phone",phone);
                ints.putExtra("address",address);
                startActivity(ints);
            }
        });
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
        } else {
            // permission already granted run sms send

        }

    }
    public void find_weather(){
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject main_object = response.getJSONObject("main");
                    Log.d("response<><><><><><>", String.valueOf(response));
                    Log.d("Weathe.................", String.valueOf(main_object));
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String description = object.getString("description");
                    double tempz = (main_object.getDouble("temp"));
                    String humid = String.valueOf((int)main_object.getDouble("humidity"));
                    String temp = String.valueOf(tempz);
                    Log.d("Temperature>>>>>>>>>>>",temp);
                    maxTemp = (int) tempz;
                    minTemp = (int) tempz;
                    currentTemperature.setText(temp+(char) 0x00B0+"C");
                    weatherDescription.setText(description.toUpperCase());
                    currentHumidity.setText("Humidity: "+humid+"%");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }

    public void find_update(){
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray main_array = response.getJSONArray("list");
                    int initialDate = 0;
                    int val = 1;

                    int temp1 = 0;
                    int temp2 = 0;
                    int temp3 = 0;
                    int temp4 = 0;

                    for(int i=0;i < main_array.length();i++) {
                        JSONObject main_object = main_array.getJSONObject(i);
                        JSONObject maain = main_object.getJSONObject("main");
                        JSONArray array = main_object.getJSONArray("weather");
                        JSONObject object = array.getJSONObject(0);
                        String description = object.getString("description");
                        String date = String.valueOf(main_object.getString("dt_txt"));
                        double futureTemp = (maain.getDouble("temp"));
                        int nextDate = Integer.parseInt(date.substring(8,10));
                        int timeHr = Integer.parseInt(date.substring(11,13));

                        if(i==0){
                            initialDate = Integer.parseInt(date.substring(8,10));
                        }
                        if(nextDate-initialDate==0){
                            if((int)futureTemp>maxTemp){
                                maxTemp = (int) futureTemp;
                            }
                            if((int)futureTemp < minTemp){
                                minTemp = (int) futureTemp;
                            }
                        }
                        if(nextDate-initialDate==1){
                            if(timeHr == 12){
                                temp1 = (int) futureTemp;
                                if(description.toUpperCase().contains("RAIN")){
                                    weatherIcon.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("SNOW")){
                                    weatherIcon.setImageResource(R.drawable.snowy);
                                }
                                if(description.toUpperCase().contains("THUNDER")){
                                    weatherIcon.setImageResource(R.drawable.hailstorm);
                                }
                                if(description.toUpperCase().contains("SHOWER")){
                                    weatherIcon.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("CLOUD")){
                                    weatherIcon.setImageResource(R.drawable.cloudy);
                                }
                            }
                        }
                        if(nextDate-initialDate==2){
                            if(timeHr == 12){
                                temp2 = (int) futureTemp;
                                if(description.toUpperCase().contains("RAIN")){
                                    weatherIcon2.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("SNOW")){
                                    weatherIcon2.setImageResource(R.drawable.snowy);
                                }
                                if(description.toUpperCase().contains("THUNDER")){
                                    weatherIcon2.setImageResource(R.drawable.hailstorm);
                                }
                                if(description.toUpperCase().contains("SHOWER")){
                                    weatherIcon2.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("CLOUD")){
                                    weatherIcon2.setImageResource(R.drawable.cloudy);
                                }
                            }
                        }
                        if(nextDate-initialDate==3){
                            if(timeHr == 12){
                                temp3 = (int) futureTemp;
                                if(description.toUpperCase().contains("RAIN")){
                                    weatherIcon3.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("SNOW")){
                                    weatherIcon3.setImageResource(R.drawable.snowy);
                                }
                                if(description.toUpperCase().contains("THUNDER")){
                                    weatherIcon3.setImageResource(R.drawable.hailstorm);
                                }
                                if(description.toUpperCase().contains("SHOWER")){
                                    weatherIcon3.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("CLOUD")){
                                    weatherIcon3.setImageResource(R.drawable.cloudy);
                                }
                            }
                        }
                        if(nextDate-initialDate>=4){
                            if(timeHr == 12){
                                temp4 = (int) futureTemp;
                                if(description.toUpperCase().contains("RAIN")){
                                    weatherIcon4.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("SNOW")){
                                    weatherIcon4.setImageResource(R.drawable.snowy);
                                }
                                if(description.toUpperCase().contains("THUNDER")){
                                    weatherIcon4.setImageResource(R.drawable.hailstorm);
                                }
                                if(description.toUpperCase().contains("SHOWER")){
                                    weatherIcon4.setImageResource(R.drawable.rainy);
                                }
                                if(description.toUpperCase().contains("CLOUD")){
                                    weatherIcon4.setImageResource(R.drawable.cloudy);
                                }
                            }
                        }
                        String humid = String.valueOf(maain.getDouble("humidity"));

                    }
                    Log.d("weather<><><>-----<>", String.valueOf(temp2));


                    tempDay.setText(String.valueOf(temp1)+(char) 0x00B0+"C");
                    tempDay2.setText(String.valueOf(temp2)+(char) 0x00B0+"C");
                    tempDay3.setText(String.valueOf(temp3)+(char) 0x00B0+"C");
                    tempDay4.setText(String.valueOf(temp4)+(char) 0x00B0+"C");
                    minimumTemp.setText("Min Temp: "+ String.valueOf(minTemp)+(char) 0x00B0+"C");
                    maximumTemp.setText("Max Temp: " + String.valueOf(maxTemp)+(char) 0x00B0+"C");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }
}
