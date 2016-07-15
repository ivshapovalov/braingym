package ru.brainworkout.brain_gym;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class NumberSearchActivity extends AppCompatActivity {
    private Chronometer mChronometer;
    private final String TAG = this.getClass().getSimpleName();

    private boolean mChronometerIsWorking = false;
    private long mChronometerCount = 0;

    private SharedPreferences mSettings;
    private String mNumberSearchLang;
    private int mTextSize = 0;
    private int mNumberSearchSize;
    private int mNumberSearchNumberSymbols;
    private int mNumberSearchFontSizeChange;
    private int mNumberSearchMaxTime;
    private int mNumberSearchExampleTime;
    private int mTextSizeDirection;

    private int mHeight = 0;
    private int mWidth = 0;

    private ArrayList<Integer> alphabetColors = new ArrayList<>();

    ArrayList<NumberSearchExample> matrix = new ArrayList<>();
    ArrayList<Integer> ElementsWithFontSizeChanges = new ArrayList<>();

    private String Question;
    private int indAnswer;
    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    private long mNumberSearchExBeginTime = 0;
    private long elapsedMillis;
    //алфавиты
    private ArrayList<String> AlphabetRu;
    private ArrayList<String> AlphabetEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //id - 600 +i
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_search);

        //matrixClear();
        mChronometer = (Chronometer) findViewById(R.id.chronometer_number_search);

        AlphabetRu= Utils.AlphabetRu();
        AlphabetEn= Utils.AlphabetEn();

    }

    public void NumberSearchСlear_onClick(View view) {

        //matrixClear();
        timerStop(false);

    }

    private void timerStop(boolean auto) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
        mChronometerCount = 0;
        mChronometerIsWorking = false;
        mCountRightAnswers = 0;
        mCountAllAnswers = 0;
        mNumberSearchExBeginTime = 0;

        ChangeButtonText("buttonNumberSearchStartPause", "Старт");

        int timerMaxID = getResources().getIdentifier("tvNumberSearchTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        if (txtTimerMaxTime != null) {
            txtTimerMaxTime.setTextSize(mTextSize);
            String txt;
            if (!auto) {
                txt = "Тест: " + String.valueOf(mNumberSearchMaxTime);
            } else {
                txt = "Тест окончен";
            }
            txtTimerMaxTime.setText(txt);
        }

        int answerID = getResources().getIdentifier("tvNumberSearchAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            txtAnswer.setTextSize(mTextSize);
            if (!auto) {
                txtAnswer.setText("");
            }
        }

        int exID = getResources().getIdentifier("tvNumberSearchExample", "id", getPackageName());
        TextView txtExample = (TextView) findViewById(exID);
        if (txtExample != null) {
            txtExample.setTextSize(mTextSize);
            txtExample.setText("");
        }


        int mCountNumberSearch = mNumberSearchSize * mNumberSearchSize;
        for (int i = 1; i <= mCountNumberSearch; i++) {
            //int resID=getResources().getIdentifier("txt"+String.valueOf(300+i), "id", getPackageName());
            //TextView txt = (TextView) findViewById(resID);
            Button but = (Button) findViewById(600 + i);
            //txt.setVisibility(View.INVISIBLE);
            if (but != null) {
                but.setText("");
                but.setTextSize(mTextSize);
                but.setBackgroundResource(R.drawable.textview_border);
                //txt.setVisibility(View.GONE);
                //
            }
        }

        if (mNumberSearchExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvNumberSearchTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);

            if (txtTimerExTime != null) {
                String txt;
                if (!auto) {
                    txt = "Пример: " + String.valueOf(mNumberSearchExampleTime);
                } else {
                    txt = "";
                }

                txtTimerExTime.setText(txt);
                txtTimerExTime.setTextSize(mTextSize);
            }
        }
    }

    private void numberSearchClear() {

        int mCountNumberSearch = mNumberSearchSize * mNumberSearchSize;
        for (int i = 1; i <= mCountNumberSearch; i++) {
            //int resID=getResources().getIdentifier("txt"+String.valueOf(300+i), "id", getPackageName());
            //TextView txt = (TextView) findViewById(resID);
            Button but = (Button) findViewById(600 + i);
            //txt.setVisibility(View.INVISIBLE);
            if (but != null) {
                but.setText("");
                but.setTextSize(mTextSize);
                but.setBackgroundResource(R.drawable.textview_border);
                //txt.setVisibility(View.GONE);
                //
            }
        }


        TableLayout frame = (TableLayout) findViewById(R.id.tableNumberSearch);
        if (frame != null) {
            frame.removeAllViews();
            frame.setStretchAllColumns(true);
        }

        int resID = getResources().getIdentifier("tvNumberSearchExample", "id", getPackageName());
        TextView txt = (TextView) findViewById(resID);

        if (txt != null) {


                if (frame != null) {
                    mHeight = (frame.getHeight()+txt.getHeight()) / (mNumberSearchSize+1);
                    mWidth = frame.getWidth() / (mNumberSearchSize);

                    int divider=16;
                    switch (mNumberSearchLang) {
                        case "Digit":
                            divider=divider;
                            break;
                        case "Ru":
                            divider+=3;
                            break;
                        case "En":
                            divider+=3;
                            break;
                    }

                    switch (mNumberSearchNumberSymbols) {
                        case 3:
                            divider+=1;
                            break;
                        case 4:
                            divider+=3;
                            break;
                        case 5:
                            divider+=7;
                            break;
                    }

                    switch (mNumberSearchSize) {
                        case 5:
                            divider+=1;
                            break;
                        case 6:
                            divider+=3;
                            break;
                        case 7:
                            divider+=5;
                            break;
                    }

                    mTextSize = (int) (Math.min(mWidth, mHeight)*(mNumberSearchSize) / divider / getApplicationContext().getResources().getDisplayMetrics().density);

                }
            txt.setText(" ");
            txt.setTextSize(mTextSize);
            txt.setHeight(mHeight);
            txt.setTextColor(Color.parseColor("#FF6D6464"));

        }


        int trowID = getResources().getIdentifier("trowNumberSearch", "id", getPackageName());
        TableRow trow1 = (TableRow) findViewById(trowID);

        if (trow1 != null) {
            trow1.setBackgroundResource(R.drawable.rounded_corners1);
        }

        //ChangeButtonText("buttonMatrixStartPause", "Старт");

    }

    private void changeTimer(long elapsedMillis) {

        for (int i = 0; i < ElementsWithFontSizeChanges.size(); i++) {
            Button but = (Button) findViewById(600 + ElementsWithFontSizeChanges.get(i));
            if (but != null) {

                float mButTextSize = but.getTextSize()
                        / getApplicationContext().getResources().getDisplayMetrics().density;
                if (mButTextSize == mTextSize) {
                    but.setTextSize(mButTextSize - 1);
                    mTextSizeDirection = 2;
                } else if (mButTextSize == mTextSize - 5) {
                    but.setTextSize(mButTextSize + 1);
                    mTextSizeDirection = 8;
                } else if (mTextSizeDirection == 8) {
                    but.setTextSize(mButTextSize + 1);
                } else if (mTextSizeDirection == 2) {
                    but.setTextSize(mButTextSize - 1);
                }


            }
        }


        int timerMaxID = getResources().getIdentifier("tvNumberSearchTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);
        if (txtTimerMaxTime != null) {
            int time = (int) (mNumberSearchMaxTime - (elapsedMillis / 1000));
            String txt = "Тест: " + String.valueOf(time);
            txtTimerMaxTime.setText(txt);
            txtTimerMaxTime.setTextSize(mTextSize);
        }
        if (mNumberSearchExampleTime != 0) {
            int timerExID = getResources().getIdentifier("tvNumberSearchTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (txtTimerExTime != null) {
                int time = (mNumberSearchExampleTime - ((int) (((elapsedMillis - mNumberSearchExBeginTime) / 1000)) % mNumberSearchExampleTime));
                //System.out.println("mStrupeExampleTime=" + mStrupExampleTime + ", time=" + time + ", elapsed millis=" + elapsedMillis + ", mStrupExBeginTime=" + mStrupExBeginTime);
                if (time == mNumberSearchExampleTime) {
                    //новый пример
                    String txt = "Пример: " + String.valueOf(time);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                    mCountAllAnswers++;
                    int answerID = getResources().getIdentifier("tvNumberSearchAnswers", "id", getPackageName());
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
            int timerExID = getResources().getIdentifier("tvNumberSearchTimerExTime", "id", getPackageName());
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
                getPreferencesFromFile();

                numberSearchClear();


                int timerID = getResources().getIdentifier("tvNumberSearchTimerMaxTime", "id", getPackageName());
                TextView txtTimerMaxTime = (TextView) findViewById(timerID);
                if (txtTimerMaxTime != null) {
                    txtTimerMaxTime.setTextSize(mTextSize);
                    String txt = "Тест: " + String.valueOf(mNumberSearchMaxTime);
                    txtTimerMaxTime.setText(txt);

                }
                mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        elapsedMillis = SystemClock.elapsedRealtime()
                                - mChronometer.getBase();


                        if (mNumberSearchMaxTime - (elapsedMillis / 1000) < 1) {
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
            ChangeButtonText("buttonNumberSearchStartPause", "Пауза");


        } else {

            //mChronometerBase=mChronometer.getBase();
            mChronometerCount = SystemClock.elapsedRealtime() - mChronometer.getBase();
            mChronometer.stop();
            mChronometerIsWorking = false;
            ChangeButtonText("buttonNumberSearchStartPause", "Старт");
        }
    }

    private void createExample() {

        Random random = new Random();

        int mCountAnswers = Math.abs(random.nextInt(10) + 7);
        String txtNum = "";
        int num;
        matrix.clear();

        while (matrix.size() != mCountAnswers) {
            txtNum = "";
            for (int i = 0; i < mNumberSearchNumberSymbols; i++) {

                switch (mNumberSearchLang) {
                    case "Digit":
                        num = random.nextInt(10);
                        txtNum += String.valueOf(num);
                        break;
                    case "Ru":
                        num = random.nextInt(AlphabetRu.size());
                        txtNum += AlphabetRu.get(num);
                        break;
                    case "En":
                        num = random.nextInt(AlphabetEn.size());
                        txtNum += AlphabetEn.get(num);
                        break;
                }

            }
            int indColor1 = Math.abs(random.nextInt() % 7);
            int indColor2 = Math.abs(random.nextInt() % 7);
            int indColor3 = Math.abs(random.nextInt() % 7);

            //тип 0 - обычная кнопка
            //тип 1 - мигающая кнопка
            //тип 2 - увеличивающаяся кнопка

            int indType = Math.abs(random.nextInt() % (mNumberSearchNumberSymbols-1));

            NumberSearchExample Ex = new NumberSearchExample(txtNum, indColor1, indColor2,indColor3, indType);
            matrix.add(Ex);

        }
        int mMatrixSize = mNumberSearchSize * mNumberSearchSize;
        while (matrix.size() != mMatrixSize) {
            int indPlace = random.nextInt(matrix.size());
            matrix.add(indPlace % matrix.size(), null);
        }

        boolean AnswerIsFound = false;
        while (!AnswerIsFound) {
            indAnswer = Math.abs(random.nextInt() % mMatrixSize);
            if (matrix.get(indAnswer) != null) {
                AnswerIsFound = true;
                Question = matrix.get(indAnswer).getWord();

            }
        }

        drawExamplesAndAnswers();
    }

    private void drawExamplesAndAnswers() {

        Random random = new Random();
        TableLayout layout = (TableLayout) findViewById(R.id.tableNumberSearch);
        //layout.removeAllViews();

        layout.setStretchAllColumns(true);
        layout.setShrinkAllColumns(true);

        //layout.setBackgroundColor(Color.BLACK);


        for (Integer numString = 1; numString <= mNumberSearchSize; numString++) {
            System.out.println("numString:" + String.valueOf(numString));
            TableRow row = new TableRow(this);
            row.setMinimumHeight(mHeight);
            row.setMinimumWidth(mWidth);
            row.setGravity(Gravity.CENTER);

            for (int numColumn = 1; numColumn <= mNumberSearchSize; numColumn++) {
                System.out.println("numColumn:" + String.valueOf(numColumn));
                Button but = (Button) findViewById(600 + (numString - 1) * mNumberSearchSize + numColumn);
                if (but == null) {

                    but = new Button(this);
                    but.setId(600 + (numString - 1) * mNumberSearchSize + numColumn);
                    but.setMinimumHeight(mHeight);
                    but.setWidth(mWidth);
                    but.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    //but.setPadding(5,5,5,5);
                    row.addView(but);
                }

                but.setTextSize(mTextSize);
                NumberSearchExample Ex = matrix.get((numString - 1) * mNumberSearchSize + numColumn - 1);
                System.out.println("Число:" + String.valueOf((numString - 1) * mNumberSearchSize + numColumn - 1));
                if (Ex == null) {
                    but.setText("0000");
                    but.setTextColor(Color.WHITE);
                    but.setBackgroundResource(R.drawable.textview_border);
                } else {
                    but.setText(String.valueOf(Ex.getWord()));
                    //but.setBackgroundColor(Ex.getColor());
                    int type = Ex.getType();
                    if (type == 1) {
                        final AnimationDrawable drawable = new AnimationDrawable();
                        final Handler handler = new Handler();
                        int ms1 = Math.abs(random.nextInt() % 1000);
                        int ms2 = Math.abs(random.nextInt() % 1000);
                        int ms3 = Math.abs(random.nextInt() % 1000);
                        drawable.addFrame(new ColorDrawable(alphabetColors.get(Ex.getColor1())), ms1);
                        drawable.addFrame(new ColorDrawable(alphabetColors.get(Ex.getColor2())), ms2);
                        drawable.addFrame(new ColorDrawable(alphabetColors.get(Ex.getColor3())), ms3);
                        drawable.setOneShot(false);
                        but.setBackgroundDrawable(drawable);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                drawable.start();
                            }
                        }, 100);
                    } else if (type == 0) {
                        but.setBackgroundColor(alphabetColors.get(Ex.getColor1()));
                    } else if (type == 2) {
                        ElementsWithFontSizeChanges.add((numString - 1) * mNumberSearchSize + numColumn - 1);
                        but.setBackgroundColor(alphabetColors.get(Ex.getColor1()));
                    }


                    //mNumberSearchTextSize = (int) (Math.min(mWidth, mHeight) / 3 / getApplicationContext().getResources().getDisplayMetrics().density) + mNumberSearchFontSizeChange;
                    but.setTextSize(mTextSize);
                    but.setGravity(Gravity.CENTER);
                    //but.setBackgroundResource(R.drawable.textview_border);
                    but.setTextColor(Color.WHITE);


                    but.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button_onClick(v);
                        }
                    });

                }
            }
            layout.addView(row);
        }
        int exampleID = getResources().getIdentifier("tvNumberSearchExample", "id", getPackageName());
        TextView txtExample = (TextView) findViewById(exampleID);
        if (txtExample != null) {

            txtExample.setText("Ищем слово: "+Question);
            txtExample.setTextSize(mTextSize+2);

        }
    }


    public void button_onClick(View view) {

        if (mChronometerIsWorking) {
            //int a = Integer.valueOf(String.valueOf(((AppCompatTextView) view).getText().charAt(0)));

            int a = view.getId() % 100;
            if (a - 1 == indAnswer) {

                mCountRightAnswers++;
            }
            mCountAllAnswers++;
            mNumberSearchExBeginTime = elapsedMillis;
            int timerExID = getResources().getIdentifier("tvNumberSearchTimerExTime", "id", getPackageName());
            TextView txtTimerExTime = (TextView) findViewById(timerExID);
            if (mNumberSearchExampleTime != 0) {
                if (txtTimerExTime != null) {
                    String txt = "Пример: " + String.valueOf(mNumberSearchExampleTime);
                    txtTimerExTime.setText(txt);
                    txtTimerExTime.setTextSize(mTextSize);
                }
            }
            int answerID = getResources().getIdentifier("tvNumberSearchAnswers", "id", getPackageName());
            TextView txtAnswer = (TextView) findViewById(answerID);
            if (txtAnswer != null) {
                String txt = String.valueOf(mCountRightAnswers) + "/" + String.valueOf(mCountAllAnswers);
                txtAnswer.setText(txt);
                txtAnswer.setTextSize(mTextSize);
            }

            createExample();
        }
    }


    public void NumberSearchOptions_onClick(View view) {

        Intent intent = new Intent(NumberSearchActivity.this, NumberSearchActivityOptions.class);
        intent.putExtra("mNumberSearchTextSize", mTextSize - mNumberSearchFontSizeChange);
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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
            mNumberSearchFontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_FONT_SIZE_CHANGE, 0);
        } else {
            mNumberSearchFontSizeChange = 0;
        }

        mNumberSearchLang = "Digit";
        if (mSettings.contains(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_LANGUAGE)) {
            // Получаем язык из настроек
            try {
                mNumberSearchLang = mSettings.getString(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_LANGUAGE, "Digit");
            } catch (Exception e) {
                mNumberSearchLang = "Digit";
            }

        } else {
            mNumberSearchLang = "Digit";

        }
        if (mSettings.contains(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_SIZE)) {
            // Получаем язык из настроек
            try {
                mNumberSearchSize = mSettings.getInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_SIZE, 5);
            } catch (Exception e) {
                mNumberSearchSize = 5;
            }

        } else {
            mNumberSearchSize = 5;

        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_NUMBER_SYMBOLS)) {
            // Получаем язык из настроек
            try {
                mNumberSearchNumberSymbols = mSettings.getInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_NUMBER_SYMBOLS, 4);
            } catch (Exception e) {
                mNumberSearchNumberSymbols = 4;
            }

        } else {
            mNumberSearchNumberSymbols = 4;

        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_TEST_TIME)) {
            mNumberSearchMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_TEST_TIME, 60);
        } else {
            mNumberSearchMaxTime = 60;
        }


        if (mSettings.contains(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_EXAMPLE_TIME)) {
            mNumberSearchExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_EXAMPLE_TIME, 0);
        } else {
            mNumberSearchExampleTime = 0;
        }

        alphabetColors.add(Color.RED);
        alphabetColors.add(Color.parseColor("#FFA500"));
        alphabetColors.add(Color.parseColor("#53b814"));
        alphabetColors.add(Color.parseColor("#FF7B15CE"));
        alphabetColors.add(Color.BLUE);
        alphabetColors.add(Color.parseColor("#EE82EE"));
        alphabetColors.add(Color.parseColor("#8B4513"));

        //MainActivity.MyLogger(TAG,"Тест");
    }

    private class NumberSearchExample {
        private String word;
        private int color1;
        private int color2;
        private int color3;
        private int type;

        public NumberSearchExample(String word, int color1, int color2,int color3, int type) {
            this.word = word;
            this.color1 = color1;
            this.color2 = color2;
            this.color3 = color3;
            this.type = type;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getColor1() {
            return color1;
        }

        public void setColor1(int color1) {
            this.color1 = color1;
        }

        public int getColor2() {
            return color2;
        }

        public void setColor2(int color2) {
            this.color2 = color2;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getColor3() {
            return color3;
        }

        public void setColor3(int color3) {
            this.color3 = color3;
        }
    }


}