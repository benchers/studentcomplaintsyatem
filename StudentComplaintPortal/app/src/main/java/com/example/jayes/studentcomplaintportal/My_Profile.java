package com.example.jayes.studentcomplaintportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class My_Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
     String idno;
    String pass;
    ProgressDialog loading;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";
    public static final String KEY_ENROLL_NO= "enrollno";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_HOSTEL_ADDRESS = "hosteladdress";
    public static final String KEY_MOBILE_NO = "mobileno";
    TextView idt;
    TextView namet;
    TextView enrollnot;
    EditText emailt;
    EditText mobnot;
    EditText haddrt;
    EditText pass1t;
    EditText pass2t;
    private static final String REGISTER_URL = "http://foodtohome.freeoda.com/se/update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prof);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Profile");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        idno=sharedPreferences.getString(KEY_ID, "").trim();
        final String username =idno.trim();
        pass=sharedPreferences.getString(KEY_PASSWORD, "");
        final String password =pass.trim();
        getData(username, password);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         idt=(TextView)findViewById(R.id.ID_No);
         namet=(TextView)findViewById(R.id.Name);
         enrollnot=(TextView)findViewById(R.id.Enrollment_No);
         emailt=(EditText)findViewById(R.id.Email);
         mobnot=(EditText)findViewById(R.id.Mobile_No);
         haddrt=(EditText)findViewById(R.id.Hostel_Addr);
         pass1t=(EditText)findViewById(R.id.Password);
         pass2t=(EditText)findViewById(R.id.Re_Password);
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
                Toast.makeText(My_Profile.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getData(final String username,final String password) {
        // String id = editTextId.getText().toString().trim();
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        // String url = Config.DATA_URL+editTextId.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.DATA_URL1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(My_Profile.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //Adding parameters to request
                params.put(KEY_ID,username);
                params.put(KEY_PASSWORD,password);
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
            JSONArray result = jsonObject.getJSONArray("result");

            JSONObject data = result.getJSONObject(0);
            String name=data.getString(KEY_NAME);
            String id=data.getString(KEY_ID);
            String enrollno=data.getString(KEY_ENROLL_NO);
            String email = data.getString(KEY_EMAIL);
            String hadr = data.getString(KEY_HOSTEL_ADDRESS);
            String mobno = data.getString(KEY_MOBILE_NO);
            String pass = data.getString(KEY_PASSWORD);
            namet.setText("Name "+name);
            idt.setText("Id "+ id);
            enrollnot.setText("Enroll no "+enrollno);
            emailt.setText(email);
            haddrt.setText(hadr);
            mobnot.setText(mobno);
            pass1t.setText(pass);
            //Item_List.add(item);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(View view){
        final String email = emailt.getText().toString().trim();
        final String addr = haddrt.getText().toString().trim();
        final String mobileno = mobnot.getText().toString().trim();
        final String password = pass1t.getText().toString().trim();
        loading = ProgressDialog.show(this,"Please wait...","Registering..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("successfully Updated")){
                            Toast.makeText(My_Profile.this, response, Toast.LENGTH_LONG).show();
                            loading.dismiss();

                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putString(KEY_PASSWORD, password);
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            // intent.putExtra("username", username);
                            // intent.putExtra("password",password);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(My_Profile.this,response,Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(My_Profile.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_ID,idno);
                params.put(KEY_EMAIL,email);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_HOSTEL_ADDRESS,addr);
                params.put(KEY_MOBILE_NO,mobileno);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

