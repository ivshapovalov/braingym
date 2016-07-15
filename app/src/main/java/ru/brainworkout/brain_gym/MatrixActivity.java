package ru.brainworkout.brain_gym;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

    private ArrayList<Integer> matrix = new ArrayList<>();
    private int mMaxDigits;

    private SharedPreferences mSettings;
    private String mMatrixLang;
    private int mMatrixSize;
    private int mTextSize;
    private int mHeight = 0;
    private int mWidth = 0;
    private int mMatrixTextSize = 12;
    private int mMatrixFontSizeChange;
    private int mMatrixMaxTime;
    private int mMatrixExampleTime;

    private boolean mMatrixIsClickable;
    private int mCountRightAnswers = 0;
    private int mCountMissedAnswers = 0;
    private int mCountAllAnswers = 0;
    private long mMatrixExBeginTime = 0;
    private long elapsedMillis;

    private int mIndexNextSymbol;
    //алфавиты
    private ArrayList<String> AlphabetRu;
    private ArrayList<String> AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //id - 300+i
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_matrix);
        AlphabetRu = Utils.AlphabetRu();
        AlphabetEn = Utils.AlphabetEn();
    }


    public void MatrixСlear_onClick(View view) {

        timerStop(false);

    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;

        mMatrixExBeginTime = 0;
        mCountMissedAnswers = 0;

        ChangeButtonText("buttonMatrixStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvMatrixTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            //txtTimerMaxTime.setTextSize(mTextSize);
            String txt;
            if (!auto) {
                if (mMatrixIsClickable) {
                    txt = "Тест: " + String.valueOf(mMatrixMaxTime);
                } else {
                    txt = "Тест: " + String.valueOf(0);
                }
            } else {
                txt = "Тест окончен";
            }
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("tvMatrixAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            //txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setText("");
            }

        }

        for (int i = 1; i <= mMaxDigits; i++) {
            TextView txt1 = (TextView) findViewById(300 + i);
            if (txt1 != null) {
                txt1.setText("");
                //txt1.setTextSize(mMatrixTextSize);
                //txt1.setTextColor(Color.WHITE);

            }
        }

        int exID = getResources().getIdentifier("tvMatrixExample", "id", getPackageName());
        TextView txtEx = (TextView) findViewById(exID);
        if (txtEx != null) {
            txtEx.setTextSize(mMatrixTextSize);
            txtEx.setText(" ");
            txtEx.setTextColor(Color.WHITE);
        }

        for (int i = 1; i <= mMaxDigits; i++) {
            TextView txt1 = (TextView) findViewById(300 + i);
            if (txt1 != null) {
                txt1.setText("");
                //txt1.setTextSize(mMatrixTextSize);
                //txt1.setTextColor(Color.WHITE);

            }
        }

        if (mMatrixIsClickable) {
            if (mMatrixExampleTime != 0) {
                int timerExID = getResources().getIdentifier("tvMatrixTimerExTime", "id", getPackageName());
                TextView txtTimerExTime = (TextView) findViewById(timerExID);

                if (txtTimerExTime != null) {
                    String txt;
                    if (!auto) {
                        txt = "Символ: " + String.valueOf(mMatrixExampleTime);
                    } else {
                        txt = "";
                    }

                    txtTimerExTime.setText(txt);
                    //txtTimerExTime.setTextSize(mTextSize);
                }
            }
        }
    }

    private void matrixClear() {

        TableLayout layout = (TableLayout) findViewById(R.id.tableMatrix);
        layout.removeAllViews();
        layout.setStretchAllColumns(true);
        //mTextSize = (int) (Math.min(mWidth, mHeight) / 5 / getApplicationContext().getResources().getDisplayMetrics().density) + mMatrixFontSizeChange;


        int resID = getResources().getIdentifier("tvMatrixExample", "id", getPackageName());
        TextView txt = (TextView) findViewById(resID);

        if (txt != null) {

            //txt.setHeight(mHeight);
            mHeight = (layout.getHeight() + txt.getHeight()) / (mMatrixSize + 1);
            mWidth = layout.getWidth() / (mMatrixSize);

            mMatrixTextSize = (int) (Math.min(mWidth, mHeight) / 3 / getApplicationContext().getResources().getDisplayMetrics().density) + mMatrixFontSizeChange;

            txt.setHeight(mHeight);
            txt.setText(" ");
            txt.setTextSize(mMatrixTextSize);
            txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        int trowID = getResources().getIdentifier("trowMatrix", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);

        }

        for (int i = 1; i <= mMaxDigits; i++) {
            TextView txt1 = (TextView) findViewById(300 + i);
            if (txt1 != null) {
                txt1.setText("");
                txt1.setTextSize(mMatrixTextSize);
                txt1.setTextColor(Color.WHITE);

            }
        }
    }

    private void changeTimer(long elapsedMillis) {

        int timerMaxID = getResources().getIdentifier("tvMatrixTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (!mMatrixIsClickable) {
            int time = (int) (elapsedMillis / 1000);
            String txt = "Тест: " + String.valueOf(time);
            txtTimerMaxTime.setText(txt);
            //txtTimerMaxTime.setTextSize(mTextSize);

        } else {
            if (txtTimerMaxTime != null) {
                int time = (int) (mMatrixMaxTime - (elapsedMillis / 1000));
                String txt = "Тест: " + String.valueOf(time);
                txtTimerMaxTime.setText(txt);
                //txtTimerMaxTime.setTextSize(mTextSize);
            }
            if (mMatrixExampleTime != 0) {
                int timerExID = getResources().getIdentifier("tvMatrixTimerExTime", "id", getPackageName());
                TextView txtTimerExTime = (TextView) findViewById(timerExID);
                if (txtTimerExTime != null) {
                    int time = (mMatrixExampleTime - ((int) (((elapsedMillis - mMatrixExBeginTime) / 1000)) % mMatrixExampleTime));
                    //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                    if (time == mMatrixExampleTime) {
                        //новый пример
                        String txt = "Символ: " + String.valueOf(time);
                        txtTimerExTime.setText(txt);
                        //txtTimerExTime.setTextSize(mTextSize);
                        mIndexNextSymbol = matrix.indexOf(matrix.get(mIndexNextSymbol) + 1);
                        mCountMissedAnswers++;
                        drawNextSymbol();


                    } else {
                        String txt = "Символ: " + String.valueOf(time);
                        txtTimerExTime.setText(txt);
                        //txtTimerExTime.setTextSize(mTextSize);
                    }

                }

            } else {
                int timerExID = getResources().getIdentifier("tvMatrixTimerExTime", "id", getPackageName());
                TextView txtTimerExTime = (TextView) findViewById(timerExID);
                if (txtTimerExTime != null) {

                    txtTimerExTime.setText(" ");
                    //txtTimerExTime.setTextSize(mTextSize);
                }
            }
        }
    }


    private void drawMatrixTest() {
        mIndexNextSymbol = 0;
        mCountRightAnswers = 0;
        //для матриц тестов id начинается со 300
        TableLayout layout = (TableLayout) findViewById(R.id.tableMatrix);
        //layout.removeAllViews();

//        layout.setStretchAllColumns(true);
//        layout.setShrinkAllColumns(true);

        layout.setBackgroundColor(Color.BLACK);
//        int mHeight = layout.getHeight() / mMatrixSize;
//        int mWidth = layout.getWidth() / mMatrixSize;


        for (Integer numString = 1; numString <= mMatrixSize; numString++) {
            TableRow row = new TableRow(this);
            row.setMinimumHeight(mHeight);
            row.setMinimumWidth(mWidth);
            row.setGravity(Gravity.CENTER);

            //row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0,1));

//            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(mWidth,mHeight);
//            row.setLayoutParams(params);

            for (int numColumn = 1; numColumn <= mMatrixSize; numColumn++) {

                TextView txt = (TextView) findViewById(300 + (numString - 1) * mMatrixSize + numColumn);
                if (txt == null) {
                    txt = new TextView(this);
                    txt.setId(300 + (numString - 1) * mMatrixSize + numColumn);
                    txt.setMinimumHeight(mHeight);
                    row.addView(txt);
                }
                switch (mMatrixLang) {
                    case "Digit":
                        txt.setText(String.valueOf(matrix.get((numString - 1) * mMatrixSize + numColumn - 1)));
                        break;
                    case "Ru":
                        String strRu = AlphabetRu.get(matrix.get((numString - 1) * mMatrixSize + numColumn - 1) - 1);
                        txt.setText(strRu);
                        break;
                    case "En":
                        String strEn = AlphabetEn.get(matrix.get((numString - 1) * mMatrixSize + numColumn - 1) - 1);
                        txt.setText(strEn);
                        break;
                }

                //mMatrixTextSize = (int) (Math.min(mWidth, mHeight) / 3 / getApplicationContext().getResources().getDisplayMetrics().density) + mMatrixFontSizeChange;
                //mTextSize = (int) (Math.min(mWidth, mHeight) / 5 / getApplicationContext().getResources().getDisplayMetrics().density) + mMatrixFontSizeChange;
                txt.setTextSize(mMatrixTextSize);
                //txt.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));

                //txt.setTypeface(null, Typeface.BOLD);
                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.textview_border);
                txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                if (mMatrixIsClickable) {
                    txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txt_onClick((TextView) v);
                        }
                    });
                }
                //row.addView(txt);
            }
            layout.addView(row);
        }
        //setContentView(layout);
        //int r=0;

        if (mMatrixIsClickable) {
            mIndexNextSymbol = matrix.indexOf(1);
            drawNextSymbol();
        }
    }

    public void txt_onClick(TextView view) {

        if (mMatrixIsClickable) {
            if (mChronometerIsWorking) {
                //int a = Integer.valueOf(String.valueOf(((AppCompatTextView) view).getText().charAt(0)));

                int a = view.getId() % 100;
                if (a - 1 == mIndexNextSymbol) {

                    mCountRightAnswers++;
                    if (mCountRightAnswers + mCountMissedAnswers == mCountAllAnswers) {
                        timerStop(true);
                    } else {

                        //System.out.println("Угадали. Формируем новый номер");

                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(50); //You can manage the blinking time with this parameter
                        anim.setStartOffset(0);
                        anim.setRepeatMode(Animation.REVERSE);
                        //anim.setRepeatCount(Animation.INFINITE);
                        anim.setRepeatCount(1);
                        view.startAnimation(anim);

                        mMatrixExBeginTime = elapsedMillis;
                        mIndexNextSymbol = matrix.indexOf(matrix.get(mIndexNextSymbol) + 1);
                        drawNextSymbol();
                    }
                } else {

                    //System.out.println("Выбрали: " + (a - 1) + ". Надо: " + mIndexNextSymbol);

                    //view.setBackgroundResource(R.drawable.textview_border);
                }
                int answerID = getResources().getIdentifier("tvMatrixAnswers", "id", getPackageName());
                TextView txtAnswer = (TextView) findViewById(answerID);
                if (txtAnswer != null) {
                    String txt = String.valueOf(mCountRightAnswers) + "/" + String.valueOf(mCountAllAnswers);
                    txtAnswer.setText(txt);
                    //txtAnswer.setTextSize(mTextSize);
                }
            }
        }
    }


    private void createMatrix() {
        int num;

        matrix.clear();

        while (matrix.size() != mMaxDigits) {
            Random random = new Random();
            num = random.nextInt(mMaxDigits) + 1;
            int indPlace = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));
            if (!matrix.contains(num)) {
                matrix.add(indPlace, num);
            }
        }
    }


    public void matrixStartPause_onClick(View view) {


        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());


                getPreferencesFromFile();
                matrixClear();
                createMatrix();
                drawMatrixTest();
                if (mMatrixIsClickable) {

                    int timerID = getResources().getIdentifier("tvMatrixTimerMaxTime", "id", getPackageName());
                    TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                    if (txtTimerMaxTime != null) {
                        //txtTimerMaxTime.setTextSize(mTextSize);
                        String txt = "Тест: " + String.valueOf(mMatrixMaxTime);
                        txtTimerMaxTime.setText(txt);
                        //txtTimerMaxTime.setTextSize(mTextSize);
                    }
                    mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometer) {
                            elapsedMillis = SystemClock.elapsedRealtime()
                                    - mChronometer.getBase();

                            if (mMatrixMaxTime - (elapsedMillis / 1000) < 1) {
                                timerStop(true);
                            }
                            if (elapsedMillis > 1000) {

                                changeTimer(elapsedMillis);

                                //elapsedMillis=0;
                            }
                        }
                    });
                } else {
                    int timerID = getResources().getIdentifier("tvMatrixTimerMaxTime", "id", getPackageName());
                    TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                    if (txtTimerMaxTime != null) {
                        //txtTimerMaxTime.setTextSize(mTextSize);
                        String txt = "Тест: " + String.valueOf(0);
                        txtTimerMaxTime.setText(txt);
                        //txtTimerMaxTime.setTextSize(mTextSize);
                    }
                    mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometer) {
                            elapsedMillis = SystemClock.elapsedRealtime()
                                    - mChronometer.getBase();

                            if (elapsedMillis > 1000) {

                                changeTimer(elapsedMillis);

                                //elapsedMillis=0;
                            }
                        }
                    });
                }

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

    private void drawNextSymbol() {

        //System.out.println("Новый номер: " + mIndexNextSymbol);

        int exampleID = getResources().getIdentifier("tvMatrixExample", "id", getPackageName());
        TextView txtExample = (TextView) findViewById(exampleID);
        if (txtExample != null) {

            //txtExample.setTextSize(mMatrixTextSize);
            switch (mMatrixLang) {
                case "Digit":
                    txtExample.setText(String.valueOf(matrix.get(mIndexNextSymbol)));
                    break;
                case "Ru":
                    txtExample.setText(AlphabetRu.get(matrix.get(mIndexNextSymbol) - 1));
                    break;
                case "En":
                    txtExample.setText(AlphabetEn.get(matrix.get(mIndexNextSymbol) - 1));
                    break;
            }
        }
    }

    public void MatrixOptions_onClick(View view) {

        Intent intent = new Intent(MatrixActivity.this, MatrixActivityOptions.class);
        intent.putExtra("mMatrixTextSize", mMatrixTextSize - mMatrixFontSizeChange);
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
                mMatrixLang = "Digit";
            }
        } else {
            mMatrixLang = "Digit";
        }
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_SIZE)) {
            try {
                mMatrixSize = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_SIZE, 5);
            } catch (Exception e) {
                mMatrixSize = 5;
            }
        } else {

            mMatrixSize = 5;
        }
        mMaxDigits = mMatrixSize * mMatrixSize;
        mCountAllAnswers = mMaxDigits;
        mMatrixIsClickable = false;
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_CLICKABLE)) {
            try {
                mMatrixIsClickable = mSettings.getBoolean(MainActivity.APP_PREFERENCES_MATRIX_CLICKABLE, false);
            } catch (Exception e) {
                mMatrixIsClickable = false;
            }

        } else {
            mMatrixIsClickable = false;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_TEST_TIME)) {
            mMatrixMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_TEST_TIME, 60);
        } else {
            mMatrixMaxTime = 60;
        }


        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_EXAMPLE_TIME)) {
            mMatrixExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_EXAMPLE_TIME, 0);
        } else {
            mMatrixExampleTime = 0;
        }
    }


}