package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DB.DBHelperBranch;
import com.example.myapplication.Services.BranchService;

public class BranchInfo extends AppCompatActivity {
    private DBHelperBranch dbHelperBranch;
    private BranchService branchService;
    private TextView textNameBranchInfo, textPhoneBranchInfo;
    private ImageView imageBranchInfo;
    private Button btnBranchInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_info);

        textNameBranchInfo = (TextView) findViewById(R.id.textNameBranchInfo);
        textPhoneBranchInfo = (TextView) findViewById(R.id.textPhoneBranchInfo);
        imageBranchInfo = (ImageView) findViewById(R.id.imageBranchInfo);
        btnBranchInfo = (Button) findViewById(R.id.btnBranchInfo);

        dbHelperBranch = new DBHelperBranch(this);
        branchService = new BranchService();

        Intent intentIN = getIntent();

        textNameBranchInfo.setText(intentIN.getStringExtra("name"));
        textPhoneBranchInfo.setText(intentIN.getStringExtra("phone"));
        Glide.with(BranchInfo.this)
                .load(intentIN.getStringExtra("image"))
                .override(500, 500)
                .into(imageBranchInfo);

        btnBranchInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BranchCatalog.class);
                startActivity(intent);
            }
        });

    }
}