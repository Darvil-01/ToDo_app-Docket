package com.example.ToDo_Docket.Services;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.os.Build.VERSION_CODES.R;
import static com.example.ToDo_Docket.Notification.notifyChannel.ChannelId;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.ToDo_Docket.DbHelpers.Task_DbHelper;
import com.example.ToDo_Docket.R;

import com.example.ToDo_Docket.Task;


import java.util.Calendar;
import java.util.Random;

public class notification_discp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String duetime = null;
        Calendar calendar = Calendar.getInstance();
        int curr_hr = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, curr_hr + 1);

        duetime = (String) (DateFormat.format("HH:mm", calendar));

        Task_DbHelper helper = new Task_DbHelper(context, null);
       if(helper.checkIsEntity("Today")) {                                  //if today exists ele app will crach
            String massg = helper.getTaskDiscp(duetime);

            // Drawable drawable= ResourcesCompat.getDrawable(com.example.ToDo_Docket, com.example.ToDo_Docket.R.drawable.ic_warning,null)

            if (!massg.equals("null")) {
                Intent intent1 = new Intent(context, Task.class);
                intent1.putExtra("title","Today");
                int i=new Random().nextInt(50);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, i, intent1, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ChannelId)
                        .setContentTitle("Task not Done: ")
                        .setSmallIcon(com.example.ToDo_Docket.R.mipmap.todo_logo)
                        //.setLargeIcon(com.example.ToDo_Docket.R.drawable.ic_warning)
                        .setContentText(massg)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);


                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(i, builder.build());
            }
        }
    }

}
