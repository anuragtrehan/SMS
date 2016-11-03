package anurag.com.navdrawer.Fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;


import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anurag.com.navdrawer.Adapter.AdapterFabric;
import anurag.com.navdrawer.Adapter.AdapterLining;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.AddStyle;
import anurag.com.navdrawer.R;


public class AddStyleFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    Spinner brand,season,originalss,designer,fb_code,ln_code;
    EditText date,styleno,patternno,comment,fb_width,fb_consumption,ln_width,ln_consumption;
    Button image,submit,cancel,addfabric,addlining,delete;
    AdapterFabric adapterFabric;
    AdapterLining adapterLining;
    AddStyle fabricitems;
    AddStyle liningitems;
    private List<AddStyle> addStyle1= new ArrayList<AddStyle>();
    private List<AddStyle> addStyle2= new ArrayList<AddStyle>();
    ListView fabriclist,lininglist;
    ImageView imageView,imageView1,imageView2,imageView3;

    ProgressDialog progress;

    private String[] encoded_string = new String[4];
    private String[] image_name = new String[4];
    private Bitmap[] bitmap = new Bitmap[4];
    private File file;
    private Uri file_uri;
    String[] imagpath = new String[4];
    private int cameracheck=0;
    boolean isImageFitToScreen;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;
    int action;
    int db;
    JsonLink jsonLink = new JsonLink();
//    private static String ADDSTYLE_URL = "http://192.168.0.110:8068/tms/addstyle.php" ;


    private static String TAG_POST = "posts";

    String f_code1, l_code1,f_width1,f_consumption1,l_width1,l_consumption1;
    String[] f_code= null,f_width=null,f_consumption=null,l_code=null,l_width=null,l_consumption=null;
    String[] b_name=null,d_name=null,s_name=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_style, container, false);
        brand      = (Spinner)view.findViewById(R.id.brand);
        season     = (Spinner)view.findViewById(R.id.season);
        originalss = (Spinner)view.findViewById(R.id.size);
        designer   = (Spinner)view.findViewById(R.id.designer);
        fb_code    =(Spinner)view.findViewById(R.id.fb_code);
        ln_code    =(Spinner)view.findViewById(R.id.ln_code);

        date =     (EditText)view.findViewById(R.id.date);
        styleno =  (EditText)view.findViewById(R.id.style);
        patternno =(EditText)view.findViewById(R.id.pattern);
        comment = (EditText)view.findViewById(R.id.comment);
        fb_width =(EditText)view.findViewById(R.id.fb_width);
        fb_consumption=(EditText)view.findViewById(R.id.fb_cons);
        ln_width=(EditText)view.findViewById(R.id.ln_width);
        ln_consumption=(EditText)view.findViewById(R.id.ln_cons);

        image = (Button) view.findViewById(R.id.image);
        submit = (Button) view.findViewById(R.id.submit);
        cancel = (Button) view.findViewById(R.id.cancel);
        addfabric=(Button) view.findViewById(R.id.fb_add);
        addlining=(Button) view.findViewById(R.id.ln_add);
        delete=(Button) view.findViewById(R.id.delete);

        fabriclist= (ListView) view.findViewById(R.id.fabric);
        lininglist= (ListView) view.findViewById(R.id.lining);

        imageView=(ImageView)view.findViewById(R.id.imagevw);
        imageView1=(ImageView)view.findViewById(R.id.imagevw1);
        imageView2=(ImageView)view.findViewById(R.id.imagevw2);
        imageView3=(ImageView)view.findViewById(R.id.imagevw3);

        new ParseAddStyle().execute();
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        addstyledata();


        addfabric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((fb_width.getText().toString()).matches("") || (fb_consumption.getText().toString()).matches("") ){
                    Toast.makeText(getActivity(),"Width and Consumption both are mandatory",Toast.LENGTH_SHORT).show();

                }
                else {
                    f_width1 = fb_width.getText().toString();
                    f_consumption1 = fb_consumption.getText().toString();
                    f_code1 = fb_code.getSelectedItem().toString();


                    fabricitems = new AddStyle(f_code1, f_width1, f_consumption1, 1);
                    addStyle1.add(fabricitems);
                    adapterFabric = new AdapterFabric(addStyle1, getActivity());
                    fabriclist.setAdapter(adapterFabric);

                }
            }
        });
        addlining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ln_width.getText().toString()).matches("") || (ln_consumption.getText().toString()).matches("")) {
                    Toast.makeText(getActivity(), "Width and Consumption both are mandatory", Toast.LENGTH_SHORT).show();

                } else {
                    l_width1 = ln_width.getText().toString();
                    l_consumption1 = ln_consumption.getText().toString();
                    l_code1 = ln_code.getSelectedItem().toString();

                    liningitems = new AddStyle(l_code1, l_width1, l_consumption1, 2);
                    addStyle2.add(liningitems);
                    adapterLining = new AdapterLining(addStyle2, getActivity());
                    lininglist.setAdapter(adapterLining);
                }
            }
        });

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validateData() == true) {
                action = 6;
                new ParseAddStyle().execute();
            }
        }
    });
    cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr = new WelcomeFragment();
                FragmentManager fm = getFragmentManager();
                android.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, fr);
                ft.commit();
        }
        });
    delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImages();
            }
    });

    fabriclist.setOnTouchListener(new ListView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            // Handle ListView touch events.
            v.onTouchEvent(event);
            return true;
        }
    });


    lininglist.setOnTouchListener(new ListView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            // Handle ListView touch events.
            v.onTouchEvent(event);
            return true;
        }
    });

    image.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (cameracheck < 4) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri(cameracheck);
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, 10);
            } else {
                Toast.makeText(getActivity(), "Only 4 images can be send", Toast.LENGTH_SHORT).show();
            }
        }
    });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.parse("file://" +imagpath[0]),"image/*");
//                startActivity(intent);
//            }
//        });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath[0]!=null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imagpath[0]), "image/*");
                        startActivity(intent);
                    }
                }
            });


            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath[1]!=null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imagpath[1]), "image/*");
                        startActivity(intent);
                    }
                }
            });


            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath[2]!=null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imagpath[2]), "image/*");
                        startActivity(intent);
                    }
                }
            });


            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath[3]!=null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imagpath[3]), "image/*");
                        startActivity(intent);
                    }
                    }
            });
        }

    private boolean validateData(){

        if((date.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Date cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if((styleno.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Style number cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if((patternno.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Pattern number cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        else if((comment.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Comment cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }


        else if(fabriclist.getCount()==0){
            Toast.makeText(getActivity(),"Fabric cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }

        else{
            return true;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            if (cameracheck < 4)
            {

                bitmap[cameracheck] = BitmapFactory.decodeFile(file_uri.getPath());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap[cameracheck].compress(Bitmap.CompressFormat.JPEG,50, stream);
                byte[] array = stream.toByteArray();
                encoded_string[cameracheck] = Base64.encodeToString(array, 0);
                Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap[cameracheck],64, 64);
                if (cameracheck == 0) {

                    imageView.setImageBitmap(resized);

                    cameracheck++;
                } else if (cameracheck == 1) {
                    imageView1.setImageBitmap(resized);
                    cameracheck++;
                } else if (cameracheck == 2) {
                    imageView2.setImageBitmap(resized);
                    cameracheck++;
                } else if (cameracheck == 3) {
                    imageView3.setImageBitmap(resized);
                    cameracheck++;
                }
            }
        }
    }
    private void deleteImages()
    {
        cameracheck=0;
        for(int i=0;i<4;i++)
        {
            bitmap[i]=null;
            imagpath[i]=null;
        }
        imageView.setImageBitmap(null);
        imageView1.setImageBitmap(null);
        imageView2.setImageBitmap(null);
        imageView3.setImageBitmap(null);
    }

    public void addstyledata()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        date.setText(formattedDate);


    }
    public void spinners()
    {
        brand.setOnItemSelectedListener(this);
        season.setOnItemSelectedListener(this);
        designer.setOnItemSelectedListener(this);
        fb_code.setOnItemSelectedListener(this);
        ln_code.setOnItemSelectedListener(this);
        originalss.setOnItemSelectedListener(this);

        List<String> brandlist = new ArrayList<String>();
        if(b_name != null) {
            for (int i = 0; i < b_name.length; i++) {
                brandlist.add(b_name[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on brand",Toast.LENGTH_SHORT).show();
        }

        List<String> seasonlist = new ArrayList<String>();
        if(s_name!=null) {
            for (int i = 0; i < s_name.length; i++) {
                seasonlist.add(s_name[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on season",Toast.LENGTH_SHORT).show();
        }

        List<String> designerlist = new ArrayList<String>();
        if(d_name!=null) {
            for (int i = 0; i < d_name.length; i++) {
                designerlist.add(d_name[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on designer",Toast.LENGTH_SHORT).show();
        }

        List<String> fb_codelist = new ArrayList<String>();
        if(f_code!=null) {
            for (int i = 0; i < f_code.length; i++) {
                fb_codelist.add(f_code[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on fabric code",Toast.LENGTH_SHORT).show();
        }


        List<String> ln_codelist = new ArrayList<String>();
        if(l_code!=null) {
            for (int i = 0; i < l_code.length; i++) {
                ln_codelist.add(l_code[i]);
            }
        }
        else{
            Toast.makeText(getActivity(),"no data available on lining code",Toast.LENGTH_SHORT).show();
        }



        List<String> originalsslist = new ArrayList<String>();
        originalsslist.add("XS");
        originalsslist.add("S");
        originalsslist.add("M");
        originalsslist.add("L");
        originalsslist.add("XL");
        originalsslist.add("XXL");

        // Creating adapter for spinner
        ArrayAdapter<String> brandadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, brandlist);
        ArrayAdapter<String> seasonadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, seasonlist);
        ArrayAdapter<String> deasigneradapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, designerlist);
        ArrayAdapter<String> originalssadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, originalsslist);
        ArrayAdapter<String> fb_codeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, fb_codelist);
        ArrayAdapter<String> ln_codeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ln_codelist);
// Specify the layout to use when the list of choices appears
       brandadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       seasonadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       deasigneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       originalssadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       fb_codeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       ln_codeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        brand.setAdapter(brandadapter);
        season.setAdapter(seasonadapter);
        designer.setAdapter(deasigneradapter);
        originalss.setAdapter(originalssadapter);
        fb_code.setAdapter(fb_codeadapter);
        ln_code.setAdapter(ln_codeadapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void select(int action)
    {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("action", String.valueOf(action));

        query = builder.build().getEncodedQuery();
        try {

            jsonObject = jparser.sendJson(query, jsonLink.getADDSTYLE_URL());
            if(jsonObject!=null)
            {
            success = jsonObject.getInt("success");
            message = jsonObject.getString("message");
            if (success == 1) {
                Log.d("TAG", String.valueOf(success));

                postarray = jsonObject.getJSONArray(TAG_POST);

                if (action == 1) {
                    b_name = new String[postarray.length()];


                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);

                        b_name[i] = c.getString("b_name");

                    }

                } else if (action == 2) {
                    s_name = new String[postarray.length()];


                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);

                        s_name[i] = c.getString("s_name");

                    }

                } else if (action == 3) {
                    d_name = new String[postarray.length()];


                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);

                        d_name[i] = c.getString("d_name");

                    }

                } else if (action == 4) {
                    f_code = new String[postarray.length()];


                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);

                        f_code[i] = c.getString("f_code");
                        Log.d("f_code", f_code[i]);
                    }

                } else if (action == 5) {
                    l_code = new String[postarray.length()];


                    for (int i = 0; i < postarray.length(); i++) {
                        JSONObject c = postarray.getJSONObject(i);

                        l_code[i] = c.getString("ln_code");
                        Log.d("l_code", l_code[i]);
                    }

                }
            } else
            {
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
    public void updatedata(int action1)
    {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("action", String.valueOf(action1))
                .appendQueryParameter("date", String.valueOf(date.getText().toString()))
                .appendQueryParameter("brand", brand.getSelectedItem().toString())
                .appendQueryParameter("season", season.getSelectedItem().toString())
                .appendQueryParameter("styleno", styleno.getText().toString())
                .appendQueryParameter("size", originalss.getSelectedItem().toString())
                .appendQueryParameter("designer", designer.getSelectedItem().toString())
                .appendQueryParameter("pattern", patternno.getText().toString())
                .appendQueryParameter("comment_org", comment.getText().toString());

Log.d("date",String.valueOf(date.getText().toString()));
        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getADDSTYLE_URL());
            if(jsonObject!=null) {
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");
                if (success == 1) {
                    Log.d("TAG", String.valueOf(success));
                    Log.d("Message", message);
                } else {
                    Log.d("TAG", String.valueOf(success));
                    Log.d("Message", message);
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    public void submitfb(int action)
    {

        for(int i=0;i<fabriclist.getCount();i++)
        {

            View childView = fabriclist.getChildAt(i);
            TextView code = (TextView) childView.findViewById(R.id.code);
            TextView width = (TextView) childView.findViewById(R.id.width);
            TextView consumption = (TextView) childView.findViewById(R.id.consumpion);
            String fcode = code.getText().toString();
            String fwidth = width.getText().toString();
            String fcons= consumption.getText().toString();



            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("styleno", styleno.getText().toString())
                    .appendQueryParameter("fb_code", fcode)
                    .appendQueryParameter("fb_width",fwidth)
                    .appendQueryParameter("fb_cons", fcons);


            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getADDSTYLE_URL());
                    if(jsonObject!=null) {
                        success = jsonObject.getInt("success");
//                        message = jsonObject.getString("message");
                        if (success == 1) {
                            Log.d("TAG", String.valueOf(success));
 //                           Log.d("Message", message);
                            Log.d("fbwidth", fwidth);

                        } else {
                            Log.d("TAG", String.valueOf(success));
 //                           Log.d("Message", message);
                        }
                    }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }


        }
    }
    private void submitln(int action)
    {
        if(lininglist.getCount()!=0) {
            for (int i = 0; i < lininglist.getCount(); i++) {
                View childView1 = lininglist.getChildAt(i);
                TextView code1 = (TextView) childView1.findViewById(R.id.code);
                TextView width1 = (TextView) childView1.findViewById(R.id.width);
                TextView consumption1 = (TextView) childView1.findViewById(R.id.consumpion);
                String lcode = code1.getText().toString();
                String lwidth = width1.getText().toString();
                String lcons = consumption1.getText().toString();

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("action", String.valueOf(action))
                        .appendQueryParameter("styleno", styleno.getText().toString())
                        .appendQueryParameter("ln_code", lcode)
                        .appendQueryParameter("ln_width", lwidth)
                        .appendQueryParameter("ln_cons", lcons);

                query = builder.build().getEncodedQuery();
                try {
                    jsonObject = jparser.sendJson(query, jsonLink.getADDSTYLE_URL());
                    if (jsonObject != null) {
                        success = jsonObject.getInt("success");
                        //                   message = jsonObject.getString("message");
                        if (success == 1) {
                            Log.d("TAG", String.valueOf(success));
//                        Log.d("Message", message);
                        } else {
                            Log.d("TAG", String.valueOf(success));
                            //                       Log.d("Message", message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getFileUri(int cameracheck) {
        image_name[cameracheck] = styleno.getText().toString()+String.valueOf(cameracheck)+".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name[cameracheck]
        );

        file_uri = Uri.fromFile(file);
        imagpath[cameracheck]=file_uri.getPath();
    }
    private void makeRequest(final int k) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, jsonLink.getADDSTYLE_URL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                    Log.d("REsonse",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("action",String.valueOf(action));
                map.put("encoded_string",encoded_string[k]);
                map.put("k",String.valueOf(k));
                map.put("image",image_name[k]);
                map.put("style_no",styleno.getText().toString());
                map.put("cameracheck",String.valueOf(cameracheck));

                return map;
            }
        };
        requestQueue.add(request);
    }

    public class ParseAddStyle extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            progress=new ProgressDialog(getActivity());
            progress.setMessage("Loading");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.setProgress(0);
            progress.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(action==6)
            {
            updatedata(action);
             action=7;
                Log.d("check","one");
                if(success==1)
                {

             submitfb(action);
             action=8;
             submitln(action);
                action=9;

                    Log.d("check","two");
                    for (int i = 0; i < cameracheck; i++) {
                        makeRequest(i);
                    }
                    Log.d("check","three");
                }
        }
            else
            {
                action = 1;
                select(action);
                action = 2;
                select(action);
                action = 3;
                select(action);
                action = 4;
                select(action);
                action = 5;
                select(action);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {

            if(action<6)
            spinners();

            if(action==7||action==9)
          Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

            progress.cancel();
        }
    }

}
