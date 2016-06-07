package ru.brainworkout.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MathActivity2Options extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int mMath2MaxDigit;
    private int mMath2MaxTime;
    private int mMath2ExampleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math2_options);

        getPreferencesFromFile();

    }

    public void buttonSave_onClick(View view) {


        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(MainActivity.APP_PREFERENCES_MATH2_MAXIMUM_DIGIT, mMath2MaxDigit);
        editor.putInt(MainActivity.APP_PREFERENCES_MATH2_MAX_TIME, mMath2MaxTime);
        editor.putInt(MainActivity.APP_PREFERENCES_MATH2_EXAMPLE_TIME, mMath2ExampleTime);

        editor.apply();


        this.finish();
    }

    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void buttonCancel_onClick(View view) {

        this.finish();

    }

    private void getPreferencesFromFile() {

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH2_MAXIMUM_DIGIT)) {
            mMath2MaxDigit=mSettings.getInt(MainActivity.APP_PREFERENCES_MATH2_MAXIMUM_DIGIT, 0);
        } else {
            mMath2MaxDigit = 0;
        }

        int maxDigitID = getResources().getIdentifier("rbMath2MaxDigit" + mMath2MaxDigit, "id", getPackageName());
        RadioButton maxDigit = (RadioButton) findViewById(maxDigitID );
        if (maxDigit != null) {
            maxDigit.setChecked(true);
        }
        //
        RadioGroup rgMaxDigit = (RadioGroup) findViewById(R.id.rgMath2MaxDigit);

        if (rgMaxDigit != null) {
            rgMaxDigit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMath2MaxDigit100:
                            mMath2MaxDigit = 100;
                            break;
                        case R.id.rbMath2MaxDigit300:
                            mMath2MaxDigit = 300;
                            break;
                        case R.id.rbMath2MaxDigit1000:
                            mMath2MaxDigit = 1000;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH2_MAX_TIME)) {
            mMath2MaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH2_MAX_TIME, 60);
        } else {
            mMath2MaxTime = 60;
        }

        //время тестирования
        int maxtimeID = getResources().getIdentifier("rbMath2MaxTime" + mMath2MaxTime, "id", getPackageName());
        RadioButton butTime = (RadioButton) findViewById(maxtimeID);
        if (butTime != null) {
            butTime.setChecked(true);
        }

        //
        RadioGroup rgMaxTime = (RadioGroup) findViewById(R.id.rgMath2MaxTime);

        if (rgMaxTime != null) {
            rgMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMath2MaxTime60:
                            mMath2MaxTime = 60;
                            break;
                        case R.id.rbMath2MaxTime120:
                            mMath2MaxTime = 120;
                            break;
                        case R.id.rbMath2MaxTime180:
                            mMath2MaxTime = 180;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH2_EXAMPLE_TIME)) {
            mMath2ExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH2_EXAMPLE_TIME, 0);
        } else {
            mMath2ExampleTime = 0;
        }

        int extimeID = getResources().getIdentifier("rbMath2ExTime" + mMath2ExampleTime, "id", getPackageName());
        RadioButton exTime = (RadioButton) findViewById(extimeID);
        if (exTime != null) {
            exTime.setChecked(true);
        }
        //
        RadioGroup rgExTime = (RadioGroup) findViewById(R.id.rgMath2ExTime);

        if (rgExTime != null) {
            rgExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMath2ExTime0:
                            mMath2ExampleTime = 0;
                            break;
                        case R.id.rbMath2ExTime5:
                            mMath2ExampleTime = 5;
                            break;
                        case R.id.rbMath2ExTime10:
                            mMath2ExampleTime = 10;
                            break;
                        default:
                            break;
                    }
                }
            });
        }



    }


}