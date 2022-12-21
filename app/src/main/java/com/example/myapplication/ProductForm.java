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
import com.example.myapplication.DB.DBHelperProduct;
import com.example.myapplication.Entities.Product;
import com.example.myapplication.Services.ProductService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

public class ProductForm extends AppCompatActivity {
    private Button btnForm;
    private EditText editNameForm, editDescriptionForm, editPriceForm;
    private DBFirebase dbFirebase;
    private ImageView imageForm;
    private ActivityResultLauncher<String> content;
    private StorageReference storageReference;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        btnForm = (Button) findViewById(R.id.btnForm);
        editNameForm = (EditText) findViewById(R.id.editNameForm);
        editDescriptionForm = (EditText) findViewById(R.id.editDescriptionForm);
        editPriceForm = (EditText) findViewById(R.id.editPriceForm);
        imageForm = (ImageView) findViewById(R.id.imageForm);
        urlImage = "";

        try {
            dbFirebase = new DBFirebase();
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
                                                    Glide.with(ProductForm.this)
                                                            .load(downLoadUrl)
                                                            .override(500, 500)
                                                            .into(imageForm);
                                                }
                                            });
                                        }
                                    });
                        }
                    });
        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        Intent intentIN = getIntent();
        if (intentIN.getBooleanExtra("edit", false)) {
            editNameForm.setText(intentIN.getStringExtra("name"));
            editDescriptionForm.setText(intentIN.getStringExtra("description"));
            editPriceForm.setText(intentIN.getStringExtra("price"));
        }

        imageForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product(
                        editNameForm.getText().toString(),
                        editDescriptionForm.getText().toString(),
                        editPriceForm.getText().toString(),
                        urlImage
                );

                if (intentIN.getBooleanExtra("edit", false)) {
                    product.setId(intentIN.getStringExtra("id"));
                    dbFirebase.updateProduct(product);
                } else {
                    dbFirebase.insertProduct(product);
                }

                Intent intent = new Intent(getApplicationContext(), ProductCatalog.class);
                startActivity(intent);
            }
        });
    }

    public void clean(){
        editNameForm.setText("");
        editDescriptionForm.setText("");
        editPriceForm.setText("");
        imageForm.setImageResource(R.drawable.ic_launcher_background);
    }
}
