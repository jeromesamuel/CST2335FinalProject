package com.example.jeromesamuel.cst2335final;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Activity Name =
 * @author Yahya Wardere
 * @version 1.0
 */
public class web extends Activity {
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView myWebView = (WebView) findViewById(R.id.webView);


        Intent i = getIntent();
        String easyPuzzle = i.getStringExtra("linkToPage");
        myWebView.loadUrl(easyPuzzle);
        myWebView.saveWebArchive(easyPuzzle);


    }
}
