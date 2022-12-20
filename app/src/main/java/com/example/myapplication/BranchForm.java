package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DB.DBFirebaseBranch;
import com.example.myapplication.Entities.Branch;

public class BranchForm extends AppCompatActivity {
    private Button btnBranchForm;
    private EditText editNameBranchForm, editPhoneBranchForm;
    private DBFirebaseBranch dbFirebaseBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_form);

        btnBranchForm = (Button) findViewById(R.id.btnBranchForm);
        editNameBranchForm = (EditText) findViewById(R.id.editNameBranchForm);
        editPhoneBranchForm = (EditText) findViewById(R.id.editPhoneBranchForm);

        dbFirebaseBranch = new DBFirebaseBranch();

        Intent intentIN = getIntent();
        if (intentIN.getBooleanExtra("edit", false)) {
            editNameBranchForm.setText(intentIN.getStringExtra("name"));
            editPhoneBranchForm.setText(intentIN.getStringExtra("phone"));
        }

        btnBranchForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Branch branch = new Branch(
                        editNameBranchForm.getText().toString(),
                        editPhoneBranchForm.getText().toString(),
                        "",
                        "",
                        ""
                );

                if (intentIN.getBooleanExtra("edit", false)) {
                    branch.setId(intentIN.getStringExtra("id"));
                    dbFirebaseBranch.updateData(branch);
                } else {
                    dbFirebaseBranch.insertData(branch);
                }

                Intent intent = new Intent(getApplicationContext(), BranchCatalog.class);
                startActivity(intent);
            }
        });
    }
}