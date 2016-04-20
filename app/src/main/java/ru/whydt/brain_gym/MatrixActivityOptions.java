package ru.whydt.brain_gym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MatrixActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mMatrixLang;
    private int mMatrixSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_options);


        ;
        getPreferencesFromFile();
        setPreferencesOnScreen();

    }


    public void buttonHome_onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void buttonSave_onClick(View view) {


        if ((!mMatrixLang.equals("Digit")) && (mMatrixSize>5)) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "В выбранных языках нет столько букв. Выберите цифры или измените размерность!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(MainActivity.APP_PREFERENCES_MATRIX_LANGUAGE, mMatrixLang);
            editor.putInt(MainActivity.APP_PREFERENCES_MATRIX_SIZE, mMatrixSize);
            editor.apply();

            this.finish();
        }

    }

    public void buttonCancel_onClick(View view) {

        this.finish();

    }

    private void getPreferencesFromFile() {
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        mMatrixLang = "Digit";
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_LANGUAGE)) {
            // Получаем язык из настроек
            try {
                mMatrixLang = mSettings.getString(MainActivity.APP_PREFERENCES_MATRIX_LANGUAGE, "Digit");
            } catch (Exception e) {
                mMatrixLang = "Digit";
            }
            try {
                mMatrixSize = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_SIZE, 5);
            } catch (Exception e) {
                mMatrixSize=5;
            }
        } else {
            mMatrixLang = "Digit";
            mMatrixSize=5;
        }
    }

    private void setPreferencesOnScreen() {
        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("radioButtonLang" + mMatrixLang, "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);
        if (but != null) {
            but.setChecked(true);
        }

        //
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgMatrixLanguage);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonLangDigit:
                            mMatrixLang = "Digit";
                            break;
                        case R.id.radioButtonLangRu:
                            mMatrixLang = "Ru";
                            break;
                        case R.id.radioButtonLangEn:
                            mMatrixLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //Установим настройки в зависимости от сохраненного размера
        int sizeID = getResources().getIdentifier("radioButtonSize" + String.valueOf(mMatrixSize), "id", getPackageName());
        but = (RadioButton) findViewById(sizeID);
        if (but != null) {
            but.setChecked(true);
        }

        //
        radiogroup = (RadioGroup) findViewById(R.id.rgMatrixSize);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.radioButtonSize5:
                            mMatrixSize= 5;
                            break;
                        case  R.id.radioButtonSize6:
                            mMatrixSize= 6;
                            break;
                        case  R.id.radioButtonSize7:
                            mMatrixSize= 7;
                            break;
                        default:
                            mMatrixSize= 5;
                            break;
                    }
                }
            });
        }
    }

}