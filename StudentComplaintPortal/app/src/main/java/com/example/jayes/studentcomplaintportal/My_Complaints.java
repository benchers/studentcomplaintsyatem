package com.example.jayes.studentcomplaintportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import static com.example.jayes.studentcomplaintportal.LoginActivity.MyPREFERENCES;

public class My_Complaints extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    ProgressDialog loading;
    ArrayList<String> aid = new ArrayList<String>();
    ArrayList<String> amessage = new ArrayList<String>();
    ArrayList<String> asubject = new ArrayList<String>();
    ArrayList<String> aauthority = new ArrayList<String>();
    ArrayList<String> adate = new ArrayList<String>();
    ArrayList<String> atime = new ArrayList<String>();
    ArrayList<String> apriority = new ArrayList<String>();
    ListView listview = null;
    public static final String ID="id";
    SharedPreferences sharedPreferences;
    SQLiteListAdapter ListAdapter ;
    public static final String API_KEY="apikey";
    public static final String  REG_TOKEN ="regtoken";
    public static final String MESSAGE1="message";
    public static final String DATE="date";
    public static final String TIME="time";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Comaplaints");
        listview = (ListView) findViewById(R.id.listview_03);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        if (id == R.id.action_settings) {
            SharedPreferences preferences =getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Complaint) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.My_Complaints) {
            Intent intent = new Intent(this,My_Complaints.class);
            startActivity(intent);
        } else if (id == R.id.Account) {
            Intent intent = new Intent(this,My_Profile.class);
            startActivity(intent);

        }
        else if (id == R.id.Help) {
            String[] TO = {"studentcomplaintportal.vnit@gmail.com"};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(My_Complaints.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getData() {
        // String id = editTextId.getText().toString().trim();
        sharedPreferences=getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        final String id=sharedPreferences.getString("id","").toString().trim();
        // String url = Config.DATA_URL+editTextId.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.DATA_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(My_Complaints.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ID,id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){
        ArrayList<String> userdata = new ArrayList<String>();
        try {
            loading.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            for (int i = 0; i < result.length(); i++) {
                JSONObject data = result.getJSONObject(i);
                String id= data.getString(Config.ID);
                String message=data.getString(Config.MESSAGE);
                String subject=data.getString(Config.SUBJECT);
                String authority=data.getString(Config.AUTH);
                String date=data.getString(Config.DATE);
                String time=data.getString(Config.TIME);
                String priority=data.getString(Config.PRIORITY);
                aid.add(id);
                amessage.add(message);
                asubject.add(subject);
                aauthority.add(authority);
                adate.add(date);
                atime.add(time);
                apriority.add(priority);
                // Toast.makeText(getApplicationContext(),data.getString(Config.TOKEN)+"\ntotal:"+data.getString("total"),Toast.LENGTH_SHORT).show();
            }
            ListAdapter = new SQLiteListAdapter(this,aid,amessage,asubject,aauthority,adate,atime,apriority);
            listview.setAdapter(ListAdapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
