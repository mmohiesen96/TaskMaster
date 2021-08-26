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
            TaskItem taskItem = TaskItem.builder()
                    .title(title)
                    .description(body)
                    .status(state)
                    .build();

            if (isNetworkAvailable(getApplicationContext())) {
                Log.i(TAG, "onClick: the network is available");
            } else {
                Log.i(TAG, "onClick: net down");
            }

            saveTaskToAPI(taskItem);
            TaskDataManager.getInstance().getData().add(new Task(taskItem.getTitle() , taskItem.getDescription(),taskItem.getStatus()));
            Toast.makeText(AddTask.this, "Task saved", Toast.LENGTH_SHORT).show();
            Task myTask = new Task(title , body , state);
            myTask.setImage(myTaskImage);
            taskDAO.insertOne(myTask);
            Toast.makeText(AddTask.this, "Added", Toast.LENGTH_SHORT).show();
            Intent allIntent = new Intent(this , MainActivity.class);
            startActivity(allIntent);
        });
    }
    public TaskItem saveTaskToAPI(TaskItem task) {
        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i(TAG, "Saved item: " + task.getTitle()),
                error -> Log.e(TAG, "Could not save item to API/dynamodb" + task.getTitle()));
        return task;

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnected();
    }




//    public void addTask(View view) {
//        Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show();
//    }
}