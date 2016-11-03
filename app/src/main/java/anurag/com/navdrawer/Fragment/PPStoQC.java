package anurag.com.navdrawer.Fragment;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.res.Resources;
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

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.R;


public class PPStoQC extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText date;
    Spinner stage,size,qc,styleno;
    TextView orderno;
    Button submit,cancel;

    ProgressDialog progress;

    ArrayAdapter<String> stageadapter;
    ArrayAdapter<String> sizeadapter;
    ArrayAdapter<String> qcadapter ;
    ArrayAdapter<String> styleadapter;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink=new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;
    private int action;
//    private static String ADDPPS_URL = "http://a0682321.ngrok.io/tms/ppstoqc.php";
//    private static String ADDPPS_URL = Resources.getSystem().getString(R.string.ADDPPS_URL);
    private static String TAG_POST = "posts";

    String[] size1={"xs","s","m","l","xl","xxl"},qc1,styleno1,orderno1;
    int[] stage1={1,2,3,4};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ppstoqc, container, false);

        date= (EditText) view.findViewById(R.id.date);

        stage= (Spinner) view.findViewById(R.id.stage);
        size= (Spinner) view.findViewById(R.id.size);
        qc= (Spinner) view.findViewById(R.id.quality);

        orderno= (TextView)view.findViewById(R.id.order_no);

        styleno= (Spinner)view.findViewById(R.id.style_no);

        submit=(Button)view.findViewById(R.id.submit);
        cancel=(Button)view.findViewById(R.id.cancel);
        adddate();
        action = 0;
        new ParsePPS().execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = 2;
                new ParsePPS().execute();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new WelcomeFragment();
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                fr.setArguments(args);
                ft.replace(R.id.frame_container, fr);
                ft.commit();
            }
        });
    }
    private void adddate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        date.setText(formattedDate);
    }
    public void spinner()
    {
        stage.setOnItemSelectedListener(this);
        size.setOnItemSelectedListener(this);
        qc.setOnItemSelectedListener(this);
        styleno.setOnItemSelectedListener(this);

        List<String> stagelist = new ArrayList<String>();
        for(int i=0;i<stage1.length;i++)
        {stagelist.add(String.valueOf(stage1[i]));}

        List<String> sizelist = new ArrayList<String>();
        for(int i=0;i<size1.length;i++)
        {sizelist.add(size1[i]);}

        List<String> qclist = new ArrayList<String>();
        if(qc1!=null) {
            for (int i = 0; i < qc1.length; i++) {
                qclist.add(qc1[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on quality checker",Toast.LENGTH_SHORT).show();
        }


        List<String> stylelist = new ArrayList<String>();
        if(styleno1!=null) {
            for (int i = 0; i < styleno1.length; i++) {
                stylelist.add(styleno1[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on style number",Toast.LENGTH_SHORT).show();
        }

         stageadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stagelist);
         sizeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sizelist);
         qcadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, qclist);
         styleadapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,stylelist);

        stage.setAdapter(stageadapter);
        size.setAdapter(sizeadapter);
        qc.setAdapter(qcadapter);
        styleno.setAdapter(styleadapter);

    }
    private void select(int action)
    {
        if(action==0)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action));

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getADDPPS_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        styleno1 = new String[postarray.length()];
                        orderno1 = new String[postarray.length()];


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            styleno1[i] = c.getString("style_no");
                            orderno1[i] = c.getString("job_order_no");
                            Log.d("orderno", orderno1[i]);
                        }

                    } else
                        Log.d("TAG", String.valueOf(success));
                    Log.d("TAG", message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(action==1)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action));

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getADDPPS_URL());

                success=jsonObject.getInt("success");
                message=jsonObject.getString("message");
                if(success==1)
                {
                    Log.d("TAG", String.valueOf(success));

                    postarray = jsonObject.getJSONArray(TAG_POST);

                   qc1 = new String[postarray.length()];



                    for (int i = 0; i < postarray.length(); i++)
                    {
                        JSONObject c = postarray.getJSONObject(i);

                        qc1[i] = c.getString("qc");

                    }

                }
                else
                    Log.d("TAG", String.valueOf(success));
                Log.d("TAG", message);

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

    }

    private void submitOrder(int action){

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("date", date.getText().toString())
                .appendQueryParameter("action", String.valueOf(action))
                .appendQueryParameter("stage",stage.getSelectedItem().toString() )
                .appendQueryParameter("style_no", styleno.getSelectedItem().toString())
                .appendQueryParameter("job_order_no",orderno.getText().toString() )
                .appendQueryParameter("size",size.getSelectedItem().toString() )
                .appendQueryParameter("qc", qc.getSelectedItem().toString())
                .appendQueryParameter("qc_status", String.valueOf(1));

        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getADDPPS_URL());

            success=jsonObject.getInt("success");
            message=jsonObject.getString("message");
            if(success==1) {
                Log.d("TAG", String.valueOf(success));
                Log.d("Message", message);
            }
            else
            {
                Log.d("TAG", String.valueOf(success));
                Log.d("Message", message);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

       if(parent.getAdapter()==styleadapter)
       {
           orderno.setText(orderno1[position]); // do something for the 1st spinner}


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    private class ParsePPS extends AsyncTask<Void,Void,Void>
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
        if(action==0)
        {
            select(action);
            action=1;
            select(action);
        }
        else if(action==2)
        {
            submitOrder(action);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {

        if(action==1)
        {
            spinner();
        }
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        progress.cancel();
    }
}
}
