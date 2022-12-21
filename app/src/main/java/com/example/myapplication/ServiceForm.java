package com.example.myapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.DB.DBFirebaseService;
import com.example.myapplication.DB.DBHelperProduct;
import com.example.myapplication.Entities.Product;
import com.example.myapplication.Entities.Service;
import com.example.myapplication.Services.ProductService;
import com.example.myapplication.Services.Services;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ServiceForm extends AppCompatActivity {
    private Button btnServiceForm;
    private EditText editNameServiceForm, editDescriptionServiceForm, editPriceServiceForm;
    private DBFirebaseService dbFirebaseService;
    private ImageView imageServiceForm;
    private ActivityResultLauncher<String> content;
    private StorageReference storageReference;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);

        btnServiceForm = (Button) findViewById(R.id.btnServiceForm);
        editNameServiceForm = (EditText) findViewById(R.id.editNameServiceForm);
        editDescriptionServiceForm = (EditText) findViewById(R.id.editDescriptionServiceForm);
        editPriceServiceForm = (EditText) findViewById(R.id.editPriceServiceForm);

        imageServiceForm = (ImageView) findViewById(R.id.imageServiceForm);
        urlImage = "";

        try {
            dbFirebaseService = new DBFirebaseService();
            storageReference = FirebaseStorage.getInstance().getReference();
            content = registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            Uri uri = result;
                            StorageReference filePath = storageReference.child("images").child(uri.getLastPathSegment());
                            filePath.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getApplicationContext(),"Imagen Cargada", Toast.LENGTH_SHORT).show();
                                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Uri downLoadUrl = uri;
                                                    urlImage = downLoadUrl.toString();
                                                    Glide.with(ServiceForm.this)
                                                            .load(downLoadUrl)
                                                            .override(500, 500)
                                                            .into(imageServiceForm);
                                                }
                                            });
                                        }
                                    });
                        }
                    });
        }catch (Exception e){
            Log.e("DB", e.toString());
        };

        Intent intentIN = getIntent();
        if (intentIN.getBooleanExtra("edit", false)) {
            editNameServiceForm.setText(intentIN.getStringExtra("name"));
            editDescriptionServiceForm.setText(intentIN.getStringExtra("description"));
            editPriceServiceForm.setText(intentIN.getStringExtra("price"));
        }

        imageServiceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

        btnServiceForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = new Service(
                        editNameServiceForm.getText().toString(),
                        editDescriptionServiceForm.getText().toString(),
                        editPriceServiceForm.getText().toString(),
                        urlImage
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

    public void clean(){
        editNameServiceForm.setText("");
        editDescriptionServiceForm.setText("");
        editPriceServiceForm.setText("");
        imageServiceForm.setImageResource(R.drawable.ic_launcher_background);
    }
}