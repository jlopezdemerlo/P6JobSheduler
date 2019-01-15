package net.iessochoa.joseantoniolopez.p6jobsheduler.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import net.iessochoa.joseantoniolopez.p6jobsheduler.MainActivity;
import net.iessochoa.joseantoniolopez.p6jobsheduler.R;

// para probar enviar desde adb
//adb shell am broadcast -a android.intent.action.BOOT_COMPLETED
public class BootReceiver extends BroadcastReceiver {
    //Context context;
    public BootReceiver() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
       //context=context;
        Log.i("Ejemplo JobService:","Receiver ejecutado");
        JobInfo job = new JobInfo.Builder(0, new ComponentName(context, MiTareaBootJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                //.setRequiresCharging(true)
                // .setMinimumLatency(1000)
                //.setOverrideDeadline(1500)
                .setPeriodic(10000)

                .build();
        JobScheduler jobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(job);
//        throw new UnsupportedOperationException("Not yet implemented");
    }


}
