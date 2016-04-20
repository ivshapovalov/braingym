package ru.whydt.brain_gym;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;

import android.widget.LinearLayout;
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

    //алфавиты
    private String[] AlphabetRu;
    private String[] AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        //matrixClear();
        mChronometer = (Chronometer) findViewById(R.id.chronometer_matrix);

        CreateAlphabetRu();
        CreateAlphabetEn();

    }

    public void matrixForm_onClick(View view) {
        //matrixClear();
        getPreferencesFromFile();
        ArrayList<Integer> matrix = createMatrix();
        drawMatrixTest(matrix);
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


        int mMaxDigits = mMatrixSize * mMatrixSize;
        for (Integer numString = 1; numString <= mMatrixSize; numString++) {
            TableRow row = new TableRow(this);
            //row.setBackgroundColor(Color.BLACK);
            row.setMinimumHeight(mHeight);
            row.setMinimumWidth(mWidth);
            //row.setPadding(0, 0, 1, 0); //Border between rows
            row.setGravity(Gravity.CENTER);

            int a;

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
                } else {
                    //txt.setVisibility(View.VISIBLE);
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
                txt.setTextSize(Math.min(mWidth,mHeight)/4);
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

        ChangeButtonText("buttonMarixStartPause","Старт");

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

    private ArrayList createMatrix() {
        ArrayList<Integer> matrix = new ArrayList<>();
        int num;
        int indPlace = 0;
        int mMaxDigits = mMatrixSize * mMatrixSize;
        while (matrix.size() != mMaxDigits) {
            Random random = new Random();
            num = random.nextInt(mMaxDigits) + 1;
            indPlace = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));
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

    private void CreateAlphabetRu() {

        AlphabetRu=new String[25];
        AlphabetRu[0]="А";       AlphabetRu[7]="З";       AlphabetRu[13]="О";       AlphabetRu[19]="Ф";
        AlphabetRu[1]="Б";       AlphabetRu[8]="И";       AlphabetRu[14]="П";       AlphabetRu[20]="Х";
        AlphabetRu[2]="В";       AlphabetRu[9]="К";       AlphabetRu[15]="Р";       AlphabetRu[21]="Ц";
        AlphabetRu[3]="Г";       AlphabetRu[10]="Л";       AlphabetRu[16]="С";       AlphabetRu[22]="Ч";
        AlphabetRu[4]="Д";       AlphabetRu[11]="М";       AlphabetRu[17]="Т";       AlphabetRu[23]="Щ";
        AlphabetRu[5]="Е";       AlphabetRu[12]="Н";       AlphabetRu[18]="У";       AlphabetRu[24]="Щ";
        AlphabetRu[6]="Ж";
         }

    private void CreateAlphabetEn() {

        AlphabetEn=new String[25];
        AlphabetEn[0]="A";       AlphabetEn[7]="H";       AlphabetEn[13]="N";       AlphabetEn[19]="T";
        AlphabetEn[1]="B";       AlphabetEn[8]="I";       AlphabetEn[14]="O";       AlphabetEn[20]="U";
        AlphabetEn[2]="C";       AlphabetEn[9]="J";       AlphabetEn[15]="P";       AlphabetEn[21]="V";
        AlphabetEn[3]="D";       AlphabetEn[10]="K";       AlphabetEn[16]="Q";       AlphabetEn[22]="W";
        AlphabetEn[4]="E";       AlphabetEn[11]="L";       AlphabetEn[17]="R";       AlphabetEn[23]="X";
        AlphabetEn[5]="F";       AlphabetEn[12]="M";       AlphabetEn[18]="S";       AlphabetEn[24]="Y";
        AlphabetEn[6]="G";
    }


}