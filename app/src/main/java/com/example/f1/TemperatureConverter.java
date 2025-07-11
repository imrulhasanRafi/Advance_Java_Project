package com.example.f1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DecimalFormat;

public class TemperatureConverter extends AppCompatActivity {

    private TextInputEditText inputTemperature;
    private RadioGroup fromGroup, toGroup;
    private MaterialButton convertButton;
    private TextView resultText, formulaText;
    private MaterialToolbar toolbar;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_converter);

        // Initialize toolbar
        toolbar = findViewById(R.id.toolbart);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            // Go back to previous activity
            onBackPressed();

            // OR go to specific home activity (uncomment one)
            // startActivity(new Intent(this, MainActivity.class));
            // finish();
        });

        // Initialize views
        inputTemperature = findViewById(R.id.inputTemperature);
        fromGroup = findViewById(R.id.fromGroup);
        toGroup = findViewById(R.id.toGroup);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);
        formulaText = findViewById(R.id.formulaText);

        // Set default selections
        ((RadioButton)findViewById(R.id.fromCelsius)).setChecked(true);
        ((RadioButton)findViewById(R.id.toFahrenheit)).setChecked(true);

        convertButton.setOnClickListener(v -> convertTemperature());

        inputTemperature.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) convertTemperature();
                else clearResults();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        fromGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (inputTemperature.getText().length() > 0) convertTemperature();
        });

        toGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (inputTemperature.getText().length() > 0) convertTemperature();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void convertTemperature() {
        try {
            String inputStr = inputTemperature.getText().toString();
            if (inputStr.isEmpty()) {
                clearResults();
                return;
            }

            double inputTemp = Double.parseDouble(inputStr);
            String from = getSelectedFromUnit();
            String to = getSelectedToUnit();

            if (from.equals(to)) {
                resultText.setText(inputStr + " " + to);
                formulaText.setText("Same units - no conversion needed");
                return;
            }

            double result = 0;
            String formula = "";

            // Conversion logic
            double celsius;
            switch (from) {
                case "Celsius":
                    celsius = inputTemp;
                    formula = "Celsius → ";
                    break;
                case "Fahrenheit":
                    celsius = (inputTemp - 32) * 5/9;
                    formula = "(°F - 32) × 5/9 = °C → ";
                    break;
                case "Kelvin":
                    celsius = inputTemp - 273.15;
                    formula = "K - 273.15 = °C → ";
                    break;
                default:
                    celsius = 0;
            }

            switch (to) {
                case "Celsius":
                    result = celsius;
                    formula += "°C";
                    break;
                case "Fahrenheit":
                    result = (celsius * 9/5) + 32;
                    formula += "(°C × 9/5) + 32 = °F";
                    break;
                case "Kelvin":
                    result = celsius + 273.15;
                    formula += "°C + 273.15 = K";
                    break;
            }

            resultText.setText(decimalFormat.format(result) + " " + to);
            formulaText.setText(formula);

        } catch (NumberFormatException e) {
            resultText.setText("Invalid input");
            formulaText.setText("");
        }
    }

    private String getSelectedFromUnit() {
        int selectedId = fromGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.fromCelsius) {
            return "Celsius";
        } else if (selectedId == R.id.fromFahrenheit) {
            return "Fahrenheit";
        } else if (selectedId == R.id.fromKelvin) {
            return "Kelvin";
        }
        return "Celsius"; // default return
    }

    private String getSelectedToUnit() {
        int selectedId = toGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.toCelsius) {
            return "Celsius";
        } else if (selectedId == R.id.toFahrenheit) {
            return "Fahrenheit";
        } else if (selectedId == R.id.toKelvin) {
            return "Kelvin";
        }
        return "Fahrenheit"; // default return
    }

    private void clearResults() {
        resultText.setText("");
        formulaText.setText("");
    }
}


















//package com.example.f1;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.os.Bundle;
//import com.google.android.material.appbar.MaterialToolbar;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.content.Intent;
//import android.view.MenuItem;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.appbar.MaterialToolbar;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputEditText;
//import java.text.DecimalFormat;
//
//public class TemperatureConverter extends AppCompatActivity {
//
//
//    MaterialToolbar toolbar = findViewById(R.id.toolbart);
//
//
//
//
//
//
//    private TextInputEditText inputTemperature;
//    private RadioGroup fromGroup, toGroup;
//    private MaterialButton convertButton;
//    private TextView resultText, formulaText;
//
//    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_temperature_converter);
//
//        // Initialize views
//        inputTemperature = findViewById(R.id.inputTemperature);
//        fromGroup = findViewById(R.id.fromGroup);
//        toGroup = findViewById(R.id.toGroup);
//        convertButton = findViewById(R.id.convertButton);
//        resultText = findViewById(R.id.resultText);
//        formulaText = findViewById(R.id.formulaText);
//
//        // Set default selections
//        ((RadioButton)findViewById(R.id.fromCelsius)).setChecked(true);
//        ((RadioButton)findViewById(R.id.toFahrenheit)).setChecked(true);
//
//        // Convert button click listener
//        convertButton.setOnClickListener(v -> convertTemperature());
//
//        // Real-time conversion as user types
//        inputTemperature.addTextChangedListener(new TextWatcher() {
//            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 0) convertTemperature();
//                else clearResults();
//            }
//            @Override public void afterTextChanged(Editable s) {}
//        });
//
//        // Convert when units change
//        fromGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (inputTemperature.getText().length() > 0) convertTemperature();
//        });
//
//        toGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (inputTemperature.getText().length() > 0) convertTemperature();
//        });
//    }
//
//    private void convertTemperature() {
//        try {
//            String inputStr = inputTemperature.getText().toString();
//            if (inputStr.isEmpty()) {
//                clearResults();
//                return;
//            }
//
//            double inputTemp = Double.parseDouble(inputStr);
//            String from = getSelectedFromUnit();
//            String to = getSelectedToUnit();
//
//            // Don't convert if same units
//            if (from.equals(to)) {
//                resultText.setText(inputStr + " " + to);
//                formulaText.setText("Same units - no conversion needed");
//                return;
//            }
//
//            double result = 0;
//            String formula = "";
//
//            // Convert to Celsius first
//            double celsius;
//            switch (from) {
//                case "Celsius":
//                    celsius = inputTemp;
//                    formula = "Celsius → ";
//                    break;
//                case "Fahrenheit":
//                    celsius = (inputTemp - 32) * 5/9;
//                    formula = "(°F - 32) × 5/9 = °C → ";
//                    break;
//                case "Kelvin":
//                    celsius = inputTemp - 273.15;
//                    formula = "K - 273.15 = °C → ";
//                    break;
//                default:
//                    celsius = 0;
//            }
//
//            // Convert from Celsius to target unit
//            switch (to) {
//                case "Celsius":
//                    result = celsius;
//                    formula += "°C";
//                    break;
//                case "Fahrenheit":
//                    result = (celsius * 9/5) + 32;
//                    formula += "(°C × 9/5) + 32 = °F";
//                    break;
//                case "Kelvin":
//                    result = celsius + 273.15;
//                    formula += "°C + 273.15 = K";
//                    break;
//            }
//
//            // Display results
//            resultText.setText(decimalFormat.format(result) + " " + to);
//            formulaText.setText(formula);
//
//        } catch (NumberFormatException e) {
//            resultText.setText("Invalid input");
//            formulaText.setText("");
//        }
//    }
//
//    private String getSelectedFromUnit() {
//        int selectedId = fromGroup.getCheckedRadioButtonId();
//
//        if (selectedId == R.id.fromCelsius) return "Celsius";
//        if (selectedId == R.id.fromFahrenheit) return "Fahrenheit";
//        if (selectedId == R.id.fromKelvin) return "Kelvin";
//
//        return "Celsius"; // default
//    }
//
//    private String getSelectedToUnit() {
//        int selectedId = toGroup.getCheckedRadioButtonId();
//
//        if (selectedId == R.id.toCelsius) return "Celsius";
//        if (selectedId == R.id.toFahrenheit) return "Fahrenheit";
//        if (selectedId == R.id.toKelvin) return "Kelvin";
//
//        return "Fahrenheit"; // default
//    }
//
//    private void clearResults() {
//        resultText.setText("");
//        formulaText.setText("");
//    }
//}
//
//
//
//
//
//
//
