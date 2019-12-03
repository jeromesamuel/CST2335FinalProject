package com.example.jeromesamuel.cst2335final;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Activity Name =
 * @author Yahya Wardere
 * @version 1.0
 */

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "MyDatabase";
    static int VERSION_NUM = 8;
    final static String KEY_ID = "ID";
    final static String KEY_MESSAGE = "MESSAGE";
    final static String KEY_LINK = "LINK";
    final static String KEY_IMAGE_LINK = "IMAGE";
    final static String KEY_WORD_COUNT = "COUNT";
    final static String KEY_IMAGE_TITLE = "TITLE";
    final static String KEY_WORD_pub = "PUB";


    /**
     *
     * @param ctx
     */
    public NewsDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL("CREATE TABLE " + DATABASE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " VARCHAR(256)," + KEY_LINK + " VARCHAR(256)," + KEY_WORD_COUNT + " VARCHAR(256)," + KEY_IMAGE_LINK + " VARCHAR(256)," + KEY_IMAGE_TITLE + " VARCHAR(256)," + KEY_WORD_pub + " VARCHAR(256));");

    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);

    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}