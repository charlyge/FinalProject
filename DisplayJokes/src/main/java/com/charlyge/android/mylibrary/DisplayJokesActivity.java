package com.charlyge.android.mylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayJokesActivity extends AppCompatActivity {
    public static String DISPLAY_JOKES_KEY = "DISPLAY_JOKES_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_jokes);
        DisplayJokesFragment displayJokesFragment = new DisplayJokesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.display_jokes_container,displayJokesFragment).commit();

    }
}
