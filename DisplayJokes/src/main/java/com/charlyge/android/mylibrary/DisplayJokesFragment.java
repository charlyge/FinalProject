package com.charlyge.android.mylibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.charlyge.android.mylibrary.DisplayJokesActivity.DISPLAY_JOKES_KEY;

public class DisplayJokesFragment extends Fragment {

    public DisplayJokesFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.display_jokes_fragment,container,false);
        TextView jokeTextView = root.findViewById(R.id.display_jokes);
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra(DISPLAY_JOKES_KEY)){

            String joke = intent.getStringExtra(DISPLAY_JOKES_KEY);
            if(joke!=null){
                jokeTextView.setText(joke);
            }
            else{
                Toast.makeText(getActivity(),"An Error Occurred",Toast.LENGTH_LONG).show();
            }

        }
        return root;
    }
}
