package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.Entities.Product;

import java.util.ArrayList;

public class ProductCatalog extends AppCompatActivity {
    private ListView listViewProducts;
    private ProductAdapter productAdapter;
    private ArrayList<Product> arrayProducts;
    private DBFirebase dbFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        listViewProducts = (ListView) findViewById(R.id.listViewProduct);

        dbFirebase = new DBFirebase();
        arrayProducts = new ArrayList<>();
        productAdapter = new ProductAdapter(this, arrayProducts);

        listViewProducts.setAdapter(productAdapter);
        dbFirebase.getData(productAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.itemAdd:
                intent = new Intent(getApplicationContext(), ProductForm.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Agregar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemFavorite:
                Toast.makeText(getApplicationContext(),"Favorito", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemShare:
                Toast.makeText(getApplicationContext(),"Compartir", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}