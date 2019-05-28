package com.example.virtualclassroom;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadNotesActivity extends AppCompatActivity {

    Button selectNotes, uploadNotes;
    TextView fileInfo;
    Uri pdfUri;

    FirebaseDatabase database2;
    FirebaseStorage storage2;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        storage2 = FirebaseStorage.getInstance();
        database2 = FirebaseDatabase.getInstance();

        selectNotes = (Button)findViewById(R.id.select_notes);
        uploadNotes = (Button)findViewById(R.id.upload_notes);
        fileInfo = (TextView)findViewById(R.id.file_info);

        selectNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(UploadNotesActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else {
                    ActivityCompat.requestPermissions(UploadNotesActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        uploadNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pdfUri != null)
                uploadFile(pdfUri);
                else
                    Toast.makeText(UploadNotesActivity.this, "select a file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadFile(Uri pdfUri)
    {
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setTitle("Uploading File....");
        pDialog.setProgress(0);
        pDialog.show();
        final String fileName=System.currentTimeMillis()+"";
        final StorageReference sRef = storage2.getReference();
        sRef.child("uploads").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      String url = sRef.getDownloadUrl().toString();

                        DatabaseReference dRef = database2.getReference();

                        dRef.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Toast.makeText(UploadNotesActivity.this, "File successfully uploaded", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(UploadNotesActivity.this, "Oops! uploading failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadNotesActivity.this, "Oops! uploading failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                // track the progress
                int currentProgress= (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                pDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
        {
            Toast.makeText(UploadNotesActivity.this, "please provide permission...", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf()
    {
        Intent a= new Intent();
        a.setType("application/pdf");
        a.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(a, 86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 86 && resultCode==RESULT_OK && data != null)
        {
            pdfUri = data.getData();
            fileInfo.setText("file selected:"+data.getData().getLastPathSegment());
        }
        else
        {
            Toast.makeText(UploadNotesActivity.this, "please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}
