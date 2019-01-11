package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.charlyge.android.mylibrary.DisplayJokesActivity;

import java.util.Objects;

import static com.charlyge.android.mylibrary.DisplayJokesActivity.DISPLAY_JOKES_KEY;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.AsynTaskListener {
    private boolean isTaskRunning = false;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // If we are returning here from a screen orientation
        // and the AsyncTask is still working, re-create and display the
        // progress dialog.
        if (isTaskRunning) {
           progressBar.setVisibility(View.VISIBLE);
        }
    }
   
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button bt_tellJoke = root.findViewById(R.id.button_tell_joke);
        bt_tellJoke.setOnClickListener(v -> tellJoke());
        progressBar = root.findViewById(R.id.progress_bar);
       
        return root;
    }


    private void tellJoke() {
         if(!isTaskRunning){
             new EndpointsAsyncTask(this).execute();
         }



    }

    @Override
    public void onTaskStart() {
        isTaskRunning = true;
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
            if(progressBar!=null){
                progressBar.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onTaskComplete(String result) {
        isTaskRunning = false;
       Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
           if(progressBar!=null){
               progressBar.setVisibility(View.GONE);
               Intent intent = new Intent(getActivity(),DisplayJokesActivity.class);
               intent.putExtra(DISPLAY_JOKES_KEY,result);
               startActivity(intent);
           }
       });
    }

    @Override
    public void onDetach() {
        // All dialogs should be closed before leaving the activity in order to avoid
        // the: Activity has leaked window com.android.internal.policy... exception
        if (progressBar != null) {
           progressBar.setVisibility(View.GONE);
        }
        super.onDetach();
    }
}

// Objects.requireNonNull(getActivity()).runOnUiThread(() -> progressBar.setVisibility(View.GONE));
//
