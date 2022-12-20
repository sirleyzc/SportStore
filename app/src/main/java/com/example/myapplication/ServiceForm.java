package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.DB.DBFirebaseService;
import com.example.myapplication.Entities.Product;
import com.example.myapplication.Entities.Service;

public class ServiceForm extends AppCompatActivity {
    private Button btnServiceForm;
    private EditText editNameServiceForm, editDescriptionServiceForm, editPriceServiceForm;
    private DBFirebaseService dbFirebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);

        btnServiceForm = (Button) findViewById(R.id.btnServiceForm);
        editNameServiceForm = (EditText) findViewById(R.id.editNameServiceForm);
        editDescriptionServiceForm = (EditText) findViewById(R.id.editDescriptionServiceForm);
        editPriceServiceForm = (EditText) findViewById(R.id.editPriceServiceForm);

        dbFirebaseService = new DBFirebaseService();

        Intent intentIN = getIntent();
        if (intentIN.getBooleanExtra("edit", false)) {
            editNameServiceForm.setText(intentIN.getStringExtra("name"));
            editDescriptionServiceForm.setText(intentIN.getStringExtra("description"));
            editPriceServiceForm.setText(intentIN.getStringExtra("price"));
        }

        btnServiceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = new Service(
                        editNameServiceForm.getText().toString(),
                        editDescriptionServiceForm.getText().toString(),
                        editPriceServiceForm.getText().toString(),
                        ""
                );

                if (intentIN.getBooleanExtra("edit", false)) {
                    service.setId(intentIN.getStringExtra("id"));
                    dbFirebaseService.updateData(service);
                } else {
                    dbFirebaseService.insertData(service);
                }

                Intent intent = new Intent(getApplicationContext(), ServiceCatalog.class);
                startActivity(intent);
            }
        });
    }
}