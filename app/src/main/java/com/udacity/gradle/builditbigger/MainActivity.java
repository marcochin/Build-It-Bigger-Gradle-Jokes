package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.marco.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.mcochin.jokedealer.JokeDealerActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements AsyncTaskFragment.Callback{
    private ProgressBar mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressWheel = (ProgressBar)findViewById(R.id.progress_wheel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joke_dealer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the button to retrieve the joke is clicked"
     * @param view The view being clicked
     */
    public void tellJoke(View view){
        mProgressWheel.setVisibility(View.VISIBLE);

        //set up the AsyncTask Fragment
        AsyncTaskFragment asyncTaskFragment =
                (AsyncTaskFragment)getSupportFragmentManager().findFragmentByTag(AsyncTaskFragment.TAG);

        if(asyncTaskFragment == null) {
            asyncTaskFragment = new AsyncTaskFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(asyncTaskFragment, AsyncTaskFragment.TAG).commit();
        }

        //start an AsyncTask to fetch joke from the cloud!
        asyncTaskFragment.beginTask();
    }

    @Override
    public void onPostExecute(String joke) {
        mProgressWheel.setVisibility(View.INVISIBLE);

        //Pass joke to new activity to be displayed!
        Intent jokeDealerIntent = new Intent(MainActivity.this, JokeDealerActivity.class);
        jokeDealerIntent.putExtra(JokeDealerActivity.JOKE_KEY, joke);
        startActivity(jokeDealerIntent);
    }
}
