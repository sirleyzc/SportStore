package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapters.BranchAdapter;
import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.DB.DBFirebase;
import com.example.myapplication.DB.DBFirebaseBranch;
import com.example.myapplication.Entities.Branch;
import com.example.myapplication.Entities.Product;

import java.util.ArrayList;

public class BranchCatalog extends AppCompatActivity {
    private ListView listViewBranch;
    private BranchAdapter branchAdapter;
    private ArrayList<Branch> arrayBranch;
    private DBFirebaseBranch dbFirebaseBranch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_catalog);

        listViewBranch = (ListView) findViewById(R.id.listViewBranch);

        dbFirebaseBranch = new DBFirebaseBranch();
        arrayBranch = new ArrayList<>();
        branchAdapter = new BranchAdapter(this, arrayBranch);

        listViewBranch.setAdapter(branchAdapter);
        dbFirebaseBranch.getData(branchAdapter);
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