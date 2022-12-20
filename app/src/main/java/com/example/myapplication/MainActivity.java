package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnHomeProduct, btnHomeService, btnHomeBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHomeProduct = (Button) findViewById(R.id.btnHomeProduct);
        btnHomeService = (Button) findViewById(R.id.btnHomeService);
        btnHomeBranch = (Button) findViewById(R.id.btnHomeBranch);

        btnHomeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductCatalog.class);
                startActivity(intent);
            }
        });

        btnHomeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ServiceCatalog.class);
                startActivity(intent);
            }
        });

        btnHomeBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BranchCatalog.class);
                startActivity(intent);
            }
        });
    }
}