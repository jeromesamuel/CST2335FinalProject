package com.example.jeromesamuel.cst2335final;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class SavedCustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> titles;
    private final ArrayList<String> pubDates;
    private final ArrayList<String> extraInfos;

    ImageView imageView;



    public SavedCustomListAdapter(Activity context, ArrayList<String> titles,ArrayList<String> pubDates , ArrayList<String> extraInfos) {
        super(context, R.layout.saved_list_parts, titles);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.titles = titles;
        this.pubDates =pubDates;
        this.extraInfos =extraInfos;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.saved_list_parts, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.Savedtext);
        //imageView = (ImageView) rowView.findViewById(R.id.savedIcon);

        TextView pubDate = (TextView) rowView.findViewById(R.id.Savedtext1);




        txtTitle.setText("222");
        pubDate.setText("22");
     txtTitle.setTextColor(Color.BLACK);




        return rowView;

    }





}
