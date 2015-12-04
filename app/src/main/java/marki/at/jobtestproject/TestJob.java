package marki.at.jobtestproject;

import android.os.SystemClock;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.UUID;

import timber.log.Timber;

public class TestJob extends Job {

    private String id;

    public TestJob() {
        super(new Params(500).requireNetwork().persist());
        id = UUID.randomUUID().toString();
        Timber.e("Add TestJob with id: %s", id);
    }


    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        Timber.e("RUN TestJob with ID: %s", id);
        Timber.d("START sleeping");
        SystemClock.sleep(13_000); // sleep for 13 seconds
        Timber.d("END sleeping");
    }

    @Override
    protected void onCancel() {
        Timber.e("On cancel called");
    }
}
