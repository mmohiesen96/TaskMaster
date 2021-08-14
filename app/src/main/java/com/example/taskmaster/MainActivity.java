package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private TaskAdapter taskAdapter;
    private List<Task> myTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycle_list);
        myTasks = new ArrayList<>();
        myTasks.add(new Task("Task 1 " , "My First Task"  , State.ASSIGNED));
        myTasks.add(new Task("Task 2 " , "My First Task"  , State.NEW));
        myTasks.add(new Task("Task 3 " , "My First Task"  , State.IN_PROGRESS));
        taskAdapter = new TaskAdapter(myTasks, new TaskAdapter.onClicker() {
            @Override
            public void onClickListener(int position) {
                Intent myIntent = new Intent(getApplicationContext() , TaskDetail.class);
                myIntent.putExtra("Title" , myTasks.get(position).getTitle());
                myIntent.putExtra("Body" , myTasks.get(position).getBody());
                myIntent.putExtra("State" , myTasks.get(position).getState().name());
                startActivity(myIntent);

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