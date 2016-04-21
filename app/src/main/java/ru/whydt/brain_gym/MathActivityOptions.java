package ru.whydt.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MathActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int MathMaxDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_options);

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        MathMaxDigit = 150;
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT)) {
            // Получаем язык из настроек
            MathMaxDigit = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT, 150);
        } else {
            MathMaxDigit = 150;
        }


        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("radioButton" + String.valueOf(MathMaxDigit), "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);

        if (but != null) {
            but.setChecked(true);
        }

        //
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgMathMaxDigit);
//
        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            MathMaxDigit = 150;
                            break;
                        case R.id.radioButton150:
                            MathMaxDigit = 150;
                            break;
                        case R.id.radioButton300:
                            MathMaxDigit = 300;
                            break;
                        case R.id.radioButton1000:
                            MathMaxDigit = 1000;
                            break;
                        default:
                            MathMaxDigit = 150;
                            break;
                    }
                }
            });
        }
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

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT, MathMaxDigit);
        editor.apply();

//        Intent intent = new Intent(MathActivityOptions.this, MathActivity.class);
//        startActivity(intent);
        this.finish();

    }

    public void buttonCancel_onClick(View view) {

//        Intent intent = new Intent(MathActivityOptions.this, MathActivity.class);
//        startActivity(intent);
        this.finish();

    }


}