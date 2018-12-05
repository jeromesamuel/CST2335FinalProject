package com.example.jeromesamuel.cst2335final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class NewsDatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "MyDatabase";
    static int VERSION_NUM = 3;
    final static String KEY_ID = "ID";
    public static final String TABLE_NAME = "newsData";
        final static String NEWS_TITLE = "title";
    final static String PUBLISH_DATE = "publishdate";
    final static String NEWS_ARTICLE = "article";
    final static String NEWS_LINK = "link";
    final static String NEWS_IMAGE = "image";

    public NewsDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, 3);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        String makeTable = "CREATE TABLE " + TABLE_NAME + "("
                + PUBLISH_DATE + " TEXT," + NEWS_ARTICLE + " TEXT,"+ NEWS_IMAGE + " TEXT,"+ NEWS_TITLE + " TEXT,"
                + NEWS_LINK  + " TEXT" + ")";

        db.execSQL(makeTable);

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