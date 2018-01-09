package com.inzenjer.automaticdoorlock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class Login_login extends Fragment {
    EditText user, pass;
    String username, password;
    Button login;
    ProgressDialog pg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_login_login, container, false);
        user = rootView.findViewById(R.id.user);
        pass = rootView.findViewById(R.id.pass);
        login = rootView.findViewById(R.id.login);
        pg = new ProgressDialog(getActivity());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                password = pass.getText().toString();
                login();
            }
        });
        return rootView;


    }

    private void login() {
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        pg.setMessage("Loading...");
        pg.show();
        String url = Const.loginip;
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("json exeption on login response", response.toString());
                try {
                    JSONObject js = new JSONObject(response);
                    String status = js.getString("status");
                    if (status.contentEquals("success")) {

                        startActivity(new Intent(getActivity(), Home.class));
                    }
                } catch (JSONException je) {
                    Log.e("json exeption on login response", je.toString());
                }

                pg.hide();
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


                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(strReq);
    }
}
