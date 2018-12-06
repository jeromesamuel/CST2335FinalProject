package com.example.jeromesamuel.cst2335final;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BusRouteMain extends AppCompatActivity {
    Button getStopInfo;
    EditText stopNumber;
    Button favButton;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_main);

        toolbar = findViewById(R.id.busToolbar);
        toolbar.setTitle("OC Transpo Bus Finder");
        setSupportActionBar(toolbar);

        getStopInfo = (Button) findViewById(R.id.goButton);
        stopNumber = (EditText) findViewById(R.id.stopNumEditText);
        favButton = (Button) findViewById(R.id.favoritesButton);


        getStopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String stopNum =  stopNumber.getText().toString();
                String added = getResources().getString(R.string.added);


                Intent intent = new Intent(BusRouteMain.this, BusRouteList.class);
                intent.putExtra("Stop Number", stopNum);
                startActivity(intent);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i("favebutton", "has been clicked");
                String stopNum =  stopNumber.getText().toString();
                Intent intent = new Intent(BusRouteMain.this, BusFavorites.class);
                intent.putExtra("Stop Number", stopNum);
                startActivity(intent);




            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater info = getMenuInflater();
        info.inflate(R.menu.bus_help_menu, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.helpMenu:
                Log.d("ic info clicked", "here");
                String helpMsg = getResources().getString(R.string.help_menu);
                AlertDialog alertDialog = new AlertDialog.Builder(BusRouteMain.this).create();
                alertDialog.setTitle("Information");
                alertDialog.setMessage(helpMsg);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                //insert code to switch activity here
                break;

            case R.id.favesBtn:
                Intent intent = new Intent(BusRouteMain.this, BusFavorites.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }



}
