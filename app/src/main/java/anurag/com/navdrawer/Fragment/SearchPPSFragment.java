package anurag.com.navdrawer.Fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.R;


public class SearchPPSFragment extends Fragment {
TextView date,brand,season,style_no,sample_size,designer,vendor,rating,comments;
ImageView image1,image2,image3,image4;
RatingBar ratingbar;

String  date1,brand1,season1,style_no1,stage1,sample_size1,designer1,vendor1,comments1,path1,path2,path3,path4;
float rating1;
Bitmap[] bitmap;
String[] imagpath1 = new String[4];
//private String url="192.168.0.110:8068/tms/";

ProgressDialog progress;

JSONObject jsonObject = new JSONObject();
JsonParser jparser = new JsonParser();

    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;

    private static int action;
    JsonLink jsonLink = new JsonLink();
    //   private static String SEARCH_RESULT_URL = "http://a0682321.ngrok.io/tms/searchresult.php";
    private static String TAG_POST = "posts";



    public SearchPPSFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_p, container, false);
        date = (TextView) view.findViewById(R.id.date);
        brand= (TextView) view.findViewById(R.id.brand);
        season= (TextView) view.findViewById(R.id.season);
        style_no= (TextView) view.findViewById(R.id.style_no);
        sample_size= (TextView) view.findViewById(R.id.pps_size);
        designer= (TextView) view.findViewById(R.id.designer);
        vendor= (TextView) view.findViewById(R.id.vendor);
//        rating= (TextView) view.findViewById(R.id.rating);
        ratingbar= (RatingBar) view.findViewById(R.id.ratingbar);
        comments= (TextView) view.findViewById(R.id.comment);

        image1=(ImageView) view.findViewById(R.id.image1);
        image2=(ImageView) view.findViewById(R.id.image2);
        image3=(ImageView) view.findViewById(R.id.image3);
        image4=(ImageView) view.findViewById(R.id.image4);

        style_no1=getArguments().getString("style_no");
        stage1=getArguments().getString("stage");

        Log.d("stylno",style_no1);
        Log.d("stage",stage1);
        action=0;
        new ParseSearchPPS().execute();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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

    private void getData()
    {
        if(action==0) {
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("action", String.valueOf(action))
                    .appendQueryParameter("style_no", style_no1)
                    .appendQueryParameter("stage", stage1);

            query = builder.build().getEncodedQuery();
            try {
                jsonObject = jparser.sendJson(query, jsonLink.getSEARCH_PPS_URL());
                if(jsonObject!=null) {
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Log.d("TAG", String.valueOf(success));

                        postarray = jsonObject.getJSONArray(TAG_POST);

                        date1        = new String();
                        brand1       = new String();
                        season1      = new String();
                        sample_size1 = new String();
                        designer1    = new String();
                        vendor1  = new String();
//                        rating1  = new String();
                        comments1     = new String();
                        path1        = new String();
                        path2        = new String();
                        path3        = new String();
                        path4        = new String();


                        for (int i = 0; i < postarray.length(); i++) {
                            JSONObject c = postarray.getJSONObject(i);

                            date1  = c.getString("tc_date");
                            brand1= c.getString("brand");
                            season1= c.getString("season");
                            sample_size1= c.getString("size");
                            designer1= c.getString("designer");
                            vendor1= c.getString("vendor");
                            rating1= Float.parseFloat(c.getString("rating"));
                            comments1= c.getString("comments");
                            path1= c.getString("path1");
                            path2= c.getString("path2");
                            path3= c.getString("path3");
                            path4= c.getString("path4");

                            Log.d("date1       ",date1       );
                            Log.d("brand1      ",brand1      );
                            Log.d("season1     ",season1     );
                            Log.d("sample_size1",sample_size1);
                            Log.d("deisgner1   ",designer1   );
                            Log.d("pattern_no1 ",String.valueOf(rating1 ));
                            Log.d("comment1    ",comments1    );
                        }

                    } else
                        Log.d("TAG", String.valueOf(success));
                    Log.d("TAG", message);
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
        sample_size.setText(sample_size1);
        designer.setText(designer1);
        vendor.setText(vendor1);
//      rating.setText(rating1);
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingbar.setRating(rating1);
            }
        });
        ratingbar.setRating(rating1);

        comments.setText(comments1);
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
    private class ParseSearchPPS extends AsyncTask<Void,Void,Void>
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

            getData();
            getImage();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
        setData();
        setImage();
            progress.cancel();
            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        }
    }
}
