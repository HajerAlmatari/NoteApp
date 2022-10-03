package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private EditText mLoginEmail, mLoginPassword;
    private RelativeLayout mLogin, mGoToSignUp;
    private TextView mGoToForgotPassword;
    private ProgressBar mProgressBarOfMainActivity;
    private FirebaseAuth firebaseAuth;


    boolean isConnected = false;
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mLogin = findViewById(R.id.login);
        mGoToSignUp = findViewById(R.id.goToSignUp);
        mGoToForgotPassword = findViewById(R.id.goToForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        mProgressBarOfMainActivity = findViewById(R.id.progressBarOfMainActivity);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseUser != null) {

            finish();

            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);

        }


        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });


        mGoToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please enter all required information", Toast.LENGTH_LONG).show();
                } else {


                    if (isConnected) {
                        //                    Toast.makeText(getApplicationContext(),"Login successfully ",Toast.LENGTH_LONG).show();

                        mLogin.setVisibility(View.INVISIBLE);
                        mProgressBarOfMainActivity.setVisibility(View.VISIBLE);

                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    checkEmailVerification();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Account Not Exists", Toast.LENGTH_LONG).show();
                                    mProgressBarOfMainActivity.setVisibility(View.INVISIBLE);
                                    mLogin.setVisibility(View.VISIBLE);

                                }

                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();

                    }

                }


            }
        });


    }

    private void checkEmailVerification() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified() == true) {
            Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
            getWindow().setWindowAnimations(0);
            finish();
            startActivity(new Intent(MainActivity.this, NotesActivity.class));
        } else {
            mProgressBarOfMainActivity.setVisibility(View.INVISIBLE);
            mLogin.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "verify your email to login", Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }

    }

    private void registerNetworkCallback() {

        try {

            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    isConnected = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    isConnected = false;
                }
            });

        } catch (Exception e) {
            isConnected = false;
        }

    }


    private void unregisterNetworkCallback() {

        connectivityManager.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());

    }


    @Override
    protected void onResume() {
        super.onResume();

        registerNetworkCallback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkCallback();
    }


}