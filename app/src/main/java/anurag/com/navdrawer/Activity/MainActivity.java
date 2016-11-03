package anurag.com.navdrawer.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import anurag.com.navdrawer.Fragment.AdminFragment;
import anurag.com.navdrawer.Fragment.AdminView;
import anurag.com.navdrawer.Fragment.UserFragment;
import anurag.com.navdrawer.Fragment.WelcomeFragment;
import anurag.com.navdrawer.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.user);
        text.setText("Admin");
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fr = new WelcomeFragment();
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();
        fr.setArguments(args);
        ft.replace(R.id.frame_container, fr);
        ft.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
//        }
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sign_out) {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (id == R.id.Brand) {
         AdminFragment fr = new AdminFragment();
            fr.setCheckid(1);
            transaction.replace(R.id.frame_container,fr).commit();

        } else if (id == R.id.Season) {
            AdminFragment fr = new AdminFragment();
            fr.setCheckid(2);
            transaction.replace(R.id.frame_container,fr).commit();

        } else if (id == R.id.Designer) {
            AdminFragment fr = new AdminFragment();
            fr.setCheckid(3);
            transaction.replace(R.id.frame_container,fr).commit();

        } else if (id == R.id.Technical_Designer) {
            UserFragment uf = new UserFragment();
            uf.setCheckUserid(1);
            transaction.replace(R.id.frame_container,uf).commit();

        } else if (id == R.id.Production_Merchandiser) {
            UserFragment uf = new UserFragment();
            uf.setCheckUserid(2);
            transaction.replace(R.id.frame_container,uf).commit();

        } else if (id == R.id.Quality_Checker) {
            UserFragment uf = new UserFragment();
            uf.setCheckUserid(3);
            transaction.replace(R.id.frame_container,uf).commit();

        } else if (id == R.id.Vendor) {
            UserFragment uf = new UserFragment();
            uf.setCheckUserid(4);
            transaction.replace(R.id.frame_container,uf).commit();

        } else if (id == R.id.Fabric_Code) {
            AdminFragment fr = new AdminFragment();
            fr.setCheckid(4);
            transaction.replace(R.id.frame_container,fr).commit();

        }
        else if (id == R.id.Lining_Code) {
            AdminFragment fr = new AdminFragment();
            fr.setCheckid(5);
            transaction.replace(R.id.frame_container,fr).commit();

        }
        else if (id == R.id.View)
        {
            AdminView fr = new AdminView();
            transaction.replace(R.id.frame_container,fr).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
/* Technical_Designer=1
 * Production_Merchandiser=2
 * Quality_Checker=3
 * Vendor=4*/