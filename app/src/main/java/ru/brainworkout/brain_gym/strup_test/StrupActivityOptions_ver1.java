package ru.brainworkout.brain_gym.strup_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import ru.brainworkout.brain_gym.MainActivity;
import ru.brainworkout.brain_gym.R;


public class StrupActivityOptions_ver1 extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mStrupLang;
    private int mStrupMaxTime;
    private int mStrupExampleTime;
    private String mStrupExampleType;
    private int mStrupVer1FontSizeChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup_options_ver1);

        getPreferencesFromFile();
        setPreferencesOnScreen();

    }

    public void buttonStrupOptionsVer1Save_onClick(View view) {

        //MainActivity.APP_PREFERENCES;
        // int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        //Button but = (Button) findViewById(resID);

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, mStrupLang);
        editor.putInt(MainActivity.APP_PREFERENCES_STRUP_VER1_TEST_TIME, mStrupMaxTime);
        editor.putInt(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME, mStrupExampleTime);
        editor.putString(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE, mStrupExampleType);
        int sizeID = getResources().getIdentifier("evStrupVer1OptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            mStrupVer1FontSizeChange= Integer.parseInt((txtSize.getText().toString().equals("")?"0":txtSize.getText().toString()));
        }

        editor.putInt(MainActivity.APP_PREFERENCES_STRUP_VER1_FONT_SIZE_CHANGE, mStrupVer1FontSizeChange);
        editor.apply();


        this.finish();
    }

    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void buttonStrupOptionsVer1Cancel_onClick(View view) {

        this.finish();

    }

    private void getPreferencesFromFile() {

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_VER1_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
            mStrupVer1FontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_STRUP_VER1_FONT_SIZE_CHANGE, 0);
        } else {
            mStrupVer1FontSizeChange = 0;
        }

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

          if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE)) {
            mStrupExampleType = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE, "RANDOM");
        } else {
            mStrupExampleType = "RANDOM";
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            mStrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            mStrupLang = "Ru";
        }
    }


    private void setPreferencesOnScreen() {

        int sizeID = getResources().getIdentifier("evStrupVer1OptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            txtSize.setText(String.valueOf(mStrupVer1FontSizeChange));
        }

        int sizeLabelID = getResources().getIdentifier("tvStrupVer1FontSizeLabel", "id", getPackageName());
        TextView tvSizeLabel = (TextView) findViewById(sizeLabelID);

        int mStrupVer1TextSize = getIntent().getIntExtra("mStrupVer1TextSize", 0);

        if (tvSizeLabel != null) {
            tvSizeLabel.setText("Изменение шрифта ("+String.valueOf(mStrupVer1TextSize)+"+/-sp):");
        }

        int maxtimeID = getResources().getIdentifier("rbStrupVer1MaxTime" + mStrupMaxTime, "id", getPackageName());
        RadioButton butTime = (RadioButton) findViewById(maxtimeID);
        if (butTime != null) {
            butTime.setChecked(true);
        }

        RadioGroup radiogroupMaxTime = (RadioGroup) findViewById(R.id.rgStrupVer1MaxTime);

        if (radiogroupMaxTime != null) {
            radiogroupMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbStrupVer1MaxTime60:
                            mStrupMaxTime = 60;
                            break;
                        case R.id.rbStrupVer1MaxTime120:
                            mStrupMaxTime = 120;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        int extimeID = getResources().getIdentifier("rbStrupVer1ExTime" + mStrupExampleTime, "id", getPackageName());
        RadioButton exTime = (RadioButton) findViewById(extimeID);
        if (exTime != null) {
            exTime.setChecked(true);
        }

        RadioGroup radiogroupExTime = (RadioGroup) findViewById(R.id.rgStrupVer1ExTime);

        if (radiogroupExTime != null) {
            radiogroupExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbStrupVer1ExTime0:
                            mStrupExampleTime = 0;
                            break;
                        case R.id.rbStrupVer1ExTime5:
                            mStrupExampleTime = 5;
                            break;
                        case R.id.rbStrupVer1ExTime10:
                            mStrupExampleTime = 10;
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        int extypeID = getResources().getIdentifier("rbStrupVer1ExType" + mStrupExampleType, "id", getPackageName());
        RadioButton exType = (RadioButton) findViewById(extypeID);
        if (exType != null) {
            exType.setChecked(true);
        }

        RadioGroup radiogroupExType = (RadioGroup) findViewById(R.id.rgStrupVer1ExType);

        if (radiogroupExType != null) {
            radiogroupExType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbStrupVer1ExTypeRANDOM:
                            mStrupExampleType = "RANDOM";
                            break;
                        case R.id.rbStrupVer1ExTypeCOLOR:
                            mStrupExampleType = "COLOR";
                            break;
                        case R.id.rbStrupVer1ExTypeWORD:
                            mStrupExampleType = "WORD";
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("rbStrupVer1Lang" + mStrupLang, "id", getPackageName());
        RadioButton butLang = (RadioButton) findViewById(langID);
        if (butLang != null) {
            butLang.setChecked(true);
        }


        //
        RadioGroup radiogroupLang = (RadioGroup) findViewById(R.id.rgStrupVer1Lang);

        if (radiogroupLang != null) {
            radiogroupLang.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbStrupVer1LangRu:
                            mStrupLang = "Ru";
                            break;
                        case R.id.rbStrupVer1LangEn:
                            mStrupLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }


}