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

public class RegActivity extends AppCompatActivity {

    EditText emailEdit ;
    EditText passEdit;
    EditText confirmPassEdit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        emailEdit =  findViewById(R.id.email);
        passEdit = findViewById(R.id.password);
        confirmPassEdit = findViewById(R.id.confrimPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Register(View view) {
        String email = emailEdit.getText().toString();
        String pass = passEdit.getText().toString();
        String confPass = confirmPassEdit.getText().toString();

        if (!email.contains("@")){
            emailEdit.setError("please insert valid email");
            emailEdit.requestFocus();
            return;
        }
        if ( pass.length() <= 6 ){
            passEdit.setError("password must be > 6");
            return;
        }
        if ( !pass.equals(confPass)) {
            confirmPassEdit.setError(" Passwords must match");
            passEdit.setError(" Passwords must match");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goHome();
                        } else {
                            Toast.makeText(RegActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
                            Log.d("ERROR", "onComplete: "+task.getException());


                        }
                    }
                });



    }

    private void goHome() {
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);

    }
}



