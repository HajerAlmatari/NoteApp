package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNote extends AppCompatActivity {


    EditText mCreateTitleOfNote, mCreateContentOfNote;
    FloatingActionButton mSaveNoteFab;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);


        mCreateTitleOfNote = findViewById(R.id.createTitleOfNote);
        mCreateContentOfNote = findViewById(R.id.createContentOfNote);
        mSaveNoteFab = findViewById(R.id.saveNoteFab);
        Toolbar toolbar = findViewById(R.id.toolBarOfCreatNote);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        mSaveNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mCreateTitleOfNote.getText().toString();
                String content = mCreateContentOfNote.getText().toString();

                if(title.isEmpty() || content.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please Enter All Required Information",Toast.LENGTH_LONG).show();

                }else {


                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();

                    Map<String, Object> note = new HashMap<>();

                    note.put("title",title);
                    note.put("content",content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Note Created Successfully",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(CreateNote.this, NotesActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Failed to create note",Toast.LENGTH_LONG).show();

                        }
                    });


                }
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }
}