package com.calcit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        //receive input
        char in = 'n';
        switch (view.getId()) {
            case R.id.button_0:
                in = '0';
                break;
            case R.id.button_1:
                in = '1';
                break;
            case R.id.button_2:
                in = '2';
                break;
            case R.id.button_3:
                in = '3';
                break;
            case R.id.button_4:
                in = '4';
                break;
            case R.id.button_5:
                in = '5';
                break;
            case R.id.button_6:
                in = '6';
                break;
            case R.id.button_7:
                in = '7';
                break;
            case R.id.button_8:
                in = '8';
                break;
            case R.id.button_9:
                in = '9';
                break;
            case R.id.buttonPlus:
                in = '+';
                break;
            case R.id.buttonMinus:
                in = '-';
                break;
            case R.id.buttonMultiply:
                in = '*';
                break;
            case R.id.buttonDivision:
                in = '/';
                break;
            case R.id.buttonSin:
                in = 's';
                break;
            case R.id.buttonCos:
                in = 'c';
                break;
            case R.id.buttonTan:
                in = 't';
                break;
            case R.id.buttonCalc:
                in = '=';
                break;
            case R.id.buttonReset:
                in = 'r';
                break;

        }

        //if this is a digit, it is probably a number the user is typing in
          // check if the current value on screen is 0 or not,
          //if 0
        if(Character.isDigit(in))
        {
            if (currentScreen.equals("0"))
            {
                currentScreen = Character.toString(in);

            }
            else
            {
                currentScreen += Character.toString(in);
            }

            Log.d("VALUE",   "currentScreen is now " + currentScreen);
            updateScreen();
        }
        else
        {
            //since the user input is strictly controlled through the buttons
            //we can assume all non-digit input at this can only be operators
            switch (in)
            {
                case '=':
                    //user has decide to execute the operation
                    //first we parse the second operand in
                    operand2 = parseScreen();

                    //then calculation can be started
                    //if we save the result in op1, user can later chain the result into another operation
                    operand1 = doMath(operand1, operand2, operator);

                    //print on screen
                    currentScreen = Integer.toString(operand1);
                    updateScreen();

                    //reset operand2
                    operand2 = 0;

                    break;

                case 'r':
                    reset();
                    updateScreen();
                    break;

                case '+':
                case '-':
                case '*':
                case '/':
                    operand1 = parseScreen();
                    setOperator(in);
                    clearScreen();
                    break;

                case 's':
                case 'c':
                case 't':
                    operand1 = parseScreen();
                    operand2 = 0;
                    setOperator(in);
                    clearScreen();
                    break;
            }
        }


    }

    private void reset()
    {
        operand1 =0;
        operand2 =0;

        operator = '0';
        operatorFlag = false;

        clearScreen();
    }


    private int doMath(int op1, int op2, char optor)
    {
        int ret =0;

        switch (optor)
        {
            case '+':
                ret = op1 + op2;
                break;
            case '-':
                ret = op1 - op2;
                break;
            case '*':
                ret = op1 * op2;
                break;
            case '/':
                ret = (int) (op1 / op2);
                break;
            case 's':
                ret = (int) Math.sin(op1);
            case 'c':
                ret = (int) Math.cos(op1);
            case 't':
                ret = (int) Math.tan(op1);
        }

        operatorFlag = false;
        return ret;
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

    private int parseScreen()
    {
        return Integer.parseInt(currentScreen);
    }

    private void clearScreen()
    {
        currentScreen = "0";
    }
}
