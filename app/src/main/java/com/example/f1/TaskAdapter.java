package com.example.f1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public TaskAdapter(List<Task> taskList, Context context, SharedPreferencesHelper sharedPreferencesHelper, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.context = context;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTask.setText(task.getText());

        // Update text appearance based on completion status
        if (task.isCompleted()) {
            holder.textViewTask.setAlpha(0.5f);
            holder.textViewTask.setPaintFlags(holder.textViewTask.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.textViewTask.setAlpha(1.0f);
            holder.textViewTask.setPaintFlags(holder.textViewTask.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // Handle item click for completion toggle
        holder.itemView.setOnClickListener(v -> {
            task.setCompleted(!task.isCompleted());
            sharedPreferencesHelper.saveTasks(taskList);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        ImageButton buttonEdit, buttonDelete;

        TaskViewHolder(@NonNull View itemView, OnTaskClickListener listener) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.text_view_task);
            buttonEdit = itemView.findViewById(R.id.button_edit);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            buttonEdit.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(position);
                    }
                }
            });

            buttonDelete.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}