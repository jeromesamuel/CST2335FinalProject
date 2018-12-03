package com.example.jeromesamuel.cst2335final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class BusRouteList extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    ArrayList<HashMap<String, String>> busList;
    ArrayList<String> busRouteArray;
    ArrayList<String> busDirectionArray;
    ListAdapter adapter;
    ProgressDialog busLoad;
    ListView listview;
    String stopNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("Current method: ", "in on Create");

        busRouteArray = new ArrayList<String>();
        busDirectionArray = new ArrayList<String>();

        busList = new ArrayList<>();
        setContentView(R.layout.activity_bus_route_list);
        listview = (ListView) findViewById(R.id.busList);
        stopNum = getIntent().getStringExtra("Stop Number");
        new GetBusRoutes().execute();


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               listview.getAdapter().getItem(position);
               Log.d("listview", listview.getAdapter().getItem(position).toString());
               String pos = Integer.toString(position);
               Log.d("Position", pos);

               TextView lvBusNum =  findViewById(R.id.busRoute);
               String busNum = ((TextView)view.findViewById(R.id.busRoute)).getText().toString();

               Log.d("listViewBusNumber", busNum);
               Intent intent = new Intent(BusRouteList.this, BusRouteView.class);
               intent.putExtra("stopNumber", stopNum);
               intent.putExtra("busNumber", busNum);
               startActivity(intent);
            }
        });
    }

    private class GetBusRoutes extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            busLoad = new ProgressDialog(BusRouteList.this);
            busLoad.setMessage("Loading bus list");
            busLoad.show();
        }



        @Override
        protected Void doInBackground(Void... voids) {
            busRouteArray.clear();
            busDirectionArray.clear();
            JSONHandler json = new JSONHandler();
            Exception exception = null;
//            Log.e(TAG, "Response from url: " + busRouteJSON);


try {
    URL url = new URL("http://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+stopNum);


    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    factory.setNamespaceAware(false);
    XmlPullParser parser = factory.newPullParser();

    parser.setInput(json.getInputStream(url), "UTF-8");
    int eventType = parser.getEventType();
    boolean insideItem = false;

    while (eventType != XmlPullParser.END_DOCUMENT){

        if(eventType == XmlPullParser.START_TAG){
            if (parser.getName().equalsIgnoreCase("Route"))
            {

                insideItem = true;
            }

            else if(parser.getName().equalsIgnoreCase("RouteNo")){
                if (insideItem) {

                    busRouteArray.add(parser.nextText());


                }
            }

            else if(parser.getName().equalsIgnoreCase("RouteHeading")){
                if (insideItem) {

                    busDirectionArray.add(parser.nextText());


                }
            }

        }
        //if we are at an END_TAG and the END_TAG is called "item"
        else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item"))
        {
            insideItem = false;
        }

        eventType = parser.next(); //move to next element
    }


}
catch (MalformedURLException e)
{
    Log.d("Exception: ", "MalformedURLException");
    exception = e;
}
catch (XmlPullParserException e)
{
    Log.d("Exception: ", "XMLPullParserException");
    exception = e;
}
catch (IOException e)
{
    Log.d("Exception: ", "IOException");
    exception = e;
}

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Dismiss the progress dialog
            if (busLoad.isShowing())
                busLoad.dismiss();
            /**
             * Updating parsed xml data into ListView
             * */
            ListAdapter adapter = new CustomListAdapter(
                    BusRouteList.this, busRouteArray, busDirectionArray);

                    listview.setAdapter(adapter);

        }

    }




}

