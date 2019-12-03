package com.example.jeromesamuel.cst2335final;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity Name =
 * @author Yahya Wardere
 * @version 1.0
 */
public class CBCMainActivity extends AppCompatActivity {



    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> imageLinks;
    ArrayList<String> extraInfos;
    ArrayList<String> pubDate;
    ArrayList<String> wordCount;
    int Ave,max,min;


    String description;
    android.support.v7.widget.Toolbar toolbar;


    /**
     *
     * @return boolean
     */
    boolean internet_connection(){

        ConnectivityManager ConnectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = ConnectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc_main);
        toolbar = findViewById(R.id.cbcToolbar);


        setSupportActionBar(toolbar);


        lvRss = (ListView) findViewById(R.id.listView);

        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        imageLinks = new ArrayList<String>();
        extraInfos = new ArrayList<String>();
        pubDate = new ArrayList<String>();
        wordCount= new ArrayList<String>();

        toolbar.setTitle("CBC NEWS");
        setSupportActionBar(toolbar);

        if (internet_connection()){
            // Execute DownloadJSON AsyncTask
            new ProcessInBackground().execute();
        }else{
            //create a snackbar telling the user there is no internet connection and issuing a chance to reconnect
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection.",
                    Snackbar.LENGTH_SHORT);

            snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //recheck internet connection and call DownloadJson if there is internet
                }
            }).show();
        }



    }

    /**
     *
     * @param url
     * @return null
     */
    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    /**
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater info2 = getMenuInflater();
        info2.inflate(R.menu.info_menu, menu);

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
                AlertDialog alertDialog = new AlertDialog.Builder(CBCMainActivity.this).create();
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
                Intent intent = new Intent(CBCMainActivity.this, saved_List.class);
                Bundle extras = new Bundle();


                intent.putExtras(extras);
                //intent.putExtra("extraInfo", extraInfos.get(i));

                startActivity(intent);
                //insert code to switch activity here
                break;

            case R.id.item_stat:
                String stst_info = getResources().getString(R.string.stat);
                AlertDialog alertDialog2 = new AlertDialog.Builder(CBCMainActivity.this).create();
                alertDialog2.setTitle(getResources().getString(R.string.Statistics));
                int total =0;
                for(int i = 0; i<wordCount.size(); i++){
                    total +=Integer.valueOf(wordCount.get(i));
                }
                int avg = total / wordCount.size();
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


    /**
     * Activity Name =
     * @author Yahya Wardere
     * @version 1.0
     */

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        Exception exception = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         *
         * @param params
         * @return exception
         */
        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL("https://www.cbc.ca/cmlink/rss-world/");

                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using the currently configured
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");

                /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                 * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
                 * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
                 * We should skip the "<title>" tag which is a child of "<channel>" tag,
                 * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
                 *
                 * In order to achieve this, we will make use of a boolean variable called "insideItem".
                 */
                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                int eventType = xpp.getEventType(); //loop control variable

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <title> and </title>
                                titles.add(xpp.nextText());
                            }
                        }
                        //if the tag is called "link"
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                links.add(xpp.nextText());

                            }


                        }
                        //if the tag is called "description"
                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem)
                            {
                                // extract the text between <description> and </description>

                                //String currentTempString = parser.getAttributeValue(null, "img");
                                // String currentTempString2 = "test";
                                // extract the text between <link> and </link>

                                description  =  xpp.nextText();


                                String imageLink = "src='(.*)\' alt";


                                // Create a Pattern object
                                Pattern imageLinkPattern = Pattern.compile(imageLink);
                                // Now create matcher object.
                                Matcher  imageLinkMatcher = imageLinkPattern.matcher(description);


                                //  String test1 = description.substring(description.indexOf("<p>")+3);
                                String test2 = description.substring(description.indexOf("<p>")+3,description.length()-20);



                                if (imageLinkMatcher.find( )) {


                                    imageLinks.add(imageLinkMatcher.group(1));
                                    extraInfos.add(test2);

                                    String trimmed = test2.trim();
                                    int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
                                    wordCount.add(Integer.toString(words));
                                    int total =0;
                                    for(int i = 0; i<wordCount.size(); i++){
                                        total +=Integer.valueOf(wordCount.get(i));
                                    }
                                    int avg = total / wordCount.size();

                                    max= Integer.valueOf(Collections.max(wordCount)) ;
                                    min= Integer.valueOf( Collections.min(wordCount));
                                    Ave = avg;

                                    System.out.println("The Average IS:" + avg +" The Max is:"+Collections.max(wordCount)+" The Min is:"+Collections.min(wordCount));

                                    lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(CBCMainActivity.this, news.class);
                                            Bundle extras = new Bundle();
                                            extras.putString("imageLink", imageLinks.get(i));
                                            extras.putString("extraInfo", extraInfos.get(i));
                                            extras.putString("PageLink",links.get(i));
                                            extras.putString("wordCount",wordCount.get(i));
                                            extras.putString("titles",titles.get(i));
                                            extras.putString("pubDate",pubDate.get(i));

                                            intent.putExtras(extras);
                                            //intent.putExtra("extraInfo", extraInfos.get(i));

                                            startActivity(intent);
                                        }
                                    });
                                }
                            }


                        }

                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {

                                pubDate.add(xpp.nextText());
                            }
                        }





                    }
                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        /**
         *
         * @param s
         */
        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);



            CustomListAdapter adapter = new CustomListAdapter(CBCMainActivity.this, titles,imageLinks,pubDate);

            lvRss.setAdapter(adapter);


        }

    }
}