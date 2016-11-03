package anurag.com.navdrawer.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.R;


public class AddOrderFragment extends Fragment implements AdapterView.OnItemSelectedListener

{


    TextView comment;

    EditText date, order_no, production;
    Button upload,submit,cancel;
    CheckBox s,m,l,xl;
    Spinner style,vendor;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink =new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;
    int action;
//    private static String ADDORDER_URL = "http://a0682321.ngrok.io/tms/addorder.php";
    private static String TAG_POST = "posts";

    ProgressDialog progress;

    private String encoded_string;
    private String path;

    int s1=0,m1=0,l1=0,xl1=0;

    String[] styleno,comment1,vendor1;
    public AddOrderFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_add_order, container, false);

        date= (EditText) view.findViewById(R.id.date);
        comment= (TextView) view.findViewById(R.id.comment);
        order_no= (EditText) view.findViewById(R.id.order_no);
        production = (EditText) view.findViewById(R.id.production);

        upload=(Button) view.findViewById(R.id.upload);
        submit=(Button) view.findViewById(R.id.submit);
        cancel=(Button) view.findViewById(R.id.cancel);

        style=(Spinner)view.findViewById(R.id.style);
        vendor=(Spinner)view.findViewById(R.id.vendor);

        s=(CheckBox)view.findViewById(R.id.s);
        m=(CheckBox)view.findViewById(R.id.m);
        l=(CheckBox)view.findViewById(R.id.l);
        xl=(CheckBox)view.findViewById(R.id.xl);

        adddate();
        action = 1;
        new ParseAddOrder().execute();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateData()==true) {
                    action = 3;
                    new ParseAddOrder().execute();
                }
//                else{
//                    Toast.makeText(getActivity(),"Some fields are empty",Toast.LENGTH_SHORT).show();
//                }

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
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PICKFILE_RESULT_CODE=1;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==1  && resultCode == Activity.RESULT_OK)
        {
            Uri result = data.getData();
            path = result.getPath();
            Log.d("path", getFileExt(path));
            if(getFileExt(path).equals("xlsx")) {
                Log.d("path", getFileExt(path));
                File yourFile = new File(path);
                byte[] bytes = convertFileToByteArray(yourFile);
                encoded_string = Base64.encodeToString(bytes, Base64.DEFAULT);
                Log.d("encoded", encoded_string);
            }
            else
            {
                Toast.makeText(getActivity(),"Select excel File",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }
    private boolean validateData(){

        if((date.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Date cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if((order_no.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Order number cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if((production.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Production cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!(s.isChecked() || m.isChecked() || l.isChecked() || xl.isChecked())){
            Toast.makeText(getActivity(),"Select atleast one checkbox",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(path==null||encoded_string==null){
            Toast.makeText(getActivity(),"Upload Chart",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(comment1!=null)
        comment.setText(comment1[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }

    public void spinner()
    {
        style.setOnItemSelectedListener(this);
        vendor.setOnItemSelectedListener(this);

        List<String> stylelist = new ArrayList<String>();
        if(styleno!=null) {
            for (int i = 0; i < styleno.length; i++) {
                stylelist.add(styleno[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on style number",Toast.LENGTH_SHORT).show();
        }

        List<String> vendorlist = new ArrayList<String>();
        if(vendor1!=null) {
            for (int i = 0; i < vendor1.length; i++) {
                vendorlist.add(vendor1[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on vendor",Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> styleadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stylelist);
        ArrayAdapter<String> vendoradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, vendorlist);

        style.setAdapter(styleadapter);
        vendor.setAdapter(vendoradapter);


    }

    private void adddate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        date.setText(formattedDate);
    }
    private void submitOrder(int action){
        if(s.isChecked())
            s1=1;
        if(m.isChecked())
            m1=1;
        if(l.isChecked())
            l1=1;
        if(xl.isChecked())
        {
            xl1=1;
        }
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("date", date.getText().toString())
                .appendQueryParameter("action", String.valueOf(action))
                .appendQueryParameter("style_no", style.getSelectedItem().toString())
                .appendQueryParameter("job_order_no",order_no.getText().toString() )
                .appendQueryParameter("vendor",vendor.getSelectedItem().toString())
                .appendQueryParameter("production_quantity", production.getText().toString())
                .appendQueryParameter("s",String.valueOf(s1) )
                .appendQueryParameter("m", String.valueOf(m1))
                .appendQueryParameter("l", String.valueOf(l1))
                .appendQueryParameter("xl",String.valueOf(xl1));

        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getADDORDER_URL());

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

    private void select(int action)
    {
        if(action==1) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action));

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getADDORDER_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        styleno = new String[postarray.length()];
                        comment1 = new String[postarray.length()];


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            comment1[i] = c.getString("comment_org");
                            Log.d("comment", comment1[i]);
                            styleno[i] = c.getString("style_no");


                        }

                    } else
                        Log.d("TAG", String.valueOf(success));
                    Log.d("TAG", message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action));

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getADDORDER_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        vendor1 = new String[postarray.length()];


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            vendor1[i] = c.getString("vendor");

                        }

                    } else {
                        Log.d("TAG", String.valueOf(success));
                        Log.d("TAG", message);
                    }
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, jsonLink.getADDORDER_URL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("message",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("encoded_string",encoded_string);
                map.put("file",style.getSelectedItem().toString()+".xlsx");
                map.put("style_no",style.getSelectedItem().toString());
                map.put("action",String.valueOf(action));
                Log.d("path",path);
                return map;
            }
        };
        requestQueue.add(request);
    }
    private class ParseAddOrder extends AsyncTask<Void,Void,Void>
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
            if(action==1)
            {
            select(action);
            action=2;
            select(action);
            }
            if(action==3)
            {
            submitOrder(action);
             action=4;
                Log.d("check","one");
                if(success==1)
                {
                    Log.d("check","two");
                    makeRequest();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(action==2)
                spinner();

            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

            progress.cancel();
        }
    }
}