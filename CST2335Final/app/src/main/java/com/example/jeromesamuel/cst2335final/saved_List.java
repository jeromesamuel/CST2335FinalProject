package com.example.jeromesamuel.cst2335final;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity Name =
 * @author Yahya Wardere
 * @version 1.0
 */
public class saved_List extends AppCompatActivity {
    ListView lvRss2;

    int Deleted =0;
    android.support.v7.widget.Toolbar toolbar;
    ArrayList<String> listArrayextra = new ArrayList<>();
    ArrayList<String> listArrayimagelink = new ArrayList<>();
    ArrayList<String> listArraylink = new ArrayList<>();
    ArrayList<String> listArraycount = new ArrayList<>();
    ArrayList<String> listArraytitle = new ArrayList<>();
    ArrayList<String> listArraypub = new ArrayList<>();
    ArrayList<String> wordCount = new ArrayList<>();
    int Ave,max,min;

    protected NewsDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        toolbar = findViewById(R.id.cbcToolbar);
        toolbar.setTitle("CBC SAVED ARTICLES");
        setSupportActionBar(toolbar);
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.SavedList,
                Snackbar.LENGTH_SHORT);

        snackbar.setAction(R.string.Enjoy, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recheck internet connection and call DownloadJson if there is internet
            }
        }).show();


        Button delete = (Button) findViewById(R.id.delete);

        ImageView imageView = ( ImageView) findViewById(R.id.imageViewNewsSaved);
        TextView textViewNews = (TextView) findViewById(R.id.textViewNewsSaved);
        TextView textViewLink= (TextView) findViewById(R.id.textViewLinkSaved);
        lvRss2 = (ListView) findViewById(R.id.SavedListView);






        ///////////////////////////////////////////
        dbHelper = new NewsDatabaseHelper(this);  //1
        db = dbHelper.getWritableDatabase();  //2

        Cursor cursor = db.query(false, NewsDatabaseHelper.DATABASE_NAME,
                new String[] { NewsDatabaseHelper.KEY_ID, NewsDatabaseHelper.KEY_MESSAGE,NewsDatabaseHelper.KEY_LINK,NewsDatabaseHelper.KEY_IMAGE_LINK,NewsDatabaseHelper.KEY_WORD_COUNT,NewsDatabaseHelper.KEY_IMAGE_TITLE,NewsDatabaseHelper.KEY_WORD_pub }, null, null, null, null,
                null, null);
        int rows = cursor.getCount(); // number of rows returned   //3



        cursor.moveToFirst(); // move to first result  //4

        while (!cursor.isAfterLast()) {

            listArrayextra.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_MESSAGE)));
            listArraylink.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_LINK)));
            listArrayimagelink.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_IMAGE_LINK)));
            listArraycount.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_WORD_COUNT)));
            listArraytitle.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_IMAGE_TITLE)));
            listArraypub.add(cursor.getString(cursor.getColumnIndex(NewsDatabaseHelper.KEY_WORD_pub)));

            cursor.moveToNext();

        }  //5



        for (int i = 0; i < cursor.getColumnCount(); i++) {
            cursor.getColumnCount();
        }  //6

        new ProcessInBackground2().execute();


    }
    /**
     * Activity Name =
     * @author Yahya Wardere
     * @version 1.0
     */
    public class ProcessInBackground2 extends AsyncTask<Integer, Void, Exception>
    {

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Exception doInBackground(Integer... params) {


            lvRss2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(saved_List.this, SavedNews.class);
                    Bundle extras = new Bundle();
                    extras.putString("imageLink", listArrayimagelink.get(i));
                    extras.putString("extraInfo", listArrayextra.get(i));
                    extras.putString("PageLink",listArraylink.get(i));

                    intent.putExtras(extras);


                    startActivity(intent);
                }
            });


            return exception;
        }

        public InputStream getInputStream(URL url)
        {
            try
            {

                return url.openConnection().getInputStream();
            }
            catch (IOException e)
            {
                return null;
            }
        }


        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            SavedCustomListAdapter adapter2 = new SavedCustomListAdapter(saved_List.this, listArraytitle,listArrayimagelink,listArraypub);
            lvRss2.setAdapter(adapter2);

        }

    }

    /**
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater info3 = getMenuInflater();
        info3.inflate(R.menu.info_menu, menu);

        return true;

    }

    /**
     *
     * @param item
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_info:

                String helpMsg = getResources().getString(R.string.info);
                AlertDialog alertDialog = new AlertDialog.Builder(saved_List.this).create();
                alertDialog.setTitle("Information");
                alertDialog.setMessage(helpMsg);

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Log.d("ic info clicked", "here3");
                            }
                        });
                alertDialog.show();
                break;
            case R.id.item_save:
                Intent intent = new Intent(saved_List.this, saved_List.class);
                startActivity(intent);
                //insert code to switch activity here
                break;

            case R.id.item_stat:
                String stst_info = getResources().getString(R.string.stat);
                AlertDialog alertDialog2 = new AlertDialog.Builder(saved_List.this).create();
                alertDialog2.setTitle(getResources().getString(R.string.Statistics));
                for(int j=0;listArrayextra.size()>j;j++){
                    String trimmed = listArrayextra.get(j).trim();
                    int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
                    wordCount.add(Integer.toString(words));
                }

                int total =0;
                for(int i = 0; i<wordCount.size(); i++){
                    total +=Integer.valueOf(wordCount.get(i));
                }
                int avg = total / wordCount.size();

                max= Integer.valueOf(Collections.max(wordCount)) ;
                min= Integer.valueOf( Collections.min(wordCount));
                Ave = avg;


                alertDialog2.setMessage(stst_info+"\n\n"+getResources().getString(R.string.Min)+Collections.min(wordCount)+"\n"+getResources().getString(R.string.Average)+avg+"\n"+getResources().getString(R.string.Max)+Collections.max(wordCount));

                alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Log.d("ic info clicked", "here3");
                            }
                        });
                alertDialog2.show();
                break;





        }
        return super.onOptionsItemSelected(item);
    }

}
