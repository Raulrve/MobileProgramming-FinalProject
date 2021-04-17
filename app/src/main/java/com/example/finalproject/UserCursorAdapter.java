package com.example.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCursorAdapter {

    public static ListAdapter displayOtherUsers(Context context, Cursor cursor){
        if (cursor != null){
            List<Map<String, String>> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                String firstname = cursor.getString(cursor.getColumnIndex(UserContentProvider.COLUMN_FIRSTNAME));
                Log.e("=====", "=====firstname : " +firstname);
                cursor.getString(1);
                HashMap<String, String> item = new HashMap<>();
                item.put(UserContentProvider.COLUMN_FIRSTNAME, firstname);
                list.add(item);
            }
            String[] from = new String[] {UserContentProvider.COLUMN_FIRSTNAME};
            int[] to = new int[] {android.R.id.text1};
            SimpleAdapter adapter = new SimpleAdapter(context, list, android.R.layout.simple_list_item_2, from, to);
            return adapter;
        }
        return null;
    }

    class UserCursorAdapter1 extends CursorAdapter {

        public UserCursorAdapter1(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

        }
    }



}
