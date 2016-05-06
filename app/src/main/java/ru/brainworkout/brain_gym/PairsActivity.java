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


public class PairsActivity extends AppCompatActivity {
    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;

    private ArrayList<PairsElement> matrix = new ArrayList<>();

    private SharedPreferences mSettings;
    private String mPairsLang;
    private int mPairsSizeHeight;
    private int mPairsSizeWidth;
    private int mTextSize;
    private int mHeight = 0;
    private int mWidth = 0;
    private int mPairsTextSize = 12;

    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    private long elapsedMillis;

    private int mFirstSymbolIndex = 0;

    //алфавиты
    private ArrayList<String> AlphabetRu;
    private ArrayList<String> AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairs);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_pairs);
        AlphabetRu = MainActivity.AlphabetRu();
        AlphabetEn = MainActivity.AlphabetEn();
    }


    private void PairsClear() {

        TableLayout layout = (TableLayout) findViewById(R.id.tablePairs);
        layout.removeAllViews();
        layout.setStretchAllColumns(true);
        //mTextSize = (int) (Math.min(mWidth, mHeight) / 5 / getApplicationContext().getResources().getDisplayMetrics().density) + mMatrixFontSizeChange;


        int resID = getResources().getIdentifier("tvPairsAttemptCount", "id", getPackageName());
        TextView txt = (TextView) findViewById(resID);

        if (txt != null) {

            //txt.setHeight(mHeight);
            mHeight = (layout.getHeight() + txt.getHeight()) / (mPairsSizeHeight + 1);
            mWidth = layout.getWidth() / (mPairsSizeWidth);

            mPairsTextSize = (int) (Math.min(mWidth, mHeight) / 3 / getApplicationContext().getResources().getDisplayMetrics().density);

            txt.setHeight(mHeight);
            txt.setText(" ");
            txt.setTextSize(mPairsTextSize);
            txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        int trowID = getResources().getIdentifier("trowPairs", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);

        }

        for (int i = 1; i <= mPairsSizeWidth * mPairsSizeHeight; i++) {
            TextView txt1 = (TextView) findViewById(700 + i);
            if (txt1 != null) {
                txt1.setText("");
                txt1.setTextSize(mPairsTextSize);
                txt1.setTextColor(Color.WHITE);

            }
        }

    }

    public void btPairsСlear_onClick(View view) {

        timerStop(false);

    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;


        ChangeButtonText("btPairsStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvPairsTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            //txtTimerMaxTime.setTextSize(mTextSize);
            String txt;
            if (!auto) {

                txt = "Тест: " + String.valueOf(0);

            } else {
                txt = "Тест окончен";
            }
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("tvPairsAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            //txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setText("");
            }

        }

        int exID = getResources().getIdentifier("tvPairsExample", "id", getPackageName());
        TextView txtEx = (TextView) findViewById(exID);
        if (txtEx != null) {
            txtEx.setTextSize(mPairsTextSize);
            txtEx.setText(" ");
            txtEx.setTextColor(Color.WHITE);
        }

    }

    private void changeTimer(long elapsedMillis) {

        int timerMaxID = getResources().getIdentifier("tvPairsTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);


        int time = (int) (elapsedMillis / 1000);
        String txt = "Тест: " + String.valueOf(time);
        txtTimerMaxTime.setText(txt);
        //txtTimerMaxTime.setTextSize(mTextSize);


    }


    private void drawPairsTest() {

        mCountRightAnswers = 0;
        //для матриц тестов id начинается со 700
        TableLayout layout = (TableLayout) findViewById(R.id.tablePairs);
        layout.setBackgroundColor(Color.BLACK);
//        int mHeight = layout.getHeight() / mMatrixSize;
//        int mWidth = layout.getWidth() / mMatrixSize;

        for (Integer numString = 1; numString <= mPairsSizeHeight; numString++) {
            TableRow row = new TableRow(this);
            row.setMinimumHeight(mHeight);
            row.setMinimumWidth(mWidth);
            row.setGravity(Gravity.CENTER);

            for (int numColumn = 1; numColumn <= mPairsSizeWidth; numColumn++) {

                TextView txt = (TextView) findViewById(700 + (numString - 1) * mPairsSizeWidth + numColumn);
                if (txt == null) {
                    txt = new TextView(this);
                    txt.setId(700 + (numString - 1) * mPairsSizeWidth + numColumn);
                    txt.setMinimumHeight(mHeight);
                    row.addView(txt);
                }
//                switch (mPairsLang) {
//                    case "Digit":
//                        txt.setText(String.valueOf(matrix.get((numString - 1) * mPairsSizeWidth + numColumn - 1)));
//                        break;
//                    case "Ru":
//                        String strRu = AlphabetRu.get(matrix.get((numString - 1) * mPairsSizeWidth + numColumn - 1) - 1);
//                        txt.setText(strRu);
//                        break;
//                    case "En":
//                        String strEn = AlphabetEn.get(matrix.get((numString - 1) * mPairsSizeWidth + numColumn - 1) - 1);
//                        txt.setText(strEn);
//                        break;
//                }

                txt.setTextSize(mPairsTextSize);

                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.textview_border);
                txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));


                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_onClick((TextView) v);
                    }
                });

                //row.addView(txt);
            }
            layout.addView(row);
        }
    }

    public void txt_onClick(TextView view) {

        if (mChronometerIsWorking) {
            int ind = view.getId() % 100 - 1;
            PairsElement m1 = matrix.get(ind);
            if (m1.isOpened()) {
                //ничего не делаем. ячейка уже открыта
            } else {
                switch (mPairsLang) {
                    case "Digit":
                        view.setText(String.valueOf(matrix.get(ind - 1).getNum()));
                        break;
                    case "Ru":
                        String strRu = AlphabetRu.get(matrix.get(ind - 1).getNum());
                        view.setText(strRu);
                        break;
                    case "En":
                        String strEn = AlphabetEn.get(matrix.get(ind - 1).getNum());
                        view.setText(strEn);
                        break;
                }
                if (mFirstSymbolIndex == 0) {
                    mFirstSymbolIndex = ind;
                } else {

                    int num1 = matrix.get(mFirstSymbolIndex).getNum();
                    int num2 = matrix.get(ind).getNum();
                    if (num1 == num2) {
                        //угадали
                        matrix.get(mFirstSymbolIndex).setOpened(true);
                        matrix.get(ind).setOpened(true);
                        mFirstSymbolIndex = 0;
                        mCountRightAnswers++;
                    } else {
                        //не угадали
                        view.setText("");
                        TextView txt = (TextView) findViewById(700 + ind + 1);
                        if (txt != null) {
                            txt.setText("");
                        }

                    }

                    if (mCountRightAnswers == mCountAllAnswers) {
                        timerStop(true);
                    } else {

                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(50); //You can manage the blinking time with this parameter
                        anim.setStartOffset(0);
                        anim.setRepeatMode(Animation.REVERSE);
                        //anim.setRepeatCount(Animation.INFINITE);
                        anim.setRepeatCount(1);
                        view.startAnimation(anim);

                        TextView txt = (TextView) findViewById(700 + ind + 1);
                        if (txt != null) {
                            txt.startAnimation(anim);
                        }


                    }


                }
            }
        }
    }

    private void createPairs() {
        int num;
        matrix.clear();

        Random random = new Random();
        int mMaxDigits = mPairsSizeHeight * mPairsSizeWidth;
        while (matrix.size() != mMaxDigits) {

            num = random.nextInt() % (mMaxDigits / 2);
            if (!matrix.contains(num)) {
                int indPlace1 = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));
                int indPlace2 = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));

                PairsElement m = new PairsElement(num);
                matrix.add(indPlace1, m);
                matrix.add(indPlace2, m);
            }
        }
    }


    public void btPairsStartPause_onClick(View view) {

        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());

                getPreferencesFromFile();
                PairsClear();
                createPairs();
                drawPairsTest();

                int timerID = getResources().getIdentifier("tvPairsTimerMaxTime", "id", getPackageName());
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
            } else {
                mChronometer.setBase(SystemClock.elapsedRealtime() - mChronometerCount);
            }

            mChronometer.start();
            mChronometerIsWorking = true;
            ChangeButtonText("byPairsStartPause", "Пауза");

        } else {

            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;

            ChangeButtonText("btPairsStartPause", "Старт");
        }

    }

    public void btPairsOptions_onClick(View view) {

        Intent intent = new Intent(PairsActivity.this, PairsActivityOptions.class);
        startActivity(intent);

    }

    private void ChangeButtonText(String ButtonID, String ButtonText) {

        int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        Button but = (Button) findViewById(resID);
        if (but != null) {
            but.setText(ButtonText);
        }
    }

    public void btPairsHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void getPreferencesFromFile() {
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        mPairsLang = "Ru";
        if (mSettings.contains(MainActivity.APP_PREFERENCES_PAIRS_LANGUAGE)) {
            // Получаем язык из настроек
            mPairsLang = mSettings.getString(MainActivity.APP_PREFERENCES_PAIRS_LANGUAGE, "Ru");
        } else {
            mPairsLang = "Ru";
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_PAIRS_SIZE_WIDTH)) {
            // Получаем язык из настроек
            mPairsSizeWidth = mSettings.getInt(MainActivity.APP_PREFERENCES_PAIRS_SIZE_WIDTH, 3);
        } else {
            mPairsSizeWidth = 3;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_PAIRS_SIZE_HEIGHT)) {
            // Получаем язык из настроек
            mPairsSizeHeight = mSettings.getInt(MainActivity.APP_PREFERENCES_PAIRS_SIZE_HEIGHT, 4);
        } else {
            mPairsSizeHeight = 4;
        }

        mCountAllAnswers = mPairsSizeHeight * mPairsSizeWidth;

    }

    private class PairsElement {
        private int num;
        private boolean IsOpened;

        public PairsElement(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public boolean isOpened() {
            return IsOpened;
        }

        public void setOpened(boolean opened) {
            IsOpened = opened;
        }
    }

}