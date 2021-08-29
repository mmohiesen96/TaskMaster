package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.TaskItem;

import java.util.List;

public class AllTasks extends AppCompatActivity {
    private TaskAdapter taskAdapter;
    private List<Task> myTasks;
    private List<TaskItem> myTasksDb;
    private TaskDAO taskDAO;
    private TaskDB taskDB;
    private void notifyDatasetChanged() {
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        taskDB = Room.databaseBuilder(getApplicationContext() , TaskDB.class , AddTask.TASK_ITEM).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        taskDAO = taskDB.taskDAO();
        MainActivity mainActivity = new MainActivity();

        RecyclerView recyclerView = findViewById(R.id.list2);
        this.myTasksDb = mainActivity.taskListAmp;
        taskAdapter = new TaskAdapter(myTasksDb, new TaskAdapter.onClicker() {
            @Override
            public void onClickListener(int position) {
                Intent myIntent = new Intent(getApplicationContext() , TaskDetail.class);
                myIntent.putExtra("Title" , myTasksDb.get(position).getTitle());
                myIntent.putExtra("Body" , myTasksDb.get(position).getDescription());
                myIntent.putExtra("State" , myTasksDb.get(position).getStatus());
                startActivity(myIntent);

            }

            @Override
            public void onDeleteListener(int position) {
                taskDAO.delete(myTasks.get(position));
                myTasks.remove(position);
                notifyDatasetChanged();
                Toast.makeText(AllTasks.this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent deleteIntent = new Intent(AllTasks.this , MainActivity.class);
                startActivity(deleteIntent);
            }

        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);
    }
}