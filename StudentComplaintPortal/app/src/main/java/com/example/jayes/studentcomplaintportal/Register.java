package com.example.jayes.studentcomplaintportal;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.jayes.studentcomplaintportal.LoginActivity.MyPREFERENCES;
import static com.example.jayes.studentcomplaintportal.R.id.username;


public class Register extends AppCompatActivity {
    private static final String REGISTER_URL = "http://foodtohome.freeoda.com/se/register.php";
    private ProgressDialog loading;
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";
    public static final String KEY_ENROLL_NO= "enrollno";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_HOSTEL_ADDRESS = "hosteladdress";
    public static final String KEY_MOBILE_NO = "mobileno";
    SharedPreferences sharedPreferences; ;//= PreferenceManager.getDefaultSharedPreferences(this);
    private EditText editTextname;
    private EditText editTextid;
    private EditText editTextenrollno;
    private EditText editTextemail;
    private EditText editTexthosteladdress;
    private EditText editTextmobileno;
    private EditText editTextpassword;
    private EditText editTextrepassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent i = getIntent();
        //token1=i.getStringExtra("token");
        sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editTextname = (EditText) findViewById(R.id.Name);
        editTextid = (EditText) findViewById(R.id.ID_No);
        editTextenrollno= (EditText) findViewById(R.id.Enrollment_No);
        editTextemail= (EditText) findViewById(R.id.Email);
        editTexthosteladdress= (EditText) findViewById(R.id.Hostel_Addr);
        editTextmobileno= (EditText) findViewById(R.id.Mobile_No);
        editTextpassword= (EditText) findViewById(R.id.Password);
        editTextrepassword= (EditText) findViewById(R.id.Re_Password);
        // buttonRegister = (Button) findViewById(R.id.buttonRegister);
        setTitle("Register");
    }

    public void registerUser(View view) {
        // Log.d("My firebase id", "Refreshed token: " +  Global.token);
        final String name = editTextname.getText().toString().trim();
        final String id = editTextid.getText().toString().trim();
        final String enrollno = editTextenrollno.getText().toString().trim();
        final String email = editTextemail.getText().toString().trim();
        final String hosteladdress = editTexthosteladdress.getText().toString().trim();
        final String mobileno = editTextmobileno.getText().toString().trim();
        final String password = editTextpassword.getText().toString().trim();
        final String repassword = editTextrepassword.getText().toString().trim();
        loading = ProgressDialog.show(this, "Please wait...", "Registering..", false, false);
        if (name.isEmpty() || id.isEmpty() || enrollno.isEmpty() || email.isEmpty() || hosteladdress.isEmpty() || mobileno.isEmpty() ||password.isEmpty())
        {
            loading.dismiss();
            Toast.makeText(Register.this, "please enter data in all fields", Toast.LENGTH_LONG).show();
        }
        else
        if (Pattern.matches("[a-zA-Z]+", id) == true || id.length() != 5)
        {
            loading.dismiss();
            Toast.makeText(Register.this, "id number is not valid", Toast.LENGTH_LONG).show();
        }
        else
        if (enrollno.length() != 10)
        {
            loading.dismiss();
            Toast.makeText(Register.this, "enrollno number is not valid", Toast.LENGTH_LONG).show();
        }
        else
        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
        {
            loading.dismiss();
            Toast.makeText(Register.this, "enter valid email", Toast.LENGTH_LONG).show();
        }
        else
        if (mobileno.length() != 10)
        {
            loading.dismiss();
            Toast.makeText(Register.this, "mobile number is not valid", Toast.LENGTH_LONG).show();
        }

        else
        if (!password.equals(repassword) )
        {
            loading.dismiss();
            Toast.makeText(Register.this, "password not match", Toast.LENGTH_LONG).show();
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("successfully registered")) {
                                Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                                loading.dismiss();
                                Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                                Editor editor = sharedPreferences.edit();
                                editor.putString(KEY_NAME, name);
                                editor.putString(KEY_ID, id);
                                editor.putString(KEY_ENROLL_NO, enrollno);
                                editor.putString(KEY_EMAIL, email);
                                editor.putString(KEY_PASSWORD, password);
                                editor.putString(KEY_HOSTEL_ADDRESS, hosteladdress);
                                editor.putString(KEY_MOBILE_NO, mobileno);
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("password", password);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                                loading.dismiss();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NAME, name);
                    params.put(KEY_ID, id);
                    params.put(KEY_ENROLL_NO, enrollno);
                    params.put(KEY_EMAIL, email);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_HOSTEL_ADDRESS, hosteladdress);
                    params.put(KEY_MOBILE_NO, mobileno);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

}
