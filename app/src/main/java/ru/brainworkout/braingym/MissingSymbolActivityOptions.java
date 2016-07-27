package ru.brainworkout.braingym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MissingSymbolActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mMissingSymbolLang;
    private int mMissingSymbolMaxTime;
    private int mMissingSymbolExampleTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_symbol_options);

        getPreferencesFromFile();

    }

    public void buttonSave_onClick(View view) {


        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE, mMissingSymbolLang);
        editor.putInt(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME, mMissingSymbolMaxTime);
        editor.putInt(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME, mMissingSymbolExampleTime);

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
            mMissingSymbolMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME, 60);
        } else {
            mMissingSymbolMaxTime = 60;
        }

        //время тестирования
        int maxtimeID = getResources().getIdentifier("rbMissingSymbolMaxTime" + mMissingSymbolMaxTime, "id", getPackageName());
        RadioButton butTime = (RadioButton) findViewById(maxtimeID);
        if (butTime != null) {
            butTime.setChecked(true);
        }

        //
        RadioGroup rgMaxTime = (RadioGroup) findViewById(R.id.rgMissingSymbolMaxTime);

        if (rgMaxTime != null) {
            rgMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMissingSymbolMaxTime60:
                            mMissingSymbolMaxTime = 60;
                            break;
                        case R.id.rbMissingSymbolMaxTime120:
                            mMissingSymbolMaxTime = 120;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME)) {
            mMissingSymbolExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME, 0);
        } else {
            mMissingSymbolExampleTime = 0;
        }

        int extimeID = getResources().getIdentifier("rbMissingSymbolExTime" + mMissingSymbolExampleTime, "id", getPackageName());
        RadioButton exTime = (RadioButton) findViewById(extimeID);
        if (exTime != null) {
            exTime.setChecked(true);
        }
        //
        RadioGroup rgExTime = (RadioGroup) findViewById(R.id.rgMissingSymbolExTime);

        if (rgExTime != null) {
            rgExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMissingSymbolExTime0:
                            mMissingSymbolExampleTime = 0;
                            break;
                        case R.id.rbMissingSymbolExTime5:
                            mMissingSymbolExampleTime = 5;
                            break;
                        case R.id.rbMissingSymbolExTime10:
                            mMissingSymbolExampleTime = 10;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE)) {
            // Получаем язык из настроек
            mMissingSymbolLang = mSettings.getString(MainActivity.APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE, "Digit");
        } else {
            mMissingSymbolLang = "Digit";
        }
        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("rbMissingSymbolLang" + mMissingSymbolLang, "id", getPackageName());
        RadioButton butLang = (RadioButton) findViewById(langID);
        if (butLang != null) {
            butLang.setChecked(true);
        }

        //
        RadioGroup rgLang = (RadioGroup) findViewById(R.id.rgMissingSymbolLang);

        if (rgLang != null) {
            rgLang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMissingSymbolLangDigit:
                            mMissingSymbolLang = "Digit";
                            break;
                        case R.id.rbMissingSymbolLangRu:
                            mMissingSymbolLang = "Ru";
                            break;
                        case R.id.rbMissingSymbolLangEn:
                            mMissingSymbolLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }


}