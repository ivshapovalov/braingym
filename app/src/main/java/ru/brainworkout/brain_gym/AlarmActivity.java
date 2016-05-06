package ru.brainworkout.brain_gym;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;
import java.util.StringTokenizer;

public class AlarmActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    private int mAlarmTimeHour;
    private int mAlarmTimeMinute;
    private String mAlarmLocalPath;
    private boolean mAlarmIsPathLocal;
    private boolean mAlarmIsActive;

    MediaPlayer mp;
    FTPClient mFTPClient;
    String mFileName;

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
        setPreferencesOnScreen();
        setAlarmTimeOnView();
        //addListenerOnButton();
        //setCurrentTimeOnView();


        runAlarm();

        int mesID = getResources().getIdentifier("tvMessages", "id", getPackageName());
        txtMessages = (TextView) findViewById(mesID);

    }

    private void runAlarm() {


    }

    private void setPreferencesOnScreen() {

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

        int isPathLocalID = getResources().getIdentifier("rbAlarmIsPathLocal", "id", getPackageName());
        RadioButton butIsPathLocal = (RadioButton) findViewById(isPathLocalID);
        if (butIsPathLocal != null) {
            butIsPathLocal.setChecked(mAlarmIsPathLocal);
        }

        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.rgAlarmIsPathLocal);

        if (radiogroup != null) {
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case -1:
                            break;
                        case R.id.rbAlarmIsPathLocal:
                            mAlarmIsPathLocal = true;
                            break;
                        case R.id.rbAlarmIsPathExternal:
                            mAlarmIsPathLocal = false;
                            break;
                        default:
                            mAlarmIsPathLocal = true;
                            break;
                    }
                    hideSomeOptions();
                }

            });
        }

        int localPathID = getResources().getIdentifier("etLocalPath", "id", getPackageName());
        EditText etLocalPath = (EditText) findViewById(localPathID);
        if (etLocalPath != null) {
            etLocalPath.setText(mAlarmLocalPath);
        }

        hideSomeOptions();
    }

    private void hideSomeOptions() {

        int lineLocalPathID = getResources().getIdentifier("lineLocalPath", "id", getPackageName());
        LinearLayout lineLocalPath = (LinearLayout) findViewById(lineLocalPathID);
        if (lineLocalPath != null) {
            if (!mAlarmIsPathLocal) {
                lineLocalPath.setVisibility(View.GONE);
            } else {
                lineLocalPath.setVisibility(View.VISIBLE);
            }
        }

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

        if (mSettings.contains(MainActivity.APP_PREFERENCES_ALARM_IS_PATH_LOCAL)) {
            // Получаем язык из настроек
            mAlarmIsPathLocal = mSettings.getBoolean(MainActivity.APP_PREFERENCES_ALARM_IS_PATH_LOCAL, true);
        } else {
            mAlarmIsPathLocal = true;
        }

        if (mSettings.contains(MainActivity.APP_PREFERENCES_ALARM_LOCAL_PATH)) {

            mAlarmLocalPath = mSettings.getString(MainActivity.APP_PREFERENCES_ALARM_LOCAL_PATH, "");
        } else {
            mAlarmLocalPath = "";
        }

    }

    public void alarmPause_onClick(View view) {

        if (mp != null) {
            try {
                mp.pause();
                WriteMessage("Pause: "+ mFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class FilesComparator implements Comparator<File> {
        public int compare(File f1, File f2) {
            if (f1.isDirectory() && f2.isFile()) {
                return -1;
            }
            if (f1.isFile() && f2.isDirectory()) {
                return 1;
            }
            return f1.compareTo(f2);
        }
    }

    public void playAlarm_onClick(View view) {

        if (mp != null) {
            mp.start();
            WriteMessage("Play: "+ mFileName);
        } else {
            String mDirPath = "";
            String mFullPath = "";
            Random random = new Random();

            if (mAlarmIsPathLocal) {
                try {
                    File path = new File(mAlarmLocalPath);
                    File[] files;
                    if (!path.exists()) {
                        throw new IOException("Cannot access " + mAlarmLocalPath + ": No such file or directory");
                    }
                    if (path.isFile()) {
                        files = new File[]{path};
                    } else {
                        files = path.listFiles();
                        Arrays.sort(files, new FilesComparator());
                    }
                    boolean mFileIsFound = false;

                    while (!mFileIsFound) {
                        int num = random.nextInt(files.length);
                        File file = files[num];

                        if (file.isFile()) {
                            if (!file.getName().contains(".mp3")) {
                                continue;
                            }
                            mFileIsFound = true;
                            mFileName=file.getName();
                            mFullPath = "/mnt" + mAlarmLocalPath +mFileName;
                            startPlayer(mFullPath);
                        }
                    }
                } catch (Exception e) {
                }
            } else {
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
                                    mFileName=file.getName();
                                    WriteMessage("Нашли на сервере: " + mFileName);
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

                                    try {
                                        mDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/music/";
                                        mFullPath = mDirPath + file.getName();
                                        dir = new File(mDirPath);
                                        dir.mkdirs();
                                        //проверим есть ли файл
                                        File mNewFile = new File(mFullPath);
                                        if (mNewFile.exists()) {
                                            down = true;
                                            WriteMessage("Локальный файл: " + mFileName);
                                        } else {
                                            output = new FileOutputStream(mNewFile);

                                            WriteMessage("Скачиваем: " + mFileName);
                                            down = mFTPClient.retrieveFile(mFileName, output);
                                            output.close();


                                        }
                                    } catch (Exception e) {
                                    }


                                    if (down) {
                                        startPlayer(mFullPath);
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

        }
    }

    private void startPlayer(String mFullPath) {
        try {
            mp = new MediaPlayer();
            mp.setDataSource(mFullPath);
            WriteMessage("Play: " + mFileName);
            mp.prepare();
            mp.setLooping(false);
            WriteMessage("Play: " + mFileName);
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
        } catch (Exception e) {}

    }

    public void alarmStop_onClick(View view) {
        if (mp != null) {
            try {
                mp.release();
                mp=null;
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
        editor.putBoolean(MainActivity.APP_PREFERENCES_ALARM_IS_PATH_LOCAL, mAlarmIsPathLocal);
        editor.putString(MainActivity.APP_PREFERENCES_ALARM_LOCAL_PATH, mAlarmLocalPath);

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

    public void etLocalPath_onClick(View view) {

        Intent intent = new Intent(this, OpenDirActivity.class);
        startActivityForResult(intent, 1);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mAlarmLocalPath = data.getStringExtra("url");

        int localPathID = getResources().getIdentifier("etLocalPath", "id", getPackageName());
        EditText etLocalPath = (EditText) findViewById(localPathID);
        if (etLocalPath != null) {
            etLocalPath.setText(mAlarmLocalPath);
        }

    }
}
