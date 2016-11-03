package anurag.com.navdrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anurag.com.navdrawer.Pojo.AdminPojo;
import anurag.com.navdrawer.R;

/**
 * Created by AnuragTrehan on 4/12/2016.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<AdminPojo> adminPojo;
    int checkid;
    public CustomAdapter(List<AdminPojo> adminPojo, Context context,int checkid) {
        this.adminPojo = adminPojo;
        this.context = context;
        this.checkid = checkid;
    }

    @Override
    public int getCount() {

        return adminPojo.size();
    }

    @Override
    public Object getItem(int position) {
        return adminPojo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return adminPojo.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (checkid == 1) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.single, null);
            }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.single);

            AdminPojo adminPojo1 = adminPojo.get(position);
            // setting the image resource and title

            txtTitle.setText(adminPojo1.getBrand());
            return convertView;
        }
     else  if (checkid == 2) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.single, null);
            }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.single);

            AdminPojo adminPojo1 = adminPojo.get(position);
            // setting the image resource and title

            txtTitle.setText(adminPojo1.getSeason());
            return convertView;
        }
     else if (checkid == 3) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.doubles, null);
            }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.double1);
            TextView txtTitle2 = (TextView) convertView.findViewById(R.id.double2);

            AdminPojo adminPojo1 = adminPojo.get(position);
            // setting the image resource and title

            txtTitle.setText(adminPojo1.getDesigner_name());
            txtTitle2.setText(adminPojo1.getBrand());
            return convertView;
        }
        if(checkid==4) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.doubles, null);
            }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.double1);
            TextView txtTitle2 = (TextView) convertView.findViewById(R.id.double2);

            AdminPojo adminPojo1 = adminPojo.get(position);
            // setting the image resource and title

            txtTitle.setText(adminPojo1.getFabric_code());
            txtTitle2.setText(adminPojo1.getFabric_detail());
            return convertView;
        }
        else  {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.single, null);
            }
            TextView txtTitle = (TextView) convertView.findViewById(R.id.single);


            AdminPojo adminPojo1 = adminPojo.get(position);
            // setting the image resource and title

            txtTitle.setText(adminPojo1.getLn_code());

            return convertView;
        }
    }
}
