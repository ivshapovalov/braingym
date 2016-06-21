package ru.brainworkout.brain_gym.strup_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import ru.brainworkout.brain_gym.MainActivity;
import ru.brainworkout.brain_gym.R;


public class StrupActivity extends AppCompatActivity {

    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private static final int STRUP_EXAMPLES_COUNT = 45;
    private static String mArrayColorNames[];
    private static int mArrayColorValues[];
    private static final int STRINGS_COUNT = 15;
    private ArrayList<StrupExample> strupExamples = new ArrayList<>();

    //настройки
    private SharedPreferences mSettings;
    private String mStrupLang;
    private int mStrupFontSizeChange;
    private int mStrupTextSize;
    private int mStrupColorsCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //id - 200+i

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_strup);
    }

    private void drawStrupTest() {

        StrupExample currentEx;
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        int mHeight = frame.getHeight() * 98 / 100 / STRINGS_COUNT;
        int mWidth = frame.getWidth() * 90 / 100 / (STRUP_EXAMPLES_COUNT / STRINGS_COUNT);
        int numColumn = 0;
        int numString = 0;
        for (Integer i = 1; i <= STRUP_EXAMPLES_COUNT; i++) {
            TextView txt = (TextView) findViewById(200 + i);
            if (txt == null) {
                txt = new TextView(this);
                txt.setId(200 + i);
                numColumn = (i - 1) / STRINGS_COUNT;
                numString = (i - 1) % STRINGS_COUNT;
                //txt.setPadding((numColumn) * mWidth , numString * mHeight, 0, 0);
                txt.setPadding((numColumn) * mWidth + frame.getWidth() / 100 * 10, frame.getHeight() * 2 / 100 + numString * mHeight, 0, 0);
                //txt.setPadding((i-(i-1)%mCountStrings)/(mCountStrings/2)*72+25,150+(i-1)%mCountStrings*30,0,0);
                frame.addView(txt);
            } else {
                txt.setVisibility(View.VISIBLE);
            }

            currentEx = strupExamples.get(i - 1);
            txt.setText(mArrayColorNames[currentEx.getIndWord()]);
            txt.setTextColor(mArrayColorValues[currentEx.getIndColor()]);

            //txt.setTextSize(Math.min(mWidth,mHeight)/6);
            mStrupTextSize = (int) (Math.min(mWidth, mHeight) / 2 / getApplicationContext().getResources().getDisplayMetrics().density) + mStrupFontSizeChange;
            txt.setTextSize(mStrupTextSize);
        }
    }

    public void strupClear_onClick(View view) {


        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;

        ChangeButtonText("buttonStrupStartPause", "Старт");

        strupClear();

    }

    private void strupClear() {

        for (int i = 1; i <= STRUP_EXAMPLES_COUNT; i++) {
            TextView txt = (TextView) findViewById(200 + i);

            if (txt != null) {
                txt.setText("");
            }

        }
    }

    private ArrayList<StrupExample> createStrupExamples() {
        strupExamples.clear();

        Random random = new Random();


        while (strupExamples.size() != STRUP_EXAMPLES_COUNT) {

            int indColor = Math.abs(random.nextInt() % (mStrupColorsCount + 1));
            int indWord = Math.abs(random.nextInt() % (mStrupColorsCount + 1));
            indWord = (indWord==mStrupColorsCount ? 0 : indWord);
            indColor=(indColor == mStrupColorsCount ? 0 : indColor);
//            System.out.println("size " + strupExamples.size());
//            System.out.println("color " + indColor);
//            System.out.println("word " + indWord);

            if (strupExamples.size() != 0 && mArrayColorNames[indWord].equals(strupExamples.get(strupExamples.size() - 1).getIndWord())
                    ) {
                continue;
            }
            if (strupExamples.size() >= STRINGS_COUNT) {
                if (mArrayColorNames[indWord].equals(strupExamples.get(strupExamples.size() - (STRINGS_COUNT)).getIndWord()) ||
                        mArrayColorValues[indColor] == strupExamples.get(strupExamples.size() - (STRINGS_COUNT)).getIndColor())
                {
                    continue;
                }
            }
            if (strupExamples.size() != 0 && (indColor == strupExamples.get(strupExamples.size() - 1).getIndColor())
                    ) {
                continue;
            }

            StrupExample newEx = new StrupExample(indWord, indColor);

            strupExamples.add(newEx);

        }
        //Collections.shuffle(strupExamples);

        return strupExamples;
    }

    public void startPause_onClick(View view) {

        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());

                strupClear();
                getPreferencesFromFile();
                strupExamples = createStrupExamples();
                drawStrupTest();
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
        intent.putExtra("mStrupTextSize", mStrupTextSize - mStrupFontSizeChange);
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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
            mStrupFontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE, 0);
        } else {
            mStrupFontSizeChange = 0;
        }
        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            mStrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            mStrupLang = "Ru";
        }
        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_COLORS_COUNT)) {
            // Получаем язык из настроек
            mStrupColorsCount = mSettings.getInt(MainActivity.APP_PREFERENCES_STRUP_COLORS_COUNT, 4);
        } else {
            mStrupColorsCount = 4;
        }
        formArrays();


    }

    private void formArrays() {
        mArrayColorValues = new int[mStrupColorsCount + 1];
        mArrayColorNames = new String[mStrupColorsCount + 1];
        try {
            switch (mStrupLang) {
                case "Ru":
                    mArrayColorNames[0] = "КРАСНЫЙ";
                    mArrayColorNames[1] = "ЖЕЛТЫЙ";
                    mArrayColorNames[2] = "ЗЕЛЕНЫЙ";
                    mArrayColorNames[3] = "СИНИЙ";
                    mArrayColorNames[4] = "СЕРЫЙ";
                    mArrayColorNames[5] = "КОРИЧНЕВЫЙ";
                    mArrayColorNames[6] = "ФИОЛЕТОВЫЙ";

                    break;
                case "En":
                    mArrayColorNames[0] = "RED";
                    mArrayColorNames[1] = "YELLOW";
                    mArrayColorNames[2] = "GREEN";
                    mArrayColorNames[3] = "BLUE";
                    mArrayColorNames[4] = "GREY";
                    mArrayColorNames[5] = "BROWN";
                    mArrayColorNames[6] = "VIOLET";


                default:
                    break;
            }
        } catch (Exception e) {

        }
        try {
            mArrayColorValues[0] = Color.parseColor("#FF2020");
            mArrayColorValues[1] = Color.parseColor("#FFD8CD02");
            mArrayColorValues[2] = Color.parseColor("#006400");
            mArrayColorValues[3] = Color.parseColor("#00BFFF");
            mArrayColorValues[4] = Color.parseColor("#9C9C9C");
            mArrayColorValues[5] = Color.parseColor("#A56A2A");
            mArrayColorValues[6] = Color.parseColor("#836FFF");


        } catch (Exception e) {

        }
    }

    private class StrupExample {
        private int indWord;
        private int indColor;

        public StrupExample(int indWord, int indColor) {
            this.indWord = indWord;
            this.indColor = indColor;
        }

        public int getIndColor() {
            return indColor;
        }

        public void setIndColor(int indColor) {
            this.indColor = indColor;
        }

        public int getIndWord() {
            return indWord;
        }

        public void setIndWord(int indWord) {
            this.indWord = indWord;
        }
    }

}