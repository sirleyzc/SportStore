package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BranchInfo extends AppCompatActivity {
    private TextView textNameBranchInfo, textPhoneBranchInfo;
    private ImageView imgBranchInfo;
    private Button btnBranchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_info);

        textNameBranchInfo = (TextView) findViewById(R.id.textNameBranchInfo);
        textPhoneBranchInfo = (TextView) findViewById(R.id.textPhoneBranchInfo);
        btnBranchInfo = (Button) findViewById(R.id.btnBranchInfo);

        Intent intentIN = getIntent();

        textNameBranchInfo.setText(intentIN.getStringExtra("name"));
        textPhoneBranchInfo.setText(intentIN.getStringExtra("phone"));

        btnBranchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BranchCatalog.class);
                startActivity(intent);
            }
        });

    }
}