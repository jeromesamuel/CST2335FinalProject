package com.example.jeromesamuel.cst2335final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById (R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle("CST2335");
        toolbar.setSubtitle("Final Project");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.busRouteIcon:
                //insert code to switch activity here
                Intent intent = new Intent(MainActivity.this, BusRouteMain.class);
                startActivityForResult(intent, 5);
                break;
            case R.id.movieDBIcon:
                //insert code to switch activity here
                break;
            case R.id.newsIcon:
                //insert code to switch activity here
                intent = new Intent(MainActivity.this, CBCMainActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
