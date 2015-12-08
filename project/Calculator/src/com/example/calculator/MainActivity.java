package com.example.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
 
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
 
public class MainActivity extends Activity implements OnClickListener {
 
    private TextView mCalculatorDisplay;
    private Boolean userIsInTheMiddleOfTypingANumber = false;
    private CalculatorBrain mCalculatorBrain;
    private static final String DIGITS = "0123456789.";
    private Button backSpace;
    private Button historyButton;
    String result;
    static String data;
    static ArrayList<String> resultdb= new ArrayList<String>();
 
    DecimalFormat df = new DecimalFormat("@###########");
 
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mCalculatorBrain = new CalculatorBrain();
        mCalculatorDisplay = (TextView) findViewById(R.id.textView1);
 
        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);
 
        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
 
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonToggleSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonClearMemory).setOnClickListener(this);
        findViewById(R.id.buttonAddToMemory).setOnClickListener(this);
        findViewById(R.id.buttonSubtractFromMemory).setOnClickListener(this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener(this);
        backSpace=(Button)findViewById(R.id.back);
        backSpace.setOnClickListener(this);
        historyButton=(Button)findViewById(R.id.history);
        historyButton.setOnClickListener(this);
        
        
        // The following buttons only exist in layout-land (Landscape mode) and require extra attention.
        // The messier option is to place the buttons in the regular layout too and set android:visibility="invisible".
        if (findViewById(R.id.buttonSquareRoot) != null) {
            findViewById(R.id.buttonSquareRoot).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonSquared) != null) {
            findViewById(R.id.buttonSquared).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonInvert) != null) {
            findViewById(R.id.buttonInvert).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonSine) != null) {
            findViewById(R.id.buttonSine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonCosine) != null) {
            findViewById(R.id.buttonCosine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonTangent) != null) {
            findViewById(R.id.buttonTangent).setOnClickListener(this);
        }
    }
 
    @SuppressWarnings("null")
	@Override
    public void onClick(View v) {
 
        String buttonPressed = ((Button) v).getText().toString();
 
        if (DIGITS.contains(buttonPressed)) {
 
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {
 
                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }
 
            } else {
 
                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                    // before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                } else {
                    mCalculatorDisplay.setText(buttonPressed);
                }
 
                userIsInTheMiddleOfTypingANumber = true;
            }
 
        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {
 
                mCalculatorBrain.setOperand(Double.parseDouble(mCalculatorDisplay.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }
 
            mCalculatorBrain.performOperation(buttonPressed);
            mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
            
         
        }
       // resultdb.add(String.valueOf(mCalculatorBrain.getResult()));
        
        if (v == backSpace){
        	
        	//mCalculatorDisplay = DONT_CLEAR; 
             String str=mCalculatorDisplay.getText().toString();
             if (str.length() >1 ) { 
                 str = str.substring(0, str.length() - 1);
                 mCalculatorDisplay.setText(str);
                 }
            else if (str.length() <=1 ) {
            	mCalculatorDisplay.setText("0");
        }
                
        }
        
        if(v==historyButton){
        	
        	 //resultdb.add(String.valueOf(mCalculatorBrain.getResult()));
       /* ArrayList<String> resuultdb = null;
        resuultdb.add(mCalculatorDisplay.getText().toString());*/
        	
       /* for(String res:resuultdb){
        	
       
        	data=data+"\n,"+res;
        }*/
        
        	
        	//alertfunction();
        	 //resultdb.add("skjxkls");
        	startActivity(new Intent(MainActivity.this, ResultHistoryActivity.class));
        	
        }
 
    }
 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
        resultdb.add(String.valueOf(mCalculatorBrain.getResult()));
        
    }
 
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
        
      
    }
    
}