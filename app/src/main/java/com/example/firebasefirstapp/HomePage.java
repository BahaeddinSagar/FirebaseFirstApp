package com.example.firebasefirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {
    private static final String TAG = "User";
    TextView welcomeText;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        welcomeText = findViewById(R.id.welcome);
        welcomeText.setText("Welcome " + currentUser.getEmail());
        Log.d(TAG, "onCreate: "+currentUser.getUid() +"\n");

    }

    public void signout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this,MainActivity.class);
        navigateUpTo(intent);
        // another way to go back to main Acitivty
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(i);


    }
}
