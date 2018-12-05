package com.example.jeromesamuel.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BusFavorites extends AppCompatActivity {

    BusDatabaseHelper bdh;
    SQLiteDatabase sqldb;
    Cursor cursor;
    ChatAdapter busAdapter;
    ArrayList<String> stopList;
    ListView busStopList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_favorites);
        stopList = new ArrayList<String>();

        String stopNum = getIntent().getStringExtra("Stop Number");

        bdh = new BusDatabaseHelper(this);
        sqldb = bdh.getWritableDatabase();
        String query = "SELECT * FROM " + bdh.TABLE_NAME;
        cursor = sqldb.rawQuery(query, null);
        busAdapter = new ChatAdapter(this);

        final Cursor results = sqldb.query(false, BusDatabaseHelper.TABLE_NAME,
                new String[]{BusDatabaseHelper.KEY_ID, BusDatabaseHelper.COLUMN_STOP},
                null, null, null, null, null, null);
        int rows = results.getCount();//number of rows
        results.moveToFirst();
/////////////////

        stopList.add(stopNum);
        ContentValues insertValues = new ContentValues();
        insertValues.put(BusDatabaseHelper.COLUMN_STOP, stopNum);
        sqldb.insert(BusDatabaseHelper.TABLE_NAME, "", insertValues);
        busAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()


        //Toast.makeText(this, "Stop is already in favorites", Toast.LENGTH_SHORT).show();


        Log.i("BusDatabaseHelper", "onCreate: Cursor row count =" + results.getCount());
        while (!results.isAfterLast()) {
            stopList.add(results.getString(results.getColumnIndex(BusDatabaseHelper.COLUMN_STOP)));
            Log.i("BusDatabaseHelper", "SQL MESSAGE: " + results.getString(
                    results.getColumnIndex(BusDatabaseHelper.COLUMN_STOP)));
            results.moveToNext();
        }

        Log.i("BusDatabaseHelper", "Cusor's column count =" + results.getColumnCount());
        Log.i("BusDatabaseHelper", "onCreate: Cursor row count =" + results.getCount());

        results.moveToFirst();
        for (int i = 0; i < results.getColumnCount(); i++) {
            Log.i(ACTIVITY_SERVICE, "SQL Column Rows: " + results.getColumnName(i));
        }

        busStopList = findViewById(R.id.stopList);

        busStopList.setAdapter(busAdapter);




        busStopList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                busStopList.getAdapter().getItem(position);
                Log.d("listview", busStopList.getAdapter().getItem(position).toString());
                String pos = Integer.toString(position);
                Log.d("Position", pos);

                TextView lvBusNum =  findViewById(R.id.busStop);
                String stopNum = ((TextView)view.findViewById(R.id.busStop)).getText().toString();
                Log.d("stopNum", stopNum);

                Intent intent = new Intent(BusFavorites.this, BusRouteList.class);
                intent.putExtra("Stop Number", stopNum);
                Snackbar.make(view, stopNum+" has been selected", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivity(intent);
            }
        });




    }
    public boolean ifStopInFavorites(String stop){
for(int i =0; i < stopList.size(); i++){
    if (stop.equalsIgnoreCase(stopList.get(i).toString())){
        return true;
    }
    else
        return false;
    }
    return true;
    }




    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return stopList.size();
        }

        public String getItem(int position){
            return stopList.get(position);


        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = BusFavorites.this.getLayoutInflater();

            View result = null ;

                result = inflater.inflate(R.layout.favorites_list_item, null);


            TextView message = (TextView)result.findViewById(R.id.busStop);
            message.setText( getItem(position) ); // get the string at position
            return result;

        }



        public long getItemId(int position){

            if (cursor == null)
                throw new NullPointerException("ERROR: cursor is null");

            // refresh the cursors
            cursor = sqldb.rawQuery("SELECT * FROM " + BusDatabaseHelper.TABLE_NAME, null);
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(BusDatabaseHelper.KEY_ID));

        }
    }

    protected void onDestroy(){

        super.onDestroy();
        bdh.close();
    }

}

