package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    private AppBarConfiguration appBarConfiguration;
    private TaskAdapter taskAdapter;
    private List<Task> myTasks;
    private TaskDAO taskDAO;
    private TaskDB taskDB;
    private Handler handler;
    @SuppressLint("NotifyDataSetChanged")
    private void notifyDatasetChanged() {
        taskAdapter.notifyDataSetChanged();
    }
    private void getTaskDataFromAPI() {
        List<Task> taskItemList = new ArrayList<>();
        Amplify.API.query(ModelQuery.list(TaskItem.class),
                response -> {
                    for (TaskItem task : response.getData()) {
                        taskItemList.get().add(new Task(task.getTitle(), task.getDescription(), task.getStatus()));
                        Log.i(TAG, "onCreate: the tasks are => " + task.getTitle());
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> {
                    Log.e(TAG, "onCreate: Failed to get tasks => " + error.toString());
                    taskItemList = showTasksSavedInDataBase();
                    handler.sendEmptyMessage(1);
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.list1);
        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                return false;
            }
        });
        taskDB = Room.databaseBuilder(getApplicationContext() , TaskDB.class , AddTask.TASK_ITEM).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        taskDAO = taskDB.taskDAO();
        myTasks = taskDAO.findAll();
        taskAdapter = new TaskAdapter(myTasks, new TaskAdapter.onClicker() {
            @Override
            public void onClickListener(int position) {
                Intent myIntent = new Intent(getApplicationContext() , TaskDetail.class);
                myIntent.putExtra("Title" , myTasks.get(position).getTitle());
                myIntent.putExtra("Body" , myTasks.get(position).getBody());
                myIntent.putExtra("State" , myTasks.get(position).getState());
                startActivity(myIntent);

            }

            @Override
            public void onDeleteListener(int position) {
                taskDAO.delete(myTasks.get(position));
                myTasks.remove(position);
                notifyDatasetChanged();
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public void toAddTask(View view) {
        Intent addIntent = new Intent(this , AddTask.class);
        startActivity(addIntent);
    }

    public void toAllTasks(View view) {
        Intent allIntent = new Intent(this , AllTasks.class);
        startActivity(allIntent);
    }

    public void toSettings(View view) {
        Intent settingsIntent = new Intent(this , Settings.class);
        startActivity(settingsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView homeText = findViewById(R.id.textView);
        homeText.setText(sharedPreferences.getString("username" , "User's") + " Tasks");
    }

    public void onEat(View view) {
        Intent detailIntent = new Intent(this , TaskDetail.class);
        detailIntent.putExtra("task" , "Eat task");
        startActivity(detailIntent);
    }

    public void onCode(View view) {
        Intent detailIntent = new Intent(this , TaskDetail.class);
        detailIntent.putExtra("task" , "Code task");
        startActivity(detailIntent);
    }

    public void onSleep(View view) {
        Intent detailIntent = new Intent(this , TaskDetail.class);
        detailIntent.putExtra("task" , "Sleep task");
        startActivity(detailIntent);
    }
}