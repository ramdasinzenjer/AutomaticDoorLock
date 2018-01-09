package com.inzenjer.automaticdoorlock;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityRegister extends Fragment implements AdapterView.OnItemSelectedListener{
    String[]choose={"Admin","User",};
    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_register, container, false);

        Spinner spin=(Spinner)rootView.findViewById(R.id.spi);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,choose);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getContext(),choose[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
