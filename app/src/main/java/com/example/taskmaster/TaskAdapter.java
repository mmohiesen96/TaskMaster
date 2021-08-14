package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.viewHolder> {
    private List<Task> myTasks;
    private onClicker myOnClicker;
    public interface onClicker{
        void onClickListener(int position);
    }

    public TaskAdapter(List<Task> myTasks, onClicker myOnClicker) {
        this.myTasks = myTasks;
        this.myOnClicker = myOnClicker;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout , parent , false);
        return new viewHolder(view , myOnClicker);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.viewHolder holder, int position) {
        Task task = myTasks.get(position);
        holder.myTask.setText(task.getTitle());
    }


    @Override
    public int getItemCount() {
        return myTasks.size();
    }

    static class  viewHolder extends RecyclerView.ViewHolder{
        private TextView myTask;

        public viewHolder(@NonNull  View itemView , onClicker onClicker) {
            super(itemView);
            myTask = itemView.findViewById(R.id.task_title);
            myTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicker.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}
