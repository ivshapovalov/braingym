package ru.brainworkout.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class RectanglesCountActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int mRectanglesCountSizeHeight;
    private int mRectanglesCountSizeWidth;
    private int mRectanglesCountMaxTime;
    private int mRectanglesCountExampleTime;
    private String mRectanglesCountFilling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangles_count_options);

        getPreferencesFromFile();
        setPreferencesOnScreen();
    }


    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void buttonSave_onClick(View view) {


        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_FILLING, mRectanglesCountFilling);
        editor.putInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_SIZE_HEIGHT, mRectanglesCountSizeHeight);
        editor.putInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_SIZE_WIDTH, mRectanglesCountSizeWidth);
        editor.putInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_TEST_TIME, mRectanglesCountMaxTime);
        editor.putInt(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_EXAMPLE_TIME, mRectanglesCountExampleTime);

        editor.apply();

        this.finish();


    }

    public void buttonCancel_onClick(View view) {

        this.finish();

    }

    private void getPreferencesFromFile() {

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_FILLING)) {
            // Получаем язык из настроек
            mRectanglesCountFilling = mSettings.getString(MainActivity.APP_PREFERENCES_RECTANGLES_COUNT_FILLING, "M");
        } else {
            mRectanglesCountFilling = "M";
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

    private void setPreferencesOnScreen() {

        int mFillingID = getResources().getIdentifier("rbRectCountFilling" + mRectanglesCountFilling, "id", getPackageName());
        RadioButton btFilling = (RadioButton) findViewById(mFillingID);

        if (btFilling != null) {
            btFilling.setChecked(true);
        }

        RadioGroup rgFilling = (RadioGroup) findViewById(R.id.rgRectCountFilling);
//
        if (rgFilling != null) {
            rgFilling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String mResName = "";
                    try {
                        mResName = getResources().getResourceName(checkedId);
                        mRectanglesCountFilling = String.valueOf(mResName.charAt(mResName.length() - 1));
                    } catch (Exception e) {
                        //mPairsSizeHeight = 4;
                    }
                }
            });
        }

        int mHeightID = getResources().getIdentifier("rbRectCountSizeHeight" + String.valueOf(mRectanglesCountSizeHeight), "id", getPackageName());
        RadioButton btHeight = (RadioButton) findViewById(mHeightID);

        if (btHeight != null) {
            btHeight.setChecked(true);
        }

        RadioGroup rgHeight = (RadioGroup) findViewById(R.id.rgRectCountSizeHeight);
//
        if (rgHeight != null) {
            rgHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String mResName = "";
                    try {
                        mResName = getResources().getResourceName(checkedId);
                        mRectanglesCountSizeHeight = Integer.valueOf(String.valueOf(mResName.charAt(mResName.length() - 1)));
                    } catch (Exception e) {
                        //mPairsSizeHeight = 4;
                    }
                }
            });
        }

        int mWidthID = getResources().getIdentifier("rbRectCountSizeWidth" + String.valueOf(mRectanglesCountSizeWidth), "id", getPackageName());
        RadioButton btWidth = (RadioButton) findViewById(mWidthID);

        if (btWidth != null) {
            btWidth.setChecked(true);
        }

        RadioGroup rgWidth = (RadioGroup) findViewById(R.id.rgRectCountSizeWidth);
//
        if (rgWidth != null) {
            rgWidth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String mResName = "";
                    try {
                        mResName = getResources().getResourceName(checkedId);
                        mRectanglesCountSizeWidth = Integer.valueOf(String.valueOf(mResName.charAt(mResName.length() - 1)));
                    } catch (Exception e) {
                        //mPairsSizeHeight = 4;
                    }

                }
            });
        }

        int mMaxTimeID = getResources().getIdentifier("rbRectCountMaxTime" + String.valueOf(mRectanglesCountMaxTime), "id", getPackageName());
        RadioButton btMaxTime = (RadioButton) findViewById(mMaxTimeID);

        if (btMaxTime != null) {
            btMaxTime.setChecked(true);
        }

        RadioGroup rgMaxTime = (RadioGroup) findViewById(R.id.rgRectCountMaxTime);
//
        if (rgMaxTime != null) {
            rgMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rbRectCountMaxTime60:
                            mRectanglesCountMaxTime = 60;
                            break;
                        case R.id.rbRectCountMaxTime120:
                            mRectanglesCountMaxTime = 120;
                            break;
                        case R.id.rbRectCountMaxTime180:
                            mRectanglesCountMaxTime = 180;
                            break;
                        default:
                            mRectanglesCountMaxTime = 120;
                            break;

                    }

                }
            });

            int mExTimeID = getResources().getIdentifier("rbRectCountExampleTime" + String.valueOf(mRectanglesCountExampleTime), "id", getPackageName());
            RadioButton btExTime = (RadioButton) findViewById(mExTimeID);

            if (btExTime != null) {
                btExTime.setChecked(true);
            }

            RadioGroup rgExTime = (RadioGroup) findViewById(R.id.rgRectCountExampleTime);
//
            if (rgExTime != null) {
                rgExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rbRectCountExampleTime0:
                                mRectanglesCountExampleTime = 0;
                                break;
                            case R.id.rbRectCountExampleTime5:
                                mRectanglesCountExampleTime = 5;
                                break;
                            case R.id.rbRectCountExampleTime10:
                                mRectanglesCountExampleTime = 10;
                                break;
                            case R.id.rbRectCountExampleTime20:
                                mRectanglesCountExampleTime = 20;
                                break;
                            default:
                                mRectanglesCountExampleTime = 0;
                                break;

                        }

                    }
                });
            }

        }

    }
}