package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;

public class UserContract {

    static Cursor mCursor;
    CursorAdapter mCursorAdapter;

    String[] mProjection;
    String[] mListColumns;
    String mSelectionArgs;
    String mOrderBy;

    int[] mListItems;

//    public static boolean checkUser (Context context, String firstname, String password){
//        //context.getContentResolver().query();
//        return true;
//    }

    public static void addUser(Context context, ContentValues values)
    {
        context.getContentResolver().insert(UserContentProvider.CONTENT_URI, values);
    }

    public static Cursor queryUser(Context context,String firstname)
    {
        String [] stringArr = { firstname };
        return context.getContentResolver().query(UserContentProvider.CONTENT_URI, null , UserContentProvider.COLUMN_FIRSTNAME + " = ?", stringArr, null);
    }

    public static Cursor queryDatabase(Context context){
        UserContentProvider.MainDatabaseHelper db = new UserContentProvider.MainDatabaseHelper(context);
        return db.getReadableDatabase().rawQuery("select rowid _id,* from " + UserContentProvider.TABLE_NAME, null);
    }

    public static void removeUser(Context context, String empId){
        String mSelectionClause = UserContentProvider.COLUMN_FIRSTNAME + " = ? ";
        String[] mSelectionArgs = { empId };
        context.getContentResolver().delete(UserContentProvider.CONTENT_URI, mSelectionClause, mSelectionArgs);
    }



}
