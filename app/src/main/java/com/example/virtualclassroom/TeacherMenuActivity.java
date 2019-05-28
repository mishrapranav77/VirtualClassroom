package com.example.virtualclassroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TeacherMenuActivity extends AppCompatActivity {
    ImageView uploadLecture;
    ImageView uploadNotes;
    ImageView uploadQuiz;
    ImageView studentResults;
    ImageView doubt;
    ImageView uploadAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);

       uploadLecture= (ImageView)findViewById(R.id.lectureUpload);
       uploadNotes=(ImageView)findViewById(R.id.notesUpload);
       uploadQuiz=(ImageView)findViewById(R.id.quizUpload);
       studentResults=(ImageView)findViewById(R.id.studentResults);
       doubt=(ImageView)findViewById(R.id.doubtTeacher);
       uploadAssignment= (ImageView)findViewById(R.id.assignmentUpload);



       uploadLecture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(TeacherMenuActivity.this, UploadLectureActivity.class));
           }
       });

       uploadNotes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(TeacherMenuActivity.this, UploadNotesActivity.class));
           }
       });

       uploadQuiz.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(TeacherMenuActivity.this, UploadQuizActivity.class));
           }
       });

       studentResults.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(TeacherMenuActivity.this, StudentResultsActivity.class));
           }
       });

       doubt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(TeacherMenuActivity.this, ChatActivity.class));
           }
       });

       uploadAssignment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(TeacherMenuActivity.this, UploadAssignmentActivity.class));
           }
       });
    }
}
