package ru.brainworkout.brain_gym;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Random;

public class AlarmActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int mAlarmTimeHour;
    private int mAlarmTimeMinute;
    private boolean mAlarmIsActive;

    MediaPlayer mp;
    FTPClient mFTPClient;

    private TimePicker mTimePicker;

    private int hour;
    private int minute;

    static final int TIME_DIALOG_ID = 999;

    private TextView txtMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getPreferencesFromFile();
        setAlarmTimeOnView();
        //addListenerOnButton();
        //setCurrentTimeOnView();


        runAlarm();

        int mesID = getResources().getIdentifier("tvMessages", "id", getPackageName());
        txtMessages = (TextView) findViewById(mesID);





    }

    private void runAlarm() {


    }

    private void getPreferencesFromFile() {

        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(MainActivity.APP_PREFERENCES_ALARM_TIME_HOUR)) {
            // Получаем язык из настроек
            mAlarmTimeHour = mSettings.getInt(MainActivity.APP_PREFERENCES_ALARM_TIME_HOUR, 6);
        } else {
            mAlarmTimeHour = 6;
        }


        if (mSettings.contains(MainActivity.APP_PREFERENCES_ALARM_TIME_MINUTE)) {
            // Получаем язык из настроек
            mAlarmTimeMinute = mSettings.getInt(MainActivity.APP_PREFERENCES_ALARM_TIME_MINUTE, 0);
        } else {
            mAlarmTimeMinute = 0;
        }


        if (mSettings.contains(MainActivity.APP_PREFERENCES_ALARM_IS_ACTIVE)) {
            // Получаем язык из настроек
            mAlarmIsActive = mSettings.getBoolean(MainActivity.APP_PREFERENCES_ALARM_IS_ACTIVE, false);
        } else {
            mAlarmIsActive = false;
        }

        int isActiveID = getResources().getIdentifier("cbAlarmIsActive", "id", getPackageName());
        CheckBox butIsActive = (CheckBox) findViewById(isActiveID);
        if (butIsActive != null) {
            butIsActive.setChecked(mAlarmIsActive);

            butIsActive.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mAlarmIsActive = isChecked;
                }
            });
        }


    }

    public void playAlarm_onClick(View view) {


        mFTPClient = new FTPClient();
        try {
            mFTPClient.connect("brainworkout.ru");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) ;

        boolean status = false;
        try {
            status = mFTPClient.login("upironok_brain", "123456789159753");
            //System.out.println(status);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (status) {
            mFTPClient.enterLocalPassiveMode();


            Random random = new Random();
            FTPFile[] files = new FTPFile[0];
            try {
                files = mFTPClient.listFiles();
                if (files != null && files.length != 0) {

//                    for (FTPFile ftpFile : files) {
//
//                        if (ftpFile.getType() == FTPFile.FILE_TYPE) {
//                            System.out.println("File: " + ftpFile.getName());
//                        }
//                    }

                    boolean mFileIsFound = false;

                    while (!mFileIsFound) {
                        int num = random.nextInt(files.length);
                        FTPFile file = files[num];
                        //file = files[3];

                        if (file.isFile()) {
                            if (!file.getName().contains(".mp3")) {
                                continue;

                            }
                            WriteMessage("Нашли на сервере: " + file.getName());
                            mFileIsFound = true;

                            //System.out.println("Playing: "+file.getName());
                            //mp = MediaPlayer.create(AlarmActivity.this, Uri.parse("http://brainworkout.ru/music/" + file.getName()));
//                            mp = MediaPlayer.create(AlarmActivity.this, Uri.parse("http://brainworkout.ru/music/" + file.getName()));
//                            //Звук будет проигрываться только 1 раз:
//                            if (mp != null) {
//                                mp.setLooping(false);
//                                //Установка обработчика события на момент готовности проигрывателя:
//                                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                                    public void onPrepared(MediaPlayer mp) {
//                                        //При готовности к проигрыванию запуск вывода звука:
//                                        mp.start();
//                                    }
//                                });
//                                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                    public void onCompletion(MediaPlayer mp) {
//                                        mp.release();
//                                    }
//                                });
//                            }

                            //качаем файл


                            boolean down = false;
                            OutputStream output;
                            File dir;
                            String mDirPath = "";
                            String mFullPath = "";
                            try {
                                mDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/music/";
                                mFullPath = mDirPath + file.getName();
                                dir = new File(mDirPath);
                                dir.mkdirs();
                                //проверим есть ли файл
                                File mNewFile = new File(mFullPath);
                                if (mNewFile.exists()) {
                                    down = true;
                                    WriteMessage("Локальный файл: " + file.getName());
                                } else {
                                    output = new FileOutputStream(mNewFile);

                                    WriteMessage("Скачиваем: " + file.getName());
                                    down = mFTPClient.retrieveFile(file.getName(), output);
                                    output.close();


                                }
                            } catch (Exception e) {
                            }


                            if (down) {
                                mp = new MediaPlayer();
                                mp.setDataSource(mFullPath);
                                WriteMessage("Запускаем: " + file.getName());
                                mp.prepare();
                                mp.setLooping(false);
                                WriteMessage("Проигрываем: " + file.getName());
                                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                    public void onPrepared(MediaPlayer mp) {
                                        //При готовности к проигрыванию запуск вывода звука:
                                        mp.start();

                                    }
                                });
                                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    public void onCompletion(MediaPlayer mp) {
                                        mp.release();
                                    }
                                });

                            }

                        } else {
                            System.out.println("File not a file: " + file.getName());
                        }

                    }

                    //get output stream
                    //OutputStream output;
                    //output = new FileOutputStream(getApplicationInfo().dataDir + "/files/" + file.getName());
                    //get the file from the remote system
                    //boolean down=mFTPClient.retrieveFile(file.getName(), output);
                    //close output stream
                    // boolean down = true;

                    //output.close();
                    //System.out.println("downloading: " + down);
                    //if (down) {

                    //String path = "/data/data/ru.brainworkout.brain_gym/files/1.mp3";
//                            String path="/1.mp3";
//                            MediaPlayer mp = new MediaPlayer();
//                            mp.setDataSource(R.raw.m1);
//                            mp.prepare();
//                            mp.setLooping(false);
                    //Установка обработчика события на момент готовности проигрывателя:
//                            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                                public void onPrepared(MediaPlayer mp) {
//                                    //При готовности к проигрыванию запуск вывода звука:
//                                    mp.start();
//                                }
//                            });
//                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                                public void onCompletion(MediaPlayer mp) {
//                                    mp.release();
//                                }
//                            });
                    //mMediaPlayer.start();
//
//
//                              Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getApplicationInfo().dataDir+"/files/"+ "02. Mephisto Waltz N 1.mp3"));
//                            startActivity(intent);
                    //MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.m1);

                    //}
                    //delete the file
                    //mFTPClient.deleteFile(file.getName());


                    //FileInputStream fi=new FileInputStream(file);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //boolean good=ftpUpload(mFTPClient,"Z://1.txt","1.txt","");
            //System.out.println(good);
            try {
                mFTPClient.logout();
                mFTPClient.disconnect();
            } catch (Exception e) {
            }
        }


    }


    public void alarmStop_onClick(View view) {
        if (mp!=null) {
            try {
                mp.release();
                WriteMessage("Остановили проигрыватель");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void alarmSave_onClick(View view) {

        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(MainActivity.APP_PREFERENCES_ALARM_TIME_HOUR, mAlarmTimeHour);
        editor.putInt(MainActivity.APP_PREFERENCES_ALARM_TIME_MINUTE, mAlarmTimeMinute);
        editor.putBoolean(MainActivity.APP_PREFERENCES_ALARM_IS_ACTIVE, mAlarmIsActive);

        editor.apply();

        //this.finish();


    }


    public void setCurrentTimeOnView() {

        mTimePicker = (TimePicker) findViewById(R.id.timePicker1);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into timepicker
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);

    }

    public void setAlarmTimeOnView() {

        mTimePicker = (TimePicker) findViewById(R.id.timePicker1);

        // set current time into timepicker
        mTimePicker.setCurrentHour(mAlarmTimeHour);
        mTimePicker.setCurrentMinute(mAlarmTimeMinute);

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mAlarmTimeHour = hourOfDay;
                mAlarmTimeMinute = minute;
            }
        });

    }

    public void addListenerOnButton() {

        //Button but = (Button) findViewById(R.id.butAlarmSet);
        TimePicker but = (TimePicker) findViewById(R.id.timePicker1);

        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute, false);

        }
        return null;
    }


    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    // set current time into timepicker
                    mTimePicker.setCurrentHour(hour);
                    mTimePicker.setCurrentMinute(minute);

                }
            };

private void WriteMessage(String text) {
    if (txtMessages != null) {
        txtMessages.setText(text);
    }
}

}
