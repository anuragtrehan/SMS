package anurag.com.navdrawer.Fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import anurag.com.navdrawer.Adapter.CustomAdapterUser;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.AdminUser;
import anurag.com.navdrawer.R;


public class UserFragment extends ListFragment {
    Button add;
    String u_username, u_pass, u_name, u_contact,  u_usernameold;
    String u_email=null;
//    String u_name1,u_email1;
    String[] name = null;
    String[] username = null;
    String[] password = null;
    String[] contact = null;
    String[] email = null;
    int[] type1 = null;

    ProgressDialog progress;

    CustomAdapterUser customAdapterUser;
    private List<AdminUser> adminUsers;
    int checkuserid;
    int action;
    int type;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink = new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;

  //  private static String DATA_URL1 = "http://192.168.0.110:8068/tms/adminuser.php";
    private static String TAG_POST = "posts";

    public UserFragment() {
        // Required empty public constructor
    }

    public void setCheckUserid(int id) {
        checkuserid = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        add= (Button) view.findViewById(R.id.add);
        action=0;


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(checkuserid == 1)
        {type=1;
        new ParseAdminUser().execute();
        }
        else if(checkuserid == 2)
        {type=2;
            new ParseAdminUser().execute();
        }
        else if(checkuserid == 3)
        {type=3;
            new ParseAdminUser().execute();
        }
        else if(checkuserid == 4)
        {type=4;
            new ParseAdminUser().execute();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            action = 3;
            editdialog();
            }
        });



    }



    AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
           
            diaolg(position);
            return true;
        }
    };
    public void userdata()
    {
        adminUsers = new ArrayList<AdminUser>();

        if(username!=null) {
            for (int i = 0; i < username.length; i++) {
                AdminUser items = new AdminUser(contact[i], email[i], name[i], password[i], username[i]);

                adminUsers.add(items);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available",Toast.LENGTH_SHORT).show();
        }

        customAdapterUser = new CustomAdapterUser(adminUsers, getActivity());
        setListAdapter(customAdapterUser);
        getListView().setOnItemLongClickListener(listener);
    }
    public void diaolg(final int position)
    {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setNegativeButton("Edit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        action=1;
                        u_usernameold=username[position];
                        editdialog();
                        dialog.cancel();
                    }


                });
        alertDialog.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        action=2;

                        u_username=username[position];
                        new ParseAdminUser().execute();
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void editdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            if(action==3)
            alertDialog.setTitle("Add User");
            if(action==1)
            alertDialog.setTitle("Edit User");

            alertDialog.setMessage("Enter Details");

            // Set an EditText view to get user input
            final EditText input1 = new EditText(getActivity());
            final EditText input2 = new EditText(getActivity());
            final EditText input3 = new EditText(getActivity());
            final EditText input4 = new EditText(getActivity());
            final EditText input5 = new EditText(getActivity());

                input1.setHint("Username");
                input2.setHint("Password");
                input3.setHint("Contact");
                input4.setHint("Name");
                input5.setHint("Email");


            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input1);
            layout.addView(input2);
            layout.addView(input3);
            layout.addView(input4);
            layout.addView(input5);


            alertDialog.setView(layout);


            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                                u_username = input1.getText().toString();
                                u_pass=input2.getText().toString();
                                u_contact=input3.getText().toString();
                                u_name=input4.getText().toString();
                                u_email=input5.getText().toString();
                            if(u_username.equals("") || u_pass.equals("") || u_contact.equals("") || u_name.equals("") || u_email.equals("")){
                                Toast.makeText(getActivity(),"Some fields are empty , enter again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                new ParseAdminUser().execute();
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



    public void insert(String u_username,String u_pass,String u_name,String u_contact,String u_email)
    {
            String message;
    String abc;
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action",String.valueOf(action))
                    .appendQueryParameter("u_username", u_username)
                    .appendQueryParameter("u_pass", u_pass)
                    .appendQueryParameter("u_contact",u_contact)
                    .appendQueryParameter("u_name", u_name)
                    .appendQueryParameter("u_type", String.valueOf(type))
                    .appendQueryParameter("u_email",u_email);


            query = builder.build().getEncodedQuery();
        Log.d("inapp",String.valueOf(action));
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL1());

                success=jsonObject.getInt("success");
                message=jsonObject.getString("message");
//                abc=jsonObject.getString("error");
                if(success==1)
                {
                    Log.d("TAG", String.valueOf(success));

                    Log.d("Message",message);

                    postarray = jsonObject.getJSONArray(TAG_POST);

                    username= new String[postarray.length()];
                    password = new String[postarray.length()];
                    contact = new String[postarray.length()];
                    email = new String[postarray.length()];
                    name = new String[postarray.length()];
                    type1= new int[postarray.length()];
                    for (int i = 0; i < postarray.length(); i++)
                    {
                        JSONObject c = postarray.getJSONObject(i);
                        type1[i] = c.getInt("u_type");

                            username[i] = c.getString("u_username");
                            password[i] = c.getString("u_pass");
                            contact[i] = c.getString("u_contact");
                            email[i] = c.getString("u_email");
                            name[i] = c.getString("u_name");


                    }
                }
                else
                    Log.d("AfterTAG", String.valueOf(success));
                Log.d("Message",message);
//                Log.d("EX", abc);

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }


    public void delete()
    {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("u_username", u_username)
                .appendQueryParameter("action", String.valueOf(action))
                 .appendQueryParameter("u_type", String.valueOf(type));
        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL1());

            success=jsonObject.getInt("success");
            if(success==1)
            {
                Log.d("TAG", String.valueOf(success));
                postarray = jsonObject.getJSONArray(TAG_POST);
                username= new String[postarray.length()];
                password = new String[postarray.length()];
                contact = new String[postarray.length()];
                email = new String[postarray.length()];
                name = new String[postarray.length()];
                type1= new int[postarray.length()];
                for (int i = 0; i < postarray.length(); i++)
                {
                    JSONObject c = postarray.getJSONObject(i);
                    type1[i] = c.getInt("u_type");
                    if(type1[i]==type)
                    {
                        username[i] = c.getString("u_username");
                        password[i] = c.getString("u_pass");
                        contact[i] = c.getString("u_contact");
                        email[i] = c.getString("u_email");
                        name[i] = c.getString("u_name");
                    }

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

    public void update()
    {
        String message;
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("u_username", u_username)
                .appendQueryParameter("u_pass", u_pass)
                .appendQueryParameter("u_type", String.valueOf(type))
                .appendQueryParameter("u_contact",u_contact)
                .appendQueryParameter("u_name",u_name)
                .appendQueryParameter("u_email",u_email)
                .appendQueryParameter("u_usernameold",u_usernameold)
                .appendQueryParameter("action",String.valueOf(action));
        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL1());
            if(jsonObject!=null) {
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");
                if (success == 1) {
                    Log.d("TAG", String.valueOf(success));
                    Log.d("Message", message);
                    postarray = jsonObject.getJSONArray(TAG_POST);
                    username = new String[postarray.length()];
                    password = new String[postarray.length()];
                    contact = new String[postarray.length()];
                    email = new String[postarray.length()];
                    name = new String[postarray.length()];
                    type1 = new int[postarray.length()];
                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);
                        type1[i] = c.getInt("u_type");

                        username[i] = c.getString("u_username");
                        password[i] = c.getString("u_pass");
                        contact[i] = c.getString("u_contact");
                        email[i] = c.getString("u_email");
                        name[i] = c.getString("u_name");


                    }
                } else
                    Log.d("AfterTAG", String.valueOf(success));
                Log.d("Message", message);
            }

        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }
    public void select()
    {
        String message;
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("action", String.valueOf(action))
                .appendQueryParameter("u_type", String.valueOf(type));
        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getDATA_URL1());
            if(jsonObject!=null) {
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");
                if (success == 1) {
                    Log.d("TAG", String.valueOf(success));

                    postarray = jsonObject.getJSONArray(TAG_POST);

                    username = new String[postarray.length()];
                    password = new String[postarray.length()];
                    contact = new String[postarray.length()];
                    email = new String[postarray.length()];
                    name = new String[postarray.length()];
                    type1 = new int[postarray.length()];

                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);
                        type1[i] = c.getInt("u_type");

                        username[i] = c.getString("u_username");
                        password[i] = c.getString("u_pass");
                        contact[i] = c.getString("u_contact");
                        email[i] = c.getString("u_email");
                        name[i] = c.getString("u_name");


                    }
                    message = String.valueOf(postarray.length());
                } else
                    Log.d("TAG", String.valueOf(success));
                Log.d("TAG", message);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public class ParseAdminUser extends AsyncTask<Void, Void, Boolean> {

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
            if(action==0)
            {  select();}
            else if(action==1)
            {update();}
            else if(action==2)
            {   delete();}
            else if(action==3)
            {Log.d("Query","Insert");
                insert(u_username, u_pass, u_name, u_contact, u_email);}
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//            Toast.makeText(getActivity(),String.valueOf(action),Toast.LENGTH_SHORT).show();
            userdata();
            progress.cancel();
        }
    }
}










