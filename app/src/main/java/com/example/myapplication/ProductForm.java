package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.Entities.Product;

public class ProductForm extends AppCompatActivity {
    private Button btnForm;
    private EditText editNameForm, editDescriptionForm, editPriceForm;
    private DBFirebase dbFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        btnForm = (Button) findViewById(R.id.btnForm);
        editNameForm = (EditText) findViewById(R.id.editNameForm);
        editDescriptionForm = (EditText) findViewById(R.id.editDescriptionForm);
        editPriceForm = (EditText) findViewById(R.id.editPriceForm);

        dbFirebase = new DBFirebase();

        Intent intentIN = getIntent();
        if (intentIN.getBooleanExtra("edit", false)) {
            editNameForm.setText(intentIN.getStringExtra("name"));
            editDescriptionForm.setText(intentIN.getStringExtra("description"));
            editPriceForm.setText(intentIN.getStringExtra("price"));
        }

        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product(
                        editNameForm.getText().toString(),
                        editDescriptionForm.getText().toString(),
                        editPriceForm.getText().toString(),
                        ""
                );

                if (intentIN.getBooleanExtra("edit", false)) {
                    product.setId(intentIN.getStringExtra("id"));
                    dbFirebase.updateData(product);
                } else {
                    dbFirebase.insertData(product);
                }

                Intent intent = new Intent(getApplicationContext(), ProductCatalog.class);
                startActivity(intent);
            }
        });
    }
}