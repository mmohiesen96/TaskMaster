package com.example.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent myIntent = getIntent();
        ((TextView)findViewById(R.id.textView8)).setText(myIntent.getExtras().getString("Title"));
        ((TextView)findViewById(R.id.textView9)).setText(myIntent.getExtras().getString("Body"));
        ((TextView)findViewById(R.id.textView10)).setText(myIntent.getExtras().getString("State"));


    }

}