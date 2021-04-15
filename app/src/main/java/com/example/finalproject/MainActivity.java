package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton gpsButton = (ImageButton)findViewById(R.id.gpsButton);
        final ImageButton galleryButton = (ImageButton)findViewById(R.id.photoButton);
        final ImageButton emergencyButton = (ImageButton)findViewById(R.id.emergencyButton);
        final ImageButton phoneButton = (ImageButton)findViewById(R.id.phoneButton);

        final Button settingsButton = (Button)findViewById(R.id.settingsButton);

        Cursor cursor1 = UserContract.queryDatabase(getApplicationContext());
        listView = findViewById(R.id.listView);
        listView.setAdapter(UserCursorAdapter.displayOtherUsers(this, cursor1));


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
    public void openMapsActivity(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void openGalleryActivity(){
        Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
        startActivity(intent);
    }

    public void openEmergencyActivity(){
        //Intent intent = new Intent(this, EmergencyActivity.class);
        //startActivity(intent);
    }
    public void openPhoneActivity(){
        Intent intent = new Intent(this, PhoneActivity.class);
        startActivity(intent);
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
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}