package anurag.com.navdrawer.Fragment;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import anurag.com.navdrawer.Adapter.CustomAdapter;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.AdminPojo;
import anurag.com.navdrawer.R;


public class AdminFragment extends ListFragment  {

    Button add;
    String[] brand =  null;
    String[] season = null;
    String[] designer_name =  null;
    String[] designer_brand = null;
    String[] fabric_code =    null;
    String[] fabric_detail =  null;
    String[] lining_code = null;
    String b_name,s_name,d_name,d_brand,f_code,f_detail,l_code;
    String b_nameold,s_nameold,d_nameold,f_codeold,l_codeold;

    CustomAdapter adapter;
    private List<AdminPojo> adminpojo;
    int checkid;
    int action;
    String type;

    ProgressDialog progress;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink =new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;

//    private static String DATA_URL = "http://192.168.0.110:8068/tms/admindata.php";
    private static String TAG_POST = "posts";

    public AdminFragment() {
    }

    public void setCheckid(int id) {
        checkid = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        add= (Button) view.findViewById(R.id.add);
        action=0;


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (checkid == 1) {
            type = "brand";
            new ParseAdminData().execute();
        }
        if (checkid == 2) {
            type = "season";
            new ParseAdminData().execute();
        }
        if (checkid == 3) {
            type = "designer";
            new ParseAdminData().execute();
        }
        if (checkid == 4) {
            type = "fabric";
            new ParseAdminData().execute();
        }
        if (checkid == 5) {
            type = "lining";
            new ParseAdminData().execute();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = 3;
                if(checkid==1)
                {
                    type = "brand";
                    insert(checkid);

                }
               else if(checkid==2)
                {
                    type = "season";
                    insert(checkid);

                }
               else if(checkid==3)
                {
                    type = "designer";
                    insert(checkid);

                }
               else if(checkid==4)
                {
                    type = "fabric";
                    insert(checkid);

                }
                else if(checkid==5)
                {
                    type = "lining";
                    insert(checkid);

                }
            }
        });
    }

    AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
            if(checkid==1)
            {
                b_nameold=brand[position];
                dialog(checkid,position);
            }
            if(checkid==2)
            {
                s_nameold=season[position];
                dialog(checkid,position);
            }
            if(checkid==3)
            {
                d_nameold=designer_name[position];
                dialog(checkid,position);
            }
            if(checkid==4)
            {
                f_codeold=fabric_code[position];
                dialog(checkid,position);
            }
            if(checkid==5)
            {
                l_codeold=lining_code[position];
                dialog(checkid,position);
            }
            return true;
        }
    };

    public void data ()
    {
        if (checkid == 1)
        {



            adminpojo = new ArrayList<AdminPojo>();
            if(brand!=null) {
                for (int i = 0; i < brand.length; i++) {
                    AdminPojo items = new AdminPojo(brand[i], checkid);

                    adminpojo.add(items);
                }
            }
            else{
                Toast.makeText(getActivity(),"no data available on brand",Toast.LENGTH_SHORT).show();
            }

            adapter = new CustomAdapter(adminpojo,getActivity(),checkid);
            setListAdapter(adapter);
            getListView().setOnItemLongClickListener(listener);


        }
        if (checkid == 2)
        {

            adminpojo = new ArrayList<AdminPojo>();
            if(season!=null) {
                for (int i = 0; i < season.length; i++) {
                    AdminPojo items = new AdminPojo(season[i], checkid);

                    adminpojo.add(items);
                }
            }
            else{
                Toast.makeText(getActivity(),"no data available on season",Toast.LENGTH_SHORT).show();
            }

            adapter = new CustomAdapter(adminpojo,getActivity(),checkid);
            setListAdapter(adapter);
            getListView().setOnItemLongClickListener(listener);
        }
        if (checkid == 3)
        {

            adminpojo = new ArrayList<AdminPojo>();
            if(designer_name!=null) {
                for (int i = 0; i < designer_name.length; i++) {
                    AdminPojo items = new AdminPojo(designer_brand[i], designer_name[i], checkid);

                    adminpojo.add(items);
                }
            }
            else{
                Toast.makeText(getActivity(),"no data available on designer",Toast.LENGTH_SHORT).show();
            }


            adapter = new CustomAdapter(adminpojo,getActivity(),checkid);
            setListAdapter(adapter);
            getListView().setOnItemLongClickListener(listener);
        }
        if (checkid == 4)
        {

            adminpojo = new ArrayList<AdminPojo>();
            if(fabric_code!=null) {
                for (int i = 0; i < fabric_code.length; i++) {
                    AdminPojo items = new AdminPojo(fabric_code[i], fabric_detail[i], checkid);

                    adminpojo.add(items);
                }
            }
            else{
                Toast.makeText(getActivity(),"no data available on fabric code",Toast.LENGTH_SHORT).show();
            }

            adapter = new CustomAdapter(adminpojo,getActivity(),checkid);
            setListAdapter(adapter);
            getListView().setOnItemLongClickListener(listener);


        }
        if (checkid == 5)
        {

            adminpojo = new ArrayList<AdminPojo>();
            if(lining_code!=null) {
                for (int i = 0; i < lining_code.length; i++) {
                    AdminPojo items = new AdminPojo(lining_code[i], checkid);

                    adminpojo.add(items);
                }
            }
            else{
                Toast.makeText(getActivity(),"no data available on lining code",Toast.LENGTH_SHORT).show();
            }

            adapter = new CustomAdapter(adminpojo,getActivity(),checkid);
            setListAdapter(adapter);
            getListView().setOnItemLongClickListener(listener);
        }
    }




    public class ParseAdminData extends AsyncTask<Void,Void,Boolean>
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
       protected Boolean doInBackground(Void... params) {
           if (checkid == 1)
           {
               if (action == 0)
               {
                 brand(null,action,type);

               }
               else if(action==1)
               {
                   brand(b_name,b_nameold,action,type);

               }
               else if(action==2||action==3)
               {
                   brand(b_name,action,type);
               }


           }
           if (checkid == 2)
           {
               if (action == 0)
               {
                   season(null,action,type);

               }
               else if(action==1)
               {
                  season(s_name,s_nameold,action,type);
               }
               else if(action==2||action==3)
               {
                   season(s_name,action,type);
               }


           }
           if (checkid == 3)
           {
               if (action == 0)
               {
                   designer(null,null,action,type);

               }
               else if(action==1)
               {
                   designer(d_name,d_brand,d_nameold,action,type);
               }
               else if(action==2||action==3)
               {
                   designer(d_name,d_brand,action,type);
               }


           }
           if (checkid == 4)
           {
               if (action == 0)
               {
                   fabric(null,null,action,type);

               }
               else if(action==1)
               {
                   fabric(f_code,f_detail,f_codeold,action,type);
               }
               else if(action==2||action==3)
               {
                   fabric(f_code,f_detail,action,type);
               }


           }
           if (checkid == 5)
           {
               if (action == 0)
               {
                   lining(null,action,type);

               }
               else if(action==1)
               {
                   lining(l_code,l_codeold,action,type);
               }
               else if(action==2||action==3)
               {
                   lining(l_code,action,type);
               }


           }
           return null;
       }

       @Override
       protected void onPostExecute(Boolean aBoolean)
       {

           data();

           progress.cancel();
       }
   }


    public void brand(String b_name, int action, String type)
    {
        if(action==0)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("b_name", b_name)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        brand = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            brand[i] = c.getString("b_name");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
        else if(action==2||action==3)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("b_name", b_name)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action", String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        brand = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            brand[i] = c.getString("b_name");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }

    }
    public void brand(String b_name,String b_nameold,int action,String type)
    {String abc;
        if(action==1)
        {
            if(jsonObject!=null)
            {
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("b_name", b_name)
                        .appendQueryParameter("b_nameold", b_nameold)
                        .appendQueryParameter("type", type)
                        .appendQueryParameter("action",String.valueOf(action));
                query = builder.build().getEncodedQuery();
                Log.d("before action",String.valueOf(action));
                try {
                    jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                    abc=jsonObject.getString("action");
                    Log.d("after action",abc);
                    success=jsonObject.getInt("success");
                    if(success==1)
                    {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        brand = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++)
                        {
                            JSONObject c = postarray.getJSONObject(i);
                            brand[i] = c.getString("b_name");
                        }
                    }

                    else
                        Log.d("TAG", String.valueOf(success));

                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }


    }


    public void season(String s_name,int action,String type)
    {
        if(action==0)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("s_name", s_name)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        season = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            season[i] = c.getString("s_name");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
        else if(action==2||action==3)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("s_name", s_name)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action", String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        season = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            season[i] = c.getString("s_name");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }


    }


    public void season(String s_name,String s_nameold,int action,String type)
    {
        if(action==1)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("s_name", s_name)
                    .appendQueryParameter("s_nameold", s_nameold)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        season = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            season[i] = c.getString("s_name");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }


    }
    public void lining(String l_code,int action,String type)
    {
        if(action==0)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("ln_code", l_code)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        lining_code = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            lining_code[i] = c.getString("ln_code");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
        else if(action==2||action==3)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("ln_code", l_code)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action", String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        lining_code = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            lining_code[i] = c.getString("ln_code");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }


    }


    public void lining(String l_code,String l_codeold,int action,String type)
    {
        if(action==1)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("ln_code", l_code)
                    .appendQueryParameter("ln_codeold", l_codeold)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        lining_code = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            lining_code[i] = c.getString("ln_code");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }


    }


    public void designer(String d_name,String d_brand,int action,String type)
    {
        if(action==0)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("d_name", d_name)
                    .appendQueryParameter("d_brand", d_brand)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        designer_name = new String[postarray.length()];
                        designer_brand = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            designer_name[i] = c.getString("d_name");
                            designer_brand[i] = c.getString("d_brand");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
        else if(action==2||action==3)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("d_name", d_name)
                    .appendQueryParameter("d_brand", d_brand)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action", String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        designer_name = new String[postarray.length()];
                        designer_brand = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            designer_name[i] = c.getString("d_name");
                            designer_brand[i] = c.getString("d_brand");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }


    }
    public void designer(String d_name,String d_brand,String d_nameold,int action,String type)
    {
        if(action==1)
    {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("d_name", d_name)
                .appendQueryParameter("d_brand", d_brand)
                .appendQueryParameter("d_nameold", d_nameold)
                .appendQueryParameter("type", type)
                .appendQueryParameter("action", String.valueOf(action));
        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
            if(jsonObject!=null) {
                success = jsonObject.getInt("success");
                if (success == 1) {
                    Log.d("TAG", String.valueOf(success));
                    postarray = jsonObject.getJSONArray(TAG_POST);
                    season = new String[postarray.length()];
                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);
                        designer_name[i] = c.getString("d_name");
                        designer_brand[i] = c.getString("d_brand");
                    }
                } else
                    Log.d("TAG", String.valueOf(success));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }
    }
    public void fabric(String f_code,String f_detail,int action,String type)
    {
        if(action==0)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("f_code", f_code)
                    .appendQueryParameter("f_detail", f_detail)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action",String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        fabric_code = new String[postarray.length()];
                        fabric_detail = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            fabric_code[i] = c.getString("f_code");
                            fabric_detail[i] = c.getString("f_detail");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
        else if(action==2||action==3)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("f_code", f_code)
                    .appendQueryParameter("f_detail", f_detail)
                    .appendQueryParameter("f_codeold", f_codeold)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action", String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        fabric_code = new String[postarray.length()];
                        fabric_detail = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            fabric_code[i] = c.getString("f_code");
                            fabric_detail[i] = c.getString("f_detail");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
    }
    public void fabric(String f_code,String f_detail,String f_codeold,int action,String type)
    {
        if(action==1)
        {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("f_code", f_code)
                    .appendQueryParameter("f_detail", f_detail)
                    .appendQueryParameter("f_codeold", f_codeold)
                    .appendQueryParameter("type", type)
                    .appendQueryParameter("action", String.valueOf(action));
            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));
                        postarray = jsonObject.getJSONArray(TAG_POST);
                        fabric_code = new String[postarray.length()];
                        fabric_detail = new String[postarray.length()];
                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);
                            fabric_code[i] = c.getString("f_code");
                            fabric_detail[i] = c.getString("f_detail");
                        }
                    } else
                        Log.d("TAG", String.valueOf(success));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

    public void insert(final int checkid)
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        if(checkid==1||checkid==2||checkid==5)
        {

            alertDialog.setTitle("Insert");
            alertDialog.setMessage("Enter Details");

            // Set an EditText view to get user input
            final EditText input1 = new EditText(getActivity());
            if(checkid==1)
            input1.setHint("Brand Name");
            else if(checkid==2)
            input1.setHint("Season Name");
            else
            input1.setHint("Lining Name");

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input1);

            alertDialog.setView(layout);


            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(checkid==1) {
                                b_name = input1.getText().toString();
                                if(b_name.equals("")){
                                    Toast.makeText(getActivity(),"Brand name cant be empty",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            }
                            else if(checkid==2)
                            {
                                s_name=input1.getText().toString();
                                if(s_name.equals("")){
                                    Toast.makeText(getActivity(),"Season name cant be empty",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            }
                            else
                            {
                                l_code=input1.getText().toString();
                                if(l_code.equals("")){
                                    Toast.makeText(getActivity(),"Lining code cant be empty",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            }

                        }


                    });

            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
        }
       else if(checkid==3||checkid==4)
        {

        alertDialog.setTitle("Insert");
        alertDialog.setMessage("Enter Details");

            final EditText input1 = new EditText(getActivity());
            final EditText input2 = new EditText(getActivity());
            if(checkid==3)
            {
                input1.setHint("Designer Name");
                input2.setHint("Brand Name");
            }
            else
            {
                input1.setHint("Fabric Code");
                input2.setHint("Fabric Detail");
            }

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input1);
            layout.addView(input2);
            alertDialog.setView(layout);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(checkid==3) {
                            d_name = input1.getText().toString();
                            d_brand = input2.getText().toString();
                            if(d_name.equals("") || d_brand.equals("")){
                                Toast.makeText(getActivity(),"Name and Brand both are mandatory",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                new ParseAdminData().execute();
                            }                        }
                        else
                        {
                            f_code=input1.getText().toString();
                            f_detail=input2.getText().toString();
                            if(f_code.equals("") || f_detail.equals("")){
                                Toast.makeText(getActivity(),"Code and Detail both are mandatory",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                new ParseAdminData().execute();
                            }
                        }

                    }


                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
    public void edit(final int checkid) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        if (checkid == 1 || checkid == 2 || checkid==5) {

            alertDialog.setTitle("Edit");
            alertDialog.setMessage("Enter Details");

            // Set an EditText view to get user input
            final EditText input1 = new EditText(getActivity());
            if (checkid == 1)
                input1.setHint("Brand Name");
            else if(checkid==2)
                input1.setHint("Season Name");
            else
                input1.setHint("Lining Name");

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input1);

            alertDialog.setView(layout);


            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkid == 1) {
                                b_name = input1.getText().toString();
                                if(b_name.equals("")){
                                    Toast.makeText(getActivity(),"Brand name cant be empty",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            } else if(checkid==2) {
                                s_name = input1.getText().toString();

                                if(s_name.equals("")){
                                    Toast.makeText(getActivity(),"Season name cant be empty",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            }
                            else  {
                                l_code = input1.getText().toString();

                                if(l_code.equals("")){
                                    Toast.makeText(getActivity(),"Lining code cant be empty",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            }

                        }


                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
        } else if (checkid == 3 || checkid == 4) {

            alertDialog.setTitle("Insert");
            alertDialog.setMessage("Enter Details");

            final EditText input1 = new EditText(getActivity());
            final EditText input2 = new EditText(getActivity());
            if (checkid == 3) {
                input1.setHint("Designer Name");
                input2.setHint("Brand Name");
            } else {
                input1.setHint("Fabric Code");
                input2.setHint("Fabric Detail");
            }

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input1);
            layout.addView(input2);
            alertDialog.setView(layout);

            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkid == 3) {
                                d_name = input1.getText().toString();
                                d_brand = input2.getText().toString();
                                if(d_name.equals("") || d_brand.equals("")){
                                    Toast.makeText(getActivity(),"Name and Brand both are mandatory",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            } else {
                                f_code = input1.getText().toString();
                                f_detail = input2.getText().toString();
                                if(f_code.equals("") || f_detail.equals("")){
                                    Toast.makeText(getActivity(),"Code and Detail both are mandatory",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    new ParseAdminData().execute();
                                }
                            }

                        }


                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
        }
    }
       public void dialog(final int checkid, final int position)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setNegativeButton("Edit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        action=1;
                        edit(checkid);
                    dialog.cancel();
                    }


                });
        alertDialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        action=2;

                        if(checkid==1)
                        {
                            b_name=brand[position];
                        }
                        else if(checkid==2)
                        {
                            s_name=season[position];
                        }
                        else if(checkid==3)
                        {
                            d_name=designer_name[position];
                        }
                        else if(checkid==4)
                        {
                            f_code=fabric_code[position];
                        }
                        else if(checkid==5)
                        {
                            l_code=lining_code[position];
                        }
                        new ParseAdminData().execute();
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}





