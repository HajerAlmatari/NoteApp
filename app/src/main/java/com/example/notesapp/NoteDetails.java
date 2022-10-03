package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetails extends AppCompatActivity {

    private TextView mContentOfNoteDetail, mTitleOfNoteDetail;
    private FloatingActionButton mGoToEditNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        mContentOfNoteDetail = findViewById(R.id.contentOfNoteDetail);
        mTitleOfNoteDetail = findViewById(R.id.titleOfNoteDetail);
        mGoToEditNote = findViewById(R.id.goToEditNote);

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolBarOfNoteDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mGoToEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NoteDetails.this, EditNoteActivity.class);

                intent.putExtra("title",extras.getString("title"));
                intent.putExtra("content",extras.getString("content"));
                intent.putExtra("noteId",extras.getString("noteId"));

                startActivity(intent);

            }
        });

        mContentOfNoteDetail.setText(extras.getString("content"));
        mTitleOfNoteDetail.setText(extras.getString("title"));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }
}