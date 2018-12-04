package com.example.jeromesamuel.cst2335final;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jeromesamuel.cst2335final.SavedCustomListAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class saved_List extends AppCompatActivity {
    ListView lvRss2;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> imageLinks;
    ArrayList<String> extraInfos;
    ArrayList<String> pubDate;

    String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedist);


        Button delete = (Button) findViewById(R.id.delete);

        ImageView imageView = ( ImageView) findViewById(R.id.imageViewNewsSaved);
        TextView textViewNews = (TextView) findViewById(R.id.textViewNewsSaved);
        TextView textViewLink= (TextView) findViewById(R.id.textViewLinkSaved);
        lvRss2 = (ListView) findViewById(R.id.SavedListView);


        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        imageLinks = new ArrayList<String>();
        extraInfos = new ArrayList<String>();
        pubDate = new ArrayList<String>();



    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
//        ProgressDialog progressDialog = new ProgressDialog(CBCMainActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressDialog.setMessage("Busy loading rss feed...please wait...");
//            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL("https://www.cbc.ca/cmlink/rss-world/");
titles.add("435345");
pubDate.add("2354");
imageLinks.add("23525");
              extraInfos.add("hgfdfsdad");
            }
            catch (MalformedURLException e)
            {
                exception = e;
            }

            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);


            SavedCustomListAdapter adapter2 = new SavedCustomListAdapter(saved_List.this, titles,pubDate,extraInfos);

            lvRss2.setAdapter(adapter2);




            //  progressDialog.dismiss();
        }

    }

}
