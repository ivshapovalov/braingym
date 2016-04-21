package ru.whydt.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.RadioButton;
import android.widget.RadioGroup;


public class StrupActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String StrupLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strup_options);

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE)) {
            // Получаем язык из настроек
            StrupLang = mSettings.getString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, "Ru");
        } else {
            StrupLang="Ru";
        }

        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("radioButton"+StrupLang, "id", getPackageName());
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
                            StrupLang = "Ru";
                            break;
                        case R.id.radioButtonEn:
                            StrupLang = "En";
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
        editor.putString(MainActivity.APP_PREFERENCES_STRUP_LANGUAGE, StrupLang);
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