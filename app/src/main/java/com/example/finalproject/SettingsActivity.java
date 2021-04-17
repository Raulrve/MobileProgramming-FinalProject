package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        final TextView firstNameView = findViewById(R.id.firstNameView);
        final TextView lastNameView = findViewById(R.id.lastNameView);
        final TextView birthdayView = findViewById(R.id.birthdayView);
        final TextView emergencyContactView = findViewById(R.id.emergencyContactView);

        final EditText firstNameText = findViewById(R.id.firstNameText);
        final EditText lastNameText = findViewById(R.id.lastNameText);
        final EditText birthdayText = findViewById(R.id.birthdayText);
        final EditText emergencyContactTextName = findViewById(R.id.emergencyContactName);
        final EditText emergencyContactTextNumber = findViewById(R.id.emergencyContactPhone);

        Button doneButton = findViewById(R.id.doneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempStr;
                boolean flag = false;
                tempStr = firstNameText.getText().toString();
                if (tempStr.equals("")) {
                    flag = true;
                    firstNameView.setTextColor(Color.RED);
                }
                tempStr = lastNameText.getText().toString();
                if (tempStr.equals("")) {
                    flag = true;
                    lastNameView.setTextColor(Color.RED);
                }
                tempStr = birthdayText.getText().toString();
                if (tempStr.equals("")) {
                    flag = true;
                    birthdayView.setTextColor(Color.RED);
                }
                tempStr = emergencyContactTextName.getText().toString();
                if (tempStr.equals("")) {
                    flag = true;
                    emergencyContactView.setTextColor(Color.RED);
                }
                tempStr = emergencyContactTextNumber.getText().toString();
                if (tempStr.equals("")) {
                    flag = true;
                    emergencyContactView.setTextColor(Color.RED);
                }
                if (flag) {
                    Toast.makeText(getApplicationContext(), "Error: one or more fields are empty.", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues values = new ContentValues();
                    values.put(UserContentProvider.COLUMN_FIRSTNAME, firstNameText.getText().toString());
                    values.put(UserContentProvider.COLUMN_LASTNAME, lastNameText.getText().toString());
                    values.put(UserContentProvider.COLUMN_BIRTHDAY, birthdayText.getText().toString());
                    values.put(UserContentProvider.COLUMN_EMERGENCYNAME, emergencyContactTextName.getText().toString());
                    values.put(UserContentProvider.COLUMN_EMERGENCYPHONE, emergencyContactTextName.getText().toString());

                    UserContract.addUser(getApplicationContext(), values);
                    finish();


                }
            }
        });

    }
    public void openMainActivity(){
        finish();
    }

}




