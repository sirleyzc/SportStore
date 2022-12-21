package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapters.BranchAdapter;
import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.DB.DBFirebaseBranch;
import com.example.myapplication.DB.DBHelperBranch;
import com.example.myapplication.Entities.Branch;
import com.example.myapplication.Entities.Product;
import com.example.myapplication.Services.BranchService;

import java.util.ArrayList;

public class BranchCatalog extends AppCompatActivity {
    private DBHelperBranch dbHelperBranch;
    private BranchService branchService;
    private ListView listViewBranch;
    private BranchAdapter branchAdapter;
    private ArrayList<Branch> arrayBranch;
    private DBFirebaseBranch dbFirebaseBranch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_catalog);

        arrayBranch = new ArrayList<>();
        listViewBranch = (ListView) findViewById(R.id.listViewBranch);
        try {
            dbHelperBranch = new DBHelperBranch(this);
            dbFirebaseBranch = new DBFirebaseBranch();
            branchService = new BranchService();
            arrayBranch = branchService.cursorToArray(dbHelperBranch.getBranches());

        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        branchAdapter = new BranchAdapter(this, arrayBranch);
        listViewBranch.setAdapter(branchAdapter);
        dbFirebaseBranch.getData(branchAdapter, arrayBranch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.itemAdd:
                intent = new Intent(getApplicationContext(), BranchForm.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Agregar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemMap:
                ArrayList<String> latitudes = new ArrayList<>();
                ArrayList<String> longitudes = new ArrayList<>();
                for(int i=0; i<arrayBranch.size(); i++){
                    String latitude = String.valueOf(arrayBranch.get(i).getLatitude());
                    String longitude = String.valueOf(arrayBranch.get(i).getLongitude());
                    latitudes.add(latitude);
                    longitudes.add(longitude);
                }

                intent = new Intent(getApplicationContext(), Maps.class);
                intent.putStringArrayListExtra("latitudes", latitudes);
                intent.putStringArrayListExtra("longitudes", longitudes);
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