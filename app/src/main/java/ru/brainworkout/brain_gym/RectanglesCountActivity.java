package ru.brainworkout.brain_gym;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
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


public class RectanglesCountActivity extends AppCompatActivity {
    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;

    private ArrayList<Integer> matrix = new ArrayList<>();


    private SharedPreferences mSettings;
    private int mRectanglesCountSizeHeight;
    private int mRectanglesCountSizeWidth;
    private int mRectanglesCountMaxTime;
    private int mRectanglesCountExampleTime;
    private String mRectanglesCountFilling;

    private int mHeight = 0;
    private int mWidth = 0;
    private int mMatrixTextSize;
    private int mMaxDigits;

    private int mCountRightAnswers = 0;
    private int mCountMissedAnswers = 0;
    private int mCountAllAnswers = 0;
    private long mRectanglesCountExBeginTime = 0;
    private long elapsedMillis;

    private int Answer;
    private ArrayList<Integer> AlphabetColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangles_count);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_rectangles_count);

        AlphabetColors = MainActivity.AlphabetColors();
    }


    private void RectanglesCountClear() {

        TableLayout layout = (TableLayout) findViewById(R.id.tableRectanglesCount);
        layout.removeAllViews();
        layout.setStretchAllColumns(true);

        int resID = getResources().getIdentifier("tvRectanglesCountExample", "id", getPackageName());
        TextView txt = (TextView) findViewById(resID);

        if (txt != null) {

            //txt.setHeight(mHeight);
            mHeight = (layout.getHeight() + txt.getHeight()) / (mRectanglesCountSizeHeight + 2);
            mWidth = layout.getWidth() / (mRectanglesCountSizeWidth);

            mMatrixTextSize = (int) (Math.min(mWidth, mHeight) / 3 / getApplicationContext().getResources().getDisplayMetrics().density);

            txt.setHeight(mHeight);
//            txt.setText(" ");
//            txt.setTextSize(mMatrixTextSize);
            txt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        int trowID = getResources().getIdentifier("trowRectanglesCount", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);
            trow1.setMinimumHeight(mHeight);

        }

        for (int i = 1; i <= mMaxDigits; i++) {
            TextView txt1 = (TextView) findViewById(300 + i);
            if (txt1 != null) {
                //txt1.setText("");
                //txt1.setTextSize(mMatrixTextSize);
                //txt1.setTextColor(Color.WHITE);
                //txt1.setBackgroundResource(R.drawable.textview_border);
                Drawable dr = getResources().getDrawable(R.drawable.textview_border1);
                //Drawable dr = view.getBackground();
//                ColorFilter filter = new LightingColorFilter(Color.WHITE, Color.WHITE);
//                dr.setColorFilter(filter);
                txt1.setBackgroundDrawable(dr);

            }
        }
    }

    public void RectanglesCountСlear_onClick(View view) {

        timerStop(false);

    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;

        mRectanglesCountExBeginTime = 0;
        mCountMissedAnswers = 0;

        ChangeButtonText("buttonRectanglesCountStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvRectanglesCountTimerMaxTime", "id", getPackageName());
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

        int answerID = getResources().getIdentifier("tvRectanglesCountAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            //txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setBackgroundColor(Color.WHITE);
            }

        }

        int exID = getResources().getIdentifier("tvRectanglesCountExample", "id", getPackageName());
        TextView txtEx = (TextView) findViewById(exID);
        if (txtEx != null) {
            txtEx.setTextSize(mMatrixTextSize);
            txtEx.setText(" ");
            txtEx.setTextColor(Color.WHITE);
        }


        if (mRectanglesCountExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvRectanglesCountTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);

            if (txtTimerExTime != null) {
                String txt;
                if (!auto) {
                    txt = "Символ: " + String.valueOf(mRectanglesCountExampleTime);
                } else {
                    txt = "";
                }

                txtTimerExTime.setText(txt);
                //txtTimerExTime.setTextSize(mTextSize);
            }
        }

    }

    private void changeTimer(long elapsedMillis) {

        int timerMaxID = getResources().getIdentifier("tvRectanglesCountTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);


        if (txtTimerMaxTime != null) {
            int time = (int) (mRectanglesCountMaxTime - (elapsedMillis / 1000));
            String txt = "Тест: " + String.valueOf(time);
            txtTimerMaxTime.setText(txt);
            //txtTimerMaxTime.setTextSize(mTextSize);
        }
        if (mRectanglesCountExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvRectanglesCountTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {
                int time = (mRectanglesCountExampleTime - ((int) (((elapsedMillis - mRectanglesCountExBeginTime) / 1000)) % mRectanglesCountExampleTime));
                //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                if (time == mRectanglesCountExampleTime) {
                    //новый пример
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    //txtTimerExTime.setTextSize(mTextSize);
                    mCountMissedAnswers++;
                    createRectanglesCount();
                    drawRectanglesCountTest();

                } else {
                    String txt = "Пример: " + String.valueOf(time);
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

    private void createRectanglesCount() {
        matrix.clear();
        Random random = new Random();

        //while (matrix.size() != mMaxDigits) {

        int indColorMain = Math.abs(random.nextInt() % (AlphabetColors.size() - 1)) + 1;
        int maxCountMain = 0;
        int indColor1 = 0;
        int indColor2 = 0;
        int indColor3 = 0;
        int maxCountOtherColors = random.nextInt(mMaxDigits / 5);
        boolean doIt = true;

        switch (mRectanglesCountFilling) {
            case "A":
                maxCountMain = random.nextInt(mMaxDigits / 10 * 2) + (mMaxDigits / 10);
                break;
            case "B":
                maxCountMain = random.nextInt(mMaxDigits / 10 * 3) + (mMaxDigits / 10 );
                while (doIt) {
                    indColor1 = Math.abs(random.nextInt() % AlphabetColors.size());
                    if (indColor1 != indColorMain) {
                        doIt = false;
                    }

                }
                break;
            case "C":
                maxCountMain = random.nextInt(mMaxDigits / 10 * 3) + (mMaxDigits / 10 );
                while (doIt) {
                    indColor1 = Math.abs(random.nextInt() % AlphabetColors.size());
                    if (indColor1 != indColorMain) {
                        doIt = false;
                    }
                }
                doIt = true;
                while (doIt) {
                    indColor2 = Math.abs(random.nextInt() % AlphabetColors.size());
                    if (indColor2 != indColorMain) {
                        doIt = false;
                    }
                }
                break;
            case "D":
                maxCountMain = random.nextInt(mMaxDigits / 10 * 3) + (mMaxDigits / 10);
                while (doIt) {
                    indColor1 = Math.abs(random.nextInt() % AlphabetColors.size());
                    if (indColor1 != indColorMain) {
                        doIt = false;
                    }
                }
                doIt = true;
                while (doIt) {
                    indColor2 = Math.abs(random.nextInt() % AlphabetColors.size());
                    if (indColor2 != indColorMain) {
                        doIt = false;
                    }
                }
                doIt = true;
                while (doIt) {
                    indColor3 = Math.abs(random.nextInt() % AlphabetColors.size());
                    if (indColor3 != indColorMain) {
                        doIt = false;
                    }
                }
                break;
        }
        Answer=maxCountMain;

        //формируем матрицу нулевую
        for (int i = 0; i < mMaxDigits; i++) {
            matrix.add(0);
        }
        //заполняем главным цветом
        int count = 0;
        while (count <= maxCountMain) {
            int indPlace = random.nextInt(mMaxDigits);
            if (matrix.get(indPlace) == 0) {
                matrix.set(indPlace, indColorMain);
                count++;
            }
        }
        //заполняем остальными цветами
        count = 0;
        if (indColor3 != 0) {
            while (count <= maxCountOtherColors) {
                int indPlace = random.nextInt(mMaxDigits);
                if (matrix.get(indPlace) == 0) {
                    count++;
                    matrix.set(indPlace, indColor3);
                }
            }
        }
        count = 0;
        if (indColor2 != 0) {
            while (count <= maxCountOtherColors) {
                int indPlace = random.nextInt(mMaxDigits);
                if (matrix.get(indPlace) == 0) {
                    count++;
                    matrix.set(indPlace, indColor2);
                }
            }
        }
        count = 0;
        if (indColor1 != 0) {
            while (count <= maxCountOtherColors) {
                int indPlace = random.nextInt(mMaxDigits);
                if (matrix.get(indPlace) == 0) {
                    count++;
                    matrix.set(indPlace, indColor1);
                }
            }
        }

        //}
    }

    private void drawRectanglesCountTest() {
        mCountRightAnswers = 0;
        //для матриц тестов id начинается со 300
        TableLayout layout = (TableLayout) findViewById(R.id.tableRectanglesCount);
        //layout.removeAllViews();

//        layout.setStretchAllColumns(true);
//        layout.setShrinkAllColumns(true);

        //layout.setBackgroundResource(R.drawable.textview_border1);
//        int mHeight = layout.getHeight() / mMatrixSize;
//        int mWidth = layout.getWidth() / mMatrixSize;


        for (Integer numString = 1; numString <= mRectanglesCountSizeHeight; numString++) {
            TableRow row = new TableRow(this);
            row.setMinimumHeight(mHeight);
            row.setMinimumWidth(mWidth);
            row.setGravity(Gravity.CENTER);

            //row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0,1));

//            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(mWidth,mHeight);
//            row.setLayoutParams(params);

            for (int numColumn = 1; numColumn <= mRectanglesCountSizeWidth; numColumn++) {

                TextView txt = (TextView) findViewById(300 + (numString - 1) * mRectanglesCountSizeHeight + numColumn);
                if (txt == null) {
                    txt = new TextView(this);
                    txt.setId(300 + (numString - 1) * mRectanglesCountSizeHeight + numColumn);
                    txt.setMinimumHeight(mHeight);
                    int mIndColor = matrix.get((numString - 1) * mRectanglesCountSizeHeight + numColumn - 1);
                    if (mIndColor != 0) {
                        int mColor=AlphabetColors.get(mIndColor);
                        Drawable dr = getResources().getDrawable(R.drawable.textview_border1);
                        //Drawable dr = view.getBackground();
                        ColorFilter filter = new LightingColorFilter(Color.WHITE, mColor);
                        dr.setColorFilter(filter);
                        txt.setBackgroundDrawable(dr);
                    } else {
                        Drawable dr = getResources().getDrawable(R.drawable.textview_border1);
                        txt.setBackgroundDrawable(dr);
                    }
                    row.addView(txt);
                }
                // Заполняется цветами


                txt.setGravity(Gravity.CENTER);
                //txt.setBackgroundResource(R.drawable.textview_border);

            }
            layout.addView(row);
        }
        //setContentView(layout);
        //int r=0;



    }


    public void RectanglesCountStartPause_onClick(View view) {


        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());

                getPreferencesFromFile();
                RectanglesCountClear();
                createRectanglesCount();
                drawRectanglesCountTest();

                int timerID = getResources().getIdentifier("tvMatrixTimerMaxTime", "id", getPackageName());
                TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                if (txtTimerMaxTime != null) {
                    //txtTimerMaxTime.setTextSize(mTextSize);
                    String txt = "Тест: " + String.valueOf(mRectanglesCountMaxTime);
                    txtTimerMaxTime.setText(txt);
                    //txtTimerMaxTime.setTextSize(mTextSize);
                }
                mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        elapsedMillis = SystemClock.elapsedRealtime()
                                - mChronometer.getBase();

                        if (mRectanglesCountMaxTime - (elapsedMillis / 1000) < 1) {
                            timerStop(true);
                        }
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
            ChangeButtonText("buttonRectanglesCountStartPause", "Пауза");

        } else {

            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;

            ChangeButtonText("buttonRectanglesCountStartPause", "Старт");
        }

    }

    private void ChangeButtonText(String ButtonID, String ButtonText) {

        int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        Button but = (Button) findViewById(resID);
        if (but != null) {
            but.setText(ButtonText);
        }
    }

    public void RectanglesCountOptions_onClick(View view) {

        Intent intent = new Intent(RectanglesCountActivity.this, RectanglesCountActivityOptions.class);
        startActivity(intent);

    }


    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void getPreferencesFromFile() {
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_FILLING)) {
            // Получаем язык из настроек
            mRectanglesCountFilling = mSettings.getString(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_FILLING, "B");
        } else {
            mRectanglesCountFilling = "B";
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_SIZE_WIDTH)) {
            // Получаем язык из настроек
            mRectanglesCountSizeWidth = mSettings.getInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_SIZE_WIDTH, 6);
        } else {
            mRectanglesCountSizeWidth = 6;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_SIZE_HEIGHT)) {
            // Получаем язык из настроек
            mRectanglesCountSizeHeight = mSettings.getInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_SIZE_HEIGHT, 6);
        } else {
            mRectanglesCountSizeHeight = 6;
        }
        mMaxDigits = mRectanglesCountSizeHeight * mRectanglesCountSizeWidth;

        if (mSettings.contains(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_TEST_TIME)) {
            // Получаем язык из настроек
            mRectanglesCountMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_TEST_TIME, 60);
        } else {
            mRectanglesCountMaxTime = 60;
        }
        if (mSettings.contains(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_EXAMPLE_TIME)) {
            // Получаем язык из настроек
            mRectanglesCountExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_EXAMPLE_TIME, 0);
        } else {
            mRectanglesCountExampleTime = 0;
        }
    }


}