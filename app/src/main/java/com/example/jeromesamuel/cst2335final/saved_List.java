package com.example.jeromesamuel.cst2335final;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class saved_List extends AppCompatActivity {
    ListView lvRss2;
    ArrayList<String> titles2;
    ArrayList<String> links2;
    ArrayList<String> imageLinks2;
    ArrayList<String> extraInfos2;
    ArrayList<String> pubDate2;

    String description;
    int test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);


        Button delete = (Button) findViewById(R.id.delete);

        ImageView imageView = ( ImageView) findViewById(R.id.imageViewNewsSaved);
        TextView textViewNews = (TextView) findViewById(R.id.textViewNewsSaved);
        TextView textViewLink= (TextView) findViewById(R.id.textViewLinkSaved);
        lvRss2 = (ListView) findViewById(R.id.SavedListView);


        titles2 = new ArrayList<String>();
        links2 = new ArrayList<String>();
        imageLinks2 = new ArrayList<String>();
        extraInfos2 = new ArrayList<String>();
        pubDate2 = new ArrayList<String>();

        new ProcessInBackground2().execute();


    }

    public class ProcessInBackground2 extends AsyncTask<Integer, Void, Exception>
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
                XmlPullParser parser = Xml.newPullParser();

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
                                titles2.add(xpp.nextText());
                            }
                        }
                        //if the tag is called "link"
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                links2.add(xpp.nextText());

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


                                    imageLinks2.add(imageLinkMatcher.group(1));
                                    extraInfos2.add(test2);

                                    lvRss2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(saved_List.this, SavedNews.class);
                                            Bundle extras = new Bundle();
                                            extras.putString("imageLink", imageLinks2.get(i));
                                            extras.putString("extraInfo", extraInfos2.get(i));
                                            extras.putString("PageLink",links2.get(i));

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
                                // extract the text between <title> and </title>
                                pubDate2.add(xpp.nextText());
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


        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);


          //  SavedCustomListAdapter adapter2 = new SavedCustomListAdapter(saved_List.this, titles2,pubDate2,extraInfos2);

            SavedCustomListAdapter adapter2 = new SavedCustomListAdapter(saved_List.this, titles2,imageLinks2,pubDate2);
            lvRss2.setAdapter(adapter2);




            //  progressDialog.dismiss();
        }

    }

}
