package com.example.f1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class WordCount extends AppCompatActivity {

    // UI Components
    private TextInputEditText inputText;
    private MaterialButton btnCount;
    private TextView tvCharCount, tvWordCount, tvSentenceCount, tvParagraphCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_count);

        // ----------------------------
        // 1. TOOLBAR SETUP
        // ----------------------------
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button in toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set back button click listener to return to home
        toolbar.setNavigationOnClickListener(v -> {
            // This will act as the back button press
            onBackPressed();

            // Alternative: If you need to go to specific home activity
            // Intent homeIntent = new Intent(this, MainActivity.class);
            // homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // startActivity(homeIntent);
            // finish();
        });

        // ----------------------------
        // 2. INITIALIZE VIEWS
        // ----------------------------
        inputText = findViewById(R.id.input_text);
        btnCount = findViewById(R.id.btn_count);
        tvCharCount = findViewById(R.id.tv_char_count);
        tvWordCount = findViewById(R.id.tv_word_count);
        tvSentenceCount = findViewById(R.id.tv_sentence_count);
        tvParagraphCount = findViewById(R.id.tv_paragraph_count);

        // ----------------------------
        // 3. SET UP BUTTON LISTENER
        // ----------------------------
        btnCount.setOnClickListener(v -> countText());

        // ----------------------------
        // 4. REAL-TIME TEXT ANALYSIS
        // ----------------------------
//        inputText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // Not needed for this implementation
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // Update counts as user types
//                countText();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Not needed for this implementation
//            }
//        });
    }

    // ----------------------------
    // 5. TEXT ANALYSIS METHOD
    // ----------------------------
    /**
     * Analyzes the input text and updates all count displays
     */
    private void countText() {
        String text = inputText.getText().toString().trim();

        // Character count (including spaces and punctuation)
        int charCount = text.length();
        tvCharCount.setText(String.valueOf(charCount));

        // Word count (split by whitespace)
        int wordCount = text.isEmpty() ? 0 : text.split("\\s+").length;
        tvWordCount.setText(String.valueOf(wordCount));

        // Sentence count (split by punctuation marks)
        int sentenceCount = text.isEmpty() ? 0 : text.split("[.!?]+").length;
        tvSentenceCount.setText(String.valueOf(sentenceCount));

        // Paragraph count (split by double newlines)
        int paragraphCount = text.isEmpty() ? 0 : text.split("\n\n").length;
        tvParagraphCount.setText(String.valueOf(paragraphCount));
    }

    // ----------------------------
    // 6. BACK BUTTON HANDLING
    // ----------------------------
    /**
     * Override to handle back button press consistently with toolbar back button
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}