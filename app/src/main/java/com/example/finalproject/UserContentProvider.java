package com.example.finalproject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class UserContentProvider extends ContentProvider {
    public final static String DBNAME = "user.db";
    public final static String TABLE_NAME = "users";
    public final static String COLUMN_FIRSTNAME = "firstname";
    public final static String COLUMN_LASTNAME = "lastname";
    public final static String COLUMN_BIRTHDAY = "birthday";
    public final static String COLUMN_EMERGENCYNAME = "emergencyname";
    public final static String COLUMN_EMERGENCYPHONE = "emergencyphone";

    public final static String AUTHORITY = "edu.fsu.cs.mobile.provider";
    public final static Uri CONTENT_URI = Uri.parse("content://com.mobile.finalproject.provider/" + TABLE_NAME);

    private static UriMatcher sUriMatcher;

    private MainDatabaseHelper mOpenHelper;

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) { super(context, DBNAME , null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN); }
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

            onCreate(db);
        }

    }
    private static final String SQL_CREATE_MAIN = " CREATE TABLE " +
            TABLE_NAME +
            " ( " +
            COLUMN_FIRSTNAME + " TEXT PRIMARY KEY, " +
            COLUMN_LASTNAME + " TEXT , " +
            COLUMN_BIRTHDAY + " TEXT , " +
            COLUMN_EMERGENCYNAME + " TEXT , " +
            COLUMN_EMERGENCYPHONE + " TEXT ) " ;


    @Override
    public boolean onCreate() {
        // create new database
        mOpenHelper = new MainDatabaseHelper(getContext());
        return true;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values){

        long id = mOpenHelper.getWritableDatabase()
                .insert(TABLE_NAME, null, values);
        return Uri.parse(TABLE_NAME + "/" + id);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        return mOpenHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        return mOpenHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);

    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder){
        return mOpenHelper.getReadableDatabase().query(TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);

    }

    @Override
    public String getType(Uri uri) {return null;}


}
