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


public class MatrixActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mMatrixLang;
    private int mMatrixSize;
    private int mMatrixFontSizeChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_options);

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

            int sizeID = getResources().getIdentifier("evMatrixOptionsFontSizeChange", "id", getPackageName());
            EditText txtSize = (EditText) findViewById(sizeID);

            if (txtSize != null) {
                mMatrixFontSizeChange= Integer.parseInt((txtSize.getText().toString().equals("")?"0":txtSize.getText().toString()));
            }

            editor.putInt(MainActivity.APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE, mMatrixFontSizeChange);
            editor.apply();

            this.finish();
        }

    }

    public void buttonCancel_onClick(View view) {

        this.finish();

    }

    private void getPreferencesFromFile() {
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE)) {
            // Получаем язык из настроек
            mMatrixFontSizeChange = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE, 0);
        } else {
            mMatrixFontSizeChange = 0;
        }

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
        int sizeID = getResources().getIdentifier("evMatrixOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            txtSize.setText(String.valueOf(mMatrixFontSizeChange));
        }

        int sizeLabelID = getResources().getIdentifier("tvMatrixFontSizeLabel", "id", getPackageName());
        TextView tvSizeLabel = (TextView) findViewById(sizeLabelID);

        int mMatrixTextSize = getIntent().getIntExtra("mMatrixTextSize", 0);

        if (tvSizeLabel != null) {
            tvSizeLabel.setText("Изменение шрифта ("+String.valueOf(mMatrixTextSize)+"+/-sp):");
        }

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
        int size1ID = getResources().getIdentifier("radioButtonSize" + String.valueOf(mMatrixSize), "id", getPackageName());
        but = (RadioButton) findViewById(size1ID);
        if (but != null) {
            but.setChecked(true);
        }

        //
        radiogroup = (RadioGroup) findViewById(R.id.rgMatrixSize);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
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