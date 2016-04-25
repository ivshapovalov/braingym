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


public class StrupActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mStrupLang;
    private int mStrupFontSizeChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup_options);

        getPreferencesFromFile();
        setPreferencesOnScreen();

    }

    public void buttonSave_onClick(View view) {

        //MainActivity.APP_PREFERENCES;
        // int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
        //Button but = (Button) findViewById(resID);

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, mStrupLang);

        int sizeID = getResources().getIdentifier("evStrupOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            mStrupFontSizeChange= Integer.parseInt((txtSize.getText().toString().equals("")?"0":txtSize.getText().toString()));
        }

        editor.putInt(MainActivity.APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE, mStrupFontSizeChange);
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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
            mStrupFontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE, 0);
        } else {
            mStrupFontSizeChange = 0;
        }


        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            mStrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            mStrupLang="Ru";
        }

        //Установим настройки в зависимости от сохраненного языка

    }
    private void setPreferencesOnScreen() {

        //Установим настройки в зависимости от сохраненного языка
        int sizeID = getResources().getIdentifier("evStrupOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            txtSize.setText(String.valueOf(mStrupFontSizeChange));
        }

        int sizeLabelID = getResources().getIdentifier("tvStrupFontSizeLabel", "id", getPackageName());
        TextView tvSizeLabel = (TextView) findViewById(sizeLabelID);

        int mStrupTextSize = getIntent().getIntExtra("mStrupTextSize", 0);

        if (tvSizeLabel != null) {
            tvSizeLabel.setText("Изменение шрифта (" + String.valueOf(mStrupTextSize) + "+/-sp):");
        }

        int langID = getResources().getIdentifier("radioButton"+mStrupLang, "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);
        but.setChecked(true);

        //
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.StrupLang);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
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


    }