package ru.whydt.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class StrupActivityOptions_ver1 extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mStrupLang;
    private int mStrupMaxTime;
    private int mStrupExampleTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup_options_ver1);

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

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
        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            mStrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            mStrupLang="Ru";
        }

        //время тестирования
        int maxtimeID = getResources().getIdentifier("radioButtonStrupMaxTime"+mStrupMaxTime, "id", getPackageName());
        RadioButton butTime = (RadioButton) findViewById(maxtimeID);
        butTime.setChecked(true);

        //
        RadioGroup radiogroupMaxTime = (RadioGroup) findViewById(R.id.rgStrupMaxTime);

        if (radiogroupMaxTime != null) {
            radiogroupMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonStrupMaxTime60:
                            mStrupMaxTime = 60;
                            break;
                        case R.id.radioButtonStrupMaxTime120:
                            mStrupMaxTime=120;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        int extimeID = getResources().getIdentifier("radioButtonStrupExTime"+mStrupExampleTime, "id", getPackageName());
        RadioButton exTime = (RadioButton) findViewById(extimeID);
        exTime.setChecked(true);

        //
        RadioGroup radiogroupExTime = (RadioGroup) findViewById(R.id.rgStrupExTime);

        if (radiogroupExTime != null) {
            radiogroupExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonStrupExTime0:
                            mStrupExampleTime = 0;
                            break;
                        case R.id.radioButtonStrupExTime5:
                            mStrupExampleTime=5;
                            break;
                        case R.id.radioButtonStrupExTime10:
                            mStrupExampleTime=10;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("radioButton"+mStrupLang, "id", getPackageName());
        RadioButton butLang = (RadioButton) findViewById(langID);
        butLang.setChecked(true);

        //
        RadioGroup radiogroupLang = (RadioGroup) findViewById(R.id.StrupLang);

        if (radiogroupLang != null) {
            radiogroupLang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonRu:
                            mStrupLang = "Ru";
                            break;
                        case R.id.radioButtonEn:
                            mStrupLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    public void buttonSave_onClick(View view) {

        //MainActivity.APP_PREFERENCES;
        // int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        //Button but = (Button) findViewById(resID);

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, mStrupLang);
        editor.putInt(MainActivity.APP_PREFERENCES_STRUP_VER1_TEST_TIME, mStrupMaxTime);
        editor.putInt(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME, mStrupExampleTime);
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


}