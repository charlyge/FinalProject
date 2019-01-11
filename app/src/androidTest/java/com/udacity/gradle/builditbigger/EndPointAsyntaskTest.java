package com.udacity.gradle.builditbigger;

import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class EndPointAsyntaskTest implements EndpointsAsyncTask.AsynTaskListener  {

    @Test
    public void AsyncTaskTest() throws Exception {
        new EndpointsAsyncTask(this).execute();
        Thread.sleep(7000);

    }

    @Override
    public void onTaskStart() {

    }

    @Override
    public void onTaskComplete(String result) {
        assertTrue("Error: we found this Joke = " + result, result != null);
    }
}