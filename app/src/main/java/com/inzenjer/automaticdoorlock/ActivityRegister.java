package com.inzenjer.automaticdoorlock;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inzenjer.automaticdoorlock.system.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] choose = {"Admin", "User",};
    View rootView;
    EditText email, password;
    String semail, spassword;
    Button register;
    ProgressDialog pg;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_register, container, false);

        Spinner spin = (Spinner) rootView.findViewById(R.id.spi);
        spin.setOnItemSelectedListener(this);
        email = rootView.findViewById(R.id.us);
        password= rootView.findViewById(R.id.passw);
        register= rootView.findViewById(R.id.register);
        pg = new ProgressDialog(getActivity());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setTitle("Registering");
                pg.show();
                semail = email.getText().toString();
                spassword = password.getText().toString();
                reg();
            }
        });
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, choose);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        return rootView;
    }

    private void reg() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        pg.setMessage("Loading...");
        String url = Const.regip;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json exeption on login response", response.toString());
                try {
                    JSONObject js = new JSONObject(response);
                    String status = js.getString("status");
                    if (status.contentEquals("Successfully Registered")) {

                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        pg.hide();
                    }
                } catch (JSONException je) {
                    Log.e("json exeption on login response", je.toString());
                    pg.hide();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley exeption on login response", error.toString());
                pg.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("username", semail);
                params.put("Password", spassword);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(strReq);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getContext(), choose[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
