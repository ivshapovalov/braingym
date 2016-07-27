package ru.brainworkout.braingym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class ChainCharacterActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mChainCharacterLang;
    private int mChainCharacterMaxTime;
    private int mChainCharacterExampleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_character_options);

        getPreferencesFromFile();

    }

    public void buttonSave_onClick(View view) {


        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE, mChainCharacterLang);
        editor.putInt(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_MAX_TIME, mChainCharacterMaxTime);
        editor.putInt(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME, mChainCharacterExampleTime);

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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME)) {
            mChainCharacterMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_MAX_TIME, 60);
        } else {
            mChainCharacterMaxTime = 60;
        }

        //время тестирования
        int maxtimeID = getResources().getIdentifier("rbChainCharacterMaxTime" + mChainCharacterMaxTime, "id", getPackageName());
        RadioButton butTime = (RadioButton) findViewById(maxtimeID);
        if (butTime != null) {
            butTime.setChecked(true);
        }

        //
        RadioGroup rgMaxTime = (RadioGroup) findViewById(R.id.rgChainCharacterMaxTime);

        if (rgMaxTime != null) {
            rgMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbChainCharacterMaxTime60:
                            mChainCharacterMaxTime = 60;
                            break;
                        case R.id.rbChainCharacterMaxTime120:
                            mChainCharacterMaxTime = 120;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME)) {
            mChainCharacterExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME, 0);
        } else {
            mChainCharacterExampleTime = 0;
        }

        int extimeID = getResources().getIdentifier("rbChainCharacterExTime" + mChainCharacterExampleTime, "id", getPackageName());
        RadioButton exTime = (RadioButton) findViewById(extimeID);
        if (exTime != null) {
            exTime.setChecked(true);
        }
        //
        RadioGroup rgExTime = (RadioGroup) findViewById(R.id.rgChainCharacterExTime);

        if (rgExTime != null) {
            rgExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbChainCharacterExTime0:
                            mChainCharacterExampleTime = 0;
                            break;
                        case R.id.rbChainCharacterExTime5:
                            mChainCharacterExampleTime = 5;
                            break;
                        case R.id.rbChainCharacterExTime10:
                            mChainCharacterExampleTime = 10;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE)) {
            // Получаем язык из настроек
            mChainCharacterLang = mSettings.getString(MainActivity.APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE, "Digit");
        } else {
            mChainCharacterLang = "Digit";
        }
        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("rbChainCharacterLang" + mChainCharacterLang, "id", getPackageName());
        RadioButton butLang = (RadioButton) findViewById(langID);
        if (butLang != null) {
            butLang.setChecked(true);
        }

        //
        RadioGroup rgLang = (RadioGroup) findViewById(R.id.rgChainCharacterLang);

        if (rgLang != null) {
            rgLang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbChainCharacterLangDigit:
                            mChainCharacterLang = "Digit";
                            break;
                        case R.id.rbChainCharacterLangRu:
                            mChainCharacterLang = "Ru";
                            break;
                        case R.id.rbChainCharacterLangEn:
                            mChainCharacterLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }


}