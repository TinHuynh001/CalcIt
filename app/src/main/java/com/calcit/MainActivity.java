package com.calcit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String currentScreen = "0";
    TextView tv;

    char operator = '0';
    boolean operatorFlag = false;
    int operand1 = 0;
    int operand2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.calc_screen);
    }

    public void buttonClick(View view)
    {
        if (!currentScreen.equals("0")) {
            switch (view.getId()) {
                case R.id.button_0:
                    currentScreen.concat("0");
                    break;
                case R.id.button_1:
                    currentScreen.concat("1");
                    break;
                case R.id.button_2:
                    currentScreen.concat("2");
                    break;
                case R.id.button_3:
                    currentScreen.concat("3");
                    break;
                case R.id.button_4:
                    currentScreen.concat("4");
                    break;
                case R.id.button_5:
                    currentScreen.concat("5");
                    break;
                case R.id.button_6:
                    currentScreen.concat("6");
                    break;
                case R.id.button_7:
                    currentScreen.concat("7");
                    break;
                case R.id.button_8:
                    currentScreen.concat("8");
                    break;
                case R.id.button_9:
                    currentScreen.concat("9");
                    break;
            }
            updateScreen();
        }
    }



    public void updateScreen()
    {
        tv.setText(currentScreen);

    }

    private void setOperator(char c)
    {
        operator = c;
        operatorFlag = true;
    }

    private int parseNumber(String a)
    {
        return Integer.getInteger(a);
    }
}
