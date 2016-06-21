package ru.brainworkout.brain_gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import ru.brainworkout.brain_gym.math_test.MathActivity;
import ru.brainworkout.brain_gym.strup_test.StrupActivity;
import ru.brainworkout.brain_gym.strup_test.StrupActivity_ver1;


public class MainActivity extends AppCompatActivity {

    public static final boolean isDebug = true;
    private SharedPreferences mSettings;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_STRUP_LANGUAGE = "strup_language";
    public static final String APP_PREFERENCES_STRUP_COLORS_COUNT = "strup_colors_count";
    public static final String APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE = "strup_font_size_change";
    public static final String APP_PREFERENCES_STRUP_VER1_FONT_SIZE_CHANGE = "strup_font_size_change";
    public static final String APP_PREFERENCES_STRUP_VER1_TEST_TIME = "strup_ver1_max_test_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME = "strup_ver1_max_example_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE = "strup_ver1_max_example_type";
    public static final String APP_PREFERENCES_PAIRS_LANGUAGE = "pairs_language";
    public static final String APP_PREFERENCES_PAIRS_SIZE_HEIGHT = "pairs_size_height";
    public static final String APP_PREFERENCES_PAIRS_SIZE_WIDTH = "pairs_size_width";
    public static final String APP_PREFERENCES_RECTANGLES_COUNT_FILLING = "rectangles_count_filling";
    public static final String APP_PREFERENCES_RECTANGLES_COUNT_SIZE_HEIGHT = "rectangles_count_size_height";
    public static final String APP_PREFERENCES_RECTANGLES_COUNT_SIZE_WIDTH = "rectangles_count_size_width";
    public static final String APP_PREFERENCES_RECTANGLES_COUNT_TEST_TIME = "rectangles_count_max_test_time";
    public static final String APP_PREFERENCES_RECTANGLES_COUNT_EXAMPLE_TIME = "rectangles_count_max_example_time";
    public static final String APP_PREFERENCES_MATRIX_LANGUAGE = "matrix_language";
    public static final String APP_PREFERENCES_MATRIX_SIZE = "matrix_size";
    public static final String APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE = "matrix_font_size_change";
    public static final String APP_PREFERENCES_MATRIX_CLICKABLE = "matrix_clickable";
    public static final String APP_PREFERENCES_MATRIX_TEST_TIME = "matrix_max_test_time";
    public static final String APP_PREFERENCES_MATRIX_EXAMPLE_TIME = "matrix_max_example_time";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_LANGUAGE = "number_search_language";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_SIZE = "number_search_size";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_NUMBER_SYMBOLS = "number_search_number_symbols";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_FONT_SIZE_CHANGE = "number_search_font_size_change";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_TEST_TIME = "number_search_max_test_time";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_EXAMPLE_TIME = "number_search_max_example_time";
    public static final String APP_PREFERENCES_MATH_MAXIMUM_DIGIT = "math_maximum_digit";
    public static final String APP_PREFERENCES_MATH_FONT_SIZE_CHANGE = "math_font_size_change";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE = "missing_symbol_language";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME = "missing_symbol_max_test_time";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME = "missing_symbol_max_example_time";
    public static final String APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE = "chain_character_language";
    public static final String APP_PREFERENCES_CHAIN_CHARACTER_MAX_TIME = "chain_character_max_test_time";
    public static final String APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME = "chain_character_max_example_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] i=new int[5];
           }

    public void pairsActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, PairsActivity.class);
        startActivity(intent);
    }

    public void mathActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MathActivity.class);
        startActivity(intent);

    }



    public void StrupTestActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this, StrupActivity.class);
        startActivity(intent);

    }

    public void StrupTestActivity_ver1_onClick(View view) {

        Intent intent = new Intent(MainActivity.this, StrupActivity_ver1.class);
        startActivity(intent);

    }

    public void AboutActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);

    }

    public void MatrixActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MatrixActivity.class);
        startActivity(intent);

    }

    public void MissingSymbolActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this, MissingSymbolActivity.class);
        startActivity(intent);
    }

    public void ChainCharacterActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this, ChainCharacterActivity.class);
        startActivity(intent);
    }

    public void NumberSearchActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this, NumberSearchActivity.class);
        startActivity(intent);
    }

    public void RectanglesCountActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this, RectanglesCountActivity.class);
        startActivity(intent);
    }

    public void exit_onClick(View view) {
        onBackPressed();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Вы действительно хотите покинуть программу?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("Нет", null).show();
    }




    public static void MyLogger(String TAG,String statement){
        if (isDebug) {
            Log.v(TAG, statement);
        }
    }


}
