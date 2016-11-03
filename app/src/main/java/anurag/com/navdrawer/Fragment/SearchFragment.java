package anurag.com.navdrawer.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.R;



public class SearchFragment extends Fragment {

    AutoCompleteTextView search;
    Button submit,cancel;

    ProgressDialog progress;

    String[] styleno=null;
    String searchbar;
    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink=new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;
//    private static String SEARCH_URL = "http://192.168.0.110:8068/tms/search.php";
    private static String TAG_POST = "posts";

    public SearchFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_search, container, false);
        search=(AutoCompleteTextView)view.findViewById(R.id.search);
        submit=(Button)view.findViewById(R.id.submit);
        new ParseSearchURL().execute();
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbar = search.getText().toString();
                Fragment fr = new SearchListFragment();
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();

                args.putString("style_no", searchbar);

                fr.setArguments(args);
                ft.replace(R.id.frame_container, fr);
                ft.commit();
            }
        });
    }

    private void search()
    {


        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,styleno);
        search.setAdapter(adapter);
        search.setThreshold(1);
    }

    private class ParseSearchURL extends AsyncTask<Void,Void,Void>
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
    protected Void doInBackground(Void... params) {
            jsonObject = jparser.getJson(jsonLink.getSEARCH_URL());
            if (jsonObject != null) {
                try {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");


                    postarray = jsonObject.getJSONArray(TAG_POST);

                    styleno = new String[postarray.length()];
                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);
                        styleno[i] = c.getString("style_no");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    @Override
    protected void onPostExecute(Void aVoid)
    {
        if(styleno!=null) {
            search();
        }
        else
        {
            Toast.makeText(getActivity(),"Data not Available",Toast.LENGTH_SHORT).show();
        }
        progress.cancel();
    }
}
}

