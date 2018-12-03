package com.example.jeromesamuel.cst2335final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BusRouteMain extends AppCompatActivity {
    Button getStopInfo;
    EditText stopNumber;
    Button favButton;
    TextView stopInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_main);

        getStopInfo = (Button) findViewById(R.id.goButton);
        stopNumber = (EditText) findViewById(R.id.stopNumEditText);
        favButton = (Button) findViewById(R.id.favoritesButton);
        stopInfo = (TextView) findViewById(R.id.returnStopInfo);

        getStopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String stopNum =  stopNumber.getText().toString();
                String stopInfoString = stopInfo.getText().toString();

                stopInfo.setText(stopNum);
                Intent intent = new Intent(BusRouteMain.this, BusRouteList.class);
                intent.putExtra("Stop Number", stopNum);
                startActivity(intent);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


            }
        });
    }

}
