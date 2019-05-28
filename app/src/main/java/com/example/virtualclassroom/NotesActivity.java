package com.example.virtualclassroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        WebView webView2 = findViewById(R.id.web_view);
        final ProgressBar webProgress = findViewById(R.id.web_progress);

        webProgress.setVisibility(View.VISIBLE);
        String url="";
        String finalURL="http://drive.google.com/viewerng/viewer?embedded=true&url="+url;

        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setBuiltInZoomControls(true);

        webView2.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getSupportActionBar().setTitle("Loading...");

                if (newProgress==100)
                {
                    webProgress.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }
        });

        webView2.loadUrl(finalURL);
    }
}
