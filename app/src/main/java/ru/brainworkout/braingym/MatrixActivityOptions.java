package ru.brainworkout.braingym;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MatrixActivityOptions extends AppCompatActivity {

    private SharedPreferences mSettings;
    private String mMatrixLang;
    private int mMatrixSize;
    private int mMatrixFontSizeChange;
    private boolean mMatrixIsClickable;
    private int mMatrixMaxTime;
    private int mMatrixExampleTime;

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


        if ((!mMatrixLang.equals("Digit")) && (mMatrixSize > 5)) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "В выбранных языках нет столько букв. Выберите цифры или измените размерность!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(MainActivity.APP_PREFERENCES_MATRIX_LANGUAGE, mMatrixLang);
            editor.putInt(MainActivity.APP_PREFERENCES_MATRIX_SIZE, mMatrixSize);
            editor.putInt(MainActivity.APP_PREFERENCES_MATRIX_TEST_TIME, mMatrixMaxTime);
            editor.putInt(MainActivity.APP_PREFERENCES_MATRIX_EXAMPLE_TIME, mMatrixExampleTime);
            editor.putBoolean(MainActivity.APP_PREFERENCES_MATRIX_CLICKABLE, mMatrixIsClickable);

            int sizeID = getResources().getIdentifier("evMatrixOptionsFontSizeChange", "id", getPackageName());
            EditText txtSize = (EditText) findViewById(sizeID);

            if (txtSize != null) {
                mMatrixFontSizeChange = Integer.parseInt((txtSize.getText().toString().equals("") ? "0" : txtSize.getText().toString()));
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
        } else {
            mMatrixLang = "Digit";
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_SIZE)) {
            try {
                mMatrixSize = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_SIZE, 5);
            } catch (Exception e) {
                mMatrixSize = 5;
            }
        } else {

            mMatrixSize = 5;
        }

        mMatrixIsClickable = false;
        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_CLICKABLE)) {
            // Получаем язык из настроек
            try {
                mMatrixIsClickable = mSettings.getBoolean(MainActivity.APP_PREFERENCES_MATRIX_CLICKABLE, false);
            } catch (Exception e) {
                mMatrixIsClickable = false;
            }

        } else {
            mMatrixIsClickable = false;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_TEST_TIME)) {
            mMatrixMaxTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_TEST_TIME, 0);
        } else {
            mMatrixMaxTime = 0;
        }


        if (mSettings.contains(MainActivity.APP_PREFERENCES_MATRIX_EXAMPLE_TIME)) {
            mMatrixExampleTime = mSettings.getInt(MainActivity.APP_PREFERENCES_MATRIX_EXAMPLE_TIME, 0);
        } else {
            mMatrixExampleTime = 0;
        }
    }

    private void setPreferencesOnScreen() {

        //Установим настройки в зависимости от сохраненного языка
        int sizeID = getResources().getIdentifier("evMatrixOptionsFontSizeChange", "id", getPackageName());
        EditText txtSize = (EditText) findViewById(sizeID);

        if (txtSize != null) {
            txtSize.setText(String.valueOf(mMatrixFontSizeChange));
        }

        int sizeLabelID = getResources().getIdentifier("tvMatrixOptionsFontSizeLabel", "id", getPackageName());
        TextView tvSizeLabel = (TextView) findViewById(sizeLabelID);

        int mMatrixTextSize = getIntent().getIntExtra("mMatrixTextSize", 0);

        if (tvSizeLabel != null) {
            tvSizeLabel.setText("Изменение шрифта (" + String.valueOf(mMatrixTextSize) + "+/-sp):");
        }

        //Установим настройки в зависимости от сохраненного языка
        int langID = getResources().getIdentifier("rbMatrixOptionsLang" + mMatrixLang, "id", getPackageName());
        RadioButton but = (RadioButton) findViewById(langID);
        if (but != null) {
            but.setChecked(true);
        }

        //
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgMatrixOptionsLanguage);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMatrixOptionsLangDigit:
                            mMatrixLang = "Digit";
                            break;
                        case R.id.rbMatrixOptionsLangRu:
                            mMatrixLang = "Ru";
                            break;
                        case R.id.rbMatrixOptionsLangEn:
                            mMatrixLang = "En";
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //Установим настройки в зависимости от сохраненного размера
        int size1ID = getResources().getIdentifier("rbMatrixOptionsSize" + String.valueOf(mMatrixSize), "id", getPackageName());
        but = (RadioButton) findViewById(size1ID);
        if (but != null) {
            but.setChecked(true);
        }

        //Размер
        radiogroup = (RadioGroup) findViewById(R.id.rgMatrixOptionsSize);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMatrixOptionsSize5:
                            mMatrixSize = 5;
                            break;
                        case R.id.rbMatrixOptionsSize6:
                            mMatrixSize = 6;
                            break;
                        case R.id.rbMatrixOptionsSize7:
                            mMatrixSize = 7;
                            break;
                        default:
                            mMatrixSize = 5;
                            break;
                    }
                }
            });
        }

        //матрица кликабельна

        int clickID = getResources().getIdentifier("rbMatrixOptionsIsClickable" + (mMatrixIsClickable ? "Yes" : "No"), "id", getPackageName());
        but = (RadioButton) findViewById(clickID);
        if (but != null) {
            but.setChecked(true);
        }
        radiogroup = (RadioGroup) findViewById(R.id.rgMatrixOptionsIsClickable);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMatrixOptionsIsClickableYes:
                            mMatrixIsClickable = true;
                            break;
                        case R.id.rbMatrixOptionsIsClickableNo:
                            mMatrixIsClickable = false;
                            break;
                        default:
                            mMatrixIsClickable = false;
                            break;
                    }
                    hideSomeOptions();
                }
            });
        }
        //время тестирования
        int maxtimeID = getResources().getIdentifier("rbMatrixOptionsMaxTime" + mMatrixMaxTime, "id", getPackageName());
        RadioButton butTime = (RadioButton) findViewById(maxtimeID);
        if (butTime != null) {
            butTime.setChecked(true);
        }

        //
        RadioGroup rgMaxTime = (RadioGroup) findViewById(R.id.rgMatrixOptionsMaxTime);

        if (rgMaxTime != null) {
            rgMaxTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMatrixOptionsMaxTime9999:
                            mMatrixMaxTime = 9999;
                            break;
                        case R.id.rbMatrixOptionsMaxTime60:
                            mMatrixMaxTime = 60;
                            break;
                        case R.id.rbMatrixOptionsMaxTime120:
                            mMatrixMaxTime = 120;
                            break;
                        case R.id.rbMatrixOptionsMaxTime180:
                            mMatrixMaxTime = 180;
                            break;
                        default:
                            mMatrixMaxTime = 9999;
                            break;
                    }
                }
            });
        }

        int extimeID = getResources().getIdentifier("rbMatrixOptionsExampleTime" + mMatrixExampleTime, "id", getPackageName());
        RadioButton exTime = (RadioButton) findViewById(extimeID);
        if (exTime != null) {
            exTime.setChecked(true);
        }
        //
        RadioGroup rgExTime = (RadioGroup) findViewById(R.id.rgMatrixOptionsExampleTime);

        if (rgExTime != null) {
            rgExTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbMatrixOptionsExampleTime0:
                            mMatrixExampleTime = 0;
                            break;
                        case R.id.rbMatrixOptionsExampleTime5:
                            mMatrixExampleTime = 5;
                            break;
                        case R.id.rbMatrixOptionsExampleTime10:
                            mMatrixExampleTime = 10;
                            break;
                        case R.id.rbMatrixOptionsExampleTime20:
                            mMatrixExampleTime = 20;
                            break;
                        default:
                            mMatrixExampleTime = 0;
                            break;
                    }
                }
            });
        }
        //скроем настройки если матрица не кликабельна
        hideSomeOptions();

    }

    private void hideSomeOptions() {

        int lineMaxTimeID = getResources().getIdentifier("lineMatrixOptionsMaxTime", "id", getPackageName());
        LinearLayout lmaxTime = (LinearLayout) findViewById(lineMaxTimeID);
        if (lmaxTime != null) {
            if (!mMatrixIsClickable) {
                lmaxTime.setVisibility(View.GONE);
            } else {
                lmaxTime.setVisibility(View.VISIBLE);
            }
        }
        int lineExampleTimeID = getResources().getIdentifier("lineMatrixOptionsExampleTime", "id", getPackageName());
        LinearLayout lExTime = (LinearLayout) findViewById(lineExampleTimeID);
        if (lExTime != null) {
            if (!mMatrixIsClickable) {
                lExTime.setVisibility(View.GONE);
            }else{
                lExTime.setVisibility(View.VISIBLE);
            }
        }

    }

}