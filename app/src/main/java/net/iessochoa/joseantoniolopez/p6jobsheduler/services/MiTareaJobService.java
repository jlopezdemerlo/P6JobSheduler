package net.iessochoa.joseantoniolopez.p6jobsheduler.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by JoseA on 19/01/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MiTareaJobService extends JobService {
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

    public static class MiTareaTask extends AsyncTask<JobParameters, Void, JobParameters>{
        private final MiTareaJobService jobService;

        public MiTareaTask(MiTareaJobService jobService) {
            this.jobService = jobService;
        }

        @Override
        protected JobParameters doInBackground(JobParameters... param) {
            Log.i("Ejemplo JobService:","tarea ejecutada");
            return param[0];
        }
        @Override
        protected void onPostExecute(JobParameters param) {
            jobService.jobFinished(param, false);
        }
    }
}
