package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.taskmaster.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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