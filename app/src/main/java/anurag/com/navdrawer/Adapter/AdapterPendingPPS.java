package anurag.com.navdrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anurag.com.navdrawer.Fragment.PendingPPSFragment;
import anurag.com.navdrawer.Pojo.PedningPPSPojo;
import anurag.com.navdrawer.Pojo.ViewPendingReqPojo;
import anurag.com.navdrawer.R;

/**
 * Created by AnuragTrehan on 5/2/2016.
 */
public class AdapterPendingPPS extends BaseAdapter {

    Context context;
    List<PedningPPSPojo> pendingpps;

    public AdapterPendingPPS(Context context, List<PedningPPSPojo> pendingpps) {
        this.context = context;
        this.pendingpps = pendingpps;
    }

    @Override
    public int getCount() {
        return pendingpps.size();
    }

    @Override
    public Object getItem(int position) {
        return pendingpps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pendingpps.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.pendingpps, null);
        }
        TextView txtTitle1 = (TextView) convertView.findViewById(R.id.date);
        TextView txtTitle2 = (TextView) convertView.findViewById(R.id.job_order);
        TextView txtTitle3 = (TextView) convertView.findViewById(R.id.style_no);
        TextView txtTitle4 = (TextView) convertView.findViewById(R.id.stage);
        TextView txtTitle5 = (TextView) convertView.findViewById(R.id.size);
        TextView txtTitle6 = (TextView) convertView.findViewById(R.id.chart);

        PedningPPSPojo pendingpps1 =  pendingpps.get(position);
        // setting the image resource and title

        txtTitle1.setText(pendingpps1.getDate());
        txtTitle2.setText(pendingpps1.getJob_order_no());
        txtTitle3.setText(pendingpps1.getStyle_no());
        txtTitle4.setText(String.valueOf(pendingpps1.getStage()));
        txtTitle5.setText(pendingpps1.getSize());
        txtTitle6.setText(pendingpps1.getChart());

        return convertView;
    }
}
