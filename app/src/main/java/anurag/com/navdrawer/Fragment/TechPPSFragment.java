package anurag.com.navdrawer.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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


public class TechPPSFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView date,brand,season,style_no,sample_size,designer,vendor;

    EditText comment;
    Button click,submit,cancel,delete;
    ImageView image1,image2,image3,image4,excel1,excel2,excel3,excel4;
    RatingBar ratingBar;
    ProgressDialog progress;

    private String[] encoded_string = new String[4];
    private String[] image_name = new String[4];
    private Bitmap[] bitmap = new Bitmap[4];
    private File file;
    private Uri file_uri;
    String[] imagpath = new String[4];
    String[] imagpath1 = new String[4];
    private int cameracheck=0;


    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink = new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    int action=-1;
    String message;
    private Bitmap[] bitmap1;
    int[] rating1={1,2,3,4,5};

 //   private static String TECH_PPS_URL = "http://a0682321.ngrok.io/tms/techpps.php";
    private static String TAG_POST = "posts";

    String brand1,season1,style_no1,sample_size1,designer1,vendor1,stage1,path1,path2,path3,path4,comments1;

    public TechPPSFragment()
    { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_tech_p, container, false);

        date=(TextView) view.findViewById(R.id.date);
        brand=(TextView) view.findViewById(R.id.brand);
        season=(TextView) view.findViewById(R.id.season);
        style_no=(TextView) view.findViewById(R.id.style_no);
        sample_size=(TextView) view.findViewById(R.id.pps_size);
        designer=(TextView) view.findViewById(R.id.designer);
        vendor=(TextView) view.findViewById(R.id.vendor);

//        rating= (Spinner) view.findViewById(R.id.rating);
        ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        comment=(EditText)view.findViewById(R.id.comment);

        click=(Button)view.findViewById(R.id.click);
        submit=(Button)view.findViewById(R.id.submit);
        cancel=(Button) view.findViewById(R.id.cancel);
        delete=(Button) view.findViewById(R.id.delete);

        image1=(ImageView) view.findViewById(R.id.image1);
        image2=(ImageView) view.findViewById(R.id.image2);
        image3=(ImageView) view.findViewById(R.id.image3);
        image4=(ImageView) view.findViewById(R.id.image4);
        excel1=(ImageView)view.findViewById(R.id.excel1);
        excel2=(ImageView)view.findViewById(R.id.excel2);
        excel3=(ImageView)view.findViewById(R.id.excel3);
        excel4=(ImageView)view.findViewById(R.id.excel4);


//        rating.setOnItemSelectedListener(this);
//
//        List<String> ratinglist = new ArrayList<String>();
//        for(int i=0;i<rating1.length;i++)
//        {ratinglist.add(String.valueOf(rating1[i]));}
//
//        ArrayAdapter<String> stageadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ratinglist);
//
//        rating.setAdapter(stageadapter);

 //       date1 = getArguments().getString("date");
//        String job_order=getArguments().getString("job_order");
        style_no1=getArguments().getString("style_no");
//        String style=getArguments().getString("style");
        stage1=getArguments().getString("stage");
        Log.d("style",style_no1);
        Log.d("stage",stage1) ;

//        String size=getArguments().getString("size");
//        String name=getArguments().getString("name");
        addstyledata();

        action=0;
        new ParseTechPPS().execute();
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
                    action = 1;

                    new ParseTechPPS().execute();
                }
                else{
                    Toast.makeText(getActivity(),"Some fields are empty",Toast.LENGTH_SHORT).show();
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
                    if(imagpath!=null) {
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


            excel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath1[0]!=null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imagpath1[0]), "image/*");
                        startActivity(intent);
                    }
                }
            });


            excel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath1[1]!=null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse("file://" + imagpath1[1]), "image/*");
                        startActivity(intent);
                    }
                }
            });


            excel3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagpath1[2]!=null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + imagpath1[2]), "image/*");
                    startActivity(intent);
                }
                }
            });

        excel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagpath1[3]!=null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + imagpath1[3]), "image/*");
                    startActivity(intent);
                }
            }
        });

    }
    private boolean validateData(){

        if((comment.getText().toString()).matches("")){
            Toast.makeText(getActivity(),"Date cant be empty",Toast.LENGTH_SHORT).show();
            return false;
        }


        else{
            return true;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            if (cameracheck < 4)
            {

                bitmap[cameracheck] = BitmapFactory.decodeFile(file_uri.getPath());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap[cameracheck].compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] array = stream.toByteArray();
                encoded_string[cameracheck] = Base64.encodeToString(array, 0);
                Log.d("encode",encoded_string[cameracheck]);
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
    public void addstyledata()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        date.setText(formattedDate);


    }
    private void getdata(int action) {
        if (action == 0) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1)
                    .appendQueryParameter("stage", stage1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getTECH_PPS_URL());
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
                        vendor1 = c.getString("vendor");
                        comments1 = c.getString("comments");
                        path1 = c.getString("path1");
                        path2 = c.getString("path2");
                        path3 = c.getString("path3");
                        path4 = c.getString("path4");
                        Log.d("path", path1);
                        Log.d("path", path2);
                        Log.d("path", path3);
                        Log.d("path", path4);
                        Log.d("path", brand1);
                        Log.d("path", season1);
                        Log.d("path", sample_size1);
                        Log.d("path", designer1);
                        Log.d("path", vendor1);
                        Log.d("path", comments1);


                    } else
                        Log.d("TAG", String.valueOf(success));
                    Log.d("TAG", message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void submitdata(int action)
    {
        if(action==1)
        {
            Uri.Builder builder = new Uri.Builder()

                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("date", date.getText().toString())
                    .appendQueryParameter("rating",String.valueOf(ratingBar.getRating() ))
                    .appendQueryParameter("t_comments",comment.getText().toString())
                    .appendQueryParameter("stage",stage1)
                    .appendQueryParameter("qc_status",String.valueOf(3))
                    .appendQueryParameter("style_no",style_no1 );



            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query,jsonLink.getTECH_PPS_URL());

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

    }
    private void getimage() {
        bitmap1 = new Bitmap[4];
        InputStream in = null;

        for(int i=0;i<4;i++) {
            String URL= new String();
            if(i==0 && path1.length()!=0)
            {
                URL = jsonLink.getURL()+"/tms/" + path1;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap1[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap1[i],i);
            }
            else if(i==1 && path2.length()!=0)
            { URL = jsonLink.getURL()+"/tms/" + path2;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap1[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap1[i],i);
            }
            else if(i==2 && path3.length()!=0)
            { URL = jsonLink.getURL()+"/tms/"+ path3;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap1[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap1[i],i);
            }
            else if(i==3 && path3.length()!=0)
            { URL = jsonLink.getURL()+"/tms/" + path4;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap1[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap1[i],i);
            }

//            Log.d("Path",URL);
//            Log.d("Path2",URL);


        }
    }
    private void saveimage(Bitmap bitmap,int i)
    {
        String extr = Environment.getExternalStorageDirectory().toString();
        File mFolder = new File(extr + "/SMSImages");

        imagpath1[i]=mFolder.getAbsolutePath()+File.separator+style_no1+String.valueOf(i)+".jpg";

        File f = new File(mFolder.getAbsolutePath(),style_no1+String.valueOf(i)+".jpg");
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG,70, fos);

            fos.flush();
            fos.close();

        }catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
    private void setimage()
    {
        for(int i=0;i<4;i++)
        {
            Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap1[i],64, 64);
            if(bitmap1[i]!=null)
            {
                if(i==0)
                    excel1.setImageBitmap(resized);
                if(i==1)
                    excel2.setImageBitmap(resized);
                if(i==2)
                    excel3.setImageBitmap(resized);
                if(i==3)
                    excel4.setImageBitmap(resized);
            }
        }
    }
    private void setdata()
    {

        brand.setText(brand1);
        season.setText(season1);
        style_no.setText(style_no1);
        sample_size.setText(sample_size1);
        designer.setText(designer1);
        vendor.setText(vendor1);
        comment.setText(comments1);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        StringRequest request = new StringRequest(Request.Method.POST, jsonLink.getTECH_PPS_URL(),
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
                map.put("stage",String.valueOf(stage1));
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
    private class ParseTechPPS extends AsyncTask<Void,Void,Void>
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
                getimage();
             }
            else if(action==1)
            {
                submitdata(action);
                action=2;
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
            if(brand1!=null) {
                if (action == 0)
                {   setdata();
                    setimage();
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                progress.cancel();
            }
            else {

                Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
