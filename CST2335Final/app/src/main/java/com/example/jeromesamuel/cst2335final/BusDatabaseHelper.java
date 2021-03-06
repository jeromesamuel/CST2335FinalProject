package com.example.jeromesamuel.cst2335final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Activity Name: Bus Database Helper
 *
 * This activity is used for creating and accessing the databases of favorite bus stops
 * @author Jerome Samuel
 * @version 1.2
 *
 */

public class BusDatabaseHelper extends SQLiteOpenHelper {
    protected static final String ACTIVITY_NAME = "busDatabaseHelper";
    public static final String DATABASE_NAME = "stopList.db";
    public static final int VERSION_NUM = 3;
    public static final String TABLE_NAME = "busStopData";
    public static final String KEY_ID  = "keyId";
    protected static final String COLUMN_STOP = "StopNumber";



    public BusDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);


    }

    /**
     *
     * @param sqldb
     */

    @Override
    public void onCreate(SQLiteDatabase sqldb) {

        Log.i(ACTIVITY_NAME, "Calling onCreate");
        sqldb.execSQL("CREATE TABLE "+ TABLE_NAME + "("+ KEY_ID + " INTEGER PRIMARY KEY, " + COLUMN_STOP +" VARCHAR(256));" );
        Log.i(ACTIVITY_NAME,"Calling version()"+sqldb.getVersion());




    }

    /**
     *
     * @param sqldb
     * @param i
     * @param i1
     */

    @Override
    public void onUpgrade(SQLiteDatabase sqldb, int i, int i1) {
        Log.i(ACTIVITY_NAME, "Calling onUpgrade");
        sqldb.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(sqldb);

    }

    /**
     *
     * @param sqldb
     * @param i
     * @param i1
     */
    @Override
    public void onDowngrade(SQLiteDatabase sqldb, int i, int i1){
        sqldb.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqldb);
    }
}


