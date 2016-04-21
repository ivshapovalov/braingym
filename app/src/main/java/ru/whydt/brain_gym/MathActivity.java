package ru.whydt.brain_gym;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MathActivity extends AppCompatActivity {
    private final int mCountExamples = 48;
    private Chronometer mChronometer;
    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private SharedPreferences mSettings;
    private int MathMaxDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_math);


//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//
//        setContentView(R.layout.activity_main);
//
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
    }

    public void mathForm_onClick(View view) {
        updateForm();
    }

    private void updateForm() {
        mathClear();
        getPreferencesFromFile();
        ArrayList<MathExample> mathExamples = createMathExamples();
        drawMathTest(mathExamples);
    }

    private void drawMathTest(ArrayList<MathExample> mathExamples) {
        //для математических тестов id начинается со ста
        int mCountStrings = 16;
        MathExample currentEx;

        FrameLayout layout = (FrameLayout) findViewById(R.id.frame);
        int mHeight = 0;
        try {
            mHeight = layout.getHeight() * 98 / 100 / mCountStrings;
        } catch (Exception e) {
            mHeight=0;
        }
        int mWidth = layout.getWidth() * 90 / 100 / (mCountExamples / mCountStrings);
        int numColumn = 0;
        int numString = 0;
        for (Integer i = 1; i <= mCountExamples; i++) {
            TextView txt = (TextView) findViewById(100 + i);
            if (txt == null) {
                txt = new TextView(this);

                txt.setId(100 + i);
                numColumn = (i - 1) / mCountStrings;
                numString = (i - 1) % mCountStrings;
                txt.setPadding((numColumn) * mWidth + layout.getWidth() / 100 * 10, layout.getHeight() * 2 / 100 + numString * mHeight, 0, 0);
                layout.addView(txt);
            } else {
                txt.setVisibility(View.VISIBLE);
            }
            currentEx = mathExamples.get(i - 1);
            String temp = String.valueOf(currentEx.getNum1()) + String.valueOf(currentEx.getOper()) + String.valueOf(currentEx.getNum2()) + " = ";
            txt.setText(temp);
            txt.setTextSize(Math.min(mWidth, mHeight) /3*2/getApplicationContext().getResources().getDisplayMetrics().density);
            //txt.setTextSize(Math.min(mWidth, mHeight) / 6);
            //txt.setBackgroundColor(0xfff00000);

            txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void mathClear_onClick(View view) {

        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.ground1);
        //mathClear();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;

        ChangeButtonText("buttonMathStartPause", "Старт");

    }

    private void mathClear() {

        for (int i = 1; i <= mCountExamples; i++) {
            TextView txt = (TextView) findViewById(100 + i);

            if (txt != null) {
                txt.setText("");
            }

        }

    }

    private ArrayList<MathExample> createMathExamples() {
        ArrayList<MathExample> mathExamples = new ArrayList<>();
        int num1;
        int num2 = 0;

        //генерация операции
        //1	: - деление
        //2	- - вычитание
        //3	+ - сложение
        //4	* - умножение

        while (mathExamples.size() != mCountExamples) {
            Random random = new Random();
            int indexAlg = random.nextInt(4) + 1;
            int maxNum = 0;
            switch (indexAlg) {
                case 1:
                    maxNum = 10;
                    break;
                case 2:
                    maxNum = 30;
                    break;
                case 3:
                    maxNum = 50;
                    break;
                case 4:
                    maxNum = 100;
                    break;
            }
            int indOper;

            indOper = random.nextInt(4) + 1;
            num1 = 1 + random.nextInt(maxNum);
            boolean numberIsFound = false;
            MathExample newEx = new MathExample();
            switch (indOper) {
                case 1:
                    while (!numberIsFound) {
                        num2 = 1 + random.nextInt(MathMaxDigit);
                        if (num1 % num2 == 0) {
                            numberIsFound = true;
                        }
                    }

                    newEx.setNum1(num1);
                    newEx.setNum2(num2);
                    newEx.setOper(" : ");
                    mathExamples.add(newEx);
                    break;
                case 2:
                    while (!numberIsFound) {
                        num2 = 1 + random.nextInt(MathMaxDigit);
                        if (num1 - num2 >= 0) {
                            numberIsFound = true;
                        }
                    }
                    newEx.setNum1(num1);
                    newEx.setNum2(num2);
                    newEx.setOper(" - ");
                    mathExamples.add(newEx);
                    break;
                case 3:
                    while (!numberIsFound) {
                        num2 = 1 + random.nextInt(MathMaxDigit);
                        if (num1 + num2 <= MathMaxDigit) {
                            numberIsFound = true;
                        }
                    }
                    newEx.setNum1(num1);
                    newEx.setNum2(num2);
                    newEx.setOper(" + ");
                    mathExamples.add(newEx);
                    break;
                case 4:
                    while (!numberIsFound) {
                        num2 = 1 + random.nextInt(MathMaxDigit);
                        if (num1 * num2 <= MathMaxDigit) {
                            numberIsFound = true;
                        }
                    }
                    newEx.setNum1(num1);
                    newEx.setNum2(num2);
                    newEx.setOper("* ");
                    mathExamples.add(newEx);
                    break;
                case 5:
                    while (!numberIsFound) {
                        num2 = 1 + random.nextInt(MathMaxDigit);
                        if (num1 * num2 <= MathMaxDigit) {
                            numberIsFound = true;
                        }
                    }
                    newEx.setNum1(num1);
                    newEx.setNum2(num2);
                    newEx.setOper("* ");
                    mathExamples.add(newEx);
                    break;
            }

        }

        return mathExamples;
    }

    public void startPause_onClick(View view) {

        start_pause();

    }

    private void start_pause() {
        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());
            } else {
                mChronometer.setBase(SystemClock.elapsedRealtime() - mChronometerCount);
            }

            mChronometer.start();
            mChronometerIsWorking = true;

            ChangeButtonText("buttonMathStartPause", "Пауза");


        } else {

            //mChronometerBase=mChronometer.getBase();
            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;
            ChangeButtonText("buttonMathStartPause", "Старт");
        }
    }


    private void ChangeButtonText(String ButtonID, String ButtonText) {

        int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        Button but = (Button) findViewById(resID);
        if (but != null) {
            but.setText(ButtonText);
        }
    }

    public void mathOptions_onClick(View view) {

        Intent intent = new Intent(MathActivity.this, MathActivityOptions.class);
        startActivity(intent);


    }

    public void buttonHome_onClick(View view) {

//        Intent intent = new Intent(MathActivity.this, MainActivity.class);
//        startActivity(intent);
//        this.finish();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void getPreferencesFromFile() {
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT)) {
            // Получаем язык из настроек
            MathMaxDigit = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT, 150);
        } else {
            MathMaxDigit = 150;
        }
    }

    private class MathExample {
        private int num1;
        private int num2;
        private String oper;

        public int getNum1() {
            return num1;
        }

        public void setNum1(int num1) {
            this.num1 = num1;
        }

        public int getNum2() {
            return num2;
        }

        public void setNum2(int num2) {
            this.num2 = num2;
        }

        public String getOper() {
            return oper;
        }

        public void setOper(String oper) {
            this.oper = oper;
        }
    }

}