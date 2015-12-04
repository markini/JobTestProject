package marki.at.jobtestproject;

import android.app.Application;
import android.content.Context;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.path.android.jobqueue.network.NetworkUtil;

import timber.log.Timber;

public class TestJobApplication extends Application {
    private static TestJobApplication instance;
    private JobManager jobManager;

    public TestJobApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        configureJobManager();
    }

    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Timber.d(text, args);
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Timber.e(t, text, args);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Timber.e(text, args);
                    }
                })
                .networkUtil(new NetworkUtil() {
                    @Override
                    public boolean isConnected(Context context) {
                        return true;
                    }
                })
                .minConsumerCount(0) // don't keep consumers alive if not needed
                .maxConsumerCount(5) // up to 5 consumers at a time
                .loadFactor(1) // 1 job per consumer
                .consumerKeepAlive(10) // wait 10 seconds
                .build();
        jobManager = new JobManager(this, configuration);
    }

    public JobManager getJobManager() {
        return jobManager;
    }

    public static TestJobApplication getInstance() {
        return instance;
    }
}
