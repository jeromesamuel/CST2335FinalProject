package com.example.jeromesamuel.cst2335final;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> titles;
    private final ArrayList<String> imagelinks;
    private final ArrayList<String> pubDates;

    ImageView imageView;



    public CustomListAdapter(Activity context, ArrayList<String> titles, ArrayList<String> imagelinks,ArrayList<String> pubDates) {
        super(context, R.layout.newslist, imagelinks);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.titles = titles;
        this.imagelinks = imagelinks;
        this.pubDates =pubDates;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.newslist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        imageView = (ImageView) rowView.findViewById(R.id.icon);

       TextView pubDate = (TextView) rowView.findViewById(R.id.textView1);




        txtTitle.setText(titles.get(position));
        pubDate.setText(pubDates.get(position));
       Picasso.get().load(imagelinks.get(position)).into(imageView);




        return rowView;

    }





}
