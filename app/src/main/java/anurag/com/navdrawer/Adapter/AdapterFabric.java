package anurag.com.navdrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anurag.com.navdrawer.Pojo.AddStyle;
import anurag.com.navdrawer.Pojo.AdminPojo;
import anurag.com.navdrawer.R;

/**
 * Created by AnuragTrehan on 4/18/2016.
 */
public class AdapterFabric extends BaseAdapter {
    Context context;
    List<AddStyle> addStyle;

    public AdapterFabric(List<AddStyle> addStyle, Context context) {
        this.addStyle = addStyle;
        this.context = context;
    }

    @Override
    public int getCount() {
        return addStyle.size();
    }

    @Override
    public Object getItem(int position) {
        return addStyle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return addStyle.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.fabric, null);
        }
        TextView code = (TextView) convertView.findViewById(R.id.code);
        TextView width = (TextView) convertView.findViewById(R.id.width);
        TextView consumption = (TextView) convertView.findViewById(R.id.consumpion);

        AddStyle addStyle1 = addStyle.get(position);
        // setting the image resource and title

        code.setText(addStyle1.getF_code());
        width.setText(addStyle1.getF_width());
        consumption.setText(addStyle1.getF_consumption());
        return convertView;
    }
}
