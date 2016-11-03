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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.R;


public class QCPPSFragment extends Fragment {

    TextView brand,season,style_no,sample_size,designer,vendor;
    Button click,submit,cancel,delete;
    ImageView image1,image2,image3,image4;
    EditText comment,date;

    ProgressDialog progress;

    private String[] encoded_string = new String[4];
    private String[] image_name = new String[4];
    private Bitmap[] bitmap = new Bitmap[4];
    private File file;
    private Uri file_uri;
    String[] imagpath = new String[4];
    private int cameracheck=0;


    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink=new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;
    private int action;
    String brand1,season1,style_no1,sample_size1,designer1,vendor1,stage1;

  //  private static String QCPPS_URL = "http://a0682321.ngrok.io/tms/qcpps.php";

    private static String TAG_POST = "posts";

    public QCPPSFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_qcpps, container, false);
//         date1 = getArguments().getString("date");
//        String job_order=getArguments().getString("job_order");
         style_no1=getArguments().getString("style_no");
//        String style=getArguments().getString("style");
         stage1=getArguments().getString("stage");

//        String size=getArguments().getString("size");
//        String name=getArguments().getString("name");

        date = (EditText) view.findViewById(R.id.date);
        comment=(EditText) view.findViewById(R.id.comment);

        brand= (TextView) view.findViewById(R.id.brand);
        season=(TextView) view.findViewById(R.id.season);
        style_no=(TextView) view.findViewById(R.id.style_no);
        sample_size=(TextView) view.findViewById(R.id.pps_size);
        designer=(TextView) view.findViewById(R.id.designer);
        vendor=(TextView) view.findViewById(R.id.vendor);

        click= (Button)view.findViewById(R.id.upload);
        submit=(Button)view.findViewById(R.id.submit);
        cancel=(Button)view.findViewById(R.id.cancel);
        delete=(Button)view.findViewById(R.id.delete);

        image1=(ImageView)view.findViewById(R.id.image1);
        image2=(ImageView)view.findViewById(R.id.image2);
        image3=(ImageView)view.findViewById(R.id.image3);
        image4=(ImageView)view.findViewById(R.id.image4);

        addstyledata();
        Log.d("stage",stage1);
        action=0;
        new ParseQCPPS().execute();

        return view;

    }

    @Override
    public void onStart()
    {
        super.onStart();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()==true) {
                    action = 2;

                    new ParseQCPPS().execute();
                }


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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImages();
            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameracheck<4) {
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getFileUri(cameracheck);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                    startActivityForResult(i, 10);
                }
                else
                {
                    Toast.makeText(getActivity(),"Only 4 images can be send",Toast.LENGTH_SHORT).show();}
            }
        });

            image1.setOnClickListener(new View.OnClickListener() {
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

            image2.setOnClickListener(new View.OnClickListener() {
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


            image3.setOnClickListener(new View.OnClickListener() {
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


            image4.setOnClickListener(new View.OnClickListener() {
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

        else if((comment.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Comment cant be empty",Toast.LENGTH_SHORT).show();
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
                bitmap[cameracheck].compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] array = stream.toByteArray();
                encoded_string[cameracheck] = Base64.encodeToString(array, 0);
                Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap[cameracheck],64, 64);
                if (cameracheck == 0) {

                    image1.setImageBitmap(resized);
                    cameracheck++;
                } else if (cameracheck == 1) {
                    image2.setImageBitmap(resized);
                    cameracheck++;
                } else if (cameracheck == 2) {
                    image3.setImageBitmap(resized);
                    cameracheck++;
                } else if (cameracheck == 3) {
                    image4.setImageBitmap(resized);
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
        image1.setImageBitmap(null);
        image2.setImageBitmap(null);
        image3.setImageBitmap(null);
        image4.setImageBitmap(null);
    }

    private void getdata(int action)
    {
        if(action==0)
        {                Log.d("ACTION", String.valueOf(action));
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getQCPPS_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        JSONObject c = postarray.getJSONObject(0);

                        brand1 = c.getString("brand");
                        season1 = c.getString("season");
                        sample_size1 = c.getString("size");
                        designer1 = c.getString("designer");

                        Log.d("brand", brand1);
                        Log.d("season", season1);
                        Log.d("sample", sample_size1);
                        Log.d("designer", designer1);

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
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getQCPPS_URL());

                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");
                if (success == 1)
                {
                    Log.d("TAG", String.valueOf(success));

                    postarray = jsonObject.getJSONArray(TAG_POST);

                    JSONObject c = postarray.getJSONObject(0);

                    vendor1 = c.getString("vendor");
                    Log.d("VENDOR",vendor1);

                }
                else
                    Log.d("TAG", String.valueOf(success));
                Log.d("TAG", message);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public String addstyledata()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        date.setText(formattedDate);
        return formattedDate;

    }
    private void setdata()
    {

        brand.setText(brand1);
        season.setText(season1);
        style_no.setText(style_no1);
        sample_size.setText(sample_size1);
        designer.setText(designer1);
        vendor.setText(vendor1);
    }


    private void submitdata(int action)
    {

        Uri.Builder builder = new Uri.Builder()

                .appendQueryParameter("action", String.valueOf(action))
                .appendQueryParameter("date", date.getText().toString())
                .appendQueryParameter("brand",brand1)
                .appendQueryParameter("season", season1)
                .appendQueryParameter("style_no",style_no1 )
                .appendQueryParameter("size",sample_size1 )
                .appendQueryParameter("designer", designer1)
                .appendQueryParameter("vendor",vendor1 )
                .appendQueryParameter("stage",stage1)
                .appendQueryParameter("comments",comment.getText().toString())
                .appendQueryParameter("qc_status",String.valueOf(2));
                Log.d("DATE",date.getText().toString());
        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getQCPPS_URL());

            success=jsonObject.getInt("success");
            message=jsonObject.getString("message");

            if(success==1) {
                Log.d("TAG", String.valueOf(success));
                Log.d("Message", message);
            }
            else
            {
                String error=jsonObject.getString("error");
                Log.d("TAG", String.valueOf(success));
                Log.d("Message", message);
                Log.d("Error", error);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void getFileUri(int cameracheck) {
        image_name[cameracheck] = style_no.getText().toString()+String.valueOf(cameracheck)+".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name[cameracheck]
        );

        file_uri = Uri.fromFile(file);
        imagpath[cameracheck]=file_uri.getPath();
    }

    private void makeRequest(final int k) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, jsonLink.getQCPPS_URL(),
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
                map.put("stage",stage1);
                map.put("encoded_string",encoded_string[k]);
                map.put("k",String.valueOf(k));
                map.put("image",image_name[k]);
                map.put("style_no",style_no.getText().toString());
                map.put("cameracheck",String.valueOf(cameracheck));

                return map;
            }
        };
        requestQueue.add(request);
    }
private class ParseQCPPS extends AsyncTask<Void,Void,Void>
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
            getdata(action);
            action=1;
            getdata(action);

        }
        else if(action==2)
        {
            submitdata(action);
            action=3;
            for(int i=0;i<cameracheck;i++)
            {
                makeRequest(i);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        if(action==1)
        { setdata();}

        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        progress.cancel();
    }
}
}
