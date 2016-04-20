package ru.whydt.brain_gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_STRUP_LANGUAGE="strup_language";
    public static final String APP_PREFERENCES_STRUP_VER1_TEST_TIME="strup_ver1_max_test_time";
    public static final String APP_PREFERENCES_STRUP_VER1_EXAMPLE_TIME="strup_ver1_max_example_time";
    public static final String APP_PREFERENCES_MATRIX_LANGUAGE="matrix_language";
    public static final String APP_PREFERENCES_MATH_MAXIMUM_DIGIT="math_maximum_digit";
    public static final String APP_PREFERENCES_MATRIX_SIZE="matrix_size";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        setContentView(R.layout.activity_main);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
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
    };
}