package com.example.f1;



import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Multi-Function App");
        }


        // Initialize CardViews
        CardView calculatorCard = findViewById(R.id.calculatorCard);
        CardView todoCard = findViewById(R.id.todoCard);
        CardView areaConverterCard = findViewById(R.id.areaConverterCard);
        CardView tempConverterCard = findViewById(R.id.tempConverterCard);
        CardView feedbackCard = findViewById(R.id.feedbackCard);
        CardView wordCounterCard = findViewById(R.id.wordCounterCard);

        // Set click listeners for each CardView
        calculatorCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Calculator_Activity.class);
            startActivity(intent);
        });

        todoCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TodoListActivity.class);
            startActivity(intent);
        });

        areaConverterCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AreaConverter.class);
            startActivity(intent);
        });

        tempConverterCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TemperatureConverter.class);
            startActivity(intent);
        });

        feedbackCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
            startActivity(intent);
        });

        wordCounterCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WordCount.class);
            startActivity(intent);
        });





    }





}