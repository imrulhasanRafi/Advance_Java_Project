package com.example.f1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class FeedbackActivity extends AppCompatActivity {

    // UI Components
    private TextInputEditText nameEditText, contactEditText, messageEditText;
    private int selectedRating = 0; // Stores the current star rating (0-5)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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

        // Set back button click listener
        toolbar.setNavigationOnClickListener(v -> {
            // Navigate back to home page
            onBackPressed(); // Or use finish() if simple back navigation is needed

            // Alternative: If you need to go to specific home activity
            // Intent homeIntent = new Intent(this, MainActivity.class);
            // homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // startActivity(homeIntent);
            // finish();
        });

        // ----------------------------
        // 2. INITIALIZE VIEWS
        // ----------------------------
        nameEditText = findViewById(R.id.nameEditText);
        contactEditText = findViewById(R.id.contactEditText);
        messageEditText = findViewById(R.id.messageEditText);

        // ----------------------------
        // 3. SET UP SUBMIT BUTTON
        // ----------------------------
        findViewById(R.id.submitButton).setOnClickListener(v -> submitFeedback());
    }

    // ----------------------------
    // 4. STAR RATING HANDLER
    // ----------------------------
    /**
     * Handles star rating selection
     * @param view The clicked star ImageView
     */
    public void onStarClicked(View view) {
        // Get the tag of the clicked star (1-5)
        int starNumber = Integer.parseInt(view.getTag().toString());

        // Update the selected rating
        selectedRating = starNumber;

        // Update all star images to reflect current selection
        for (int i = 1; i <= 5; i++) {
            ImageView star = findViewById(getResources().getIdentifier(
                    "star" + i, "id", getPackageName()));

            if (i <= starNumber) {
                // Fill stars up to the selected one
                star.setImageResource(R.drawable.ic_star_filled);
                star.setColorFilter(getResources().getColor(R.color.primary_color));
            } else {
                // Outline remaining stars
                star.setImageResource(R.drawable.ic_star_outline);
                star.setColorFilter(getResources().getColor(R.color.on_surface_color));
            }
        }
    }

    // ----------------------------
    // 5. FEEDBACK SUBMISSION
    // ----------------------------
    /**
     * Validates and processes the feedback form submission
     */
    private void submitFeedback() {
        // Get input values
        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        // ----------------------------
        // INPUT VALIDATION
        // ----------------------------
        if (name.isEmpty()) {
            nameEditText.setError("Please enter your name");
            nameEditText.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            contactEditText.setError("Please enter your contact info");
            contactEditText.requestFocus();
            return;
        }

        if (message.isEmpty()) {
            messageEditText.setError("Please enter your feedback");
            messageEditText.requestFocus();
            return;
        }

        if (selectedRating == 0) {
            Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show();
            return;
        }

        // ----------------------------
        // FEEDBACK PROCESSING
        // ----------------------------
        // In a real app, you would send this data to your server here
        // For demonstration, we'll just show a confirmation message

        String feedbackMessage = String.format(
                "Thank you, %s!\nRating: %d/5\nWe appreciate your feedback!",
                name, selectedRating);

        Toast.makeText(this, feedbackMessage, Toast.LENGTH_LONG).show();

        // Clear the form after submission
        resetForm();
    }

    // ----------------------------
    // 6. FORM RESET
    // ----------------------------
    /**
     * Resets the feedback form to its initial state
     */
    private void resetForm() {
        // Clear text fields
        nameEditText.setText("");
        contactEditText.setText("");
        messageEditText.setText("");

        // Reset rating
        selectedRating = 0;

        // Reset all stars to outline state
        for (int i = 1; i <= 5; i++) {
            ImageView star = findViewById(getResources().getIdentifier(
                    "star" + i, "id", getPackageName()));
            star.setImageResource(R.drawable.ic_star_outline);
            star.setColorFilter(getResources().getColor(R.color.on_surface_color));
        }
    }

    // ----------------------------
    // 7. BACK BUTTON HANDLING
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