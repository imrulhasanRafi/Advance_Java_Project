package com.example.f1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.text.DecimalFormat;

public class Calculator_Activity extends AppCompatActivity {

    // UI Components
    private TextView primaryScreen;    // Main input display (id: screen)
    private TextView secondaryScreen;  // Secondary result display (id: secondary_screen)
    private ImageButton backButton;    // Back button at top left

    // Number buttons
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;

    // Operation buttons
    private Button ac, del, percent, dot, div, mul, sub, add, eql, doubleZero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator); // Make sure this matches your XML file name

        // Initialize all views
        initializeViews();

        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        // Initialize TextViews
        primaryScreen = findViewById(R.id.screen);
        secondaryScreen = findViewById(R.id.secondary_screen);
        backButton = findViewById(R.id.backButton);

        // Initialize number buttons
        num0 = findViewById(R.id.num0);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);

        // Initialize operation buttons
        ac = findViewById(R.id.ac);
        del = findViewById(R.id.del);
        percent = findViewById(R.id.percent);
        dot = findViewById(R.id.dot);
        div = findViewById(R.id.div);
        mul = findViewById(R.id.mul);
        sub = findViewById(R.id.sub);
        add = findViewById(R.id.add);
        eql = findViewById(R.id.eql);
        doubleZero = findViewById(R.id.doublezero);
    }

    private void setupClickListeners() {
        // Back button - closes the activity
        backButton.setOnClickListener(v -> finish());

        // Number buttons (0-9)
        View.OnClickListener numberListener = v -> {
            Button button = (Button) v;
            String currentText = primaryScreen.getText().toString();

            if (currentText.equals("0")) {
                primaryScreen.setText(button.getText());
            } else {
                primaryScreen.append(button.getText());
            }
        };

        num0.setOnClickListener(numberListener);
        num1.setOnClickListener(numberListener);
        num2.setOnClickListener(numberListener);
        num3.setOnClickListener(numberListener);
        num4.setOnClickListener(numberListener);
        num5.setOnClickListener(numberListener);
        num6.setOnClickListener(numberListener);
        num7.setOnClickListener(numberListener);
        num8.setOnClickListener(numberListener);
        num9.setOnClickListener(numberListener);

        // Double zero button
        doubleZero.setOnClickListener(v -> {
            String currentText = primaryScreen.getText().toString();
            if (!currentText.equals("0")) {
                primaryScreen.append("00");
            }
        });

        // AC (All Clear) button
        ac.setOnClickListener(v -> {
            primaryScreen.setText("0");
            secondaryScreen.setText("");
        });

        // DEL (Delete) button
        del.setOnClickListener(v -> {
            String currentText = primaryScreen.getText().toString();
            if (currentText.length() > 1) {
                primaryScreen.setText(currentText.substring(0, currentText.length() - 1));
            } else {
                primaryScreen.setText("0");
            }
        });

        // Operation buttons (+, -, ×, ÷, .)
        View.OnClickListener operatorListener = v -> {
            Button button = (Button) v;
            String currentText = primaryScreen.getText().toString();
            String operator = button.getText().toString();

            // Don't allow operator as first character (except minus)
            if (currentText.equals("0") && !operator.equals("-")) {
                return;
            }

            // Replace the last operator if new one is pressed (except minus)
            char lastChar = currentText.charAt(currentText.length() - 1);
            if (isOperator(lastChar)){
                if (!operator.equals("-")) {
                    primaryScreen.setText(currentText.substring(0, currentText.length() - 1) + operator);
                }
            }else{
                primaryScreen.append(operator);
            }
        };

        add.setOnClickListener(operatorListener);
        sub.setOnClickListener(operatorListener);
        mul.setOnClickListener(operatorListener);
        div.setOnClickListener(operatorListener);
        dot.setOnClickListener(operatorListener);

        // Percentage button (special handling)
        percent.setOnClickListener(v -> {
            String currentText = primaryScreen.getText().toString();
            if (!currentText.equals("0")) {
                primaryScreen.append("%");
            }
        });

        // Equal button - performs calculation
        eql.setOnClickListener(v -> calculateResult());
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '.' || c == '%';
    }

    private void calculateResult() {
        try {
            String expression = primaryScreen.getText().toString()
                    .replace("×", "*")
                    .replace("÷", "/")
                    .replace("%", "*0.01"); // Convert % to decimal multiplier

            // Evaluate the expression
            double result = new ExpressionBuilder(expression).build().evaluate();

            // Format the result
            DecimalFormat df = new DecimalFormat("#.#######");
            String formattedResult = df.format(result);

            // Remove trailing .0 if it's an integer
            if (formattedResult.endsWith(".0")) {
                formattedResult = formattedResult.substring(0, formattedResult.length() - 2);
            }

            // Display result in secondary screen
            secondaryScreen.setText(formattedResult);

        } catch (Exception e) {
            secondaryScreen.setText("Error");
        }
    }
}











//package com.example.f1;
//import net.objecthunter.exp4j.Expression;
//import net.objecthunter.exp4j.ExpressionBuilder;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import java.text.DecimalFormat;
//
//
//public class Calculator_Activity extends AppCompatActivity {
//
//    // Declare UI components
//    private TextView screen;
//    private Button on, off, ac, del, eql;
//    private Button[] numbers = new Button[10]; // Array for number buttons 0-9
//    private Button[] operators; // Array for operator buttons
//    private boolean isOn = false; // Track if calculator is on/off
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calculator); // Set the layout
//
//        // Initialize UI components
//        initializeViews();
//
//        // Set up button click listeners
//        setupListeners();
//
//        // Initialize calculator to off state
//
//    }
//
//    // Initialize all views from the layout
//    private void initializeViews() {
//        screen = findViewById(R.id.screen);
//
//        // Power buttons
//
//        ac = findViewById(R.id.ac);
//        del = findViewById(R.id.del);
//        eql = findViewById(R.id.eql);
//
//        // Number buttons
//        numbers[0] = findViewById(R.id.num0);
//        numbers[1] = findViewById(R.id.num1);
//        numbers[2] = findViewById(R.id.num2);
//        numbers[3] = findViewById(R.id.num3);
//        numbers[4] = findViewById(R.id.num4);
//        numbers[5] = findViewById(R.id.num5);
//        numbers[6] = findViewById(R.id.num6);
//        numbers[7] = findViewById(R.id.num7);
//        numbers[8] = findViewById(R.id.num8);
//        numbers[9] = findViewById(R.id.num9);
//
//        // Operator buttons
//        Button div = findViewById(R.id.div);
//        Button mul = findViewById(R.id.mul);
//        Button sub = findViewById(R.id.sub);
//        Button add = findViewById(R.id.add);
//        Button dot = findViewById(R.id.dot);
//
//        operators = new Button[]{div, mul, sub, add, dot};
//    }
//
//    // Set up all button click listeners
//    private void setupListeners() {
//        // ON button - turns calculator on
//        on.setOnClickListener(v -> {
//            isOn = true;
//            screen.setText("0");
//            enableButtons(true);
//        });
//
//        // OFF button - turns calculator off
//
//
//        // AC (All Clear) button - clears everything
//        ac.setOnClickListener(v -> {
//            if (isOn) {
//                screen.setText("0");
//            }
//        });
//
//        // DEL (Delete) button - removes last character
//        del.setOnClickListener(v -> {
//            if (isOn) {
//                String currentText = screen.getText().toString();
//                if (currentText.length() > 1) {
//                    screen.setText(currentText.substring(0, currentText.length() - 1));
//                } else {
//                    screen.setText("0");
//                }
//            }
//        });
//
//        // Number buttons (0-9)
//        for (int i = 0; i < numbers.length; i++) {
//            final int number = i;
//            numbers[i].setOnClickListener(v -> {
//                if (isOn) {
//                    String currentText = screen.getText().toString();
//                    if (currentText.equals("0")) {
//                        screen.setText(String.valueOf(number));
//                    } else {
//                        screen.append(String.valueOf(number));
//                    }
//                }
//            });
//        }
//
//        // Operator buttons (+, -, ×, ÷, .)
//        for (Button operator : operators) {
//            operator.setOnClickListener(v -> {
//                if (isOn) {
//                    String currentText = screen.getText().toString();
//                    String operatorText = operator.getText().toString();
//
//                    // Replace × with * for calculation
//                    if (operatorText.equals("×")) {
//                        operatorText = "*";
//                    }
//
//                    // Replace ÷ with / for calculation
//                    if (operatorText.equals("÷")) {
//                        operatorText = "/";
//                    }
//
//                    // Don't allow multiple operators in a row
//                    char lastChar = currentText.charAt(currentText.length() - 1);
//                    if (!isOperator(lastChar)) {
//                        screen.append(operator.getText().toString());
//                    }
//                }
//            });
//        }
//
//        // Equal button - performs calculation
//        eql.setOnClickListener(v -> {
//            if (isOn) {
//                calculateResult();
//            }
//        });
//    }
//
//    // Turn off the calculator (disable all buttons except ON)
//
//
//    // Enable or disable all calculator buttons
//    private void enableButtons(boolean enable) {
//        for (Button number : numbers) {
//            number.setEnabled(enable);
//        }
//
//        for (Button operator : operators) {
//            operator.setEnabled(enable);
//        }
//
//        ac.setEnabled(enable);
//        del.setEnabled(enable);
//        eql.setEnabled(enable);
//        off.setEnabled(enable);
//    }
//
//    // Check if a character is an operator
//    private boolean isOperator(char c) {
//        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '.';
//    }
//
//    // Calculate the result using ScriptEngine
//
//
//    private void calculateResult() {
//        try {
//            String expression = screen.getText().toString()
//                    .replace("×", "*")
//                    .replace("÷", "/");
//
//            double result = new ExpressionBuilder(expression).build().evaluate();
//
//            DecimalFormat df = new DecimalFormat("#.#######"); // Up to 7 decimal places
//            String formattedResult = df.format(result);
//
//            // Remove trailing ".0" if it exists
//            if (formattedResult.endsWith(".0")) {
//                formattedResult = formattedResult.substring(0, formattedResult.length() - 2);
//            }
//
//            screen.setText(formattedResult);
//        } catch (Exception e) {
//            screen.setText("Error");
//        }
//    }
//
//
//
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
