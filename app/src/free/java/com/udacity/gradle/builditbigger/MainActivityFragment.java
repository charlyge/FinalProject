package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.charlyge.android.mylibrary.DisplayJokesActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Objects;

import static com.charlyge.android.mylibrary.DisplayJokesActivity.DISPLAY_JOKES_KEY;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.AsynTaskListener {
    private InterstitialAd mInterstitialAd;
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


    public void showInterstitialAad(){

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            tellJoke();
        }
    }
   
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button bt_tellJoke = root.findViewById(R.id.button_tell_joke);
        bt_tellJoke.setOnClickListener(v -> showInterstitialAad());
        progressBar = root.findViewById(R.id.progress_bar);
        mInterstitialAd = new InterstitialAd(Objects.requireNonNull(getActivity()));
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                tellJoke();
            }
        });
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
       
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
