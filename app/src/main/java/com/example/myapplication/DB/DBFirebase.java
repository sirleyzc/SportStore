package com.example.myapplication.DB;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Adapters.ProductAdapter;
import com.example.myapplication.Entities.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private FirebaseFirestore db;

    public DBFirebase() {
        this.db =  FirebaseFirestore.getInstance();
    }

    public void insertProduct(Product product){
        Map<String, Object> prod = new HashMap<>();
        prod.put("id", product.getId());
        prod.put("name", product.getName());
        prod.put("description", product.getDescription());
        prod.put("price", product.getPrice());
        prod.put("image", product.getImage());

        // Add a new document with a generated ID
        db.collection("products").add(prod);
    }

    public void getProducts(ProductAdapter productAdapter, ArrayList<Product> list){
        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product product = new Product(
                                        document.getData().get("id").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("description").toString(),
                                        document.getData().get("price").toString(),
                                        document.getData().get("image").toString()

                                );
                                list.add(product);
                            }
                            productAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void deleteProduct(String id){
        db.collection("products").whereEqualTo("id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().delete();
                            }
                        }
                    }
                });
    }

    public void updateProduct(Product product){
        db.collection("products").whereEqualTo("id", product.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().update(
                                        "name", product.getName(),
                                        "description", product.getDescription(),
                                        "price", product.getPrice(),
                                        "image", product.getImage()
                                );
                            }
                        }
                    }
                });
    }
}
