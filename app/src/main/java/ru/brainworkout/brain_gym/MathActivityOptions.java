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


public class MathActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int mMathMaxDigit;
    private int mMathFontSizeChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_options);

        getPreferencesFromFile();
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
        editor.putInt(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT, mMathMaxDigit);

        int sizeID = getResources().getIdentifier("evMathOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            mMathFontSizeChange= Integer.parseInt((txtSize.getText().toString().equals("")?"0":txtSize.getText().toString()));
        }

        editor.putInt(MainActivity.APP_PREFERENCES_MATH_FONT_SIZE_CHANGE, mMathFontSizeChange);
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

    private void getPreferencesFromFile() {

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
           mMathFontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH_FONT_SIZE_CHANGE, 0);
        } else {
            mMathFontSizeChange = 0;
        }

        //Установим настройки в зависимости от сохраненного языка
        int sizeID = getResources().getIdentifier("evMathOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            txtSize.setText(String.valueOf(mMathFontSizeChange));
        }

        int sizeLabelID = getResources().getIdentifier("tvMathFontSizeLabel", "id", getPackageName());
        TextView tvSizeLabel = (TextView) findViewById(sizeLabelID);

        int mMathTextSize = getIntent().getIntExtra("mMathTextSize", 0);

        if (tvSizeLabel != null) {
            tvSizeLabel.setText("Изменение шрифта ("+String.valueOf(mMathTextSize)+"+/-sp):");
        }



        mMathMaxDigit = 100;
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT)) {
            // Получаем язык из настроек
            mMathMaxDigit = mSettings.getInt(MainActivity.APP_PREFERENCES_MATH_MAXIMUM_DIGIT, 100);
        } else {
            mMathMaxDigit = 100;
        }

        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("radioButton" + String.valueOf(mMathMaxDigit), "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);

        if (but != null) {
            but.setChecked(true);
        }

        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgMathMaxDigit);
//
        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            mMathMaxDigit = 100;
                            break;
                        case R.id.radioButton100:
                            mMathMaxDigit = 100;
                            break;
                        case R.id.radioButton300:
                            mMathMaxDigit = 300;
                            break;
                        case R.id.radioButton1000:
                            mMathMaxDigit = 1000;
                            break;
                        default:
                            mMathMaxDigit = 100;
                            break;
                    }
                }
            });
        }
    }

}