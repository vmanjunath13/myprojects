package com.example.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static double Miles_To_KMS = 1.60934;
    public static double KMS_TO_Miles = 0.621371;
    private TextView textView, textView1, textView2, result;
    private EditText input;
    private RadioGroup rg;
    private static final String blank = "";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.input_mode1);
        textView1 = findViewById(R.id.input_mode2);
        textView2 = findViewById(R.id.history);
        input = findViewById(R.id.userInput1);
        result = findViewById(R.id.userInput2);
        rg = findViewById(R.id.radioGroup);

        textView2.setMovementMethod(new ScrollingMovementMethod());

    }

    public void radioClicked(View v) {
        String selectionText = ((RadioButton) v).getText().toString();
        result.setText("");
        if (selectionText.equals("Miles to Kilometers")) {
            textView.setText("Miles Value:");
            textView1.setText("Kilometers Value:");
        } else {
            textView.setText("Kilometers Value:");
            textView1.setText("Miles Value:");
        }
    }

    public void doConvert(View v) {
        int id = rg.getCheckedRadioButtonId();
        String value = textView2.getText().toString();
        switch (id) {
            case R.id.Miles_to_Kilometers:
                if (input.getText().toString().equals("")) {
                    //Toast.makeText(getApplicationContext(), "Enter Miles", Toast.LENGTH_LONG).show();
                    //Log.v("Default", "No value in Input field");
                    input.setText(blank);
                    Log.d(TAG, "No value in Input field");
                } else {
                    double milesValue = Double.parseDouble(input.getText().toString());
                    double KMS = milesValue * Miles_To_KMS;
                    result.setText(String.format("%.1f", KMS));
                    value = milesValue + " Mi ==> " + String.format("%.1f", KMS) + " Km\n" + value;
                    textView2.setText(value);
                }
                break;
            case R.id.Kilometers_to_Miles:
                if (input.getText().toString().equals("")) {
                    input.setText(blank);
                    Log.d(TAG, "No value in Input field");
                } else {
                    double kmsValue = Double.parseDouble(input.getText().toString());
                    double MILES = kmsValue * KMS_TO_Miles;
                    result.setText(String.format("%.1f", MILES));
                    value = kmsValue + " Km ==> " + String.format("%.1f", MILES) + " Mi\n" + value;
                    textView2.setText(value);
                }
                break;
        }
        input.setText("");
    }


    public void clearHistory(View v) {
        textView2.setText("");
        result.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("TextView", textView.getText().toString());
        outState.putString("TextView1", textView1.getText().toString());
        outState.putString("HISTORY", textView2.getText().toString());

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        textView.setText(savedInstanceState.getString("TextView"));
        textView1.setText(savedInstanceState.getString("TextView1"));
        textView2.setText(savedInstanceState.getString("HISTORY"));
    }
}
