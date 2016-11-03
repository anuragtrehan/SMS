package anurag.com.navdrawer.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import anurag.com.navdrawer.R;


public class WelcomeFragment extends Fragment {
    public WelcomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
