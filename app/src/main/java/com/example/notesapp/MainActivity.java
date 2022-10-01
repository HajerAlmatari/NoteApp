package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText mLoginEmail, mLoginPassword;
    private RelativeLayout mLogin, mGoToSignUp;
    private TextView mGoToForgotPassword;

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


        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });


        mGoToForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter all required information",Toast.LENGTH_LONG).show();
                }

                else {

                    Toast.makeText(getApplicationContext(),"Login successfully ",Toast.LENGTH_LONG).show();

                }


            }
        });


    }
}