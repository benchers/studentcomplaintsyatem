package com.example.jayes.studentcomplaintportal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.jayes.studentcomplaintportal.LoginActivity.MyPREFERENCES;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    Spinner mySpinner2;
    String text;
    EditText message;
    Spinner mySpinner1;
    private static final String URL = "http://foodtohome.freeoda.com/se/complaint.php";
    SharedPreferences sharedPreferences;
    public static final String MESSAGE="message";
    public static final String ID="id";
    public static final String SUBJECT="subject";
    public static final String AUTH="authority";
    public static final String DATE="date";
    public static final String TIME="time";
    public static final String PRIORITY="status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Write Compalaint");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        EditText message = (EditText)findViewById(R.id.message);
       // String mes=message.getText().toString().trim();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         mySpinner1=(Spinner) findViewById(R.id.spinner1);
         mySpinner2=(Spinner) findViewById(R.id.spinner2);
        text = mySpinner1.getSelectedItem().toString();
        Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG).show();
        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                if (i == 0) {
                    // message.setText("1");

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Collegeop, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner2.setAdapter(adapter);
                } else if (i == 1) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Administrationop, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner2.setAdapter(adapter);

                } else if (i == 2) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Acadamicsop, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner2.setAdapter(adapter);

                } else if (i == 3) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Departmentop, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner2.setAdapter(adapter);
                } else if (i == 4) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.Hostelop, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner2.setAdapter(adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
                Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void Submit(View view)
    {
        sharedPreferences=getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        message=(EditText)findViewById(R.id.message);
        final String id=sharedPreferences.getString("id","").toString().trim();
        final String mess = message.getText().toString().trim();
        final String authority = mySpinner1.getSelectedItem().toString();
        final String subject = mySpinner2.getSelectedItem().toString();
        final String status = "Not look Yet";
        Calendar c = Calendar.getInstance();
        String date1=c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR);
        final String date=date1.trim();
        int hour =c.get(Calendar.HOUR_OF_DAY);
        String ampm="";
        if(hour>12)
        {
            hour=hour-12;
            ampm="PM";

        }
        else
        {
            ampm="AM";
        }
        String time1 =hour+":"+c.get(Calendar.MINUTE)+ampm;
        final String time = time1.trim();
        final String sec = c.get(Calendar.SECOND)+"";
        final String cid=id+date+time+sec;
       final ProgressDialog loading = ProgressDialog.show(this,"Please wait...","Fetching data..",false,false);
        Toast.makeText(MainActivity.this,cid,Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.equalsIgnoreCase("successfully registered")){
                            loading.dismiss();
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //Adding parameters to request
                params.put(ID,id);
                params.put(PRIORITY,status );
                params.put(SUBJECT,subject);
                params.put(AUTH,authority);
                params.put(MESSAGE,mess);
                params.put(DATE,date);
                params.put(TIME,time);
                params.put("cid",cid);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
