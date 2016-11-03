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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import anurag.com.navdrawer.Adapter.AdapterPendingPPS;
import anurag.com.navdrawer.Adapter.AdapterSearchList;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.PedningPPSPojo;
import anurag.com.navdrawer.Pojo.SearchPojo;
import anurag.com.navdrawer.R;



public class SearchListFragment extends Fragment {
    ListView listView;
    ListView listView1;
    String[] date,joborder,vendor,stage;
    String[] date1,joborder1,vendor1,stage1;
    String style_no1;


    List<SearchPojo> searchPojo;
    AdapterSearchList adapterSearchList;

    List<SearchPojo> searchPojo1;
    AdapterSearchList adapterSearchList1;



    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink=new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    int action=0;
    String message;

    int datachecker;

    ProgressDialog progress;


 //   private static String SEARCH_LIST_URL = "http://192.168.0.110:8068/tms/searchlist.php";
    private static String TAG_POST = "posts";

    public SearchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_list, container, false);

        listView =(ListView) view.findViewById(R.id.listView);
        listView1 =(ListView) view.findViewById(R.id.listView2);
        style_no1=getArguments().getString("style_no");
            return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        action=0;
        new ParseSearchData().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textview3 = (TextView) view.findViewById(R.id.style_no);

                style_no1 = textview3.getText().toString();


                Fragment fr = new SearchResultFragment();
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();

                args.putString("style_no", style_no1);

                fr.setArguments(args);
                ft.replace(R.id.frame_container, fr);
                ft.commit();
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textview1 = (TextView) view.findViewById(R.id.date);
                TextView textview2 = (TextView) view.findViewById(R.id.job_order);
                TextView textview3 = (TextView) view.findViewById(R.id.style_no);
                TextView textview4 = (TextView) view.findViewById(R.id.stage);
                TextView textview5 = (TextView) view.findViewById(R.id.size);
                TextView textview6 = (TextView) view.findViewById(R.id.chart);
                String  stage1 = textview4.getText().toString();

                style_no1 = textview3.getText().toString();


                Fragment fr = new SearchPPSFragment();
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putString("style_no", style_no1);
                args.putString("stage", stage1);
                fr.setArguments(args);
                ft.replace(R.id.frame_container, fr);
                ft.commit();
            }
        });


    }


    private void data()
    {


        searchPojo = new ArrayList<SearchPojo>();
        for (int i = 0; i < vendor.length; i++) {
            String chart = style_no1 + ".xslx";
            SearchPojo items = new SearchPojo(date[i], joborder[i], style_no1, stage[i], vendor[i], chart);
                            Log.d("date",date[i]);
                            Log.d("joborder",joborder[i]);
                            Log.d("stage",stage[i]);
                            Log.d("vendor",vendor[i]);
            searchPojo.add(items);
        }

        adapterSearchList = new AdapterSearchList(getActivity(), searchPojo);
        listView.setAdapter(adapterSearchList);


    }

    private void data1()
    {


            searchPojo1 = new ArrayList<SearchPojo>();
            for (int i = 0; i < vendor1.length; i++)
            {

                SearchPojo items1 = new SearchPojo(date1[i], joborder1[i], style_no1, stage1[i], vendor1[i],"N/A");
                Log.d("date",date1[i]);
                Log.d("joborder",joborder1[i]);
                Log.d("stage",stage1[i]);
                Log.d("vendor",vendor1[i]);
                searchPojo1.add(items1);
            }

            adapterSearchList1 = new AdapterSearchList(getActivity(), searchPojo1);
            listView1.setAdapter(adapterSearchList1);




    }

    private void getdata(int action)
    {
        if(action==0) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);



            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_LIST_URL());
                if (jsonObject != null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
//                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        date = new String[postarray.length()];
                        joborder = new String[postarray.length()];
                        stage = new String[postarray.length()];
                        vendor = new String[postarray.length()];



                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            date[i] = c.getString("date");
                            joborder[i] = c.getString("job_order_no");
                            stage[i] = c.getString("stage");
                            vendor[i] = c.getString("vendor");
//                            Log.d("date",date[i]);
//                            Log.d("joborder",joborder[i]);
//                            Log.d("stage",stage[i]);
//                            Log.d("vendor",vendor[i]);
                        }
                    datachecker=1;
                    } else {
                        Log.d("TAG", String.valueOf(success));
                        Log.d("TAG", message);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       else if(action==1) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);



            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_LIST_URL());
                if (jsonObject != null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
//                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        date1 = new String[postarray.length()];
                        joborder1 = new String[postarray.length()];
                        stage1 = new String[postarray.length()];
                        vendor1 = new String[postarray.length()];



                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            date1[i] = c.getString("date");
                            joborder1[i] = c.getString("job_order_no");
                            stage1[i] = c.getString("stage");
                            vendor1[i] = c.getString("vendor");
//                            Log.d("date",date1[i]);
//                            Log.d("joborder",joborder1[i]);
//                            Log.d("stage",stage1[i]);
//                            Log.d("vendor",vendor1[i]);
                        }
                    datachecker=2;
                    } else {
                        Log.d("TAG", String.valueOf(success));
                        Log.d("TAG", message);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private class ParseSearchData extends AsyncTask<Void,Void,Void>
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
            action=1;
            if(success==1)
            getdata(action);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(jsonObject!=null)
            {
                if(datachecker==1||datachecker==2)
                data();
                if(datachecker==2)
                data1();


                progress.cancel();
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(),"No Data Availabale",Toast.LENGTH_SHORT).show();
            }
//            Log.d("action",String.valueOf(action));
            progress.cancel();
        }
    }
}
