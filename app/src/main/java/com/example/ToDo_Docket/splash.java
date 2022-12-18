package com.example.ToDo_Docket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ToDo_Docket.Services.cancel_alarm;
import com.example.ToDo_Docket.Services.changeTableName_Service;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class splash extends AppCompatActivity {
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // getSupportActionBar().hide();

//        progressBar=findViewById(R.id.progressBar);
//        int count =5000;

        changingTable();

        Thread thread=new Thread(){
            public void run(){
                try{
                    View decorView=getWindow().getDecorView();
                    int uioption=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    //int uioption1=View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uioption);
                   // decorView.setSystemUiVisibility(uioption1);
                     sleep(1500);
//                     progressBar.setProgress(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    Intent intent=new Intent(splash.this, List.class);
                    startActivity(intent);
                    finish();

                }
            }
        }; thread.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                View decoreView=getWindow().getDecorView();
//                int uioption=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                decoreView.setSystemUiVisibility(uioption);
//                startActivity(new Intent(splash.this,List.class));
//
//                finish();
//            }
//        },5000);
    }
    private void changingTable() {

        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.DAY_OF_MONTH,1);

//
//        Date futureDate = new Date(new Date().getTime() + 86400000);
//        futureDate.setHours(12);
//        futureDate.setMinutes(42);
//        futureDate.setSeconds(0);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, changeTableName_Service.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1,intent,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }





        calendar.add(Calendar.MINUTE,2);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);

        Intent cancleintent= new Intent(this, cancel_alarm.class);
        cancleintent.putExtra("key",pendingIntent);
        PendingIntent canclependingIntent=PendingIntent.getBroadcast(this,1,cancleintent,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),canclependingIntent);
        }



    }
}