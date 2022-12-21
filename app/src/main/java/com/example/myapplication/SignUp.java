package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private Button btnSignUp;
    private EditText editEmailSignUp, editPasswordSignUp, editConfirmPassSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        editEmailSignUp = (EditText) findViewById(R.id.editEmailSignUp);
        editPasswordSignUp = (EditText) findViewById(R.id.editPasswordSignUp);
        editConfirmPassSignUp = (EditText) findViewById(R.id.editConfirmPassSignUp);
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailSignUp.getText().toString().trim();
                String pass = editPasswordSignUp.getText().toString().trim();
                String confirm = editConfirmPassSignUp.getText().toString().trim();

                if (pass.compareTo(confirm) == 0) {
                    mAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Usuario Registrado con exito",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Correo Ya Registrado ", Toast.LENGTH_SHORT).show();
                                    Log.w("Register","error " + e.getMessage());
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(),"contraseña no coincide", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}