package com.example.jeromesamuel.cst2335final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class NewsDatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "MyDatabase";
    static int VERSION_NUM = 3;
    final static String KEY_ID = "ID";
    final static String KEY_MESSAGE = "MESSAGE";

    public NewsDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL("CREATE TABLE " + DATABASE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE
                + " VARCHAR(256));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}