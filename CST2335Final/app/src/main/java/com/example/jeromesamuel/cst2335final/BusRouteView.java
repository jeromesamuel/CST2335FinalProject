package com.example.jeromesamuel.cst2335final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.android.gms.maps.OnMapReadyCallback;


import javax.xml.transform.Result;

public class BusRouteView extends AppCompatActivity {
    //            public String tripDest;
    //            public String currentLocationLat;
    //            public String currentLocationLong;
    //            public String currentSpeed;
    //            public String tripStartTime;
    //            public String scheduledTime;

    ProgressBar progBar;
    public TextView busRouteNum, busLocation, busSpeed, tripStart, scheduledTimeArrival;
    public ImageView weatherImg;
    public ArrayList < String > tripDestList, currentLatList, currentLongList, speedList, startTimeList, scheduledTimeList;
    String stopNumber;
    Button refresh, mapBtn;
    String busNum;
    private GoogleMap map;
    BusQuery bq = new BusQuery();
    private static String URL;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_view);
        stopNumber = getIntent().getStringExtra("stopNumber");
        busNum = getIntent().getStringExtra("busNumber");
        URL = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775" + "&StopNo=" + stopNumber + "&routeNo=" + busNum;

        progBar = (ProgressBar) findViewById(R.id.progressBar);
        progBar.setVisibility(View.VISIBLE);
        busLocation = (TextView) findViewById(R.id.busLocation);
        busSpeed = (TextView) findViewById(R.id.busSpeed);
        tripStart = (TextView) findViewById(R.id.tripStartTime);
        scheduledTimeArrival = (TextView) findViewById(R.id.scheduledTimeAdjusted);
        refresh = (Button) findViewById(R.id.refresh);
        mapBtn = (Button) findViewById(R.id.mapBtn);
        busRouteNum = (TextView) findViewById(R.id.displayRouteNumberAndDestination);
        intent = new Intent(BusRouteView.this, BusMapView.class);

        tripDestList = new ArrayList < String > ();
        currentLatList = new ArrayList < String > ();
        currentLongList = new ArrayList < String > ();
        speedList = new ArrayList < String > ();
        startTimeList = new ArrayList < String > ();
        scheduledTimeList = new ArrayList < String > ();


        BusQuery bus = new BusQuery();
        bus.execute();
        Toast.makeText(this, stopNumber, Toast.LENGTH_SHORT).show();
        busRouteNum.setText(stopNumber);

        String lat = bq.getLat();
        String lon = bq.getLong();

        Log.d("lat in brv:", lat);
        Log.d("long in brv:", lon);
        Log.i("Getting info from BRM", stopNumber);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(intent);
            }
        });






    }





    protected class BusQuery extends AsyncTask < String, Integer, String > {

        public String tripDest;
        public String currentLocationLat;
        public String currentLocationLong;
        public String currentSpeed;
        public String tripStartTime;
        public String scheduledTime;
        Exception exception = null;

        private final String ns = null;


        public String doInBackground(String...args) {
            Log.d("Method: ", "doInBackground");
            try {
                tripDestList.clear();
                currentLongList.clear();
                currentLatList.clear();
                startTimeList.clear();
                speedList.clear();
                scheduledTimeList.clear();

                URL url = new URL("https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775" + "&stopNo=" + stopNumber + "&routeNo=" + busNum);
                Log.d("URL: ", url.toString());
                JSONHandler json = new JSONHandler();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(json.getInputStream(url), "UTF-8");
                int eventType = parser.getEventType();
                boolean insideItem = false;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    for (int i = 0; i < 1; i++)
                        if (eventType == XmlPullParser.START_TAG) {
                            if (parser.getName().equalsIgnoreCase("trip")) {
                                Log.d("getName: ", parser.getName());

                                insideItem = true;
                            } else if (parser.getName().equalsIgnoreCase("tripdestination")) {
                                if (insideItem) {
                                    Log.d("getName: ", parser.getName());
                                    tripDest = parser.nextText();
                                    tripDestList.add(tripDest);
                                    Log.d("tripDestList: ", tripDestList.get(0).toString());
                                    Log.d("tripDest: ", tripDest);


                                }
                            } else if (parser.getName().equalsIgnoreCase("tripstarttime")) {
                                if (insideItem) {

                                    tripStartTime = parser.nextText();
                                    startTimeList.add(tripStartTime);
                                    Log.d("start time: ", tripStartTime);



                                }
                            } else if (parser.getName().equalsIgnoreCase("adjustedscheduletime")) {
                                if (insideItem) {

                                    scheduledTime = parser.nextText();
                                    scheduledTimeList.add(scheduledTime);
                                    Log.d("scheduled time: ", scheduledTime);




                                }
                            } else if (parser.getName().equalsIgnoreCase("latitude")) {
                                if (insideItem) {

                                    currentLocationLat = parser.nextText();
                                    currentLatList.add(currentLocationLat);
                                    Log.d("latitude: ", currentLocationLat);
                                    setLat(currentLatList.get(0));




                                }
                            } else if (parser.getName().equalsIgnoreCase("longitude")) {
                                if (insideItem) {

                                    currentLocationLong = parser.nextText();
                                    currentLongList.add(currentLocationLong);
                                    setLong(currentLongList.get(0));
                                    Log.d("longitude: ", currentLocationLong);



                                }
                            } else if (parser.getName().equalsIgnoreCase("gpsspeed")) {
                                if (insideItem) {

                                    currentSpeed = parser.nextText();
                                    speedList.add(currentSpeed);
                                    Log.d("speed:", currentSpeed);
                                    sleep();



                                }
                            }



                        }
                        //if we are at an END_TAG and the END_TAG is called "item"
                        else if (eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                            insideItem = false;
                        }

                    eventType = parser.next(); //move to next element
                }


            } catch (MalformedURLException e) {
                Log.d("Exception: ", "MalformedURLException");
                exception = e;
            } catch (XmlPullParserException e) {
                Log.d("Exception: ", "XMLPullParserException");
                exception = e;
            } catch (IOException e) {
                Log.d("Exception: ", "IOException");
                exception = e;
            } catch (IndexOutOfBoundsException e) {
                Log.d("Exception: ", "IndexOutOfBoundsException");
                exception = e;
            }
            return null;
        }
        public void setLat(String lat) {
            currentLocationLat = lat;
        }

        public void setLong(String lon) {
            currentLocationLong = lon;
        }

        public String getLat() {

            if (currentLatList.size() > 0) {

                return currentLatList.get(0);
            } else
                return "0";
        }

        public String getLong() {
            if (currentLongList.size() > 0) {

                return currentLongList.get(0);
            } else

                return "0";
        }


        protected void onPostExecute(String args) {



            String infoNA = "Information not available";

            if (tripDestList == null)
                tripDest = infoNA;
            else if (tripDestList.size() > 0) {
                Log.d("tripDest: ARRAYOOBE: ", tripDestList.get(0));
                tripDest = tripDestList.get(0);
            }

            if (currentLatList == null && currentLongList == null)
                busLocation.setText("Location: " + infoNA);
            else if (currentLatList.size() > 0 && currentLongList.size() > 0) {
                currentLocationLat = currentLatList.get(0);
                currentLocationLong = currentLongList.get(0);
            }

            if (speedList == null)
                currentSpeed = infoNA;
            else if (speedList.size() > 0) {
                currentSpeed = speedList.get(0);
            }

            if (startTimeList == null)
                tripStartTime = infoNA;
            else if (startTimeList.size() > 0) {
                tripStartTime = startTimeList.get(0);
            }

            if (scheduledTimeList == null)
                scheduledTime = infoNA;
            else if (scheduledTimeList.size() > 0) {
                scheduledTime = scheduledTimeList.get(0);
            }




            busRouteNum.setText("Trip Destination:" + tripDest);
            publishProgress(40);
            busLocation.setText("Location: " + currentLocationLat + ", " + currentLocationLong);
            busSpeed.setText("Current speed: " + currentSpeed + " km/h");
            tripStart.setText("Trip starts/started at: " + tripStartTime);
            scheduledTimeArrival.setText("ETA: " + scheduledTime + " minutes");
            sendLocation();
            publishProgress(100);
            BusRouteView brv = new BusRouteView();
            progBar.setVisibility(View.VISIBLE);


            //    Log.i(TAG, "onPostExecute: string passed = "+args);

        }

        private void sleep() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }

        public void sendLocation(){
            intent.putExtra("latitude", currentLocationLat);
            intent.putExtra("longitude", currentLocationLong);
        }

        public void onProgressUpdate(Integer...val) {


            progBar.setProgress(val[0]);


        }


        //
        //        public boolean ifFileExists(String fname){
        //            Log.d("", "Checking if file already exists" );
        //            File file = getBaseContext().getFileStreamPath(fname);
        //            return file.exists();
        //        }
    }






    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    //    private InputStream downloadUrl(String urlString) throws IOException {
    //        URL url = new URL(urlString);
    //        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //        conn.setReadTimeout(10000 /* milliseconds */);
    //        conn.setConnectTimeout(15000 /* milliseconds */);
    //        conn.setRequestMethod("GET");
    //        conn.setDoInput(true);
    //        // Starts the query
    //        conn.connect();
    //        return conn.getInputStream();
    //    }




}