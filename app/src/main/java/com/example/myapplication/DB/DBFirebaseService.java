package com.example.myapplication.DB;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.Adapters.ServiceAdapter;
import com.example.myapplication.Entities.Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebaseService {
        private FirebaseFirestore db;

        public DBFirebaseService() {
            db = FirebaseFirestore.getInstance();
        }

        public void insertData(Service service) {
            // Create a new user with a first and last name
            Map<String, Object> serv = new HashMap<>();
            serv.put("id", service.getId());
            serv.put("name", service.getName());
            serv.put("description", service.getDescription());
            serv.put("price", service.getPrice());
            serv.put("image", service.getImage());
            db.collection("services").add(serv);
        }

        public void getData(ServiceAdapter serviceAdapter) {
            db.collection("services")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Service> list = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Service service = new Service(
                                            document.getData().get("id").toString(),
                                            document.getData().get("name").toString(),
                                            document.getData().get("description").toString(),
                                            document.getData().get("price").toString(),
                                            document.getData().get("image").toString()
                                    );
                                    list.add(service);
                                }
                                serviceAdapter.setArrayServices(list);
                                serviceAdapter.notifyDataSetChanged();
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }

        public void deleteData(String id) {
            db.collection("services").whereEqualTo("id", id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Service> list = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().delete();
                                }
                            }
                        }
                    });
        }

        public void updateData(Service service) {
            db.collection("services").whereEqualTo("id", service.getId())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<Service> list = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    document.getReference().update(
                                            "name", service.getName(),
                                            "description", service.getDescription(),
                                            "price", service.getPrice(),
                                            "image", service.getImage()
                                    );
                                }
                            }
                        }
                    });

        }
}
