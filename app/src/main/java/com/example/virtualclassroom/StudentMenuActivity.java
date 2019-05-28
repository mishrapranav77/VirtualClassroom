package com.example.virtualclassroom;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class StudentMenuActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    ImageView lecture, quiz, assignment, notes, doubt, progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        mToggle= new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lecture= (ImageView) findViewById(R.id.lecture);
        quiz=(ImageView) findViewById(R.id.quiz);
        assignment=(ImageView)findViewById(R.id.assignment);
        notes=(ImageView)findViewById(R.id.notes);
        doubt=(ImageView)findViewById(R.id.doubt);
        progress=(ImageView)findViewById(R.id.progress);

        //To start lecture
        lecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentMenuActivity.this, LectureActivity.class);
                startActivity(i);
            }
        });

        // To download notes
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j=new Intent(StudentMenuActivity.this, NotesActivity.class);
                startActivity(j);
            }
        });

        // To take quiz
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k=new Intent(StudentMenuActivity.this, QuizActivity.class);
                startActivity(k);
            }
        });

        // To submit assignment
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l=new Intent(StudentMenuActivity.this, AssignmentActivity.class);
                startActivity(l);
            }
        });

        // Doubt clearance
        doubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent m= new Intent(StudentMenuActivity.this, ChatActivity.class);
                startActivity(m);
            }
        });

        // Progress Report
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n=new Intent(StudentMenuActivity.this, ProgressActivity.class);
                startActivity(n);
            }
        });



    }

}
