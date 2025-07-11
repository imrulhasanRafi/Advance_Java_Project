package com.example.f1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class TodoListActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    // UI Components
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;
    private TextView textEmptyState;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // ----------------------------
        // 1. TOOLBAR SETUP
        // ----------------------------
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button in toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set back button click listener
        toolbar.setNavigationOnClickListener(v -> {
            // Navigate back to home
            onBackPressed();
        });

        // ----------------------------
        // 2. INITIALIZE COMPONENTS
        // ----------------------------
        initializeViews();
        setupRecyclerView();
        setupFab();
    }

    /**
     * Initialize all view components
     */
    private void initializeViews() {
        recyclerViewTasks = findViewById(R.id.recycler_view_tasks);
        fabAddTask = findViewById(R.id.fab_add_task);
        textEmptyState = findViewById(R.id.text_empty_state);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
    }

    /**
     * Set up RecyclerView with task list
     */
    private void setupRecyclerView() {
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        taskList = sharedPreferencesHelper.getTasks();
        taskAdapter = new TaskAdapter(taskList, this, sharedPreferencesHelper, this);
        recyclerViewTasks.setAdapter(taskAdapter);
        updateEmptyState();
    }

    /**
     * Set up Floating Action Button for adding new tasks
     */
    private void setupFab() {
        fabAddTask.setOnClickListener(v -> showTaskDialog(-1)); // -1 indicates new task
    }

    /**
     * Show dialog for adding/editing tasks
     * @param position Position of task being edited (-1 for new task)
     */
    private void showTaskDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_task, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText editTextTask = dialogView.findViewById(R.id.edit_text_task);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        Button buttonSave = dialogView.findViewById(R.id.button_save);

        // If editing existing task, pre-fill the text
        if (position >= 0) {
            Task task = taskList.get(position);
            editTextTask.setText(task.getText());
            buttonSave.setText("UPDATE");
        }

        buttonCancel.setOnClickListener(v -> dialog.dismiss());
        buttonSave.setOnClickListener(v -> {
            String taskText = editTextTask.getText().toString().trim();
            if (!taskText.isEmpty()) {
                if (position >= 0) {
                    // Update existing task
                    Task task = taskList.get(position);
                    task.setText(taskText);
                    taskAdapter.notifyItemChanged(position);
                } else {
                    // Add new task
                    Task newTask = new Task(taskText, false);
                    taskList.add(newTask);
                    taskAdapter.notifyItemInserted(taskList.size() - 1);
                }
                sharedPreferencesHelper.saveTasks(taskList);
                updateEmptyState();
                dialog.dismiss();
            } else {
                editTextTask.setError("Task cannot be empty");
            }
        });

        dialog.show();
    }

    // ----------------------------
    // TASK ADAPTER CALLBACKS
    // ----------------------------

    @Override
    public void onEditClick(int position) {
        showTaskDialog(position);
    }

    @Override
    public void onDeleteClick(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
        sharedPreferencesHelper.saveTasks(taskList);
        updateEmptyState();
    }

    /**
     * Update empty state visibility based on task list
     */
    private void updateEmptyState() {
        if (taskList.isEmpty()) {
            recyclerViewTasks.setVisibility(View.GONE);
            textEmptyState.setVisibility(View.VISIBLE);
        } else {
            recyclerViewTasks.setVisibility(View.VISIBLE);
            textEmptyState.setVisibility(View.GONE);
        }
    }

    // ----------------------------
    // MENU/NAVIGATION HANDLING
    // ----------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button in action bar
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Handle up navigation
        onBackPressed();
        return true;
    }
}