package ru.whydt.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class StrupActivity extends AppCompatActivity {

    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private final int mStrupExamples = 45;
    private final String mWordColors[] = new String[5];

    //настройки
    private SharedPreferences mSettings;
    private String StrupLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_strup);



    }

    public void strupForm_onClick(View view) {
        strupClear();
        getPreferencesFromFile();
        ArrayList<StrupExample> strupExamples = createStrupExamples();
        drawStrupTest(strupExamples);
    }

    private void drawStrupTest(ArrayList<StrupExample> strupExamples) {

        int mCountStrings = 15;

        StrupExample currentEx;
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        int mHeight = frame.getHeight()*98/100  / mCountStrings;
        int mWidth = frame.getWidth() * 90 / 100 / (mStrupExamples / mCountStrings);
        int numColumn = 0;
        int numString = 0;
        for (Integer i = 1; i <= mStrupExamples; i++) {
            TextView txt = (TextView) findViewById(200 + i);
            if (txt == null) {
                txt = new TextView(this);
                txt.setId(200 + i);
                numColumn = (i - 1) / mCountStrings;
                numString = (i - 1) % mCountStrings;
                //txt.setPadding((numColumn) * mWidth , numString * mHeight, 0, 0);
                txt.setPadding((numColumn) * mWidth + frame.getWidth() / 100 *10,frame.getHeight()*2/100+ numString * mHeight, 0, 0);
                //txt.setPadding((i-(i-1)%mCountStrings)/(mCountStrings/2)*72+25,150+(i-1)%mCountStrings*30,0,0);
                frame.addView(txt);
            } else {
                txt.setVisibility(View.VISIBLE);
            }

            currentEx = strupExamples.get(i - 1);
            txt.setText(currentEx.getWord());
            txt.setTextColor(currentEx.getColor());

            //txt.setTextSize(Math.min(mWidth,mHeight)/6);
            txt.setTextSize(TypedValue.COMPLEX_UNIT_SP,Math.min(mWidth, mHeight) / 2/getApplicationContext().getResources().getDisplayMetrics().density);
        }
    }

    public void strupClear_onClick(View view) {

        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.ground1);
        //strupClear();
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount=0;
        mChronometerIsWorking = false;

        ChangeButtonText("buttonStrupStartPause","Старт");

    }

    private void strupClear() {

        for (int i = 1; i <= mStrupExamples; i++) {
            TextView txt = (TextView) findViewById(200 + i);

            if (txt != null) {
                txt.setText("");
            }

        }
       // ChangeButtonText("buttonStrupStartPause", "Старт");

        //txt.setVisibility(View.GONE);


    }

    private ArrayList<StrupExample> createStrupExamples() {
        ArrayList<StrupExample> strupExamples = new ArrayList<>();
        int newColor = Color.WHITE;

        Random random = new Random();

        while (strupExamples.size() != mStrupExamples) {

            int indexColor = random.nextInt();
            switch (Math.abs(indexColor % 4)) {
                case 0:
                    newColor = Color.parseColor("#FFD8CD02");
                    break;//коричневый
                case 1:
                    newColor = Color.RED;
                    break;
                case 2:
                    newColor = Color.parseColor("#FF11B131");
                    break;//зеленый
                case 3:
                    newColor = Color.BLUE;
                    break;
                case 4:
                    newColor = Color.parseColor("#FFD8CD02");
                    break;//коричневый
            }
            int indWord = Math.abs(random.nextInt() % 4);
            int indPlace = (strupExamples.size() == 0 ? random.nextInt() : random.nextInt(strupExamples.size()));
            StrupExample newEx = new StrupExample(mWordColors[indWord], newColor);
            strupExamples.add((strupExamples.size() == 0 ? 0 : indPlace % strupExamples.size()), newEx);
        }

        return strupExamples;
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
            ChangeButtonText("buttonStrupStartPause", "Пауза");
        } else {

            //mChronometerBase=mChronometer.getBase();
            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;
            ChangeButtonText("buttonStrupStartPause", "Старт");
        }

    }

    public void strupOptions_onClick(View view) {

        Intent intent = new Intent(StrupActivity.this, StrupActivityOptions.class);
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
        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            StrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            StrupLang="Ru";
        }
        switch (StrupLang) {
            case "Ru":
                mWordColors[0] = "КРАСНЫЙ";
                mWordColors[1] = "ЖЕЛТЫЙ";
                mWordColors[2] = "ЗЕЛЕНЫЙ";
                mWordColors[3] = "СИНИЙ";
                mWordColors[4] = "СИНИЙ";
                break;
            case "En":
                mWordColors[0] = "RED";
                mWordColors[1] = "YELLOW";
                mWordColors[2] = "GREEN";
                mWordColors[3] = "BLUE";
                mWordColors[4] = "BLUE";
            default:
                break;
        }
    }

    private class StrupExample {
        private String word;
        private int color;

        public StrupExample(String word, int color) {
            this.word = word;
            this.color = color;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

}