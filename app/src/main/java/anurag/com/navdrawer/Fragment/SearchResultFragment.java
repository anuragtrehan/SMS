package anurag.com.navdrawer.Fragment;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import anurag.com.navdrawer.Adapter.AdapterFabric;
import anurag.com.navdrawer.Adapter.AdapterLining;
import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.Pojo.AddStyle;
import anurag.com.navdrawer.R;

public class SearchResultFragment extends Fragment
{
    TextView date,date_job,brand,season,style_no,sample_size,deisgner,pattern_no,job_order_no,vendor,prod_quantity,comment;
    ListView fabric_list,line_list;
    CheckBox s,m,l,xl;
    Button chart;
    ImageView image1,image2,image3,image4;
    String date1 ,date_job1, brand1, season1, style_no1, sample_size1, deisgner1, pattern_no1, job_order_no1, vendor1,prod_quantity1,comment1,chart1;
    String path1,path2,path3,path4;

    int s1,m1,l1,xl1;
    String[] fb_code,fb_width,fb_cons,ln_code,ln_width,ln_cons;

    AdapterFabric fabricadapter;
    AdapterLining liningadapter;
    private List<AddStyle> addStyle1= new ArrayList<AddStyle>();
    private List<AddStyle> addStyle2= new ArrayList<AddStyle>();

    Bitmap[] bitmap;
//    private String url="http://192.168.0.110:8068/tms/";

    ProgressDialog progress;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    String[] imagpath1 = new String[4];
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;

    private static int action;
    JsonLink jsonLink = new JsonLink();
 //   private static String SEARCH_RESULT_URL = "http://a0682321.ngrok.io/tms/searchresult.php";
    private static String TAG_POST = "posts";



    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        date = (TextView) view.findViewById(R.id.date);
        brand = (TextView) view.findViewById(R.id.brand);
        season = (TextView) view.findViewById(R.id.season);
        style_no = (TextView) view.findViewById(R.id.style);
        sample_size = (TextView) view.findViewById(R.id.size);
        deisgner = (TextView) view.findViewById(R.id.designer);
        pattern_no = (TextView) view.findViewById(R.id.pattern);
        date_job = (TextView) view.findViewById(R.id.date_job);
        job_order_no = (TextView) view.findViewById(R.id.order_no);
        vendor = (TextView) view.findViewById(R.id.vendor);
        prod_quantity = (TextView) view.findViewById(R.id.production);
//        fit_size = (TextView) view.findViewById(R.id.fit_size);
        comment = (TextView) view.findViewById(R.id.comment);
        chart = (Button) view.findViewById(R.id.chart);

        fabric_list=(ListView)view.findViewById(R.id.fabric);
        line_list=(ListView)view.findViewById(R.id.lining);

        s= (CheckBox) view.findViewById(R.id.s);
        m= (CheckBox) view.findViewById(R.id.m);
        l= (CheckBox) view.findViewById(R.id.l);
        xl= (CheckBox) view.findViewById(R.id.xl);

        image1=(ImageView) view.findViewById(R.id.image1);
        image2=(ImageView) view.findViewById(R.id.image2);
        image3=(ImageView) view.findViewById(R.id.image3);
        image4=(ImageView) view.findViewById(R.id.image4);

        style_no1=getArguments().getString("style_no");
        Log.d("STYLENO",style_no1);
        action=0;
        new ParseSearchResult().execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();




//        chart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Uri uri = Uri.parse("/sdcard/Download/"+chart1.substring(6));
//                intent.setDataAndType(uri, "application/*");
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//
//                try {
//                    startActivity(intent);
//                }
//                catch (ActivityNotFoundException e) {
//                    Toast.makeText(getActivity(),
//                            "No Application Available to View PDF",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action=4;
                new ParseSearchResult().execute();

            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagpath1!=null) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://" + imagpath1[0]), "image/*");
                    startActivity(intent);
                }
            }
        });


        image2.setOnClickListener(new View.OnClickListener() {
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


        image3.setOnClickListener(new View.OnClickListener() {
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


        image4.setOnClickListener(new View.OnClickListener() {
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
    private void getData(int action)
    {
        if(action==0) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_RESULT_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
 //                       Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        date1        = new String();
                        brand1       = new String();
                        season1      = new String();
                        sample_size1 = new String();
                        deisgner1    = new String();
                        pattern_no1  = new String();
                        comment1     = new String();
                        path1        = new String();
                        path2        = new String();
                        path3        = new String();
                        path4        = new String();


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            date1  = c.getString("date");
                            brand1= c.getString("brand");
                            season1= c.getString("season");
                            sample_size1= c.getString("size");
                            deisgner1= c.getString("designer");
                            pattern_no1= c.getString("pattern");
                            comment1= c.getString("comment_org");
                            path1= c.getString("path1");
                            path2= c.getString("path2");
                            path3= c.getString("path3");
                            path4= c.getString("path4");

//                            Log.d("date1       ",date1       );
//                            Log.d("brand1      ",brand1      );
//                            Log.d("season1     ",season1     );
//                            Log.d("sample_size1",sample_size1);
//                            Log.d("deisgner1   ",deisgner1   );
//                            Log.d("pattern_no1 ",pattern_no1 );
//                            Log.d("comment1    ",comment1    );
                        }

                    } else{
                        Log.d("TAG", String.valueOf(success));
                    Log.d("TAG", message);}
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(action==1) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_RESULT_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
//                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        date_job1 = new String();
                        job_order_no1 = new String();
                        vendor1 = new String();
                        prod_quantity1 = new String();
                        chart1 = new String();


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            date_job1       = c.getString("date");
                            job_order_no1   = c.getString("job_order_no");
                            vendor1         = c.getString("vendor");
                            prod_quantity1  = c.getString("production_quantity");
                            s1              = c.getInt("s");
                            m1              = c.getInt("m");
                            l1              = c.getInt("l");
                            xl1             = c.getInt("xl");
                            chart1          = c.getString("measurement_chart");
                            Log.i("chart",chart1);
                            Log.i("job_order_no1",job_order_no1);
//                            Log.d("vendor1       ",vendor1       );
//                            Log.d("prod_quantity1",prod_quantity1);
//                            Log.d("s1            ",String.valueOf(s1));
//                            Log.d("m1            ",String.valueOf(m1));
//                            Log.d("l1            ",String.valueOf(l1));
//                            Log.d("xl1            ",String.valueOf(xl1));

                        }

                    } else
                    {
                        Log.d("TAG", String.valueOf(success));
                        Log.d("TAG", message);
                }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(action==2) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_RESULT_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
//                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        fb_code = new String[postarray.length()];
                        fb_width = new String[postarray.length()];
                        fb_cons = new String[postarray.length()];




                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                           fb_code[i]= c.getString("fb_code");
                           fb_width[i]= c.getString("fb_width");
                           fb_cons[i]= c.getString("fb_cons");

//                            Log.d("code",fb_code[i]);
//                            Log.d("width",fb_width[i]);
//                            Log.d("stage",fb_cons[i]);

                        }

                    } else
                    {
                        Log.d("TAG", String.valueOf(success));
                        Log.d("TAG", message);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(action==3) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_RESULT_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        ln_code = new String[postarray.length()];
                        ln_width = new String[postarray.length()];
                        ln_cons = new String[postarray.length()];




                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            ln_code[i]= c.getString("ln_code");
                            ln_width[i]= c.getString("ln_width");
                            ln_cons[i]= c.getString("ln_cons");

//                            Log.d("code",ln_code[i]);
//                            Log.d("width",ln_width[i]);
//                            Log.d("stage",ln_cons[i]);

                        }

                    } else{
                        Log.d("TAG", String.valueOf(success));
                        Log.d("TAG", message);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setData()
    {
        date.setText(date1);
        brand.setText(brand1);
        season.setText(season1);
        style_no.setText(style_no1);
        sample_size.setText(sample_size1);
        deisgner.setText(deisgner1);
        pattern_no.setText(pattern_no1);
        comment.setText(comment1);

        date_job.setText(date_job1);
        job_order_no.setText(job_order_no1);
        vendor.setText(vendor1);
        prod_quantity.setText(prod_quantity1);
//        if(chart1!=null)
//        chart.setText(chart1.substring(6));
//        else
//        chart.setText("No chart available");

        if(s1==1)
            s.setChecked(true);
        if(m1==1)
            m.setChecked(true);
        if(l1==1)
            l.setChecked(true);
        if(xl1==1)
            xl.setChecked(true);

        addStyle1 = new ArrayList<AddStyle>();
        for (int i = 0; i <fb_code.length ; i++) {

            AddStyle items = new AddStyle(fb_code[i],fb_width[i],fb_cons[i],1);


            addStyle1.add(items);
        }

        fabricadapter = new AdapterFabric(addStyle1,getActivity());
        fabric_list.setAdapter(fabricadapter);
        if(ln_code!=null) {
            addStyle2 = new ArrayList<AddStyle>();
            for (int i = 0; i < ln_code.length; i++) {

                AddStyle items = new AddStyle(ln_code[i], ln_width[i], ln_cons[i], 2);


                addStyle2.add(items);
            }

            liningadapter = new AdapterLining(addStyle2, getActivity());
            line_list.setAdapter(liningadapter);
        }
        else
        {
            addStyle2 = new ArrayList<AddStyle>();


                AddStyle items = new AddStyle("N/A", "N/A", "N/A", 2);


                addStyle2.add(items);


            liningadapter = new AdapterLining(addStyle2, getActivity());
            line_list.setAdapter(liningadapter);
        }

    }

    private void getImage() {
        bitmap = new Bitmap[4];
        InputStream in = null;

        for(int i=0;i<4;i++) {
            String URL= new String();
            if(i==0 && path1.length()!=0)
            {
                URL = jsonLink.getURL()+"/tms/" + path1;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap[i],i);
            }
            else if(i==1 && path2.length()!=0)
            { URL = jsonLink.getURL()+"/tms/" + path2;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap[i],i);
            }
            else if(i==2 && path3.length()!=0)
            { URL = jsonLink.getURL()+"/tms/"+ path3;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap[i],i);
            }
            else if(i==3 && path3.length()!=0)
            { URL = jsonLink.getURL()+"/tms/" + path4;
                try {
                    in = jparser.OpenHttpConnection(URL);
                    bitmap[i] = BitmapFactory.decodeStream(in);
                    in.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                saveimage(bitmap[i],i);
            }

//            Log.d("Path",URL);
//            Log.d("Path2",URL);


        }

    }

    private void getChart()
    {
        if(chart1!=null||chart1.length()!=0) {

            String URL=jsonLink.getURL()+"/tms/"+chart1;
            Log.d("URL",URL);
            try
            {

                String filename=chart1.substring(6);

                File file = new File("/sdcard/Download",filename);
                if(file.createNewFile())
                {
                    file.createNewFile();
                }
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = jparser.OpenHttpConnection(URL);

                byte[] buffer = new byte[1024];
                int bufferLength = 0;
                while ( (bufferLength = inputStream.read(buffer)) > 0 )
                {
                    fileOutput.write(buffer, 0, bufferLength);


                }
                fileOutput.close();

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {

                e.printStackTrace();
            }
        }

    }
    private void setImage()
    {
        for(int i=0;i<4;i++)
        {

            if(bitmap[i]!=null)
            {
                Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap[i],100,100);
                if(i==0)
                    image1.setImageBitmap(resized);
                else if(i==1)
                    image2.setImageBitmap(resized);
               else if(i==2)
                    image3.setImageBitmap(resized);
               else if(i==3)
                    image4.setImageBitmap(resized);
            }
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
    private class ParseSearchResult extends AsyncTask<Void,Void,Void>
    {


        @Override
        protected void onPreExecute()
        {
             progress=new ProgressDialog(getActivity());
            progress.setMessage("Loading");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            if(action==0)
            {
                getData(action);
                action=1;
                getData(action);
                action=2;
                getData(action);
                action=3;
                getData(action);
                getImage();

//                getChart();
            }
            if(action==4){
                getChart();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {

            setData();
            setImage();
            if(action==4){
               Toast.makeText(getActivity(),"Chart Saved in Download Folder",Toast.LENGTH_SHORT).show();
            }
            progress.cancel();
        }



    }
}
