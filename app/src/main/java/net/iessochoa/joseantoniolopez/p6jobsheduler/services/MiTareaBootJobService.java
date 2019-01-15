package net.iessochoa.joseantoniolopez.p6jobsheduler.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
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

/**
 * Created by JoseA on 21/01/2017.
 */
//articulo notificaciones http://es.wikihow.com/crear-notificaciones-en-Android-Lollipop
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MiTareaBootJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new MiTareaTask(this).execute(jobParameters);
//        Log.i("Ejemplo JobService:","tarea ejecutada");
//        jobFinished(jobParameters,false);
        return true;

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public static class MiTareaTask extends AsyncTask<JobParameters, Void, JobParameters> {
        private final MiTareaBootJobService jobService;

        public MiTareaTask(MiTareaBootJobService jobService) {
            this.jobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... param) {
            Log.i("Ejemplo JobService:","tarea ejecutada MitareaBoot");
            return param[0];
        }
        @Override
        protected void onPostExecute(JobParameters param) {
            jobService.jobFinished(param, false);
            jobService.notificarResultado();
        }
    }
    private void notificarResultado(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        // .setLargeIcon((((BitmapDrawable)getResources()
                        //        .getDrawable(R.drawable.ic_launcher)).getBitmap()))
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                        .setContentTitle("Mensaje de Alerta")
                        .setContentText("Ejemplo de notificación.")
                        .setContentInfo("4")
                        .setAutoCancel(true)
                        .setTicker("Alerta!");

        Intent notIntent =
                new Intent(this, MainActivity.class);

        PendingIntent contIntent = PendingIntent.getActivity(
                this, 0, notIntent, 0);

        mBuilder.setContentIntent(contIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }
}