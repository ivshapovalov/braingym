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


public class MathActivity2 extends AppCompatActivity {

    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private final int mMath2CountAnswers = 8;

    private ArrayList<Integer> arrAnswers = new ArrayList<>();
    private ArrayList<Integer> arrExamples = new ArrayList<>();
    private int indAnswer;
    private String Question;
    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    //настройки

    private int mMath2MaxDigit;
    private int mMath2MaxTime;
    private int mMath2ExampleTime;

    private int mTextSize = 0;
    private long mMath2ExBeginTime = 0;
    private long elapsedMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_symbol);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_missing_symbol);

    }


    public void Math2Clear_onClick(View view) {

        timerStop(false);
    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;
        mCountRightAnswers = 0;
        mCountAllAnswers = 0;
        mMath2ExBeginTime = 0;

        ChangeButtonText("buttonMath2StartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvMath2TimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            txtTimerMaxTime.setTextSize(mTextSize);
            String txt;
            if (!auto) {
                txt = "Тест: " + String.valueOf(mMath2MaxTime);
            } else {
                txt = "Тест окончен";
            }
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("tvMath2Answers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setText("");
            }
        }

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMath2Answer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
               // txt.setTextSize(mTextSize);

            }

        }

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMath2Example" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                //txt.setTextSize(mTextSize);
                //txt.setTextColor(Color.parseColor("#FF6D6464"));

            }

        }

        if (mMath2ExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvMath2TimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);

            if (txtTimerExTime != null) {
                String txt;
                if (!auto) {
                    txt = "Пример: " + String.valueOf(mMath2ExampleTime);
                } else {
                    txt = "";
                }

                txtTimerExTime.setText(txt);
                txtTimerExTime.setTextSize(mTextSize);
            }
        }
    }

    private void Math2Clear() {

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMath2Answer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                txt.setTextSize(mTextSize);

            }

        }

        for (int i = 1; i <= 8; i++) {

            int resID = getResources().getIdentifier("tvMath2Example" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                txt.setTextSize(mTextSize);
                txt.setTextColor(Color.parseColor("#FF6D6464"));

            }

        }

        int trowID = getResources().getIdentifier("trowMath2", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);
        }

        for (int i = 1; i <= mMath2CountAnswers; i++) {
            int ansID = getResources().getIdentifier("tvMath2Answer" + String.valueOf(i), "id", getPackageName());
            TextView txtAns = (TextView) findViewById(ansID);

            if (txtAns != null) {
                txtAns.setText("  ");
                txtAns.setBackgroundResource(R.drawable.rounded_corners1);
                txtAns.setTextSize(mTextSize);
                txtAns.setPadding(0,mTextSize,0,mTextSize);

            }
        }
//        int table1ID = getResources().getIdentifier("tableMath2Answers", "id", getPackageName());
//        TableLayout table1 = (TableLayout) findViewById(table1ID);
//
//        if (table1 != null) {
//            table1.setBackgroundResource(R.drawable.rounded_corners1);
//        }

    }


    private void changeTimer(long elapsedMillis) {
        int timerMaxID = getResources().getIdentifier("tvMath2TimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);
        if (txtTimerMaxTime != null) {
            int time = (int) (mMath2MaxTime - (elapsedMillis / 1000));
            String txt = "Тест: " + String.valueOf(time);
            txtTimerMaxTime.setText(txt);
            txtTimerMaxTime.setTextSize(mTextSize);
        }
        if (mMath2ExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvMath2TimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {
                int time = (mMath2ExampleTime - ((int) (((elapsedMillis - mMath2ExBeginTime) / 1000)) % mMath2ExampleTime));
                //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                if (time == mMath2ExampleTime) {
                    //новый пример
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                    mCountAllAnswers++;
                    int answerID = getResources().getIdentifier("tvMath2Answers", "id", getPackageName());
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
            int timerExID = getResources().getIdentifier("tvMath2TimerExTime", "id", getPackageName());
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

                TableLayout frame = (TableLayout) findViewById(R.id.groundMath2);
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

                Math2Clear();
                getPreferencesFromFile();

                int timerID = getResources().getIdentifier("tvMath2TimerMaxTime", "id", getPackageName());
                TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                if (txtTimerMaxTime != null) {
                    txtTimerMaxTime.setTextSize(mTextSize);
                    String txt = "Тест: " + String.valueOf(mMath2MaxTime);
                    txtTimerMaxTime.setText(txt);
                    txtTimerMaxTime.setTextSize(mTextSize);
                }
                mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        elapsedMillis = SystemClock.elapsedRealtime()
                                - mChronometer.getBase();

                        if (mMath2MaxTime - (elapsedMillis / 1000) < 1) {
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
            ChangeButtonText("buttonMath2StartPause", "Пауза");


        } else {

            //mChronometerBase=mChronometer.getBase();
            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;
            ChangeButtonText("buttonMath2StartPause", "Старт");
        }
    }

    private void createExample() {

        Random random = new Random();

        int mBeginDigit=0;

            mBeginDigit = Math.abs(random.nextInt(100));

        int answer = Math.abs(random.nextInt() % (mMath2CountAnswers-2)) + mBeginDigit+1;

        arrAnswers.clear();
        arrExamples.clear();

        while (arrAnswers.size() != mMath2CountAnswers) {
            int newDigit = Math.abs(random.nextInt() % mMath2CountAnswers) + mBeginDigit;
            if (!arrAnswers.contains(newDigit)) {
                int indPlace = Math.abs((arrAnswers.size() == 0 ? random.nextInt() : random.nextInt(arrAnswers.size())));
                arrAnswers.add((arrAnswers.size() == 0 ? 0 : indPlace % arrAnswers.size()), newDigit);
            }
        }
        indAnswer = arrAnswers.indexOf(answer);

        //arrExamples.add(0,mBeginDigit);

        int ind=0;
        while (arrExamples.size() != mMath2CountAnswers) {
            //int newDigit = Math.abs(random.nextInt() % (mMath2CountAnswers -2)) + mBeginDigit+1;
            int newDigit=mBeginDigit+ind;
            ind++;
            if (newDigit != answer)
            {
                int indPlace = Math.abs(random.nextInt());
                arrExamples.add((arrExamples.size() == 0 ? 0 : indPlace % (arrExamples.size())), newDigit);
           }
        }
        //arrExamples.add(7,mBeginDigit+mMath2CountAnswers);

//        while (arrExamples.size() != mMath2CountAnswers-1) {
//            int newDigit = Math.abs(random.nextInt() % (mMath2CountAnswers -2)) + mBeginDigit+1;
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
//            if ("Digit".equals(mMath2Lang)) {
//                Question = Question + String.valueOf(arrExamples.get(i)) + "  ";
//            } else if ("Ru".equals(mMath2Lang)) {
//                Question = Question + AlphabetRu[arrExamples.get(i)] + "  ";
//            } else if ("En".equals(mMath2Lang)) {
//                Question = Question + AlphabetEn[arrExamples.get(i)] + "  ";
//            }
//        }

        drawExamplesAndAnswers();
    }

    private void drawExamplesAndAnswers() {

      for (Integer i = 1; i <= mMath2CountAnswers; i++) {
            int resID = getResources().getIdentifier("tvMath2Answer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);
            if (txt != null) {
                txt.setTextSize(mTextSize);

                    txt.setText(String.valueOf(arrAnswers.get(i - 1)));

                txt.setPadding(0,mTextSize,0,mTextSize);

            }
        }
        int mMaxExample= Collections.max(arrExamples);
        int mMinExample= Collections.min(arrExamples);
        for (Integer i = 1; i <= mMath2CountAnswers; i++) {
            int resID = getResources().getIdentifier("tvMath2Example" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);
            if (txt != null) {
                txt.setTextSize(mTextSize);

                    txt.setText(String.valueOf(arrExamples.get(i - 1)));

                //txt.setPadding(0,15,0,15);
                if (arrExamples.get(i-1).equals(mMaxExample) || arrExamples.get(i-1).equals(mMinExample)) {
                    txt.setTextColor(Color.parseColor("#FF11B131"));}
                else {
                    txt.setTextColor(Color.parseColor("#FF6D6464"));
                    //System.out.println("make color");
                }

            }
        }




//        int exampleID = getResources().getIdentifier("tvMath2Example", "id", getPackageName());
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
            mMath2ExBeginTime = elapsedMillis;
            int timerExID = getResources().getIdentifier("tvMath2TimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (mMath2ExampleTime != 0) {
                if (txtTimerExTime != null) {
                    String txt = "Пример: " + String.valueOf(mMath2ExampleTime);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                }
            }
            int answerID = getResources().getIdentifier("tvMath2Answers", "id", getPackageName());
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

    public void Math2Options_onClick(View view) {

        Intent intent = new Intent(MathActivity2.this, MathActivity2Options.class);
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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH2_MAXIMUM_DIGIT)) {
            mMath2MaxDigit=mSettings.getInt(MainActivity.APP_PREFERENCES_MATH2_MAXIMUM_DIGIT, 0);
        } else {
            mMath2MaxDigit = 0;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH2_MAX_TIME)) {
            mMath2MaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH2_MAX_TIME, 60);
        } else {
            mMath2MaxTime = 60;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH2_EXAMPLE_TIME)) {
            mMath2ExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH2_EXAMPLE_TIME, 0);
        } else {
            mMath2ExampleTime = 0;
        }


    }


}