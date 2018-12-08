package com.example.jeromesamuel.cst2335final;


import android.app.Activity;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;



/**
 * Activity Name: Bus Custom list Adapter
 *
 * This activity is used for
 * @author Jerome Samuel
 * @version 1.2
 *
 */

public class BusCustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> busRouteArray;
    private final ArrayList<String> busDirectionArray;


    /**
     *
     * @param context
     * @param busRouteArray
     * @param busDirectionArray
     */
    public BusCustomListAdapter(Activity context, ArrayList<String> busRouteArray, ArrayList<String> busDirectionArray) {
        super(context, R.layout.list_item, busRouteArray);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.busRouteArray = busRouteArray;
        this.busDirectionArray=busDirectionArray;


    }

    /**
     *
     * @param position
     * @param view
     * @param parent
     * @return
     */
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