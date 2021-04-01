package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button photoButton;
    private Button gpsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoButton = (Button) findViewById(R.id.photoButton);
        gpsButton = (Button) findViewById(R.id.gpsButton);
/*        where i left off..
            gpsButton.setOnClickListener(new View.onClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity();
            }
        });*/
    }
    public void openMapsActivity(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }
}