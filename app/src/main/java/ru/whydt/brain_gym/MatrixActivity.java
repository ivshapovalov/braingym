package ru.whydt.brain_gym;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.Chronometer;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MatrixActivity extends AppCompatActivity {
    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;

    private SharedPreferences mSettings;
    private String mMatrixLang;
    private int mMatrixSize;
    private int mMatrixTextSize;
    private int mMatrixFontSizeChange;
    //алфавиты
    private String[] AlphabetRu;
    private String[] AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        //matrixClear();
        mChronometer = (Chronometer) findViewById(R.id.chronometer_matrix);

        AlphabetRu= MainActivity.AlphabetRu();
        AlphabetEn=MainActivity.AlphabetEn();

    }


    private void drawMatrixTest(ArrayList<Integer> matrix) {
        //для матриц тестов id начинается со 300
        TableLayout layout = (TableLayout) findViewById(R.id.table);
        layout.removeAllViews();

        layout.setStretchAllColumns(true);
        layout.setShrinkAllColumns(true);


        layout.setBackgroundColor(Color.BLACK);
        int mHeight = layout.getHeight() / mMatrixSize;
        int mWidth = layout.getWidth() / mMatrixSize;


        for (Integer numString = 1; numString <= mMatrixSize; numString++) {
            TableRow row = new TableRow(this);
            //row.setBackgroundColor(Color.BLACK);
            row.setMinimumHeight(mHeight);
            row.setMinimumWidth(mWidth);
            //row.setPadding(0, 0, 1, 0); //Border between rows
            row.setGravity(Gravity.CENTER);

            //row.setBackgroundColor(Color.WHITE);
            //row.setBackground(R.drawable.rounded_corners);
            //TableRow.LayoutParams params=new TableRow.LayoutParams(layout.getWidth()*95/100,mHeight,20);
            //row.setLayoutParams(params);

            //row.setMinimumWidth(mHeight);

            // TableRow.LayoutParams llp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            //llp.setMargins(0, 0, 2, 0);//2px right-margin

            for (int numColumn = 1; numColumn <= mMatrixSize; numColumn++) {
                //int resID=getResources().getIdentifier("txt"+(String.valueOf(300+(numString-1)*5+numColumn)), "id", getPackageName());
                //TextView txt = (TextView) findViewById(resID);
                //TextView txt = (TextView) findViewById(300+(numString-1)*5+numColumn);
                TextView txt = (TextView) findViewById(300 + (numString - 1) * mMatrixSize + numColumn);
                if (txt == null) {
                    //System.out.println("Привет");
//                    ShapeDrawable border = new ShapeDrawable(new RectShape());
//                    border.getPaint().setStyle(Paint.Style.STROKE);
//                    border.getPaint().setColor(Color.BLACK);
                    //LinearLayout cell = new LinearLayout(this);
                    //cell.setGravity(Gravity.CENTER);
                    //cell.setBackgroundResource(R.drawable.textview_border);
                    //cell.setBackgroundColor(Color.WHITE);
//                    cell.setBackgroundColor(Color.WHITE);
//                    cell.setLayoutParams(llp);//2px border on the right for the cell
//                    cell.setBackgroundDrawable(border);
                    txt = new TextView(this);
                    txt.setId(300 + (numString - 1) * mMatrixSize + numColumn);
                    //txt.setPadding(0, 0, 4, 3);
                    //10% слева
                    //txt.setPadding((numColumn) * mWidth+layout.getWidth()/100*5,layout.getHeight()*5/100+ numString * mHeight, 0, 0);

                    //txt.setPadding((i - (i - 1) % mMatrixSize) / (mMatrixSize / 2) * 33 + 30, 200 + (i - 1) % mMatrixSize * 75, 0, 0);
                    //txt.setLayoutParams(new android.view.ViewGroup.LayoutParams(mWidth,mHeight));

                    //txt.setBackgroundDrawable(border);
                    //cell.addView(txt);
                    txt.setMinimumHeight(mHeight);
                    row.addView(txt);
                }
                switch (mMatrixLang) {
                    case "Digit":
                        txt.setText(String.valueOf(matrix.get((numString - 1) * mMatrixSize + numColumn - 1)));
                        break;
                    case "Ru":
                        String strRu=AlphabetRu[matrix.get((numString - 1) * mMatrixSize + numColumn - 1)-1];
                        txt.setText(strRu);
                        break;
                    case "En":
                        String strEn=AlphabetEn[matrix.get((numString - 1) * mMatrixSize + numColumn - 1)-1];
                        txt.setText(strEn);
                        break;
                    default:
                        txt.setText(String.valueOf(matrix.get((numString - 1) * mMatrixSize + numColumn - 1)));
                        break;

                }

                //txt.setText(String.valueOf((numString-1)*5+numColumn));
//                int bottom = txt.getPaddingBottom();
//                int top = txt.getPaddingTop();
//                int right = txt.getPaddingRight();
//                int left = txt.getPaddingLeft();
                //txt.setBackgroundResource(R.drawable.textview_border);
                //txt.setPadding(left, top, right, bottom);
                //findViewById(R.id.tablerow1).setBackgroundResource(R.drawable.textview_border);
                //txt.setTextSize(Math.min(mWidth,mHeight)/9);
                mMatrixTextSize=(int)(Math.min(mWidth, mHeight) / 3/getApplicationContext().getResources().getDisplayMetrics().density)+mMatrixFontSizeChange;
                txt.setTextSize(mMatrixTextSize);
                //txt.setTypeface(null, Typeface.BOLD);
                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.textview_border);
                txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                //row.addView(txt);
            }
            layout.addView(row);
        }
        //setContentView(layout);
        //int r=0;

    }

    public void matrixClear_onClick(View view) {

        //matrixClear();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount=0;
        mChronometerIsWorking = false;

        ChangeButtonText("buttonMatrixStartPause","Старт");

    }

    private void matrixClear() {

        int mCountMatrix = mMatrixSize * mMatrixSize;
        for (int i = 1; i <= mCountMatrix; i++) {
            //int resID=getResources().getIdentifier("txt"+String.valueOf(300+i), "id", getPackageName());
            //TextView txt = (TextView) findViewById(resID);
            TextView txt = (TextView) findViewById(300 + i);
            //txt.setVisibility(View.INVISIBLE);
            if (txt != null) {
                txt.setText("");
                //txt.setVisibility(View.GONE);
                //
            }
        }
        //ChangeButtonText("buttonMatrixStartPause", "Старт");

    }

    private ArrayList<Integer> createMatrix() {
        ArrayList<Integer> matrix = new ArrayList<>();
        int num;

        int mMaxDigits = mMatrixSize * mMatrixSize;
        while (matrix.size() != mMaxDigits) {
            Random random = new Random();
            num = random.nextInt(mMaxDigits) + 1;
            int indPlace = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));
            if (!matrix.contains(num)) {
               matrix.add(indPlace, num);
            }
        }
        return matrix;
    }



    public void startPause_onClick(View view) {

        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());

                getPreferencesFromFile();
                ArrayList<Integer> matrix = createMatrix();
                drawMatrixTest(matrix);

            } else {
                mChronometer.setBase(SystemClock.elapsedRealtime() - mChronometerCount);
            }

            mChronometer.start();
            mChronometerIsWorking = true;
            ChangeButtonText("buttonMatrixStartPause", "Пауза");

        } else {

            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;

            ChangeButtonText("buttonMatrixStartPause", "Старт");
        }

    }

    public void matrixOptions_onClick(View view) {

        Intent intent = new Intent(MatrixActivity.this, MatrixActivityOptions.class);
        intent.putExtra("mMatrixTextSize",mMatrixTextSize-mMatrixFontSizeChange);
        startActivity(intent);

    }

    private void ChangeButtonText(String ButtonID, String ButtonText) {

        int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        Button but = (Button) findViewById(resID);
        if (but != null) {
            but.setText(ButtonText);
        }
    }

    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void getPreferencesFromFile() {
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
            mMatrixFontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE, 0);
        } else {
            mMatrixFontSizeChange = 0;
        }
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_LANGUAGE)) {
            // Получаем язык из настроек
            try {
                mMatrixLang = mSettings.getString(MainActivity.APP_PREFERENCES_MATRIX_LANGUAGE, "Digit");
            } catch (Exception e) {
                mMatrixLang="Digit";
            }
            try {
                mMatrixSize = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_SIZE, 5);
            } catch (Exception e) {
                mMatrixSize=5;
            }
        } else {
            mMatrixLang="Digit";
            mMatrixSize=5;
        }
    }



}