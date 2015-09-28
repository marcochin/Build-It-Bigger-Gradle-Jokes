package com.udacity.gradle.builditbigger;

import android.test.ActivityInstrumentationTestCase2;
import android.test.MoreAsserts;
import android.test.suitebuilder.annotation.LargeTest;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Android Unit Test to test if the string retrieved from the cloud is not empty.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @LargeTest
    public void testIfJokeStringIsEmpty() throws Throwable {
        final AsyncTaskFragment.EndpointsAsyncTask endpointsAsyncTask =
                new AsyncTaskFragment().new EndpointsAsyncTask();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                endpointsAsyncTask.execute();
            }
        });

        try {
            //get() waits for AsyncTask to complete then gets it's return value from doInBackground()
            String joke = endpointsAsyncTask.get(30, TimeUnit.SECONDS);
            MoreAsserts.assertNotEqual(joke, "");

        }catch (CancellationException | ExecutionException | InterruptedException
                | TimeoutException e){
            fail("AsyncTask has failed");
        }
    }
}
