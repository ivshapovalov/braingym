package ru.brainworkout.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MissingSymbolActivity extends AppCompatActivity {

    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private final int mMissingSymbolCountAnswers = 8;

    private ArrayList<Integer> arrAnswers = new ArrayList<>();
    private ArrayList<Integer> arrExamples = new ArrayList<>();
    private int indAnswer;
    private String Question;
    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    //настройки

    private String mMissingSymbolLang;
    private int mMissingSymbolMaxTime;
    private int mMissingSymbolExampleTime;

    private int mTextSize = 0;
    private long mMissingSymbolExBeginTime = 0;
    private long elapsedMillis;


    private ArrayList<String> AlphabetRu;
    private ArrayList<String> AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_symbol);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_missing_symbol);


        AlphabetRu= Utils.AlphabetRu();
        AlphabetEn= Utils.AlphabetEn();
    }


    public void MissingSymbolClear_onClick(View view) {

        timerStop(false);
    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;
        mCountRightAnswers = 0;
        mCountAllAnswers = 0;
        mMissingSymbolExBeginTime = 0;

        ChangeButtonText("buttonMissingSymbolStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvMissingSymbolTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            txtTimerMaxTime.setTextSize(mTextSize);
            String txt;
            if (!auto) {
                txt = "Тест: " + String.valueOf(mMissingSymbolMaxTime);
            } else {
                txt = "Тест окончен";
            }
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("tvMissingSymbolAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setText("");
            }
        }

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMissingSymbolAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
               // txt.setTextSize(mTextSize);

            }

        }

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMissingSymbolExample" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                //txt.setTextSize(mTextSize);
                //txt.setTextColor(Color.parseColor("#FF6D6464"));

            }

        }

        if (mMissingSymbolExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvMissingSymbolTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);

            if (txtTimerExTime != null) {
                String txt;
                if (!auto) {
                    txt = "Пример: " + String.valueOf(mMissingSymbolExampleTime);
                } else {
                    txt = "";
                }

                txtTimerExTime.setText(txt);
                txtTimerExTime.setTextSize(mTextSize);
            }
        }
    }

    private void missingSymbolClear() {

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMissingSymbolAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                txt.setTextSize(mTextSize);

            }

        }

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMissingSymbolExample" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                txt.setTextSize(mTextSize);
                txt.setTextColor(Color.parseColor("#FF6D6464"));

            }

        }

        int trowID = getResources().getIdentifier("trowMissingSymbol", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);
        }

        for (int i = 1; i <= mMissingSymbolCountAnswers; i++) {
            int ansID = getResources().getIdentifier("tvMissingSymbolAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txtAns = (TextView) findViewById(ansID);

            if (txtAns != null) {
                txtAns.setText("  ");
                txtAns.setBackgroundResource(R.drawable.rounded_corners1);
                txtAns.setTextSize(mTextSize);
                txtAns.setPadding(0,mTextSize,0,mTextSize);

            }
        }
//        int table1ID = getResources().getIdentifier("tableMissingSymbolAnswers", "id", getPackageName());
//        TableLayout table1 = (TableLayout) findViewById(table1ID);
//
//        if (table1 != null) {
//            table1.setBackgroundResource(R.drawable.rounded_corners1);
//        }

    }


    private void changeTimer(long elapsedMillis) {
        int timerMaxID = getResources().getIdentifier("tvMissingSymbolTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);
        if (txtTimerMaxTime != null) {
            int time = (int) (mMissingSymbolMaxTime - (elapsedMillis / 1000));
            String txt = "Тест: " + String.valueOf(time);
            txtTimerMaxTime.setText(txt);
            txtTimerMaxTime.setTextSize(mTextSize);
        }
        if (mMissingSymbolExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvMissingSymbolTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {
                int time = (mMissingSymbolExampleTime - ((int) (((elapsedMillis - mMissingSymbolExBeginTime) / 1000)) % mMissingSymbolExampleTime));
                //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                if (time == mMissingSymbolExampleTime) {
                    //новый пример
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                    mCountAllAnswers++;
                    int answerID = getResources().getIdentifier("tvMissingSymbolAnswers", "id", getPackageName());
                    TextView txtAnswer = (TextView) findViewById(answerID);
                    if (txtAnswer != null) {
                        String txt1 = String.valueOf(mCountRightAnswers) + "/" + String.valueOf(mCountAllAnswers);
                        txtAnswer.setText(txt1);
                        txtAnswer.setTextSize(mTextSize);
                    }

                    createExample();


                } else {
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                }

            }
        } else {
            int timerExID = getResources().getIdentifier("tvMissingSymbolTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {

                txtTimerExTime.setText(" ");
                txtTimerExTime.setTextSize(mTextSize);
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

                TableLayout frame = (TableLayout) findViewById(R.id.groundMissingSymbol);
                int mWidth;
                int mHeight;
                if (frame != null) {
                    mWidth = frame.getWidth();
                    mHeight = frame.getHeight();
                } else {
                    mWidth = 0;
                    mHeight = 0;
                }
                mTextSize = (int) (Math.min(mWidth, mHeight) / 20 / getApplicationContext().getResources().getDisplayMetrics().density);

                missingSymbolClear();
                getPreferencesFromFile();

                int timerID = getResources().getIdentifier("tvMissingSymbolTimerMaxTime", "id", getPackageName());
                TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                if (txtTimerMaxTime != null) {
                    txtTimerMaxTime.setTextSize(mTextSize);
                    String txt = "Тест: " + String.valueOf(mMissingSymbolMaxTime);
                    txtTimerMaxTime.setText(txt);
                    txtTimerMaxTime.setTextSize(mTextSize);
                }
                mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        elapsedMillis = SystemClock.elapsedRealtime()
                                - mChronometer.getBase();

                        if (mMissingSymbolMaxTime - (elapsedMillis / 1000) < 1) {
                            timerStop(true);
                        }
                        if (elapsedMillis > 1000) {

                            changeTimer(elapsedMillis);

                            //elapsedMillis=0;
                        }
                    }
                });

                createExample();
            } else {
                mChronometer.setBase(SystemClock.elapsedRealtime() - mChronometerCount);
            }

            mChronometer.start();
            mChronometerIsWorking = true;
            ChangeButtonText("buttonMissingSymbolStartPause", "Пауза");


        } else {

            //mChronometerBase=mChronometer.getBase();
            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;
            ChangeButtonText("buttonMissingSymbolStartPause", "Старт");
        }
    }

    private void createExample() {

        Random random = new Random();

        int mBeginDigit=0;
        if ("Digit".equals(mMissingSymbolLang)) {
            mBeginDigit = Math.abs(random.nextInt(100));
        } else if ("Ru".equals(mMissingSymbolLang)) {
            mBeginDigit = Math.abs(random.nextInt() % (AlphabetRu.size() - mMissingSymbolCountAnswers));
        } else if ("En".equals(mMissingSymbolLang)) {
            mBeginDigit = Math.abs(random.nextInt() % (AlphabetEn.size() - mMissingSymbolCountAnswers));
        }

        int answer = Math.abs(random.nextInt() % (mMissingSymbolCountAnswers-2)) + mBeginDigit+1;

        arrAnswers.clear();
        arrExamples.clear();

        while (arrAnswers.size() != mMissingSymbolCountAnswers) {
            int newDigit = Math.abs(random.nextInt() % mMissingSymbolCountAnswers) + mBeginDigit;
            if (!arrAnswers.contains(newDigit)) {
                int indPlace = Math.abs((arrAnswers.size() == 0 ? random.nextInt() : random.nextInt(arrAnswers.size())));
                arrAnswers.add((arrAnswers.size() == 0 ? 0 : indPlace % arrAnswers.size()), newDigit);
            }
        }
        indAnswer = arrAnswers.indexOf(answer);

        //arrExamples.add(0,mBeginDigit);

        int ind=0;
        while (arrExamples.size() != mMissingSymbolCountAnswers) {
            //int newDigit = Math.abs(random.nextInt() % (mMissingSymbolCountAnswers -2)) + mBeginDigit+1;
            int newDigit=mBeginDigit+ind;
            ind++;
            if (newDigit != answer)
            {
                int indPlace = Math.abs(random.nextInt());
                arrExamples.add((arrExamples.size() == 0 ? 0 : indPlace % (arrExamples.size())), newDigit);
           }
        }
        //arrExamples.add(7,mBeginDigit+mMissingSymbolCountAnswers);

//        while (arrExamples.size() != mMissingSymbolCountAnswers-1) {
//            int newDigit = Math.abs(random.nextInt() % (mMissingSymbolCountAnswers -2)) + mBeginDigit+1;
//
//            if (newDigit == answer) {
//            } else {
//                if (!arrExamples.contains(newDigit)) {
//                    //int indPlace = Math.abs((arrExamples.size() == 0 ? random.nextInt() : random.nextInt(arrExamples.size())));
//                    //arrExamples.add((arrExamples.size() == 0 ? 0 : indPlace % arrExamples.size()), newDigit);
//                    arrExamples.add(newDigit);
//                }
//            }
//        }

        //первое и последнее числа


//        Question = "";
//        for (int i = 0; i < arrExamples.size(); i++) {
//            if ("Digit".equals(mMissingSymbolLang)) {
//                Question = Question + String.valueOf(arrExamples.get(i)) + "  ";
//            } else if ("Ru".equals(mMissingSymbolLang)) {
//                Question = Question + AlphabetRu[arrExamples.get(i)] + "  ";
//            } else if ("En".equals(mMissingSymbolLang)) {
//                Question = Question + AlphabetEn[arrExamples.get(i)] + "  ";
//            }
//        }

        drawExamplesAndAnswers();
    }

    private void drawExamplesAndAnswers() {

      for (Integer i = 1; i <= mMissingSymbolCountAnswers; i++) {
            int resID = getResources().getIdentifier("tvMissingSymbolAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);
            if (txt != null) {
                txt.setTextSize(mTextSize);
                if ("Digit".equals(mMissingSymbolLang)) {
                    txt.setText(String.valueOf(arrAnswers.get(i - 1)));
                } else if ("Ru".equals(mMissingSymbolLang)) {
                    txt.setText(String.valueOf(AlphabetRu.get(arrAnswers.get(i - 1))));
                } else if ("En".equals(mMissingSymbolLang)) {
                    txt.setText(String.valueOf(AlphabetEn.get(arrAnswers.get(i - 1))));
                }
                txt.setPadding(0,mTextSize,0,mTextSize);

            }
        }
        int mMaxExample= Collections.max(arrExamples);
        int mMinExample= Collections.min(arrExamples);
        for (Integer i = 1; i <= mMissingSymbolCountAnswers; i++) {
            int resID = getResources().getIdentifier("tvMissingSymbolExample" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);
            if (txt != null) {
                txt.setTextSize(mTextSize);
                if ("Digit".equals(mMissingSymbolLang)) {
                    txt.setText(String.valueOf(arrExamples.get(i - 1)));
                } else if ("Ru".equals(mMissingSymbolLang)) {
                    txt.setText(String.valueOf(AlphabetRu.get(arrExamples.get(i - 1))));
                } else if ("En".equals(mMissingSymbolLang)) {
                    txt.setText(String.valueOf(AlphabetEn.get(arrExamples.get(i - 1))));
                }
                //txt.setPadding(0,15,0,15);
                if (arrExamples.get(i-1).equals(mMaxExample) || arrExamples.get(i-1).equals(mMinExample)) {
                    txt.setTextColor(Color.parseColor("#FF11B131"));}
                else {
                    txt.setTextColor(Color.parseColor("#FF6D6464"));
                    //System.out.println("make color");
                }

            }
        }




//        int exampleID = getResources().getIdentifier("tvMissingSymbolExample", "id", getPackageName());
//        TextView txtExample = (TextView) findViewById(exampleID);
//        if (txtExample != null) {
//
//            txtExample.setText(Question);
//            txtExample.setTextSize(mTextSize);
//
//        }

    }

    public void txt_onClick(View view) {

        if (mChronometerIsWorking) {
            //int a = Integer.valueOf(String.valueOf(((AppCompatTextView) view).getText().charAt(0)));
            String id = view.getResources().getResourceEntryName(view.getId());
            int a = Integer.valueOf(id.substring(id.length() - 1, id.length()));
            if (a - 1 == indAnswer) {

                mCountRightAnswers++;
            }
            mCountAllAnswers++;
            mMissingSymbolExBeginTime = elapsedMillis;
            int timerExID = getResources().getIdentifier("tvMissingSymbolTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (mMissingSymbolExampleTime != 0) {
                if (txtTimerExTime != null) {
                    String txt = "Пример: " + String.valueOf(mMissingSymbolExampleTime);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                }
            }
            int answerID = getResources().getIdentifier("tvMissingSymbolAnswers", "id", getPackageName());
            TextView txtAnswer = (TextView) findViewById(answerID);
            if (txtAnswer != null) {
                String txt = String.valueOf(mCountRightAnswers) + "/" + String.valueOf(mCountAllAnswers);
                txtAnswer.setText(txt);
                txtAnswer.setTextSize(mTextSize);
            }
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(10); //You can manage the blinking time with this parameter
            anim.setStartOffset(0);
            anim.setRepeatMode(Animation.REVERSE);
            //anim.setRepeatCount(Animation.INFINITE);
            anim.setRepeatCount(5);
            view.startAnimation(anim);

            createExample();
        }
    }

    public void MissingSymbolOptions_onClick(View view) {

        Intent intent = new Intent(MissingSymbolActivity.this, MissingSymbolActivityOptions.class);
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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME)) {
            mMissingSymbolMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME, 60);
        } else {
            mMissingSymbolMaxTime = 60;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME)) {
            mMissingSymbolExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME, 0);
        } else {
            mMissingSymbolExampleTime = 0;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE)) {
            // Получаем язык из настроек
            mMissingSymbolLang = mSettings.getString(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE, "Digit");
        } else {
            mMissingSymbolLang = "Digit";
        }


    }


}