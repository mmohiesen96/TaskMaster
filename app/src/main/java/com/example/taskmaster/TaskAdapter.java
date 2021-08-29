package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskItem;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.viewHolder> {
    private List<TaskItem> myTasksDb;
    private onClicker myOnClicker;
    public interface onClicker{
        void onClickListener(int position);
        void onDeleteListener(int position);
    }


    public TaskAdapter(List<TaskItem> myTasks, onClicker myOnClicker) {
        this.myTasksDb = myTasks;
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
        TaskItem task = myTasksDb.get(position);
        holder.myTask.setText(task.getTitle());
//        holder.myTask.setText(task.getBody());
//        holder.myTask.setText(task.getState());
//        holder.image.setImageResource(task.getImage());
    }


    @Override
    public int getItemCount() {
        return myTasksDb.size();
    }

    static class  viewHolder extends RecyclerView.ViewHolder{
        private TextView myTask;
        private TextView title;
        private TextView body;
        private TextView state;
        private Button delete;
        private ImageView image;
        public viewHolder(@NonNull  View itemView , onClicker onClicker) {
            super(itemView);
            myTask = itemView.findViewById(R.id.task_title);
            image = itemView.findViewById(R.id.imageView5);
//            title = itemView.findViewById(R.id.textView8);
//            body = itemView.findViewById(R.id.textView9);
//            state = itemView.findViewById(R.id.textView10);
            delete = itemView.findViewById(R.id.button10);
            myTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicker.onClickListener(getAdapterPosition());
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClicker.onDeleteListener(getAdapterPosition());
                }
            });
        }
    }
}
