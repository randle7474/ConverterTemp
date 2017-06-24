package com.salangsang.romeo.convertertemp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TempConverterActivity extends AppCompatActivity implements
        TextView.OnEditorActionListener, View.OnClickListener {


    //define variables
    private EditText fahrenheitIN;
    private TextView celsiusLabel;
    private Button resetButton;

    //define instance variable
    private String tempString = "";

    DecimalFormat df = new DecimalFormat("###.##");

    private SharedPreferences savedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //get references to the widgets
        fahrenheitIN = (EditText) findViewById(R.id.fahrenheitIN);
        celsiusLabel = (TextView) findViewById(R.id.celsiusLabel);
        resetButton = (Button) findViewById(R.id.resetButton);

        //set the listeners for the event
        fahrenheitIN.setOnEditorActionListener(this);
        resetButton.setOnClickListener(this);

        savedValue = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.resetButton:
                fahrenheitIN.setText("");
                celsiusLabel.setText("0");
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

    private void calculateAndDisplay() {

        //get temperature from user
        tempString = fahrenheitIN.getText().toString();
        double temp;
        if(tempString.equals("")) {
            temp = 0;
        }
        else
            temp = Double.parseDouble(tempString);

        //calculate temperature
        double celsius = (temp-32)/1.8;

        //format and display
        //NumberFormat temperature = NumberFormat.getCurrencyInstance();
        celsiusLabel.setText(df.format(celsius));
    }

    @Override
    protected void onPause() {

        SharedPreferences.Editor editor = savedValue.edit();
        editor.putString("tempString", tempString);
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tempString = savedValue.getString("tempString", "");
        fahrenheitIN.setText(tempString);
        calculateAndDisplay();
    }
}