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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class news extends Activity {
    String imageLink;
    String extraInfo;
    String pageLink;
    String  wordCount;
    String pubDate;
    String titles;
    Cursor cursor;


    ArrayList<String> listArrayextra = new ArrayList<>();
    ArrayList<String> listArraylink = new ArrayList<>();
    ArrayList<String> listArrayimage = new ArrayList<>();
    ArrayList<String> listArraycount = new ArrayList<>();


    protected NewsDatabaseHelper dbHelper;
    private SQLiteDatabase db;
//
    Button Save;

    protected static final String ACTIVITY_NAME = "news";


    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final ImageView  imageView = ( ImageView) findViewById(R.id.imageViewNews);
        TextView textViewNews = (TextView) findViewById(R.id.textViewNews);
        TextView textViewLink= (TextView) findViewById(R.id.textViewLink);


        Intent intent= getIntent();
        Bundle extras = intent.getExtras();

        imageLink = extras.getString("imageLink");
        extraInfo = extras.getString("extraInfo");
        pageLink = extras.getString("PageLink");
       wordCount = extras.getString("wordCount");

        titles = extras.getString("titles");
        pubDate = extras.getString("pubDate");


        Picasso.get().load(imageLink).into(imageView);
        dbHelper = new NewsDatabaseHelper(this);  //1
        db = dbHelper.getWritableDatabase();  //2


        Cursor cursor = db.query(false, NewsDatabaseHelper.DATABASE_NAME,
                new String[] { NewsDatabaseHelper.KEY_ID, NewsDatabaseHelper.KEY_MESSAGE, NewsDatabaseHelper.KEY_LINK,NewsDatabaseHelper.KEY_IMAGE_LINK,NewsDatabaseHelper.KEY_WORD_COUNT }, null, null, null, null,
                null, null);
        int rows = cursor.getCount(); // number of rows returned   //3

        Log.i(ACTIVITY_NAME, "Cursor column count =" + cursor.getColumnCount());

        cursor.moveToFirst(); // move to first result  //4

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME,
                    "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_MESSAGE)));
            listArrayextra.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_MESSAGE)));
            listArraylink.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_LINK)));
            listArrayimage.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_IMAGE_LINK)));
            listArraycount.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_WORD_COUNT)));
            cursor.moveToNext();

        }  //5

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            cursor.getColumnCount();
            Log.i(ACTIVITY_NAME, "column count" + cursor.getColumnName(i));
        }  //6








        Save = (Button) findViewById(R.id.Save);


        textViewLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(news.this, web.class);

                intent.putExtra("linkToPage",pageLink);

                startActivity(intent);

            }
        });



        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(news.this, "Article saved", Toast.LENGTH_SHORT).show();
                String message = extraInfo;



                ContentValues newValues = new ContentValues();

                newValues.put(NewsDatabaseHelper.KEY_MESSAGE, extraInfo);
                newValues.put(NewsDatabaseHelper.KEY_IMAGE_LINK, imageLink);
                newValues.put(NewsDatabaseHelper.KEY_LINK, pageLink);
                newValues.put(NewsDatabaseHelper.KEY_WORD_COUNT, wordCount);



                db.insert(NewsDatabaseHelper.DATABASE_NAME, message, newValues);





                Save.setVisibility(view.GONE);


            }
        }); //7



        textViewNews.setText(extraInfo + "\n\n Word Count=" +wordCount);
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
