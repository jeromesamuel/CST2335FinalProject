package com.example.jeromesamuel.cst2335final;

/**
 * Created by jeromesamuel on 2018-12-02.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> busRouteArray;
    private final ArrayList<String> busDirectionArray;





    public CustomListAdapter(Activity context, ArrayList<String> busRouteArray,ArrayList<String> busDirectionArray) {
        super(context, R.layout.list_item, busRouteArray);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.busRouteArray = busRouteArray;
        this.busDirectionArray=busDirectionArray;


    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        TextView busRoute = (TextView) rowView.findViewById(R.id.busRoute);


        TextView busDirection = (TextView) rowView.findViewById(R.id.Direction);

        busRoute.setText(busRouteArray.get(position));
        busDirection.setText(busDirectionArray.get(position));
        busRoute.setTextColor(Color.BLACK);
        busDirection.setTextColor(Color.RED);


        return rowView;

    }





}