package com.example.virtualclassroom;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class LectureActivity extends AppCompatActivity {
    VideoView mainVideoView;
    ImageView playBtn;
    TextView currentTimer;
    TextView durationTimer;
    ProgressBar currentProgress;

    Uri vUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        mainVideoView= (VideoView)findViewById(R.id.videoView);
        playBtn= (ImageView)findViewById(R.id.mPlayBtn);
        currentTimer= (TextView)findViewById(R.id.currentTimer);
        durationTimer= (TextView)findViewById(R.id.durationTimer);
        currentProgress= (ProgressBar)findViewById(R.id.progressBar);
        //vUri= Uri.parse(https://firebasestorage.googleapis.com/v0/b/virtual-classroom-3fa78.appspot.com/o/IMG_5806.MOV?alt=media&token=177a4262-049a-47d1-ac44-30764c69c279);
        mainVideoView.setVideoURI(vUri);
        mainVideoView.requestFocus();
        mainVideoView.start();

    }
}
