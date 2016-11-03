package anurag.com.navdrawer.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import anurag.com.navdrawer.Json.JsonLink;
import anurag.com.navdrawer.Json.JsonParser;
import anurag.com.navdrawer.R;

public class LoginActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    EditText username,password;
    Button login;

    JSONObject jsonObject = new JSONObject();
    JsonParser jparser = new JsonParser();
    JsonLink jsonLink = new JsonLink();
    String query;
    JSONArray postarray = new JSONArray();
    int success;
    String message;
    int u_type;

//    private static String LOGIN_URL = "http://a0682321.ngrok.io/tms/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
        new ParseLogin().execute();
        }
    });
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
                finish();
            System.exit(0);
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
    private  void login()
    {
        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("u_username", username.getText().toString())
                .appendQueryParameter("u_pass", password.getText().toString());

        query = builder.build().getEncodedQuery();
        try {
            jsonObject = jparser.sendJson(query,jsonLink.getLOGIN_URL());
        if(jsonObject!=null) {
            success = jsonObject.getInt("success");
            message = jsonObject.getString("message");

            if (success == 1) {
                Log.d("TAG", String.valueOf(success));
                Log.d("Message", message);
                u_type = jsonObject.getInt("u_type");
            } else {
                Log.d("TAG", String.valueOf(success));
                Log.d("Message", message);
            }
        }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
private class ParseLogin extends AsyncTask<Void,Void,Void>
{
    @Override
    protected Void doInBackground(Void... params)
    {
        login();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (jsonObject != null) {
            if (success == 1) {
                Intent intent;
                if (u_type == 1) {
                    intent = new Intent(LoginActivity.this, Tech.class);
                    startActivity(intent);
                    finish();
                } else if (u_type == 2) {
                    intent = new Intent(LoginActivity.this, Mechant.class);
                    startActivity(intent);
                    finish();
                } else if (u_type == 3) {
                    intent = new Intent(LoginActivity.this, QCActivity.class);
                    startActivity(intent);
                    finish();
                }
                  else if (u_type==4)
                  {
                      intent = new Intent(LoginActivity.this,VednorActivity.class);
                      startActivity(intent);
                      finish();
                  }
                else {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Unable to connect to Database",Toast.LENGTH_SHORT).show();
        }
    }

}
}
