package ru.whydt.brain_gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_STRUP_LANGUAGE="strup_language";
    public static final String APP_PREFERENCES_STRUP_FONT_SIZE_CHANGE="strup_font_size_change";
    public static final String APP_PREFERENCES_STRUP_VER1_FONT_SIZE_CHANGE="strup_font_size_change";
    public static final String APP_PREFERENCES_STRUP_VER1_TEST_TIME="strup_ver1_max_test_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME="strup_ver1_max_example_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TYPE="strup_ver1_max_example_type";
    public static final String APP_PREFERENCES_MATRIX_LANGUAGE="matrix_language";
    public static final String APP_PREFERENCES_MATH_MAXIMUM_DIGIT="math_maximum_digit";
    public static final String APP_PREFERENCES_MATH_FONT_SIZE_CHANGE="math_font_size_change";
    public static final String APP_PREFERENCES_MATRIX_SIZE="matrix_size";
    public static final String APP_PREFERENCES_MATRIX_FONT_SIZE_CHANGE="matrix_font_size_change";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_LANGUAGE="missing_symbol_language";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_MAX_TIME="missing_symbol_max_test_time";
    public static final String APP_PREFERENCES_MISSING_SYMBOL_EXAMPLE_TIME="missing_symbol_max_example_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    static String[] AlphabetRu() {

        String [] AlphabetRu=new String[33];
        AlphabetRu[0]="А";       AlphabetRu[7]="Ж";       AlphabetRu[14]="Н";
        AlphabetRu[1]="Б";       AlphabetRu[8]="З";
        AlphabetRu[2]="В";       AlphabetRu[9]="И";       AlphabetRu[15]="О";       AlphabetRu[21]="Ф";
        AlphabetRu[3]="Г";       AlphabetRu[10]="Й";       AlphabetRu[16]="П";       AlphabetRu[22]="Х";
        AlphabetRu[4]="Д";       AlphabetRu[11]="К";       AlphabetRu[17]="Р";       AlphabetRu[23]="Ц";
        AlphabetRu[5]="Е";       AlphabetRu[12]="Л";       AlphabetRu[18]="С";       AlphabetRu[24]="Ч";
        AlphabetRu[6]="Ё";       AlphabetRu[13]="М";        AlphabetRu[19]="Т";

        AlphabetRu[20]="У";
        AlphabetRu[25]="Ш";
        AlphabetRu[26]="Щ";
        AlphabetRu[27]="Ъ";
        AlphabetRu[28]="Ы";
        AlphabetRu[29]="Ь";
        AlphabetRu[30]="Э";
        AlphabetRu[31]="Ю";
        AlphabetRu[32]="Я";
        return AlphabetRu;
    }

    static String[]  AlphabetEn() {

        String[] AlphabetEn=new String[26];
        AlphabetEn[0]="A";       AlphabetEn[7]="H";       AlphabetEn[13]="N";       AlphabetEn[19]="T";
        AlphabetEn[1]="B";       AlphabetEn[8]="I";       AlphabetEn[14]="O";       AlphabetEn[20]="U";
        AlphabetEn[2]="C";       AlphabetEn[9]="J";       AlphabetEn[15]="P";       AlphabetEn[21]="V";
        AlphabetEn[3]="D";       AlphabetEn[10]="K";       AlphabetEn[16]="Q";       AlphabetEn[22]="W";
        AlphabetEn[4]="E";       AlphabetEn[11]="L";       AlphabetEn[17]="R";       AlphabetEn[23]="X";
        AlphabetEn[5]="F";       AlphabetEn[12]="M";       AlphabetEn[18]="S";       AlphabetEn[24]="Y";
        AlphabetEn[6]="G";                                                             AlphabetEn[25]="Z";

        return AlphabetEn;
    }

}