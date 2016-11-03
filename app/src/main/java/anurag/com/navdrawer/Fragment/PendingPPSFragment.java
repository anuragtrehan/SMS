package anurag.com.navdrawer.Fragment;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import anurag.com.navdrawer.Adapter.AdapterPendingPPS;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.PedningPPSPojo;
import anurag.com.navdrawer.R;

public class PendingPPSFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
ListView listView;
//Spinner stage;

String[] date,joborder,styleno,size,path1,path2,path3,path4;
String stage1,job_order1,style_no1;
int[] stage;
List<PedningPPSPojo> pendingpps;
AdapterPendingPPS adapterPendingPPS;

JSONObject jsonObject = new JSONObject();
JsonParser jparser = new JsonParser();
    JsonLink jsonLink=new JsonLink();
String query;
JSONArray postarray = new JSONArray();
int success;
int action=0;
String message;

ProgressDialog progress;


//private static String PENDING_PPS_URL = "http://a0682321.ngrok.io/tms/pendingpps.php";
private static String TAG_POST = "posts";

public PendingPPSFragment()
{ }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pending_p, container, false);

        listView =(ListView) view.findViewById(R.id.listView);

//        stage =(Spinner) view.findViewById(R.id.stage);
//        stage.setOnItemSelectedListener(this);
//
//        List<String> stagelist = new ArrayList<String>();
//        for(int i=0;i<stage1.length;i++)
//        {stagelist.add(String.valueOf(stage1[i]));}
//
//        ArrayAdapter<String> stageadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stagelist);
//
//        stage.setAdapter(stageadapter);

    return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        new ParsePPSData().execute();

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textview1 = (TextView) view.findViewById(R.id.date);
            TextView textview2 = (TextView) view.findViewById(R.id.job_order);
            TextView textview3 = (TextView) view.findViewById(R.id.style_no);
            TextView textview4 = (TextView) view.findViewById(R.id.stage);
            TextView textview5 = (TextView) view.findViewById(R.id.size);
            TextView textview6 = (TextView) view.findViewById(R.id.chart);
            stage1 = textview4.getText().toString();
            job_order1 = textview2.getText().toString();
            style_no1 = textview3.getText().toString();


            Fragment fr = new TechPPSFragment();
            FragmentManager fm = getFragmentManager();
            android.app.FragmentTransaction ft = fm.beginTransaction();
            Bundle args = new Bundle();
//                args.putString("date", date1);
//                args.putString("job_order", job_order);
            args.putString("style_no", style_no1);
            args.putString("stage", String.valueOf(stage1));

//                args.putString("size", size);
//                args.putString("name", name);

            fr.setArguments(args);
            ft.replace(R.id.frame_container, fr);
            ft.commit();
        }
    });


    }
    private void data(int action) {
//        if(action==0)
//        {
        pendingpps = new ArrayList<PedningPPSPojo>();
        for (int i = 0; i < styleno.length; i++) {
            String imagename = styleno[i] + ".jpg";
            PedningPPSPojo items = new PedningPPSPojo(date[i], joborder[i], styleno[i], stage[i], size[i], imagename);

            pendingpps.add(items);
        }

        adapterPendingPPS = new AdapterPendingPPS(getActivity(), pendingpps);
        listView.setAdapter(adapterPendingPPS);
        //      }
//        if(action==1)
//        {
//            pendingpps = new ArrayList<PedningPPSPojo>();
//            for (int i = 0; i < styleno.length; i++) {
//                PedningPPSPojo items = new PedningPPSPojo(date[i], joborder[i], styleno[i], 2, size[i], path[i]);
//
//                pendingpps.add(items);
//            }
//
//            adapterPendingPPS = new AdapterPendingPPS(getActivity(), pendingpps);
//            listView.setAdapter(adapterPendingPPS);
//        }
//        if(action==3)
//        {
//            pendingpps = new ArrayList<PedningPPSPojo>();
//            for (int i = 0; i < styleno.length; i++) {
//                PedningPPSPojo items = new PedningPPSPojo(date[i], joborder[i], styleno[i], 3, size[i], path[i]);
//
//                pendingpps.add(items);
//            }
//
//            adapterPendingPPS = new AdapterPendingPPS(getActivity(), pendingpps);
//            listView.setAdapter(adapterPendingPPS);
//        }
//        if(action==4)
//        {
//            pendingpps = new ArrayList<PedningPPSPojo>();
//            for (int i = 0; i < styleno.length; i++) {
//                PedningPPSPojo items = new PedningPPSPojo(date[i], joborder[i], styleno[i], 4, size[i], path[i]);
//
//                pendingpps.add(items);
//            }
//
//            adapterPendingPPS = new AdapterPendingPPS(getActivity(), pendingpps);
//            listView.setAdapter(adapterPendingPPS);
//        }

    }


    private void getdata(int action)
    {

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(0));


            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getPENDING_PPS_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        date = new String[postarray.length()];
                        joborder = new String[postarray.length()];
                        styleno = new String[postarray.length()];
                        size = new String[postarray.length()];
                        stage = new int[postarray.length()];
//                        path1 = new String[postarray.length()];
//                        path2 = new String[postarray.length()];
//                        path3 = new String[postarray.length()];
//                        path4 = new String[postarray.length()];


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            date[i] = c.getString("date");
                            joborder[i] = c.getString("job_order_no");
                            styleno[i] = c.getString("style_no");
                            size[i] = c.getString("size");
                            stage[i] = c.getInt("stage");
                        }

                    } else
                        Log.d("TAG", String.valueOf(success));
                    Log.d("TAG", message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class ParsePPSData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(getActivity());
            progress.setMessage("Loading");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.show();

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            getdata(action);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(styleno!=null)
            {
                data(action);
                progress.cancel();

            }
            else
            {
                Toast.makeText(getActivity(),"No Data Availabale",Toast.LENGTH_SHORT).show();}

            progress.cancel();
        }
    }
}
