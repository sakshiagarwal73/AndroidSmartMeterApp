package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ElecDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tv1,tv2;
    Button btn1,btn2;
    Spinner spinner;
    String year;
    ArrayAdapter<CharSequence> adapter;
    Intent is;
    String id;
    Intent is2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elec_details);

        is = getIntent();
        id = is.getStringExtra("user");

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        spinner = findViewById(R.id.spinner);

        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(ElecDetails.this,R.array.years,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Log.d("In ElecDetails","Year is: " + year);
        //Toast.makeText(getApplicationContext(),"year is" + year,Toast.LENGTH_LONG);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("buttonIn ElecDetails","Year is: " + year);
                Log.d("buttonIn ElecDetails","id is: " + id);
                is2 = new Intent(ElecDetails.this,GraphActivity.class);
                Bundle extras  = new Bundle();
                extras.putString("user",id);
                extras.putString("year",year);
                is2.putExtras(extras);
                startActivity(is2);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ArrayAdapter<CharSequence> yearadapter = (ArrayAdapter<CharSequence>) parent.getAdapter();
        year = parent.getItemAtPosition(position).toString();
        yearadapter.notifyDataSetChanged();
        //Log.d("In ElecDetails","Year is: " + year);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
