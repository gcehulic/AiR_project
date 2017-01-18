package hr.foi.air602.notification.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;

import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.foi.air602.notification.R;
import hr.foi.air602.notification.configuration.Config;

/**
 * Created by Goran on 18.1.2017..
 */

public class NotificationUtils {
    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context mContext;

    public NotificationUtils(Context mContext){
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, String timestamp, int icon, Intent intent){
        showNotificationMessage(title, message, timestamp, icon, intent, null);
    }


    public void showNotificationMessage(final String title, final String message, final String timestamp, int icon, Intent intent, String imageUrl){
        if(TextUtils.isEmpty(message)) return;


        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );


        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        showSmallNotification(mBuilder, icon,title, message,timestamp,resultPendingIntent);

    }


    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timestamp, PendingIntent resultPendingIntent){
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);
        Notification notification;
        notification = mBuilder.setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setStyle(inboxStyle)
                .setWhen(getTimeMilliSec(timestamp))
                .setSmallIcon(icon)
                .setContentText(message)
                .build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);


    }


    public static long getTimeMilliSec(String timestamp){
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

        try {
            Date date = format.parse(timestamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isAppIsInBackground(Context context){
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH){
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo processInfo : runningAppProcesses){
                if(processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    for(String activeProcess : processInfo.pkgList){
                        if(activeProcess.equals(context.getPackageName())){
                            isInBackground = false;
                        }
                    }
                }
            }
        }  else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if(componentInfo.getPackageName().equals(context.getPackageName())){
                isInBackground = false;
            }
        }
        return  isInBackground;
    }

    public static void clearNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
