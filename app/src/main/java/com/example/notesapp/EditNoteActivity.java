package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {


    Intent data;
    private EditText mEditTitleOfNote, mEditContentOfNote;
    private FloatingActionButton mSaveEditedNoteFab;
    FirebaseAuth  firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    private ProgressBar mProgressBarOfUpdateNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mEditTitleOfNote = findViewById(R.id.editTitleOfNote);
        mEditContentOfNote = findViewById(R.id.editContentOfNote);
        mSaveEditedNoteFab = findViewById(R.id.saveEditedNoteFab);
        Toolbar toolbar = findViewById(R.id.toolBarOfEditNote);
        mProgressBarOfUpdateNote = findViewById(R.id.progressBarOfUpdateNote);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Bundle extras = getIntent().getExtras();

        data = getIntent();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");

        mEditTitleOfNote.setText(noteTitle);
        mEditContentOfNote.setText(noteContent);


        mSaveEditedNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(),"mSaveEditedNoteFab clicked",Toast.LENGTH_LONG).show();

                String newTitle = mEditTitleOfNote.getText().toString();
                String newContent = mEditContentOfNote.getText().toString();
                if(newTitle.isEmpty() || newContent.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please Enter All Required Information",Toast.LENGTH_LONG).show();

                }else {

                    mProgressBarOfUpdateNote.setVisibility(View.VISIBLE);
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                    Map<String, Object> note = new HashMap<>();
                    note.put("title",newTitle);
                    note.put("content",newContent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Note Updated Successfully",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mProgressBarOfUpdateNote.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Filed to Update",Toast.LENGTH_LONG).show();

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