package com.example.jeromesamuel.cst2335final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Activity Name: BusRouteList
 *
 * This activity is used for returning a list of busses that service a certain bus stop
 * @author Jerome Samuel
 * @version 1.2
 *
 *
 */


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
try {
    super.onCreate(savedInstanceState);
    Log.i("Current method: ", "in on Create");

    busRouteArray = new ArrayList<String>();
    busDirectionArray = new ArrayList<String>();

    busList = new ArrayList<>();
    setContentView(R.layout.activity_bus_route_list);
    listview = (ListView) findViewById(R.id.busList);
    stopNum = getIntent().getStringExtra("Stop Number");
    new GetBusRoutes().execute();

    Toast.makeText(this, "Routes for Stop " + stopNum, Toast.LENGTH_LONG).show();
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            listview.getAdapter().getItem(position);
            Log.d("listview", listview.getAdapter().getItem(position).toString());
            String pos = Integer.toString(position);
            Log.d("Position", pos);

            TextView lvBusNum = findViewById(R.id.busRoute);
            String busNum = ((TextView) view.findViewById(R.id.busRoute)).getText().toString();

            Log.d("listViewBusNumber", busNum);
            Intent intent = new Intent(BusRouteList.this, BusRouteView.class);
            intent.putExtra("stopNumber", stopNum);
            intent.putExtra("busNumber", busNum);
            startActivity(intent);
        }
    });
}
catch(Exception e){
    Toast.makeText(this, "Unable to retrieve route list", Toast.LENGTH_SHORT).show();
}
    }

    



    private class GetBusRoutes extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
           try {
               super.onPreExecute();

               busLoad = new ProgressDialog(BusRouteList.this);
               busLoad.setMessage("Loading bus list");
               busLoad.show();
           }
           catch(Exception e){
               Log.d("Error found", "in PreExecute");
               Toast.makeText(BusRouteList.this, "Error retrieving list", Toast.LENGTH_SHORT).show();
           }
        }


        /**
         *
         * @param voids
         * @return
         */

        @Override
        protected Void doInBackground(Void... voids) {
            try {
            busRouteArray.clear();
            busDirectionArray.clear();
            JSONHandler json = new JSONHandler();
            Exception exception = null;
//            Log.e(TAG, "Response from url: " + busRouteJSON);



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

}
catch (XmlPullParserException e)
{
    Log.d("Exception: ", "XMLPullParserException");

    Toast.makeText(BusRouteList.this, "Server error", Toast.LENGTH_SHORT).show();
}
catch (IOException e)
{
    Log.d("Exception: ", "IOException");
    Toast.makeText(BusRouteList.this, "Unable to find stop", Toast.LENGTH_SHORT).show();

}
catch(Exception e){
    Log.d("Exception: ", "Generic");
    Toast.makeText(BusRouteList.this, "Error", Toast.LENGTH_SHORT).show();

}

            return null;
        }

        /**
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                super.onPostExecute(aVoid);
                // Dismiss the progress dialog
                if (busLoad.isShowing())
                    busLoad.dismiss();
                /**
                 * Updating parsed xml data into ListView
                 * */
                ListAdapter adapter = new BusCustomListAdapter(
                        BusRouteList.this, busRouteArray, busDirectionArray);

                listview.setAdapter(adapter);
            }
            catch(Exception e){
                Toast.makeText(BusRouteList.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }

    }




}

