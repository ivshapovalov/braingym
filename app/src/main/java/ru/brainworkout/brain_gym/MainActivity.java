package ru.brainworkout.brain_gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_STRUP_LANGUAGE="strup_language";
    public static final String APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE="strup_font_size_change";
    public static final String APP_PREFERENCES_STRUP_VER1_FONT_SIZE_CHANGE="strup_font_size_change";
    public static final String APP_PREFERENCES_STRUP_VER1_TEST_TIME="strup_ver1_max_test_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME="strup_ver1_max_example_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE="strup_ver1_max_example_type";
    public static final String APP_PREFERENCES_MATRIX_LANGUAGE="matrix_language";
    public static final String APP_PREFERENCES_MATRIX_SIZE="matrix_size";
    public static final String APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE="matrix_font_size_change";
    public static final String APP_PREFERENCES_MATRIX_CLICKABLE="matrix_clickable";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_LANGUAGE="number_search_language";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_SIZE="number_search_size";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_FONT_SIZE_CHANGE="number_search_font_size_change";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_TEST_TIME="number_search_max_test_time";
    public static final String APP_PREFERENCES_NUMBER_SEARCH_EXAMPLE_TIME="number_search_max_example_time";
    public static final String APP_PREFERENCES_MATH_MAXIMUM_DIGIT="math_maximum_digit";
    public static final String APP_PREFERENCES_MATH_FONT_SIZE_CHANGE="math_font_size_change";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE="missing_symbol_language";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME="missing_symbol_max_test_time";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME="missing_symbol_max_example_time";
    public static final String APP_PREFERENCES_CHAIN_CHARACTER_LANGUAGE="chain_character_language";
    public static final String APP_PREFERENCES_CHAIN_CHARACTER_MAX_TIME="chain_character_max_test_time";
    public static final String APP_PREFERENCES_CHAIN_CHARACTER_EXAMPLE_TIME="chain_character_max_example_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> AlphabetRu= MainActivity.AlphabetRu();

      }

    public void mathActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MathActivity.class);
        startActivity(intent);

    }
    public void StrupTestActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this,StrupActivity.class);
        startActivity(intent);

    }

    public void StrupTestActivity_ver1_onClick(View view) {

        Intent intent = new Intent(MainActivity.this,StrupActivity_ver1.class);
        startActivity(intent);

    }

    public void AboutActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
        startActivity(intent);

    }
    public void MatrixActivity_onClick(View view) {
        Intent intent = new Intent(MainActivity.this,MatrixActivity.class);
        startActivity(intent);

    }

    public void MissingSymbolActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this,MissingSymbolActivity.class);
        startActivity(intent);
    }

    public void ChainCharacterActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this,ChainCharacterActivity.class);
        startActivity(intent);
    }

    public void NumberSearchActivity_onClick(View view) {

        Intent intent = new Intent(MainActivity.this,NumberSearchActivity.class);
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


    static ArrayList<String> AlphabetRu() {

        ArrayList<String> AlphabetRu=new ArrayList<>();
        AlphabetRu.add("А");
        AlphabetRu.add("Б");
        AlphabetRu.add("В");
        AlphabetRu.add("Г");
        AlphabetRu.add("Д");
        AlphabetRu.add("Е");
        AlphabetRu.add("Ё");
        AlphabetRu.add("Ж");
        AlphabetRu.add("З");
        AlphabetRu.add("И");
        AlphabetRu.add("Й");
        AlphabetRu.add("К");
        AlphabetRu.add("Л");
        AlphabetRu.add("М");
        AlphabetRu.add("Н");
        AlphabetRu.add("О");
        AlphabetRu.add("П");
        AlphabetRu.add("Р");
        AlphabetRu.add("С");
        AlphabetRu.add("Т");
        AlphabetRu.add("У");
        AlphabetRu.add("Ф");
        AlphabetRu.add("Х");
        AlphabetRu.add("Ц");
        AlphabetRu.add("Ч");
        AlphabetRu.add("Ш");
        AlphabetRu.add("Щ");
        AlphabetRu.add("Ъ");
        AlphabetRu.add("Ы");
        AlphabetRu.add("Ь");
        AlphabetRu.add("Э");
        AlphabetRu.add("Ю");
        AlphabetRu.add("Я");

        return AlphabetRu;
    }

    static ArrayList<String> AlphabetEn(){

        ArrayList<String> AlphabetEn=new ArrayList<>();
        AlphabetEn.add("A");
        AlphabetEn.add("B");
        AlphabetEn.add("C");
        AlphabetEn.add("D");
        AlphabetEn.add("E");
        AlphabetEn.add("F");
        AlphabetEn.add("G");
        AlphabetEn.add("H");
        AlphabetEn.add("I");
        AlphabetEn.add("J");
        AlphabetEn.add("K");
        AlphabetEn.add("L");
        AlphabetEn.add("M");
        AlphabetEn.add("N");
        AlphabetEn.add("O");
        AlphabetEn.add("P");
        AlphabetEn.add("Q");
        AlphabetEn.add("R");
        AlphabetEn.add("S");
        AlphabetEn.add("T");
        AlphabetEn.add("U");
        AlphabetEn.add("V");
        AlphabetEn.add("W");
        AlphabetEn.add("X");
        AlphabetEn.add("Y");
        AlphabetEn.add("Z");

        return AlphabetEn;
    }

}