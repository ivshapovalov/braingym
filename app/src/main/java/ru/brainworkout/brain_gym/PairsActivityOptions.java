package ru.brainworkout.brain_gym;

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
import android.widget.Toast;


public class PairsActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int mPairsSizeHeight;
    private int mPairsSizeWidth;
    private String mPairsLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairs_options);

        getPreferencesFromFile();
        setPreferencesOnScreen();
    }


    public void buttonHome_onClick(View view) {

//        Intent intent = new Intent(MathActivityOptions.this, MainActivity.class);
//        startActivity(intent);
//        this.finish();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void buttonSave_onClick(View view) {

        if ((mPairsSizeHeight*mPairsSizeWidth)%2==1) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Количество ячеек при выбранной размерности не четно. Выберите другой размер!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(MainActivity.APP_PREFERENCES_PAIRS_LANGUAGE, mPairsLang);
            editor.putInt(MainActivity.APP_PREFERENCES_PAIRS_SIZE_HEIGHT, mPairsSizeHeight);
            editor.putInt(MainActivity.APP_PREFERENCES_PAIRS_SIZE_WIDTH, mPairsSizeWidth);

            editor.apply();

//        Intent intent = new Intent(MathActivityOptions.this, MathActivity.class);
//        startActivity(intent);
            this.finish();
        }

    }

    public void buttonCancel_onClick(View view) {

//        Intent intent = new Intent(MathActivityOptions.this, MathActivity.class);
//        startActivity(intent);
        this.finish();

    }

    private void getPreferencesFromFile() {

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


    }

    private void setPreferencesOnScreen() {
        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("rbPairsLang" + String.valueOf(mPairsLang), "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);

        if (but != null) {
            but.setChecked(true);
        }

        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgPairsLang);
//
        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            mPairsLang = "Digit";
                            break;
                        case R.id.rbPairsLangDigit:
                            mPairsLang = "Digit";
                            break;
                        case R.id.rbPairsLangRu:
                            mPairsLang = "Ru";
                            break;
                        case R.id.rbPairsLangEn:
                            mPairsLang = "En";
                            break;
                        case R.id.rbPairsLangColor:
                            mPairsLang = "Color";
                            break;
                        default:
                            mPairsLang = "Digit";
                            break;
                    }
                }
            });
        }

        int heightID = getResources().getIdentifier("rbPairsSizeHeight" + String.valueOf(mPairsSizeHeight), "id", getPackageName());
        RadioButton butHeight = (RadioButton) findViewById(heightID);

        if (butHeight != null) {
            butHeight.setChecked(true);
        }

        RadioGroup rgHeight = (RadioGroup) findViewById(R.id.rgPairsSizeHeight);
//
        if (rgHeight != null) {
            rgHeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String mResName = "";
                    try {
                        mResName = getResources().getResourceName(checkedId);
                        mPairsSizeHeight = Integer.valueOf(String.valueOf(mResName.charAt(mResName.length() - 1)));
                    } catch (Exception e) {
                        //mPairsSizeHeight = 4;
                    }
                }
            });
        }

        int widthID = getResources().getIdentifier("rbPairsSizeWidth" + String.valueOf(mPairsSizeWidth), "id", getPackageName());
        RadioButton butWidth = (RadioButton) findViewById(widthID);

        if (butWidth != null) {
            butWidth.setChecked(true);
        }

        RadioGroup rgWidth = (RadioGroup) findViewById(R.id.rgPairsSizeWidth);
//
        if (rgWidth != null) {
            rgWidth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String mResName = "";
                    try {
                        mResName = getResources().getResourceName(checkedId);
                        mPairsSizeWidth = Integer.valueOf(String.valueOf(mResName.charAt(mResName.length() - 1)));
                    } catch (Exception e) {
                        //mPairsSizeHeight = 4;
                    }

                }
            });
        }

    }

}