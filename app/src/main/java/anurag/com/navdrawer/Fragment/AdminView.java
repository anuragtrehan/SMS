package anurag.com.navdrawer.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import anurag.com.navdrawer.R;


public class AdminView extends Fragment {

    Button ppsqc, ppstech;
    public AdminView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_view, container, false);

        ppsqc = (Button) view.findViewById(R.id.ppsqc);
        ppstech= (Button) view.findViewById(R.id.ppstech);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        ppsqc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                ViewPendingReqFragment vp = new ViewPendingReqFragment();
                transaction.replace(R.id.frame_container,vp).commit();

            }
        });

        ppstech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                PendingPPSFragment pps = new PendingPPSFragment();
                transaction.replace(R.id.frame_container,pps).commit();
            }
        });
    }
}
