package anurag.com.navdrawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anurag.com.navdrawer.Pojo.AdminUser;
import anurag.com.navdrawer.R;

/**
 * Created by AnuragTrehan on 4/13/2016.
 */
public class CustomAdapterUser extends BaseAdapter {

    Context context;
    List<AdminUser> adminUser;


    public CustomAdapterUser(List<AdminUser> adminUser, Context context) {
        this.adminUser = adminUser;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return adminUser.size();
    }

    @Override
    public Object getItem(int position)
    {

        return adminUser.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return adminUser.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.admin_user, null);
            }
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView username = (TextView) convertView.findViewById(R.id.username);
            TextView password = (TextView) convertView.findViewById(R.id.password);
            TextView contact = (TextView) convertView.findViewById(R.id.contact);
            TextView email = (TextView) convertView.findViewById(R.id.email);

            AdminUser adminUser1= adminUser.get(position);

            name.setText(adminUser1.getName());
            username.setText(adminUser1.getUsername());
            password.setText(adminUser1.getPassword());
            contact.setText(adminUser1.getContact());
            email.setText(adminUser1.getEmail());


            return convertView;
    }

}
