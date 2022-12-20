package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapters.ServiceAdapter;
import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.DB.DBFirebaseService;
import com.example.myapplication.Entities.Service;

import java.util.ArrayList;

public class ServiceCatalog extends AppCompatActivity {
    private ListView listViewService;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Service> arrayServices;
    private DBFirebaseService dbFirebaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_catalog);

        listViewService = (ListView) findViewById(R.id.listViewService);

        dbFirebaseService = new DBFirebaseService();
        arrayServices = new ArrayList<>();
        serviceAdapter = new ServiceAdapter(this, arrayServices);

        listViewService.setAdapter(serviceAdapter);
        dbFirebaseService.getData(serviceAdapter);
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
                intent = new Intent(getApplicationContext(), ServiceForm.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Agregar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemMap:
                intent = new Intent(getApplicationContext(), Maps.class);
                startActivity(intent);
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