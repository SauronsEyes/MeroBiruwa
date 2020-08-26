package com.example.google.sabaikobiruwa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadItem extends AppCompatActivity {
    ImageView uploadCam,itempic,uploadFile;
    EditText itemName,itemPrice,itemDescription;
    Bitmap bitma;
    int SELECT_PICTURE = 1;
    int CAMERA_REQUEST=1;
    int bcheck;
    Button uploadItem;
    RequestQueue requestQueue;
    String usersname,address,phone;
    String insertUrl = "http://192.168.43.232/Php/uploadItems.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);
        uploadCam = (ImageView) findViewById(R.id.uploadCam);
        itempic = (ImageView) findViewById(R.id.itempic);
        uploadFile = (ImageView) findViewById(R.id.uploadFile);
        uploadItem = (Button) findViewById(R.id.uploadItem);
        itemName = (EditText) findViewById(R.id.itemName);
        itemPrice = (EditText) findViewById(R.id.itemPrice);
        itemDescription = (EditText) findViewById(R.id.itemDescription);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Intent intents =getIntent();
        usersname = intents.getStringExtra("name");
        address = intents.getStringExtra("address");
        phone = intents.getStringExtra("phone");
        uploadCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams params=itempic.getLayoutParams();
                params.height=100;
                bcheck=1;
                itempic.setLayoutParams(params);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams params=itempic.getLayoutParams();
                params.height=100;
                bcheck = 2;
                itempic.setLayoutParams(params);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
        uploadItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        parameters.put("seller",usersname);
                        parameters.put("address",address);
                        parameters.put("phone",phone);
                        parameters.put("itemname",itemName.getText().toString());
                        parameters.put("price",itemPrice.getText().toString());
                        parameters.put("description",itemDescription.getText().toString());
                        parameters.put("photo",getStringImage(bitma));

                        return parameters;
                    }
                };
                requestQueue.add(requests);
                Intent intent = new Intent(UploadItem.this,BuySale.class);
                intent.putExtra("name",usersname);
                intent.putExtra("phone",phone);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && bcheck==1) {
            bitma = (Bitmap) data.getExtras().get("data");
            itempic.setImageBitmap(bitma);
            Log.d("IMAGE>>>>>>>>",getStringImage(bitma));

        }

        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE && bcheck==2) {


            Uri selectedImageURI = data.getData();

            try {
                bitma = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageURI);
                Log.d("IMAGE>>>>>>>>",getStringImage(bitma));

            } catch (IOException e) {
                e.printStackTrace();
            }
            Picasso.with(UploadItem.this).load(selectedImageURI).noPlaceholder().fit().into(itempic);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
