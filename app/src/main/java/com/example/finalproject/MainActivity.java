package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView listView, dateTimeTextview;
    private String phoneNumberToCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton gpsButton = (ImageButton)findViewById(R.id.gpsButton);
        final ImageButton galleryButton = (ImageButton)findViewById(R.id.photoButton);
        final ImageButton emergencyButton = (ImageButton)findViewById(R.id.emergencyButton);
        final ImageButton phoneButton = (ImageButton)findViewById(R.id.phoneButton);

        final Button settingsButton = (Button)findViewById(R.id.settingsButton);
        listView = findViewById(R.id.listView);
        listView.setText("No record found, go to settings and store.");
        dateTimeTextview =findViewById(R.id.dateTimeTextview);

        getData();

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryActivity();
            }
        });

        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity();
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoneActivity();
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmergencyActivity();
            }
        });

    }

    private void getData() {
        Cursor cursor1 = UserContract.queryDatabase(getApplicationContext());
        if(cursor1 != null) {
            String fname ="", lname = "";
            while (cursor1.moveToNext()) {
                // move the cursor to next row if there is any to read it's data
                fname = cursor1.getString(cursor1.getColumnIndex(UserContentProvider.COLUMN_FIRSTNAME));
                lname = cursor1.getString(cursor1.getColumnIndex(UserContentProvider.COLUMN_LASTNAME));
                phoneNumberToCall = cursor1.getString(cursor1.getColumnIndex(UserContentProvider.COLUMN_EMERGENCYPHONE));
            }
            if(!TextUtils.isEmpty(fname)) {
                listView.setText("Welcome To Your Guardian"+ " " + fname);
            }

        }

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, hh:mm");
        dateTimeTextview.setText("" + format.format(date));

    }
    public void openMapsActivity(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void openGalleryActivity(){
        Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
        startActivity(intent);
    }

    public void openEmergencyActivity(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 101);
        } else {
            Uri callUri = Uri.parse("tel://911");
            Intent callIntent = new Intent(Intent.ACTION_CALL,callUri);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            startActivity(callIntent);
        }




    }
    public void openPhoneActivity(){

        if(!TextUtils.isEmpty(phoneNumberToCall)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+phoneNumberToCall));
            startActivity(intent);
        } else {
            Toast.makeText(this, "No phone number found...", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homeButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.settingsButton){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1000);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            getData();
        }
    }
}