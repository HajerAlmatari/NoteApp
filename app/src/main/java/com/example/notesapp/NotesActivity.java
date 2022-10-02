package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class NotesActivity extends AppCompatActivity {

    private FloatingActionButton mCreateNoteFab;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mCreateNoteFab = findViewById(R.id.createNoteFab);
        firebaseAuth = FirebaseAuth.getInstance();


        getSupportActionBar().setTitle("All Notes");

        mCreateNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(NotesActivity.this, CreateNote.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){


            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(NotesActivity.this, MainActivity.class));
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}