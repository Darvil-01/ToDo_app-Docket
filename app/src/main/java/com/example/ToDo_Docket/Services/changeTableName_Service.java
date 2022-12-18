package com.example.ToDo_Docket.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.ToDo_Docket.DbHelpers.List_DPHelper;
import com.example.ToDo_Docket.DbHelpers.Task_DbHelper;

import java.security.Provider;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class changeTableName_Service extends BroadcastReceiver {
    Context Context;
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "recive", Toast.LENGTH_SHORT).show();
        
        List_DPHelper list_dpHelper=new List_DPHelper(context);
        Task_DbHelper task_dbHelper=new Task_DbHelper(context,null);
//        if(task_dbHelper.checkIsEntity("Today")){
//            Log.i("checking Today", "  onReceive:checked ");
//            if(task_dbHelper.checkIsEntity("Tomorrow")) {
//                Log.i("checking Tomorrow", "  onReceive:checked ");
//                list_dpHelper.deleteList("Today");
//                task_dbHelper.deleteTable("Today");
//                Log.i("today deleted", " onReceive:done ");
//                list_dpHelper.changeDayName("Tomorrow", "Today");
//                Log.i("name change in list", " onReceive:done ");
//                task_dbHelper.changeTableName("Tomorrow", "Today");
//                Log.i("name change in task", " onReceive:done ");
//            }
            if(task_dbHelper.checkIsEntity("Today")){
                list_dpHelper.deleteList("Today");
                task_dbHelper.deleteTable("Today");
            }
            if(task_dbHelper.checkIsEntity("Tomorrow")) {
                list_dpHelper.changeDayName("Tomorrow", "Today");
                task_dbHelper.changeTableName("Tomorrow", "Today");
            }

        GregorianCalendar gc=new GregorianCalendar();
        gc.add(Calendar.DATE,1);
        Calendar c=Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;

        String day=gc.get(Calendar.DATE)+" / "+month+" / "+c.get(Calendar.YEAR);

        if(task_dbHelper.checkIsEntity(day)) {
            list_dpHelper.changeDayName(day, "Tomorrow");
            task_dbHelper.changeTableName(day, "Tomorrow");
            Log.i("day change",day+" to Tomorrow");
        }
        Log.i("day after tomorrow", day);
        Toast.makeText(context, "change set", Toast.LENGTH_SHORT).show();
    }
}
