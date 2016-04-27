package ru.whydt.brain_gym;

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


public class NumberSearchActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mNumberSearchLang;
    private int mNumberSearchSize;
    private int mNumberSearchFontSizeChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_search_options);

        getPreferencesFromFile();
        setPreferencesOnScreen();

    }


    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void buttonSave_onClick(View view) {


//        if ((!mNumberSearchLang.equals("Digit")) && (mNumberSearchSize>5)) {
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "В выбранных языках нет столько букв. Выберите цифры или измените размерность!", Toast.LENGTH_SHORT);
//            toast.show();
//        } else {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_LANGUAGE, mNumberSearchLang);
            editor.putInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_SIZE, mNumberSearchSize);

            int sizeID = getResources().getIdentifier("evNumberSearchOptionsFontSizeChange", "id", getPackageName());
            EditText txtSize = (EditText) findViewById(sizeID);

            if (txtSize != null) {
                mNumberSearchFontSizeChange= Integer.parseInt((txtSize.getText().toString().equals("")?"0":txtSize.getText().toString()));
            }

            editor.putInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_FONT_SIZE_CHANGE, mNumberSearchFontSizeChange);
            editor.apply();

            this.finish();
//        }

    }

    public void buttonCancel_onClick(View view) {

        this.finish();

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
            try {
                mNumberSearchSize = mSettings.getInt(MainActivity.APP_PREFERENCES_NUMBER_SEARCH_SIZE, 5);
            } catch (Exception e) {
                mNumberSearchSize=5;
            }
        } else {
            mNumberSearchLang = "Digit";
            mNumberSearchSize=5;
        }
    }

    private void setPreferencesOnScreen() {

        //Установим настройки в зависимости от сохраненного языка
        int sizeID = getResources().getIdentifier("evNumberSearchOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            txtSize.setText(String.valueOf(mNumberSearchFontSizeChange));
        }

        int sizeLabelID = getResources().getIdentifier("tvNumberSearchFontSizeLabel", "id", getPackageName());
        TextView tvSizeLabel = (TextView) findViewById(sizeLabelID);

        int mNumberSearchTextSize = getIntent().getIntExtra("mNumberSearchTextSize", 0);

        if (tvSizeLabel != null) {
            tvSizeLabel.setText("Изменение шрифта ("+String.valueOf(mNumberSearchTextSize)+"+/-sp):");
        }

        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("radioButtonNumberSearchLang" + mNumberSearchLang, "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);
        if (but != null) {
            but.setChecked(true);
        }

        //
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgNumberSearchLanguage);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonNumberSearchLangDigit:
                            mNumberSearchLang = "Digit";
                            break;
                        case R.id.radioButtonNumberSearchLangRu:
                            mNumberSearchLang = "Ru";
                            break;
                        case R.id.radioButtonNumberSearchLangEn:
                            mNumberSearchLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //Установим настройки в зависимости от сохраненного размера
        int size1ID = getResources().getIdentifier("radioButtonNumberSearchSize" + String.valueOf(mNumberSearchSize), "id", getPackageName());
        but = (RadioButton) findViewById(size1ID);
        if (but != null) {
            but.setChecked(true);
        }

        //
        radiogroup = (RadioGroup) findViewById(R.id.rgNumberSearchSize);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonNumberSearchSize5:
                            mNumberSearchSize= 5;
                            break;
                        case  R.id.radioButtonSize6:
                            mNumberSearchSize= 6;
                            break;
                        case  R.id.radioButtonSize7:
                            mNumberSearchSize= 7;
                            break;
                        default:
                            mNumberSearchSize= 5;
                            break;
                    }
                }
            });
        }
    }

}