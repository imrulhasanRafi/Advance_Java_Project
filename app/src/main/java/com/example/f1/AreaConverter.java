package com.example.f1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class AreaConverter extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText etValue;
    private Button btnConvert;
    private TextView tvResult;

    private final String[] units = {
            "Feet (ft)",
            "Inches (in)",
            "Meters (m)",
            "Centimeters (cm)",
            "Millimeters (mm)",
            "Kilometers (km)",
            "Yards (yd)",
            "Miles (mi)",
            "Nautical Miles (nmi)",
            "Light Years (ly)",
            "Astronomical Units (AU)",
            "Parsecs (pc)"
    };

    private final DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
    private int fromUnitPos = 0;
    private int toUnitPos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_converter);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set toolbar navigation click listener
        toolbar.setNavigationOnClickListener(v -> {
            // This will act as the back button press
            onBackPressed();
        });

        // Initialize views
        spinnerFrom = findViewById(R.id.spinner_froms);
        spinnerTo = findViewById(R.id.spinner_too);
        etValue = findViewById(R.id.et_values);
        btnConvert = findViewById(R.id.btn_converts);
        tvResult = findViewById(R.id.tv_results);

        // Setup spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Set default selections
        spinnerFrom.setSelection(fromUnitPos);
        spinnerTo.setSelection(toUnitPos);

        // Spinner listeners
        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromUnitPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toUnitPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Convert button click listener
        btnConvert.setOnClickListener(v -> convertUnits());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void convertUnits() {
        String valueStr = etValue.getText().toString().trim();

        if (valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double value = Double.parseDouble(valueStr);
            double result = convert(value, fromUnitPos, toUnitPos);

            String fromUnit = units[fromUnitPos];
            String toUnit = units[toUnitPos];

            // Format numbers to remove trailing .0
            String formattedValue = df.format(value);
            String formattedResult = df.format(result);

            tvResult.setText(String.format("%s %s = %s %s",
                    formattedValue,
                    fromUnit,
                    formattedResult,
                    toUnit));
            tvResult.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
        }
    }

    private double convert(double value, int fromUnit, int toUnit) {
        // First convert to meters (base unit)
        double meters = 0;

        switch (fromUnit) {
            case 0: // Feet
                meters = value * 0.3048;
                break;
            case 1: // Inches
                meters = value * 0.0254;
                break;
            case 2: // Meters
                meters = value;
                break;
            case 3: // Centimeters
                meters = value * 0.01;
                break;
            case 4: // Millimeters
                meters = value * 0.001;
                break;
            case 5: // Kilometers
                meters = value * 1000;
                break;
            case 6: // Yards
                meters = value * 0.9144;
                break;
            case 7: // Miles
                meters = value * 1609.344;
                break;
            case 8: // Nautical Miles
                meters = value * 1852;
                break;
            case 9: // Light Years
                meters = value * 9.461e+15;
                break;
            case 10: // Astronomical Units
                meters = value * 1.496e+11;
                break;
            case 11: // Parsecs
                meters = value * 3.086e+16;
                break;
        }

        // Then convert to target unit
        switch (toUnit) {
            case 0: // Feet
                return meters / 0.3048;
            case 1: // Inches
                return meters / 0.0254;
            case 2: // Meters
                return meters;
            case 3: // Centimeters
                return meters * 100;
            case 4: // Millimeters
                return meters * 1000;
            case 5: // Kilometers
                return meters / 1000;
            case 6: // Yards
                return meters / 0.9144;
            case 7: // Miles
                return meters / 1609.344;
            case 8: // Nautical Miles
                return meters / 1852;
            case 9: // Light Years
                return meters / 9.461e+15;
            case 10: // Astronomical Units
                return meters / 1.496e+11;
            case 11: // Parsecs
                return meters / 3.086e+16;
            default:
                return 0;
        }
    }
}