package com.example.firebasefirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {

    private static final String TAG = "PhoneAuthTest";
    PhoneAuthProvider phoneAuthProvider;
    FirebaseAuth mAuth;
    EditText phoneNumberEdit;
    EditText confrimCodeEdit;
    Button confrimButton;
    String VeriID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        phoneAuthProvider = PhoneAuthProvider.getInstance();
        mAuth = FirebaseAuth.getInstance();

        phoneNumberEdit = findViewById(R.id.PhoneNumber);
        confrimCodeEdit = findViewById(R.id.ConfrimCode);
        confrimButton = findViewById(R.id.confrimButton);

    }


    public void SendCode(View view) {
        String phoneNumber = phoneNumberEdit.getText().toString();

        if (phoneNumber.length() != 13) {
            Toast.makeText(this, "Please provide a correct phone number +218xxx", Toast.LENGTH_SHORT).show();
            return;
        }
        phoneAuthProvider.verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                        loginWith(phoneAuthCredential);
                    }


                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.w(TAG, "onVerificationFailed", e);

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "onVerificationFailed: invalid request");
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded
                            Log.d(TAG, "onVerificationFailed: Qouta limit reached");
                        }

                        Toast.makeText(PhoneLogin.this, "Error", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onCodeSent(String verficationID, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        confrimButton.setVisibility(View.VISIBLE);
                        confrimCodeEdit.setVisibility(View.VISIBLE);
                        VeriID = verficationID;

                    }
                });


    }

    public void confrim(View view) {
        String code = confrimCodeEdit.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VeriID, code);
    }

    private void loginWith(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            goHome();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }


    private void goHome() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);

    }
}
