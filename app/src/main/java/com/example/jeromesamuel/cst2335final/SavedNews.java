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

public class SavedNews extends Activity {
    String imageLink;
    String extraInfo;
    String pageLink;
    Cursor cursor;


    ArrayList<String> listArraySave = new ArrayList<>();

    protected NewsDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    Button delete;

    protected static final String ACTIVITY_NAME = "SavedNews";


    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        ImageView  imageView = ( ImageView) findViewById(R.id.SavedimageViewNews);
        TextView textViewNews = (TextView) findViewById(R.id.SavedtextViewNews);
        TextView textViewLink= (TextView) findViewById(R.id.SavedtextViewLink);


        Intent intent= getIntent();
        Bundle extras = intent.getExtras();

        imageLink = extras.getString("imageLink");
        extraInfo = extras.getString("extraInfo");
        pageLink = extras.getString("PageLink");


        Picasso.get().load(imageLink).into(imageView);




        dbHelper = new NewsDatabaseHelper(this);  //1
        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + dbHelper.TABLE_NAME;
        // cursor = db.rawQuery(query, null);


        delete = (Button) findViewById(R.id.SavedDelete);

//        Cursor cursor = db.query(false, NewsDatabaseHelper.DATABASE_NAME,
//                new String[] { NewsDatabaseHelper.KEY_ID, NewsDatabaseHelper.NEWS_TITLE }, null, null, null, null,
//               null, null);
//       int rows = cursor.getCount(); // number of rows returned   //3
//    cursor.moveToFirst();
//
//
//        ContentValues insertValues = new ContentValues();
//        insertValues.put(NewsDatabaseHelper.NEWS_TITLE, extraInfo);
//        db.insert(BusDatabaseHelper.TABLE_NAME, "", insertValues);
//
//
//
//        while (!cursor.isAfterLast()) {
//            listArraySave.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.NEWS_TITLE)));
//            cursor.moveToNext();
//
//        }  //5
//
//        for (int i = 0; i < cursor.getColumnCount(); i++) {
//            cursor.getColumnCount();
//        }
//
//
//        for (int i = 0; i < cursor.getColumnCount(); i++) {
//            cursor.getColumnCount();
//        }  //6








        textViewLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




                Intent intent = new Intent(SavedNews.this, web.class);

                intent.putExtra("linkToPage",pageLink);

                startActivity(intent);

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SavedNews.this, "Article Deleted", Toast.LENGTH_SHORT).show();

            }
        }); //7



        textViewNews.setText(extraInfo);
        textViewLink.setText("Article link: \n"+extras.getString("imageLink"));

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
        db.close(); //8
    }
}
