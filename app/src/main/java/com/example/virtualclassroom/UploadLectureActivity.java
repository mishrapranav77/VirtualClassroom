package com.example.virtualclassroom;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UploadLectureActivity extends AppCompatActivity {
    Button ch, up;
    VideoView video;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    public Uri videoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lecture);

        mStorageRef = FirebaseStorage.getInstance().getReference("Video");

        ch=(Button)findViewById(R.id.choose);
        up=(Button)findViewById(R.id.upload);
        video=(VideoView)findViewById(R.id.vv);

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (uploadTask !=null && uploadTask.isInProgress())
                {
                   Toast.makeText(UploadLectureActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
                }
                else {
                    fileUploader();
                }
            }
        });
    }

    private String getExtension(Uri uri)
    {
        ContentResolver cr= getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileUploader()
    {
        StorageReference ref=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(videoUri));
        uploadTask= ref.putFile(videoUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UploadLectureActivity.this, "Video Uploaded Successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void fileChooser()
    {
        Intent ab = new Intent();
        ab.setType("video");
        ab.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ab, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
           videoUri=data.getData();
           video.setVideoURI(videoUri);
        }
    }
}
