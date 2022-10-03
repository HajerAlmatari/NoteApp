package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
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

public class SignUp extends AppCompatActivity {

    private EditText mSignupEmail,mSignupPassword;
    private RelativeLayout mSignup;
    private TextView mGoToLogin;
    private ProgressBar mProgressBarOfSignup;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        mSignupEmail = findViewById(R.id.signupEmail);
        mSignupPassword = findViewById(R.id.signupPassword);
        mSignup = findViewById(R.id.signup);
        mGoToLogin = findViewById(R.id.goToLogin);
        mProgressBarOfSignup = findViewById(R.id.progressBarOfSignup);


        firebaseAuth = FirebaseAuth.getInstance();

        mGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mSignupEmail.getText().toString().trim();
                String password = mSignupPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Please enter all required information",Toast.LENGTH_LONG).show();
                }
                else if(password.length() < 8){
                    Toast.makeText(getApplicationContext(),"Password should be at least 8 characters",Toast.LENGTH_LONG).show();
                }

                else {

                    mSignup.setVisibility(View.INVISIBLE);
                    mProgressBarOfSignup.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();
                                sendEmailVerification();
                            }
                            else {
                                mProgressBarOfSignup.setVisibility(View.INVISIBLE);
                                mSignup.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),"Failed to Register",Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }

            }

        });

    }

    private void sendEmailVerification(){

        FirebaseUser  firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Link send to your email",Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(SignUp.this,MainActivity.class));
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Failed to send verification",Toast.LENGTH_LONG).show();

        }

    }
}