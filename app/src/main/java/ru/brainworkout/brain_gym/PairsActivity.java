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
    private int mHeight = 0;
    private int mWidth = 0;
    private int mPairsTextSize = 12;

    private int mCountRightAnswers = 0;
    private int mCountAllAnswers = 0;
    private int mAttemptCount = 0;
    private long elapsedMillis;

    private int mFirstSymbolIndex = -1;
    private boolean mTxtIsBlocked = false;

    //алфавиты
    private ArrayList<String> AlphabetRu;
    private ArrayList<String> AlphabetEn;
    private ArrayList<Integer> AlphabetColors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //id - 700 + i
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairs);

        mChronometer = (Chronometer) findViewById(R.id.chronometer_pairs);
        AlphabetRu = Utils.AlphabetRu();
        AlphabetEn = Utils.AlphabetEn();
        AlphabetColors= Utils.AlphabetColors();
    }


    private void PairsClear() {

        TableLayout layout = (TableLayout) findViewById(R.id.tablePairs);
        layout.removeAllViews();
        layout.setStretchAllColumns(true);
        //mTextSize = (int) (Math.min(mWidth, mHeight) / 5 / getApplicationContext().getResources().getDisplayMetrics().density) + mMatrixFontSizeChange;


        //txt.setHeight(mHeight);
        mHeight = (layout.getHeight()) / (mPairsSizeHeight);
        mWidth = layout.getWidth() / (mPairsSizeWidth);

        mPairsTextSize = (int) (Math.min(mWidth, mHeight) / 3 / getApplicationContext().getResources().getDisplayMetrics().density);

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

        int answerID = getResources().getIdentifier("tvPairsAnswers", "id", getPackageName());
        TextView txtAnswer = (TextView) findViewById(answerID);
        if (txtAnswer != null) {
            //txtAnswer.setTextSize(mTextSize);
                txtAnswer.setText("");
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
        mFirstSymbolIndex = -1;
        mAttemptCount = 0;

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

        if (!auto) {
            int mMax = mPairsSizeWidth * mPairsSizeHeight;
            for (int i = 1; i <= mMax; i++) {
                TextView txt1 = (TextView) findViewById(700 + i);
                if (txt1 != null) {
                    txt1.setText("");
                    txt1.setTextSize(mPairsTextSize);
                    txt1.setTextColor(Color.WHITE);
                    txt1.setBackgroundResource(R.drawable.textview_border);
                }
            }
        }

    }

    private void changeTimer(long elapsedMillis) {

        int timerMaxID = getResources().getIdentifier("tvPairsTimerMaxTime", "id", getPackageName());
        TextView txtTimerMaxTime = (TextView) findViewById(timerMaxID);

        int time = (int) (elapsedMillis / 1000);
        String txt = "Тест: " + String.valueOf(time);
        if (txtTimerMaxTime != null) {
            txtTimerMaxTime.setText(txt);
        }
        //txtTimerMaxTime.setTextSize(mTextSize);
    }


    private void drawPairsTest() {


//        ShapeDrawable line = new ShapeDrawable(new RectShape());
//        line.setIntrinsicHeight(1);
//        line.setIntrinsicWidth(1);
//        line.getPaint().setColor(Color.MAGENTA);

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
                    txt.setMaxWidth(mWidth);
                    row.addView(txt);
                }

                txt.setTextSize(mPairsTextSize);

                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.textview_border);
                //txt.setBackgroundDrawable(line);
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

    public void txt_onClick(final TextView view) {

        if (!mTxtIsBlocked) {

            if (mChronometerIsWorking) {
                int ind = view.getId() % 100 - 1;
                PairsElement m1 = matrix.get(ind);
                if (m1.isOpened()) {
                    //ничего не делаем. ячейка уже открыта
                } else {
                    switch (mPairsLang) {
                        case "Color":
//                            Drawable dr = getResources().getDrawable(R.drawable.textview_black_with_white_borders);
//                            //Drawable dr = view.getBackground();
//                            ColorFilter filter = new LightingColorFilter(Color.BLACK, mColors.get(matrix.get(ind).getNum()));
//                            dr.setColorFilter(filter);
//                            view.setBackgroundDrawable(dr);

//                            view.getBackground().setColorFilter(mColors.get(matrix.get(ind).getNum()), PorterDuff.Mode.SRC_ATOP);

                            view.setBackgroundColor(AlphabetColors.get(matrix.get(ind).getNum()));
                            break;
                        case "Digit":
                            view.setText(String.valueOf(matrix.get(ind).getNum()));
                            break;
                        case "Ru":
                            String strRu = AlphabetRu.get(matrix.get(ind).getNum());
                            view.setText(strRu);
                            break;
                        case "En":
                            String strEn = AlphabetEn.get(matrix.get(ind).getNum());
                            view.setText(strEn);
                            break;
                    }
                    if (mFirstSymbolIndex == -1) {
                        mFirstSymbolIndex = ind;
                    } else {
                        mAttemptCount++;

                        int ansID = getResources().getIdentifier("tvPairsAnswers", "id", getPackageName());
                        TextView txtAnswers = (TextView) findViewById(ansID);
                        if (txtAnswers != null) {
                            txtAnswers.setText("Попыток: " + mAttemptCount);
                        }

                        int num1 = matrix.get(mFirstSymbolIndex).getNum();
                        int num2 = matrix.get(ind).getNum();
                        final TextView txt = (TextView) findViewById(700 + mFirstSymbolIndex + 1);

                        if (num1 == num2 && ind != mFirstSymbolIndex) {
                            //угадали

                            matrix.get(mFirstSymbolIndex).setOpened(true);
                            matrix.get(ind).setOpened(true);

                            mCountRightAnswers++;

                            Animation anim = new AlphaAnimation(0.0f, 0.0f);
                            anim.setDuration(100); //You can manage the blinking time with this parameter
                            anim.setStartOffset(0);
                            anim.setRepeatMode(0);
                            //anim.setRepeatCount(Animation.INFINITE);
                            anim.setRepeatCount(2);
                            view.startAnimation(anim);

//                        view.setText("");
//                        TextView txt = (TextView) findViewById(700 + mFirstSymbolIndex);
                            if (txt != null) {
                                txt.startAnimation(anim);
                                //txt.setText("");
                            }

                        } else {
                            mTxtIsBlocked = true;
                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    switch (mPairsLang) {
                                        case "Color":
                                            //view.getBackground().clearColorFilter();
                                            view.setBackgroundResource(R.drawable.textview_border);
                                            break;
                                        default:
                                            view.setText("");
                                            break;
                                    }

                                    mTxtIsBlocked = false;

                                }
                            }, 1500);


                            if (txt != null) {
                                txt.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        switch (mPairsLang) {
                                            case "Color":
                                                //view.getBackground().clearColorFilter();
                                                txt.setBackgroundResource(R.drawable.textview_border);
                                                break;
                                            default:
                                                txt.setText("");
                                                break;
                                        }

                                        mTxtIsBlocked = false;
                                    }
                                }, 1500);
                            }
                        }
                        mFirstSymbolIndex = -1;

                        if (mCountRightAnswers == mCountAllAnswers) {
                            timerStop(true);
                        } else {
                        }
                    }
                }
            }
        }
    }

    private void createPairs() {
        int num;
        matrix.clear();
        ArrayList<Integer> digits = new ArrayList<>();

        Random random = new Random();
        int mMaxDigits = mPairsSizeHeight * mPairsSizeWidth;
        while (matrix.size() != mMaxDigits) {

            num = Math.abs(random.nextInt() % (mMaxDigits / 2));
            if (!digits.contains(num)) {
                int indPlace1 = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));
                int indPlace2 = (matrix.size() == 0 ? 0 : random.nextInt(matrix.size()));

                PairsElement m = new PairsElement(num);
                matrix.add(indPlace1, m);
                matrix.add(indPlace2, m);
                digits.add(num);
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
            ChangeButtonText("btPairsStartPause", "Пауза");

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

        mCountAllAnswers = mPairsSizeHeight * mPairsSizeWidth / 2;

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

    private void pause(long sleeptime) {
        long expectedtime = System.currentTimeMillis() + sleeptime;
        while (System.currentTimeMillis() < expectedtime) {
            //Empty Loop
        }
    }

}