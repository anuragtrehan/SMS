package anurag.com.navdrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anurag.com.navdrawer.Pojo.PedningPPSPojo;
import anurag.com.navdrawer.Pojo.SearchPojo;
import anurag.com.navdrawer.R;

/**
 * Created by AnuragTrehan on 5/5/2016.
 */
public class AdapterSearchList extends BaseAdapter {

    Context context;
    List<SearchPojo> searchPojo;

    public AdapterSearchList(Context context, List<SearchPojo> searchPojo) {
        this.context = context;
        this.searchPojo = searchPojo;
    }

    @Override
    public int getCount() {
        return searchPojo.size();
    }

    @Override
    public Object getItem(int position) {
        return searchPojo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return searchPojo.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.searchlist, null);
        }
        TextView txtTitle1 = (TextView) convertView.findViewById(R.id.date);
        TextView txtTitle2 = (TextView) convertView.findViewById(R.id.job_order);
        TextView txtTitle3 = (TextView) convertView.findViewById(R.id.style_no);
        TextView txtTitle4 = (TextView) convertView.findViewById(R.id.stage);
        TextView txtTitle5 = (TextView) convertView.findViewById(R.id.vendor);
        TextView txtTitle6 = (TextView) convertView.findViewById(R.id.chart);

        SearchPojo searchPojo1 = searchPojo.get(position);
        // setting the image resource and title

        txtTitle1.setText(searchPojo1.getDate());
        txtTitle2.setText(searchPojo1.getJob_order_no());
        txtTitle3.setText(searchPojo1.getStyle_no());
        txtTitle4.setText(searchPojo1.getStage());
        txtTitle5.setText(searchPojo1.getVendor());
        txtTitle6.setText(searchPojo1.getChart());

        return convertView;
    }
}
