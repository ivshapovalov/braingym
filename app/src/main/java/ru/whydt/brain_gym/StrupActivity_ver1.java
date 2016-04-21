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

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.Random;


public class StrupActivity_ver1 extends AppCompatActivity {


    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private final int mStrupExamples = 8;

    private ArrayList<String> alphabetWords = new ArrayList<>();
    private ArrayList<Integer> alphabetColors = new ArrayList<>();
    private ArrayList<Integer> arrColors = new ArrayList();
    private ArrayList<Integer> arrWords = new ArrayList();
    private int answer;
    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    //настройки

    private String mStrupLang;
    private int mStrupMaxTime;
    private int mStrupExampleTime;
    private String mStrupExampleType;
    private int mTextSize = 0;

    private long mStrupExBeginTime = 0;

    private long elapsedMillis;


    private enum typeExample {
        WORD, COLOR
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup_ver1);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_strup);

        //start_pause();
    }

    public void strupForm_ver1_onClick(View view) {
        strupVer1Clear();
        getPreferencesFromFile();
        createStrupExamples();
        drawStrupTest();
    }

    private void drawStrupTest() {
        TableLayout frame = (TableLayout) findViewById(R.id.groundStrup_ver1);
        int mWidth = frame.getWidth();
        int mHeight = frame.getHeight();
        for (Integer i = 1; i <= mStrupExamples; i++) {
            int resID = getResources().getIdentifier("textViewColor" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);
            if (txt != null) {

                int curColor = alphabetColors.get(arrColors.get(i - 1));
                //int curColor = alphabetColors.get(0);
                String curWord = alphabetWords.get(arrWords.get(i - 1));
                String text = curWord;
                mTextSize = (int)(Math.min(mWidth, mHeight) / 18/getApplicationContext().getResources().getDisplayMetrics().density);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                txt.setText(text);
                txt.setTextColor(curColor);


            }
        }
    }

    public void strupVer1Clear_onClick(View view) {

        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.ground1);
        //strupClear();
        timerStop();

    }

    private void timerStop() {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;
        mCountRightAnswers = 0;
        mCountAllAnswers = 0;
        mStrupExBeginTime=0;

        ChangeButtonText("buttonStrupStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("txtTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            txtTimerMaxTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            String txt = "Тест: " + String.valueOf(mStrupMaxTime);
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("textViewAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            txtAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            txtAnswer.setText("");
        }

        if (mStrupExampleTime != 0) {
            int timerExID = getResources().getIdentifier("txtTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);

            if (txtTimerExTime != null) {
                String txt = "Пример: " + String.valueOf(mStrupExampleTime);
                txtTimerExTime.setText(txt);
                txtTimerExTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            }
        }
    }

    private void strupVer1Clear() {

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("textViewColor" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);

            }

        }

        int exID = getResources().getIdentifier("textViewExample", "id", getPackageName());
        TextView txt1 = (TextView) findViewById(exID);

        if (txt1 != null) {
            txt1.setText("  ");
            txt1.setBackgroundResource(R.drawable.rounded_corners1);
            txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);

        }

        int typeID = getResources().getIdentifier("textViewType", "id", getPackageName());
        TextView txt2 = (TextView) findViewById(typeID);

        if (txt2 != null) {
            txt2.setText("  ");
            txt2.setBackgroundResource(R.drawable.rounded_corners1);
            txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        }

        int trowID = getResources().getIdentifier("trow1", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);
        }

        int table1ID = getResources().getIdentifier("table1", "id", getPackageName());
        TableLayout table1 = (TableLayout) findViewById(table1ID);

        if (table1 != null) {
            table1.setBackgroundResource(R.drawable.rounded_corners1);
        }


    }

    private void createStrupExamples() {
        Random random = new Random();

        arrColors.clear();
        arrWords.clear();

        boolean doItWord = true;
        while (arrColors.size() != 8) {
            int indexColor = Math.abs(random.nextInt() % 8);
            if (!arrColors.contains(indexColor)) {
                int indPlace = Math.abs((arrColors.size() == 0 ? random.nextInt() : random.nextInt(arrColors.size())));
                // strupExamples.add((strupExamples.size() == 0 ? 0 : indPlace % strupExamples.size()), newEx);
                arrColors.add((arrColors.size() == 0 ? 0 : indPlace % arrColors.size()), indexColor);

            }
        }
        while (arrWords.size() != 8) {

            while (doItWord) {
                int indexWord = Math.abs(random.nextInt() % 8);
                if (!arrWords.contains(indexWord)) {
                    int indPlace = Math.abs((arrWords.size() == 0 ? random.nextInt() : random.nextInt(arrWords.size())));
                    //проверяем, не находится ли на том же месте в массиве цветов этот же цвет
                    indPlace = (arrWords.size() == 0 ? 0 : indPlace % arrWords.size());
                    if (indPlace != arrColors.indexOf(indexWord)) {
                        arrWords.add(indPlace, indexWord);
                        doItWord = false;
                    }
                }
            }
            doItWord = true;
        }


//        arrColors.clear();
//        arrColors.add(2);arrColors.add(3);arrColors.add(1);arrColors.add(0);
//        arrColors.add(5);arrColors.add(4);arrColors.add(7);arrColors.add(6);
//
//        arrWords.clear();
//        arrWords.add(7);arrWords.add(6);arrWords.add(5);arrWords.add(2);
//        arrWords.add(4);arrWords.add(1);arrWords.add(3);arrWords.add(0);


    }

    private void changeTimer(long elapsedMillis) {
        int timerMaxID = getResources().getIdentifier("txtTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);
        if (txtTimerMaxTime != null) {
            int time = (int) (mStrupMaxTime - (elapsedMillis / 1000));
            if (time == 0) {
                txtTimerMaxTime.setText("Тест окончен!");
                txtTimerMaxTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                int timerExID = getResources().getIdentifier("txtTimerExTime", "id", getPackageName());
                TextView txtTimerExTime = (TextView) findViewById(timerExID);
                if (txtTimerExTime != null) {
                    txtTimerExTime.setText("");
                }

            } else {
                String txt = "Тест: " + String.valueOf(time);
                txtTimerMaxTime.setText(txt);
                txtTimerMaxTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            }

        }
        if (mStrupExampleTime != 0) {
            int timerExID = getResources().getIdentifier("txtTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {
                int time = (mStrupExampleTime - ((int) (((elapsedMillis - mStrupExBeginTime) / 1000)) % mStrupExampleTime));
                //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                if (time == mStrupExampleTime) {
                    //новый пример
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                    mCountAllAnswers++;
                    int answerID = getResources().getIdentifier("textViewAnswers", "id", getPackageName());
                    TextView txtAnswer = (TextView) findViewById(answerID);
                    if (txtAnswer != null) {
                        String txt1 = String.valueOf(mCountRightAnswers) + "/" + String.valueOf(mCountAllAnswers);
                        txtAnswer.setText(txt1);
                        txtAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                    }

                    showNextExample();


                } else {
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                }

            }
        } else {
            int timerExID = getResources().getIdentifier("txtTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {

                txtTimerExTime.setText(" ");
                txtTimerExTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            }
        }
    }


    public void startPause_onClick(View view) {

        start_pause();

    }

    private void start_pause() {

        if (!mChronometerIsWorking) {
            if (mChronometerCount == 0) {
                mChronometer.setBase(SystemClock.elapsedRealtime());

                strupVer1Clear();
                getPreferencesFromFile();
                createStrupExamples();
                drawStrupTest();

                int timerID = getResources().getIdentifier("txtTimerMaxTime", "id", getPackageName());
                TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                if (txtTimerMaxTime != null) {
                    txtTimerMaxTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                    String txt = "Тест: " + String.valueOf(mStrupMaxTime);
                    txtTimerMaxTime.setText(txt);
                    txtTimerMaxTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                }
                mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        elapsedMillis = SystemClock.elapsedRealtime()
                                - mChronometer.getBase();

                        if (mStrupMaxTime - (elapsedMillis / 1000) < 1) {
                            timerStop();
                        }
                        if (elapsedMillis > 1000) {

                            changeTimer(elapsedMillis);

                            //elapsedMillis=0;
                        }
                    }
                });

                showNextExample();
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

    private void showNextExample() {

        Random random = new Random();
        int indexType = Math.abs(random.nextInt() % 2);
        typeExample type = null;

        switch (mStrupExampleType) {
            case "RANDOM":

                switch (indexType) {
                    case 0:
                        type = typeExample.WORD;
                        break;
                    case 1:
                        type = typeExample.COLOR;
                        break;
                }
                break;
            case "COLOR":
                type = typeExample.COLOR;
                break;
            case "WORD":
                type = typeExample.WORD;
                break;
        }

        int typeID = getResources().getIdentifier("textViewType", "id", getPackageName());
        TextView txtType = (TextView) findViewById(typeID);
        if (txtType != null) {
            //тип операции рандомно
            if (type == typeExample.COLOR) {
                txtType.setText("От цвета ищем слово");
            } else {
                txtType.setText("От слова ищем цвет");
            }
            txtType.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        }
        //type = typeExample.COLOR;
        int indexColor = 0;
        int indexWord = 0;
        boolean doIt = true;
        while (doIt) {
            indexColor = Math.abs(random.nextInt() % 8);
            indexWord = Math.abs(random.nextInt() % 8);
            if (indexColor != indexWord) {
                doIt = false;
            }
        }
        int exampleID = getResources().getIdentifier("textViewExample", "id", getPackageName());
        TextView txtExample = (TextView) findViewById(exampleID);
        if (txtExample != null) {
            //тип операции рандомно
//            indexWord=0;
//            indexColor=7;
            txtExample.setText(alphabetWords.get(indexWord));
            txtExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            txtExample.setTextColor(alphabetColors.get(indexColor));

            if (type == typeExample.WORD) {
                answer = arrColors.indexOf(indexWord);

            } else if (type == typeExample.COLOR) {
                answer = arrWords.indexOf(indexColor);
            }
        }
    }


    public void txt_onClick(View view) {
        //int a = Integer.valueOf(String.valueOf(((AppCompatTextView) view).getText().charAt(0)));
        String id = view.getResources().getResourceEntryName(view.getId());
        int a = Integer.valueOf(id.substring(id.length() - 1, id.length()));
        if (a - 1 == answer) {

            mCountRightAnswers++;
        }
        mCountAllAnswers++;
        mStrupExBeginTime = elapsedMillis;
        int timerExID = getResources().getIdentifier("txtTimerExTime", "id", getPackageName());
        TextView txtTimerExTime = (TextView) findViewById(timerExID);
        if (mStrupExampleTime != 0) {
            if (txtTimerExTime != null) {
                String txt = "Пример: " + String.valueOf(mStrupExampleTime);
                txtTimerExTime.setText(txt);
                txtTimerExTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            }
        }
        int answerID = getResources().getIdentifier("textViewAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            String txt = String.valueOf(mCountRightAnswers) + "/" + String.valueOf(mCountAllAnswers);
            txtAnswer.setText(txt);
            txtAnswer.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        }

        showNextExample();
    }

    public void strupVer1Options_onClick(View view) {

        Intent intent = new Intent(StrupActivity_ver1.this, StrupActivityOptions_ver1.class);
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

        SharedPreferences mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_VER1_TEST_TIME)) {
            mStrupMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_STRUP_VER1_TEST_TIME, 60);
        } else {
            mStrupMaxTime = 60;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME)) {
            mStrupExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME, 0);
        } else {
            mStrupExampleTime = 0;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE)) {
            mStrupExampleType = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE, "Random");
        } else {
            mStrupExampleType = "Random";
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            mStrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            mStrupLang = "Ru";
        }
        alphabetColors.clear();
        alphabetColors.add(Color.RED);
        alphabetColors.add(Color.parseColor("#FFA500"));
        alphabetColors.add(Color.parseColor("#53b814"));
        alphabetColors.add(Color.parseColor("#FF7B15CE"));
        alphabetColors.add(Color.BLUE);
        alphabetColors.add(Color.GRAY);
        alphabetColors.add(Color.parseColor("#EE82EE"));
        alphabetColors.add(Color.parseColor("#8B4513"));
        alphabetWords.clear();
        switch (mStrupLang) {

            case "Ru":
                alphabetWords.add("КРАСНЫЙ");
                alphabetWords.add("ОРАНЖЕВЫЙ");
                alphabetWords.add("ЗЕЛЕНЫЙ");
                alphabetWords.add("ФИОЛЕТОВЫЙ");
                alphabetWords.add("СИНИЙ");
                alphabetWords.add("СЕРЫЙ");
                alphabetWords.add("РОЗОВЫЙ");
                alphabetWords.add("КОРИЧНЕВЫЙ");

            case "En":
                alphabetWords.add("RED");
                alphabetWords.add("ORANGE");
                alphabetWords.add("GREEN");
                alphabetWords.add("VIOLET");
                alphabetWords.add("BLUE");
                alphabetWords.add("GRAY");
                alphabetWords.add("ROSE");
                alphabetWords.add("BROWN");
            default:
                break;
        }

    }


}