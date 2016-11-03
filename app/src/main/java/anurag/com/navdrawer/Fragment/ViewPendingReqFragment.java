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

import anurag.com.navdrawer.Adapter.AdapterViewPendingReq;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.ViewPendingReqPojo;
import anurag.com.navdrawer.R;


public class ViewPendingReqFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ListView listView;
//    Spinner stage;
    String[] date,joborder,styleno,size,qualitychecker;
    int[] stage;

    ProgressDialog progress;

    AdapterViewPendingReq avp;
    List<ViewPendingReqPojo> vpr;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink = new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    int action=0;
    String message;

 //   private static String VPR_URL = "http://a0682321.ngrok.io/tms/vpr.php";
    private static String TAG_POST = "posts";

    public ViewPendingReqFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view_pending_req, container, false);

       listView =(ListView) view.findViewById(R.id.listView1);
//       stage= (Spinner) view.findViewById(R.id.stage);

//       stage.setOnItemSelectedListener(this);
//
//        List<String> stagelist = new ArrayList<String>();
//        for(int i=0;i<stage1.length;i++)
//        {stagelist.add(String.valueOf(stage1[i]));}
//
//       ArrayAdapter<String> stageadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stagelist);
//
//        stage.setAdapter(stageadapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new ParseVPR().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
            TextView textview1 = (TextView) view.findViewById(R.id.date);
            TextView textview2 = (TextView) view.findViewById(R.id.job_order);
            TextView textview3 = (TextView) view.findViewById(R.id.style_no);
            TextView textview4 = (TextView) view.findViewById(R.id.stage);
            TextView textview5 = (TextView) view.findViewById(R.id.size);
            TextView textview6 = (TextView) view.findViewById(R.id.name);
            String date = textview1.getText().toString();
            String job_order=textview2.getText().toString();
            String style_no=textview3.getText().toString();
            String stage=textview4.getText().toString();
            String size=textview5.getText().toString();
            String name=textview6.getText().toString();

                Fragment fr=new QCPPSFragment();
                FragmentManager fm=getFragmentManager();
                android.app.FragmentTransaction ft=fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("date", date);
//                args.putString("job_order", job_order);
                args.putString("style_no", style_no);
//                args.putString("style", stage);
                args.putString("stage", stage);
//                args.putString("size", size);
//                args.putString("name", name);

                fr.setArguments(args);
                ft.replace(R.id.frame_container, fr);
                ft.commit();
            }
        });
    }

    private void data(int action)
    {
//        if(action==0)
//        {
            vpr = new ArrayList<ViewPendingReqPojo>();
            for (int i = 0; i < styleno.length; i++) {
                ViewPendingReqPojo items = new ViewPendingReqPojo(date[i], joborder[i], styleno[i], stage[i], size[i], qualitychecker[i]);

                vpr.add(items);
//          }

            avp = new AdapterViewPendingReq(getActivity(), vpr);
            listView.setAdapter(avp);
        }
//        if(action==1)
//        {
//            vpr = new ArrayList<ViewPendingReqPojo>();
//            for (int i = 0; i < styleno.length; i++) {
//                ViewPendingReqPojo items = new ViewPendingReqPojo(date[i], joborder[i], styleno[i], 2, size[i], qualitychecker[i]);
//
//                vpr.add(items);
//            }
//
//            avp = new AdapterViewPendingReq(getActivity(), vpr);
//            listView.setAdapter(avp);
//        }
//        if(action==3)
//        {
//            vpr = new ArrayList<ViewPendingReqPojo>();
//            for (int i = 0; i < styleno.length; i++) {
//                ViewPendingReqPojo items = new ViewPendingReqPojo(date[i], joborder[i], styleno[i], 3, size[i], qualitychecker[i]);
//
//                vpr.add(items);
//            }
//
//            avp = new AdapterViewPendingReq(getActivity(), vpr);
//            listView.setAdapter(avp);
//        }
//        if(action==4)
//        {
//            vpr = new ArrayList<ViewPendingReqPojo>();
//            for (int i = 0; i < styleno.length; i++) {
//                ViewPendingReqPojo items = new ViewPendingReqPojo(date[i], joborder[i], styleno[i], 4, size[i], qualitychecker[i]);
//
//                vpr.add(items);
//            }
//
//            avp = new AdapterViewPendingReq(getActivity(), vpr);
//            listView.setAdapter(avp);
//        }
    }

    private void getdata(int action)
    {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("action", String.valueOf(0));

        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query, jsonLink.getVPR_URL());
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
                qualitychecker = new String[postarray.length()];


                for (int i = 0; i < postarray.length(); i++) {
                    JSONObject c = postarray.getJSONObject(i);

                    date[i] = c.getString("date");
                    joborder[i] = c.getString("job_order_no");
                    styleno[i] = c.getString("style_no");
                    size[i] = c.getString("size");
                    stage[i] = c.getInt("stage");
                    qualitychecker[i] = c.getString("qc");
                    Log.d("style", styleno[i]);
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

    private class ParseVPR extends AsyncTask<Void,Void,Void>
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
            {  data(action);}
            else
            {    Toast.makeText(getActivity(),"No Data Available",Toast.LENGTH_SHORT).show();}

            progress.cancel();
        }
    }
}
