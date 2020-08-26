package com.example.google.sabaikobiruwa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GardenStatus extends AppCompatActivity {
    WebView displayMoisture;
    static String URL = "http://192.168.43.232/moisture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_status);
        displayMoisture = findViewById(R.id.displayMoisture);
        WebSettings webSettings = displayMoisture.getSettings();
        webSettings.setJavaScriptEnabled(true);
        displayMoisture.setWebViewClient(new WebViewClient());
        displayMoisture.loadUrl(URL);
    }
}
