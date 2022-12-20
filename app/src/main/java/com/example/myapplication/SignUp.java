package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private Button btnSignUp;
    private EditText editEmailSignUp, editPasswordSignUp, editConfirmPassSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        editEmailSignUp = (EditText) findViewById(R.id.editEmailSignUp);
        editPasswordSignUp = (EditText) findViewById(R.id.editPasswordSignUp);
        editConfirmPassSignUp = (EditText) findViewById(R.id.editConfirmPassSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailSignUp.getText().toString();
                String pass = editPasswordSignUp.getText().toString();
                String confirm = editConfirmPassSignUp.getText().toString();

                if (pass.compareTo(confirm) == 0) {
                    FirebaseAuth mAuth;
                    // ...
                    // Initialize Firebase Auth
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, pass);
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(),"contraseña no coincide", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}