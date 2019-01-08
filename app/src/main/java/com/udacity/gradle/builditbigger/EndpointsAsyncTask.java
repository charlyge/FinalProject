package com.udacity.gradle.builditbigger;


import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private AsynTaskListener asynTaskListener;
    public EndpointsAsyncTask(AsynTaskListener asynTaskListener){
        this.asynTaskListener = asynTaskListener;

    }
interface AsynTaskListener {
        void onTaskStart();
        void onTaskComplete(String result);
}
    @Override
    protected String doInBackground(Void... params) {
        asynTaskListener.onTaskStart();
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(abstractGoogleClientRequest -> abstractGoogleClientRequest.setDisableGZipContent(true));
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJokeEndPoint().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        asynTaskListener.onTaskComplete(result);
    }
}

