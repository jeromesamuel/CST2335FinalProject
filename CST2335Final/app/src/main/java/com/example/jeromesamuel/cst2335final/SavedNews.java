package com.example.jeromesamuel.cst2335final;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Activity Name =
 * @author Yahya Wardere
 * @version 1.0
 */

public class SavedNews extends Activity {
    String imageLink;
    String extraInfo;
    String pageLink;
    int row;


    protected NewsDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    Button delete;

    protected static final String ACTIVITY_NAME = "SavedNews";

    /**
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);


        dbHelper = new NewsDatabaseHelper(this); //1
        db = dbHelper.getWritableDatabase(); //2

        ImageView imageView = (ImageView) findViewById(R.id.SavedimageViewNews);
        TextView textViewNews = (TextView) findViewById(R.id.SavedtextViewNews);
        TextView textViewLink = (TextView) findViewById(R.id.SavedtextViewLink);

        Intent intent = getIntent();
        final Bundle extras = intent.getExtras();

        imageLink = extras.getString("imageLink");
        extraInfo = extras.getString("extraInfo");
        pageLink = extras.getString("PageLink");
        row = extras.getInt("row");

        textViewNews.setText(extraInfo);
        textViewLink.setText("Article link: \n" + extras.getString("PageLink"));
        Picasso.get().load(imageLink).into(imageView);

        delete = (Button) findViewById(R.id.SavedDelete);

        textViewLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SavedNews.this, web.class);

                intent.putExtra("linkToPage", pageLink);

                startActivity(intent);

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {

                db.delete(NewsDatabaseHelper.DATABASE_NAME, NewsDatabaseHelper.KEY_LINK + " LIKE " + "'" + pageLink + "'", null);
                Intent intent = new Intent(SavedNews.this, CBCMainActivity.class);
                Toast.makeText(SavedNews.this, "Article Deleted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(intent);

            }
        });






    }
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }
}