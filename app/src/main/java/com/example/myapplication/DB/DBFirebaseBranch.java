package com.example.myapplication.DB;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Adapters.BranchAdapter;
import com.example.myapplication.Entities.Branch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebaseBranch{
    private FirebaseFirestore db;

    public DBFirebaseBranch() {
        db = FirebaseFirestore.getInstance();
    }

    public void insertData(Branch branch) {
        // Create a new user with a first and last name
        Map<String, Object> bran = new HashMap<>();
        bran.put("id", branch.getId());
        bran.put("name", branch.getName());
        bran.put("phone", branch.getPhone());
        bran.put("image", branch.getImage());
        bran.put("latitude", branch.getLatitude());
        bran.put("longitude", branch.getLongitude());
        db.collection("branches").add(bran);
    }

    public void getData(BranchAdapter branchAdapter) {
        db.collection("branches")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Branch> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Branch branch = new Branch(
                                        document.getData().get("id").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("phone").toString(),
                                        document.getData().get("image").toString(),
                                        document.getData().get("latitude").toString(),
                                        document.getData().get("longitude").toString()
                                );
                                list.add(branch);
                            }
                            branchAdapter.setArrayBranch(list);
                            branchAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void deleteData(String id) {
        db.collection("branches").whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Branch> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                        }
                    }
                });
    }

    public void updateData(Branch branch) {
        db.collection("branches").whereEqualTo("id", branch.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Branch> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().update(
                                        "name", branch.getName(),
                                        "phone", branch.getPhone(),
                                        "image", branch.getImage(),
                                        "latitude", branch.getLatitude(),
                                        "longitude", branch.getLongitude()
                                );
                            }
                        }
                    }
                });

    }
}
