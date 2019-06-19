package com.example.finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraphActivity extends AppCompatActivity  {


     DatabaseReference mDatabase;

     HashMap<String,Integer> graphMonths;
     Intent is;

    LineChart lineChart;
     String prev;
     ArrayList<String> xAxes = new ArrayList<>();
     ArrayList<Entry> yAxes = new ArrayList<>();
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        is = getIntent();
        Bundle extras = is.getExtras();
        final String consumerid =extras.getString("user");
        final String year = extras.getString("year");


        Log.d("next In ElecDetails","Year is: " + year);




        mDatabase = FirebaseDatabase.getInstance().getReference();

        graphMonths = new HashMap<>();

        mDatabase.child("Years").child(year).child(consumerid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("next In ElecDetails","ABC: " );
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                DataSnapshot item = null;
                String itemmonth;
                String reading;
                while(items.hasNext())
                {
                    item = items.next();
                    itemmonth = item.child("Month").getValue().toString();
                    Log.d("next In ElecDetails","itemMonth is: " + itemmonth);
                    if(itemmonth.equals("January"))
                    {
                        prev = item.child("Previous").getValue().toString();
                    }
                    reading = item.child("Reading").getValue().toString();
                    Log.d("next In ElecDetails","Reading is: " + reading);
                    graphMonths.put(itemmonth,Integer.valueOf(reading));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("Month "," Hashmap size is: " + graphMonths.size());
        Iterator hm = graphMonths.entrySet().iterator();
        while(hm.hasNext())
        {
            Map.Entry mapEl = (Map.Entry)hm.next();
            Log.d("Month "," Hashmap is: " + mapEl.getKey().toString() + "  " +  mapEl.getValue().toString());
        }

        lineChart = (LineChart)findViewById(R.id.lineChart);

        xAxes.add("January");
        xAxes.add("February");
        xAxes.add("March");
        xAxes.add("April");
        xAxes.add("May");
        xAxes.add("June");
        xAxes.add("July");
        xAxes.add("August");
        xAxes.add("September");
        xAxes.add("October");
        xAxes.add("November");
        xAxes.add("December");

        yAxes.add(new Entry(0,Integer.valueOf(prev)));
        yAxes.add(new Entry(1,graphMonths.get("February")-graphMonths.get("January")));
        yAxes.add(new Entry(2,graphMonths.get("March")-graphMonths.get("February")));
        yAxes.add(new Entry(3,graphMonths.get("April")-graphMonths.get("March")));
        yAxes.add(new Entry(4,graphMonths.get("May")-graphMonths.get("April")));
        yAxes.add(new Entry(5,graphMonths.get("June")-graphMonths.get("May")));
        yAxes.add(new Entry(6,graphMonths.get("July")-graphMonths.get("June")));
        yAxes.add(new Entry(7,graphMonths.get("August")-graphMonths.get("July")));
        yAxes.add(new Entry(8,graphMonths.get("September")-graphMonths.get("August")));
        yAxes.add(new Entry(9,graphMonths.get("October")-graphMonths.get("September")));
        yAxes.add(new Entry(10,graphMonths.get("November")-graphMonths.get("October")));
        yAxes.add(new Entry(11,graphMonths.get("December")-graphMonths.get("November")));

        String[] xaxes = new String[xAxes.size()];

        for(int i=0;i<xAxes.size();i++)
        {
            xaxes[i] = xAxes.get(i);
        }

        LineDataSet lineDataSet = new LineDataSet(yAxes,"values");
        lineDataSet.setDrawCircles(true);
        lineDataSet.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet);

        lineChart.setData(new LineData(lineDataSets));

        lineChart.setVisibleXRangeMaximum(65f);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);

    }




}
