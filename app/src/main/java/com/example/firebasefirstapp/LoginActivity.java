package com.example.firebasefirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEdit;
    EditText passEdit;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEdit = findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

    }

    public void Login(View view) {
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();
        if (!email.contains("@")){
            emailEdit.setError("please insert valid email");
            emailEdit.requestFocus();
            return;
        }
        if ( pass.length() <= 6 ){
            passEdit.setError("password must be > 6");
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    goHome();
                } else {
                    Toast.makeText(LoginActivity.this, "Error in Authentication" , Toast.LENGTH_SHORT).show();
                    Log.d("Login Error", "onComplete: "+task.getException());
                }
            }
        });


    }

    private void goHome() {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);

    }

}
