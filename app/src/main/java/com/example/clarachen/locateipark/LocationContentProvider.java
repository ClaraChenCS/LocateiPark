package com.example.clarachen.locateipark;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.sql.SQLException;

/**
 * Created by monishadash on 4/23/16.
 */
public class LocationContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "com.example.clarachen.locateipark.locations";

    /** A uri to do operations on locations table. A content provider is identified by its uri */
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/locations" );

    /** Constant to identify the requested operation */
    private static final int LOCATIONS = 1;

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "locations", LOCATIONS);
    }

    locationDB mlocationDB;
    @Override
    public boolean onCreate() {
        mlocationDB= new locationDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uriMatcher.match(uri)==LOCATIONS){
            return mlocationDB.getAllLocations();
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = mlocationDB.insert(values);
        Uri _uri=null;
        if(rowID>0){
            _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
        }else {
            try {
                throw new SQLException("Failed to insert : " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _uri;
        //return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        count = mlocationDB.del();
        return count;
        //return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
