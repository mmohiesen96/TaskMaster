package com.example.taskmaster;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void getName(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        EditText settingText = findViewById(R.id.editTextTextPersonName3);
        prefEditor.putString("username" , settingText.getText().toString());
        prefEditor.apply();
        Toast.makeText(this, "Hello, " +  settingText.getText() + " Your Name is saved", Toast.LENGTH_SHORT).show();
    }
}