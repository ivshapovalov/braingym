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
import java.util.Random;


public class ChainCharacterActivity extends AppCompatActivity {

    private Chronometer mChronometer;

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;
    private final int mChainCharacterCountAnswers = 8;
    private final int mChainCharacterCountExamples = 20;

    private ArrayList<Integer> arrAnswers = new ArrayList<>();
    private ArrayList<Integer> arrExamples = new ArrayList<>();
    private int indAnswer;
    private String Question;
    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    //настройки

    private String mChainCharacterLang;
    private int mChainCharacterMaxTime;
    private int mChainCharacterExampleTime;

    private int mTextSize = 0;
    private long mChainCharacterExBeginTime = 0;
    private long elapsedMillis;


    private ArrayList<String> AlphabetRu;
    private ArrayList<String> AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_character);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_chain_character);


        AlphabetRu= Utils.AlphabetRu();
        AlphabetEn= Utils.AlphabetEn();
    }


    public void ChainCharacterClear_onClick(View view) {

        timerStop(false);
    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;
        mCountRightAnswers = 0;
        mCountAllAnswers = 0;
        mChainCharacterExBeginTime = 0;

        ChangeButtonText("buttonChainCharacterStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvChainCharacterTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            txtTimerMaxTime.setTextSize(mTextSize);
            String txt;
            if (!auto) {
                txt = "Тест: " + String.valueOf(mChainCharacterMaxTime);
            } else {
                txt = "Тест окончен";
            }
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("tvChainCharacterAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setText("");
            }
        }
        int exampleID = getResources().getIdentifier("tvChainCharacterExample", "id", getPackageName());
        TextView txtExample = (TextView) findViewById(exampleID);
        if (txtExample != null) {
            txtExample.setTextSize(mTextSize);
            if (!auto) {
                txtExample.setText("");
            }
        }
        for (int i = 1; i <= mChainCharacterCountAnswers; i++) {
            int ansID = getResources().getIdentifier("tvChainCharacterAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txtAns = (TextView) findViewById(ansID);

            if (txtAns != null) {
                txtAns.setText("  ");
//                txtAns.setBackgroundResource(R.drawable.rounded_corners1);
//                txtAns.setTextSize(mTextSize);
//                txtAns.setPadding(0,mTextSize/2,0,mTextSize/2);

            }
        }

        if (mChainCharacterExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvChainCharacterTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);

            if (txtTimerExTime != null) {
                String txt;
                if (!auto) {
                    txt = "Пример: " + String.valueOf(mChainCharacterExampleTime);
                } else {
                    txt = "";
                }

                txtTimerExTime.setText(txt);
                txtTimerExTime.setTextSize(mTextSize);
            }
        }
    }

    private void chainCharacterClear() {

            int resID = getResources().getIdentifier(" tvChainCharacterExample", "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);

            if (txt != null) {
                txt.setText(" ");
                txt.setTextSize(mTextSize);
                txt.setTextColor(Color.parseColor("#FF6D6464"));

            }



//        int exID = getResources().getIdentifier("tvMissingSymbolExample", "id", getPackageName());
//        TextView txt1 = (TextView) findViewById(exID);
//
//        if (txt1 != null) {
//            txt1.setText("  ");
//            txt1.setBackgroundResource(R.drawable.rounded_corners1);
//            txt1.setTextSize(mTextSize);
//
//        }

        int trowID = getResources().getIdentifier("trowChainCharacter", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);
        }

        for (int i = 1; i <= mChainCharacterCountAnswers; i++) {
            int ansID = getResources().getIdentifier("tvChainCharacterAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txtAns = (TextView) findViewById(ansID);

            if (txtAns != null) {
                txtAns.setText("  ");
                txtAns.setBackgroundResource(R.drawable.rounded_corners1);
                txtAns.setTextSize(mTextSize);
                txtAns.setPadding(0,mTextSize/2,0,mTextSize/2);

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
        int timerMaxID = getResources().getIdentifier("tvChainCharacterTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);
        if (txtTimerMaxTime != null) {
            int time = (int) (mChainCharacterMaxTime - (elapsedMillis / 1000));
            String txt = "Тест: " + String.valueOf(time);
            txtTimerMaxTime.setText(txt);
            txtTimerMaxTime.setTextSize(mTextSize);
        }
        if (mChainCharacterExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvChainCharacterTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {
                int time = (mChainCharacterExampleTime - ((int) (((elapsedMillis - mChainCharacterExBeginTime) / 1000)) % mChainCharacterExampleTime));
                //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                if (time == mChainCharacterExampleTime) {
                    //новый пример
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                    mCountAllAnswers++;
                    int answerID = getResources().getIdentifier("tvChainCharacterAnswers", "id", getPackageName());
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
            int timerExID = getResources().getIdentifier("tvChainCharacterTimerExTime", "id", getPackageName());
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

                TableLayout frame = (TableLayout) findViewById(R.id.groundChainCharacter);
                int mWidth;
                int mHeight;
                if (frame != null) {
                    mWidth = frame.getWidth();
                    mHeight = frame.getHeight();
                } else {
                    mWidth = 0;
                    mHeight = 0;
                }
                mTextSize = (int) (Math.min(mWidth, mHeight) / 18 / getApplicationContext().getResources().getDisplayMetrics().density);

                chainCharacterClear();
                getPreferencesFromFile();

                int timerID = getResources().getIdentifier("tvChainCharacterTimerMaxTime", "id", getPackageName());
                TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                if (txtTimerMaxTime != null) {
                    txtTimerMaxTime.setTextSize(mTextSize);
                    String txt = "Тест: " + String.valueOf(mChainCharacterMaxTime);
                    txtTimerMaxTime.setText(txt);
                    txtTimerMaxTime.setTextSize(mTextSize);
                }
                mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        elapsedMillis = SystemClock.elapsedRealtime()
                                - mChronometer.getBase();

                        if (mChainCharacterMaxTime - (elapsedMillis / 1000) < 1) {
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
            ChangeButtonText("buttonChainCharacterStartPause", "Пауза");


        } else {

            //mChronometerBase=mChronometer.getBase();
            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;
            ChangeButtonText("buttonChainCharacterStartPause", "Старт");
        }
    }

    private void createExample() {

        Random random = new Random();

        int symbol = 0;
        if ("Digit".equals(mChainCharacterLang)) {
            symbol = Math.abs(random.nextInt(10));
        } else if ("Ru".equals(mChainCharacterLang)) {
            symbol = Math.abs(random.nextInt() % (AlphabetRu.size()));
        } else if ("En".equals(mChainCharacterLang)) {
            symbol = Math.abs(random.nextInt() % (AlphabetEn.size()));
        }
        //System.out.println("symbol:"+symbol);
        arrAnswers.clear();
        arrExamples.clear();

        int answer = 0;
        while (arrExamples.size() != mChainCharacterCountExamples) {
            //int newDigit = Math.abs(random.nextInt() % (mMissingSymbolCountAnswers -2)) + mBeginDigit+1;
            int newDigit = Math.abs(random.nextInt() % 10);
            int indPlace = (arrExamples.size() == 0 ? 0 : Math.abs(random.nextInt()) % (arrExamples.size()));
            arrExamples.add(indPlace, newDigit);
            if (newDigit == symbol) {
                answer++;
            }
        }

        while (arrAnswers.size() != mChainCharacterCountAnswers - 1) {

            int newDigit = Math.abs(random.nextInt() % mChainCharacterCountExamples);
            if (!arrAnswers.contains(newDigit)&&newDigit!=answer) {
                int indPlace = Math.abs((arrAnswers.size() == 0 ? random.nextInt() : random.nextInt(arrAnswers.size())));
                arrAnswers.add((arrAnswers.size() == 0 ? 0 : indPlace % arrAnswers.size()), newDigit);
            }
        }
        int indPlace = Math.abs((arrAnswers.size() == 0 ? random.nextInt() : random.nextInt(arrAnswers.size())));
        arrAnswers.add(indPlace % arrAnswers.size(), answer);
        indAnswer=arrAnswers.indexOf(answer);
        //System.out.println("indAnswer:"+indAnswer);

        Question="";
        if ("Digit".equals(
                mChainCharacterLang)) {
            Question = String.valueOf(symbol) + ": ";
        } else if ("Ru".equals(mChainCharacterLang)) {
            Question = Question + AlphabetRu.get(symbol) + ": ";
        } else if ("En".equals(mChainCharacterLang)) {
            Question = Question + AlphabetEn.get(symbol) + ": ";
        }
        for (int i = 0; i < arrExamples.size(); i++) {
            if ("Digit".equals(
                    mChainCharacterLang)) {
                Question = Question + String.valueOf(arrExamples.get(i)) + "";
            } else if ("Ru".equals(mChainCharacterLang)) {
                Question = Question + AlphabetRu.get(arrExamples.get(i)) + "";
            } else if ("En".equals(mChainCharacterLang)) {
                Question = Question + AlphabetEn.get(arrExamples.get(i)) + "";
            }
        }

        //System.out.println("Question:"+Question);
        //arrExamples.add(0,mBeginDigit);


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


        drawExamplesAndAnswers();
    }

    private void drawExamplesAndAnswers() {

        for (Integer i = 1; i <= mChainCharacterCountAnswers; i++) {
            int resID = getResources().getIdentifier("tvChainCharacterAnswer" + String.valueOf(i), "id", getPackageName());
            TextView txt = (TextView) findViewById(resID);
            if (txt != null) {
                txt.setTextSize(mTextSize);
                //if ("Digit".equals(mChainCharacterLang)) {
                    txt.setText(String.valueOf(arrAnswers.get(i - 1)));
//                } else if ("Ru".equals(mChainCharacterLang)) {
//                    txt.setText(String.valueOf(AlphabetRu[arrAnswers.get(i - 1)]));
//                } else if ("En".equals(mChainCharacterLang)) {
//                    txt.setText(String.valueOf(AlphabetEn[arrAnswers.get(i - 1)]));
//                }
                txt.setPadding(0, mTextSize/2, 0, mTextSize/2);

            }
        }
        int exampleID = getResources().getIdentifier("tvChainCharacterExample", "id", getPackageName());
        TextView txtExample = (TextView) findViewById(exampleID);
        if (txtExample != null) {

            txtExample.setText(Question);
            txtExample.setTextSize(mTextSize);

        }

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
            mChainCharacterExBeginTime = elapsedMillis;
            int timerExID = getResources().getIdentifier("tvChainCharacterTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (mChainCharacterExampleTime != 0) {
                if (txtTimerExTime != null) {
                    String txt = "Пример: " + String.valueOf(mChainCharacterExampleTime);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                }
            }
            int answerID = getResources().getIdentifier("tvChainCharacterAnswers", "id", getPackageName());
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

    public void ChainCharacterOptions_onClick(View view) {

        Intent intent = new Intent(ChainCharacterActivity.this, ChainCharacterActivityOptions.class);
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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_MAX_TIME)) {
            mChainCharacterMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_MAX_TIME, 60);
        } else {
            mChainCharacterMaxTime = 60;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME)) {
            mChainCharacterExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME, 0);
        } else {
            mChainCharacterExampleTime = 0;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE)) {
            // Получаем язык из настроек
            mChainCharacterLang = mSettings.getString(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE, "Digit");
        } else {
            mChainCharacterLang = "Digit";
        }


    }


}