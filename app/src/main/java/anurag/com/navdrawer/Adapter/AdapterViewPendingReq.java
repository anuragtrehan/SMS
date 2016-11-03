package anurag.com.navdrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anurag.com.navdrawer.Pojo.ViewPendingReqPojo;
import anurag.com.navdrawer.R;

/**
 * Created by AnuragTrehan on 5/1/2016.
 */
public class AdapterViewPendingReq extends BaseAdapter
{
    Context context;
    List<ViewPendingReqPojo> vpr;

    public AdapterViewPendingReq(Context context, List<ViewPendingReqPojo> vpr) {
        this.context = context;
        this.vpr = vpr;
    }

    @Override
    public int getCount() {
        return vpr.size();
    }

    @Override
    public Object getItem(int position) {
        return vpr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vpr.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.view_pending_req, null);
        }
        TextView txtTitle1 = (TextView) convertView.findViewById(R.id.date);
        TextView txtTitle2 = (TextView) convertView.findViewById(R.id.job_order);
        TextView txtTitle3 = (TextView) convertView.findViewById(R.id.style_no);
        TextView txtTitle4 = (TextView) convertView.findViewById(R.id.stage);
        TextView txtTitle5 = (TextView) convertView.findViewById(R.id.size);
        TextView txtTitle6 = (TextView) convertView.findViewById(R.id.name);

       ViewPendingReqPojo vpr1 = vpr.get(position);
        // setting the image resource and title

        txtTitle1.setText(vpr1.getDate());
        txtTitle2.setText(vpr1.getJoborder());
        txtTitle3.setText(vpr1.getStyleno());
        txtTitle4.setText(String.valueOf(vpr1.getStage()));
        txtTitle5.setText(vpr1.getSize());
        txtTitle6.setText(vpr1.getQualitychecker());

        return convertView;
    }
}
