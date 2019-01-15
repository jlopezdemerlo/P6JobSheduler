package net.iessochoa.joseantoniolopez.p6jobsheduler.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
//https://github.com/firebase/firebase-jobdispatcher-android#user-content-firebase-jobdispatcher-
public class MiTareaFireBaseService extends JobService {
    public MiTareaFireBaseService() {
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        MiTareaTask tarea=new MiTareaTask();
        tarea.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    class MiTareaTask extends AsyncTask<Void, Void, Void> {


        public MiTareaTask() {

        }

        @Override
        protected Void doInBackground(Void... param) {
            Log.i("Ejemplo JobService:","tarea ejecutada TareaFireBase");
            return null;
        }
        @Override
        protected void onPostExecute(Void param) {

        }
    }

}
