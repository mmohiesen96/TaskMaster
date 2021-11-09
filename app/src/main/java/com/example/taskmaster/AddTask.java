package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskItem;
import com.amplifyframework.datastore.generated.model.Team;
import com.example.taskmaster.data.TaskDataManager;

import java.util.HashMap;

public class AddTask extends AppCompatActivity {

    public static final String TASK_ITEM = "task_item";
    private static final String TAG = "AddTask";
    private TaskDAO taskDAO;
    private TaskDB taskDB;
    private int myTaskImage;
    private static final HashMap<String , Integer> taskImage = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setTitle("Add task");
        taskImage.put("Task1", R.drawable.ic_clipboard);
        taskImage.put("Task2", R.drawable.ic_clipboard1);
        taskImage.put("Task3", R.drawable.ic_study);
        taskDB = Room.databaseBuilder(getApplicationContext() , TaskDB.class , TASK_ITEM).allowMainThreadQueries().build();
        taskDAO = taskDB.taskDAO();
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tasks, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String text = (String) adapterView.getItemAtPosition(position);

                myTaskImage = taskImage.get(text);
                Log.i(TAG, "onItemSelected: " + text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button addTask = findViewById(R.id.button3);
        TextView tv = findViewById(R.id.textView6);
        tv.setText("Total tasks: " + taskDAO.findAll().size());
        addTask.setOnClickListener(v -> {
            EditText e1 = findViewById(R.id.editTextTextPersonName);
            EditText e2 = findViewById(R.id.editTextTextPersonName2);
            EditText e3 = findViewById(R.id.editTextTextPersonName5);
            String title = e1.getText().toString();
            String body = e2.getText().toString();
            String state = e3.getText().toString();

            Toast.makeText(AddTask.this, "Task saved", Toast.LENGTH_SHORT).show();
            Task myTask = new Task(title , body , state);
            Team
            TaskItem myTaskDynamo = TaskItem.builder().title(title).team().build();
            Amplify.API.mutate(ModelMutation.create(myTaskDynamo),
                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            myTask.setImage(myTaskImage);
            taskDAO.insertOne(myTask);
            Toast.makeText(AddTask.this, "Added", Toast.LENGTH_SHORT).show();
            Intent allIntent = new Intent(this , AddTask.class);
            startActivity(allIntent);
        });
    }





}