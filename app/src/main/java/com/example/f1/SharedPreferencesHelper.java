package com.example.f1;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesHelper {
    private static final String PREFS_NAME = "TodoPrefs";
    private static final String TASKS_KEY = "tasks";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveTasks(List<Task> tasks) {
        String tasksJson = gson.toJson(tasks);
        sharedPreferences.edit().putString(TASKS_KEY, tasksJson).apply();
    }

    public List<Task> getTasks() {
        String tasksJson = sharedPreferences.getString(TASKS_KEY, null);
        if (tasksJson == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Task>>() {}.getType();
        return gson.fromJson(tasksJson, type);
    }
}