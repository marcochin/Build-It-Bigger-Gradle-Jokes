package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.marco.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * A retained fragment to take care of the <code>AsyncTask</code> operations over the network.
 */
public class AsyncTaskFragment extends Fragment {
    public static final String TAG = AsyncTaskFragment.class.getSimpleName();
    private Callback mCallback;

    public interface Callback{
        void onPostExecute(String joke);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void beginTask(){
        new EndpointsAsyncTask().execute();
    }

    public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Void... params) {
            if (myApiService == null) {  // Only do this once

                // Test Debug Server
                /*MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });*/


                //Live Cloud Server
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://gradlejokes.appspot.com/_ah/api/");

                myApiService = builder.build();
            }

            try {
                return myApiService.getJoke().execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String joke) {
            if(mCallback != null){
                mCallback.onPostExecute(joke);
            }
        }
    }
}
