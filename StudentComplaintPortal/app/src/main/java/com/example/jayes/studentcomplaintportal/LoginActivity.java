package com.example.jayes.studentcomplaintportal;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;
public class LoginActivity extends AppCompatActivity {
    public BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressDialog loading;
    EditText username,password;
    String token=null;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final String Login_URL = "http://foodtohome.freeoda.com/se/login.php";
    public static final String KEY_USERNAME = "id";
    public static final String KEY_PASSWORD = "password";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

    }

    @Override
    protected void onResume() {
        sharedPreferences=getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        if (sharedPreferences.contains(KEY_USERNAME))
        {
            if (sharedPreferences.contains(KEY_PASSWORD))
            {
                String user=sharedPreferences.getString(KEY_USERNAME,"");
                Intent i =new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("username",user);
                i.putExtra("password",KEY_PASSWORD);
                startActivity(i);
                finish();
            }
        }

        super.onResume();
        Log.w("MainActivity", "onResume");

    }


    //Unregistering receiver on activity paused

    public void Login(View view)
    {
        final String username1 = username.getText().toString().trim();
        final String password1 = password.getText().toString().trim();
        loading = ProgressDialog.show(this,"Please wait...","Fetching data..",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Login_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.equalsIgnoreCase("You are Successfully logged in!!")){
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            Editor editor= sharedPreferences.edit();
                            editor.putString(KEY_USERNAME,username1);
                            editor.putString(KEY_PASSWORD, password1);
                            editor.commit();
                           Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        loading.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //Adding parameters to request
                params.put(KEY_USERNAME,username1);
                params.put(KEY_PASSWORD,password1);
                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void register(View view)
    {
        Intent i = new Intent(this,Register.class);
       // i.putExtra("token",token);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

}
