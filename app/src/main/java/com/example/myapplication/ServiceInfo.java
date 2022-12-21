package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ServiceInfo extends AppCompatActivity {
    private TextView textNameServiceInfo, textDescriptionServiceInfo, textPriceServiceInfo;
    private ImageView imgServiceInfo;
    private Button btnServiceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        textNameServiceInfo = (TextView) findViewById(R.id.textNameServiceInfo);
        textDescriptionServiceInfo = (TextView) findViewById(R.id.textDescriptionServiceInfo);
        textPriceServiceInfo = (TextView) findViewById(R.id.textPriceServiceInfo);
        btnServiceInfo = (Button) findViewById(R.id.btnServiceInfo);

        Intent intentIN = getIntent();

        textNameServiceInfo.setText(intentIN.getStringExtra("name"));
        textDescriptionServiceInfo.setText(intentIN.getStringExtra("description"));
        textPriceServiceInfo.setText(intentIN.getStringExtra("price"));

        Glide.with(ServiceInfo.this)
                .load(intentIN.getStringExtra("image"))
                .override(500, 500)
                .into(imgServiceInfo);

        btnServiceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ServiceCatalog.class);
                startActivity(intent);
            }
        });

    }
}