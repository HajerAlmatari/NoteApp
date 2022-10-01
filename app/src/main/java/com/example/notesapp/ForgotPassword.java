package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ForgotPassword extends AppCompatActivity {

    private EditText mForgotPassword;
    private Button mPasswordRecoverButton;
    private TextView mGoBackToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();

        mForgotPassword = findViewById(R.id.forgotPassword);
        mPasswordRecoverButton = findViewById(R.id.passwordRecoverButton);
        mGoBackToLogin = findViewById(R.id.goBackToLogin);


        mGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mPasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mForgotPassword.getText().toString().trim();

                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Enter Email",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}