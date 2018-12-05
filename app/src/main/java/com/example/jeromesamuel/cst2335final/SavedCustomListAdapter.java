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

public class SavedCustomListAdapter extends ArrayAdapter<String> {

    private final Activity context2;
    private final ArrayList<String> titles2;
    private final ArrayList<String> imagelinks2;
    private final ArrayList<String> pubDates2;

    ImageView imageView2;



    public SavedCustomListAdapter(Activity context, ArrayList<String> titles2, ArrayList<String> imagelinks2,ArrayList<String> pubDates2) {
        super(context, R.layout.saved_list_parts, imagelinks2);
        // TODO Auto-generated constructor stub

        this.context2 = context;
        this.titles2 = titles2;
        this.imagelinks2 = imagelinks2;
        this.pubDates2 =pubDates2;

    }

    public View getView(int position, View view, ViewGroup parent) {
       LayoutInflater inflater = context2.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.saved_list_parts, null, true);



            TextView txtTitle = (TextView) rowView.findViewById(R.id.Savedtext);
            imageView2 = (ImageView) rowView.findViewById(R.id.Savedicon);

            TextView pubDate = (TextView) rowView.findViewById(R.id.Savedtext1);


            txtTitle.setText(titles2.get(position));
            pubDate.setText(pubDates2.get(position));
            Picasso.get().load(imagelinks2.get(position)).into(imageView2);



        return rowView;

    }





}
