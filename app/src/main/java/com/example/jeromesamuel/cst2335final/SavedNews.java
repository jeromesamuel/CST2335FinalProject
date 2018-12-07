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
    int extraInfos;
    Cursor cursor;


    ArrayList<String> listArrayextra = new ArrayList<>();
    ArrayList<String> listArrayimagelink = new ArrayList<>();
    ArrayList<String> listArraylink = new ArrayList<>();
    ArrayList<String> listArraycount = new ArrayList<>();

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
////////////////////////////
        dbHelper = new NewsDatabaseHelper(this);  //1
        db = dbHelper.getWritableDatabase();  //2

        Cursor cursor = db.query(false, NewsDatabaseHelper.DATABASE_NAME,
                new String[] { NewsDatabaseHelper.KEY_ID, NewsDatabaseHelper.KEY_MESSAGE,NewsDatabaseHelper.KEY_LINK,NewsDatabaseHelper.KEY_IMAGE_LINK,NewsDatabaseHelper.KEY_WORD_COUNT }, null, null, null, null,
                null, null);
        int rows = cursor.getCount(); // number of rows returned   //3

        Log.i(ACTIVITY_NAME, "Cursor column count =" + cursor.getColumnCount());

        cursor.moveToFirst(); // move to first result  //4

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME,
                    "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_MESSAGE)));
            listArrayextra.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_MESSAGE)));
            listArraylink.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_LINK)));
            listArrayimagelink.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_IMAGE_LINK)));
            listArraycount.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_WORD_COUNT)));

             cursor.moveToNext();

        }  //5



        for (int i = 0; i < cursor.getColumnCount(); i++) {
            cursor.getColumnCount();
            Log.i(ACTIVITY_NAME, "column count" + cursor.getColumnName(i));
        }  //6





        //////////////////////////////
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();

        imageLink = extras.getString("imageLink");
        extraInfo = extras.getString("extraInfo");
        pageLink = extras.getString("PageLink");
        extraInfos = extras.getInt("extraInfo2");







        delete = (Button) findViewById(R.id.SavedDelete);

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


String test = String.valueOf(listArrayextra.size()-1);
        String test2 = String.valueOf(listArrayimagelink.size()-1);

        textViewNews.setText(listArrayextra.get(Integer.valueOf(test)));
        textViewLink.setText("Article link: \n"+extras.getString("imageLink"));

//        String ee=listArrayimagelink.get(17);
//     Log.i("ChatDatabaseHelper33",ee);
        System.out.println(listArrayimagelink.size()+"--333");
        System.out.println(listArrayimagelink.get(listArrayimagelink.size()-1)+"--333");

     Picasso.get().load(listArrayimagelink.get(Integer.valueOf(test2))).into(imageView);
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
