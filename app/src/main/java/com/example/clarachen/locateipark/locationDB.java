package com.example.clarachen.locateipark;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by monishadash on 4/23/16.
 */
public class locationDB extends SQLiteOpenHelper{
    //Provide Database name
    private static String DBNAME = "latlng.db";

    /** Version number of the database */
    private static int VERSION = 1;

    /** Field 1 of the table locations, which is the primary key */
    public static final String FIELD_ROW_ID = "_id";

    /** Field 2 of the table locations, stores the latitude */
    public static final String FIELD_LAT = "lat";

    /** Field 3 of the table locations, stores the longitude*/
    public static final String FIELD_LNG = "lng";

    /** Field 4 of the table locations, stores the zoom level of map*/
    public static final String FIELD_ZOOM = "zoom";

    /** A constant, stores the the table name */
    private static final String DATABASE_TABLE = "locations";

    /** An instance variable for SQLiteDatabase */
    private SQLiteDatabase mDB;

    String sql = "create table " + DATABASE_TABLE + " ( " + FIELD_ROW_ID + " integer primary key autoincrement , " + FIELD_LNG + " double , " + FIELD_LAT + " double , " + FIELD_ZOOM + " text " + " ) ";


    public locationDB(Context context) {
        super(context, DBNAME, null, VERSION);
       // this.mDB=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "create table " + DATABASE_TABLE + " ( " + FIELD_ROW_ID + " integer primary key autoincrement , " + FIELD_LNG + " double , " + FIELD_LAT + " double , " + FIELD_ZOOM + " text " + " ) ";

        db.execSQL(sql);

    }
    /** Inserts a new location to the table locations */
    public long insert(ContentValues contentValues){
        long rowID = mDB.insert(DATABASE_TABLE, null, contentValues);
        return rowID;
    }
    /** Deletes all locations from the table */
    public int del(){
        int count = mDB.delete(DATABASE_TABLE, null, null);
        return count;
    }
    /** Returns all the locations from the table */
    public Cursor getAllLocations(){
        return mDB.query(DATABASE_TABLE, new String[] { FIELD_ROW_ID,  FIELD_LAT , FIELD_LNG, FIELD_ZOOM } , null, null, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sql);

        onCreate(db);

    }

   public static class DBtables {
        // To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
        public DBtables() {}

        // Inner class that defines the table contents
        public static abstract class TextReport implements BaseColumns {
            public static final String FIELD_ROW_ID="-id";
            public static final String FIELD_LAT = "lat";
            public static final String FIELD_LNG = "lng";
            public static final String PRIMARY_KEY = "PRIMARY KEY (" + FIELD_ROW_ID+"," + FIELD_LAT + "," + FIELD_LNG + ")";
            public static final String[] ALL_COLUMNS =
                    { FIELD_ROW_ID,FIELD_LAT, FIELD_LNG};
        }
    }




}


