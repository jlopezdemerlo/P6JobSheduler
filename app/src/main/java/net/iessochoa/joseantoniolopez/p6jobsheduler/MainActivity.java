package net.iessochoa.joseantoniolopez.p6jobsheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import net.iessochoa.joseantoniolopez.p6jobsheduler.services.MiTareaFireBaseService;
import net.iessochoa.joseantoniolopez.p6jobsheduler.services.MiTareaJobService;

public class MainActivity extends AppCompatActivity {
    private static int kJobId = 0;
    private Button btnIniciarTarea;
    private Button btnPararTarea;
    private Button btnIniciarTareaFireBase;
    private Button btnPararTareaFireBase;
    FirebaseJobDispatcher dispatcher;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //***********************JobService***********************************************
        btnIniciarTarea = (Button) findViewById(R.id.btnIniciarTarea);
        btnIniciarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Ejemplo JobService:","Iniciando tarea  TareaJS");
                JobInfo job = new JobInfo.Builder(kJobId++, new ComponentName(MainActivity.this, MiTareaJobService.class))
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                        //.setRequiresCharging(true)
                        // .setMinimumLatency(1000)
                        //.setOverrideDeadline(1500)
                        //con este indicador podemos decirle que continúe al arrancar
                        //.setPersisted(true)
                        .setPeriodic(1000)
                        .build();
                JobScheduler jobScheduler = (JobScheduler) getApplication().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(job);
            }
        });
        btnPararTarea = (Button) findViewById(R.id.btnPararTarea);
        btnPararTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                tm.cancelAll();

            }
        });
        //********************FirebaseJob******************************************************
        btnIniciarTareaFireBase = (Button) findViewById(R.id.btnIniciarTareaFirebase);
        btnPararTareaFireBase = (Button) findViewById(R.id.btnPararTareaFireBase);
        btnIniciarTareaFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Ejemplo JobSFireBase:","Iniciando tarea  TareaFireBase");
                dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(MainActivity.this));
                Bundle myExtrasBundle = new Bundle();
                myExtrasBundle.putString("some_key", "some_value");

                Job myJob = dispatcher.newJobBuilder()
                        // the JobService that will be called
                        .setService(MiTareaFireBaseService.class)
                        // uniquely identifies the job
                        .setTag("my-unique-tag")
                        // one-off job
                        .setRecurring(true)
                        // don't persist past a device reboot
                        .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                        // start between 0 and 10 seconds from now
                        .setTrigger(Trigger.executionWindow(0, 10))
                        //.setTrigger(Trigger.NOW)

                        // don't overwrite an existing job with the same tag
                        .setReplaceCurrent(false)
                        // retry with exponential backoff
                        .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                        // constraints that need to be satisfied for the job to run
                        .setConstraints(
                                // only run on an unmetered network
                                // Constraint.ON_UNMETERED_NETWORK,
                                Constraint.ON_ANY_NETWORK
                                // only run when the device is charging
                                //  Constraint.DEVICE_CHARGING
                        )
                        .setExtras(myExtrasBundle)
                        .build();

                dispatcher.mustSchedule(myJob);
            }
        });
        btnPararTareaFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatcher.cancelAll();
            }
        });

    }
}
