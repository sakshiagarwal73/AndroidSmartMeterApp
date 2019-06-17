package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class ConsumptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerYear,spinnerMonth;

    Intent is;
    ArrayAdapter<CharSequence> adapterYear;
    ArrayAdapter<CharSequence> adapterMonth;
    String year,month;
    DatabaseReference mDatabase;
    String id ;
    Button btnShow;
    TextView tvDisplay;
    int prevyear;
    String current_reading = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
        is = getIntent();
        id = is.getStringExtra("user");
         btnShow = findViewById(R.id.btnShow);
         tvDisplay = findViewById(R.id.tvDisplay);

        spinnerYear = (Spinner)findViewById(R.id.spinnerYear);
        adapterYear = ArrayAdapter.createFromResource(ConsumptionActivity.this,R.array.years,android.R.layout.simple_spinner_item);


        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setOnItemSelectedListener( this);


        spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
        adapterMonth = ArrayAdapter.createFromResource(ConsumptionActivity.this,R.array.months,android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setOnItemSelectedListener( this);

        final HashMap<String,String> hmap = new HashMap<>();
        hmap.put("February","January");
        hmap.put("March","February");
        hmap.put("April","March");
        hmap.put("May","June");
        hmap.put("July","June");
        hmap.put("August","July");
        hmap.put("September","August");
        hmap.put("October","September");
        hmap.put("November","October");
        hmap.put("December","November");

        mDatabase = FirebaseDatabase.getInstance().getReference();

       btnShow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Log.d("Inside Button","Year + " + year);
               Log.d("Inside Button","Month + " + month);
               Log.d("Inside Button","id + " + id);

               mDatabase.child("Years").child(year).child(id).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       if(month.equals("January")==false)
                       {
                           Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                           DataSnapshot item = null;
                           String itemMonthPrev;
                           String itemMonth;
                           String prevReading = "";
                           String currReading = "";
                           while(items.hasNext()) {
                               item = items.next();
                               itemMonthPrev = item.child("Month").getValue().toString();
                               if (itemMonthPrev.equals(hmap.get(month))) {
                                   prevReading = item.child("Reading").getValue().toString();
                                   break;
                               }

                           }

                           items = dataSnapshot.getChildren().iterator();
                           item = null;
                           while(items.hasNext())
                           {
                               item = items.next();
                               itemMonth = item.child("Month").getValue().toString();
                               if(month.equals(itemMonth))
                               {
                                   currReading = item.child("Reading").getValue().toString();
                               }
                           }

                           int diff = Integer.parseInt(currReading) - Integer.parseInt(prevReading);
                           tvDisplay.setText(String.valueOf(diff) + " kWh");

                       }

                       else if(month.equals("January"))
                       {
                           if(year.equals("2019"))
                           {
                               Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                               DataSnapshot item = null;
                               String currmonth = "";
                               while(items.hasNext())
                               {
                                   item = items.next();
                                   currmonth = item.child("Month").getValue().toString();
                                   if(currmonth.equals("January"))
                                   {
                                       String reading = item.child("Reading").getValue().toString();
                                       tvDisplay.setText(reading);
                                   }
                               }
                           }

                           else
                           {
                               int curryear = Integer.parseInt(year);
                               prevyear = curryear - 1;
                               Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                               DataSnapshot item = null;
                               String currmonth = "";
                               while(items.hasNext())
                               {
                                   item = items.next();
                                   currmonth = item.child("Month").getValue().toString();
                                   if(currmonth.equals("January"))
                                   {
                                        current_reading = item.child("Reading").getValue().toString();

                                   }
                               }

                           }
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

               if(current_reading.equals("")==false)
               {
                   mDatabase.child("Years").child(String.valueOf(prevyear)).child(id).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                           DataSnapshot item = null;
                           while(items.hasNext())
                           {
                               item = items.next();
                               String mnth = item.child("Month").getValue().toString();
                               String reading = "";
                               if(mnth.equals("December"))
                               {
                                   reading = item.child("Reading").getValue().toString();
                               }

                               int diff = Integer.parseInt(current_reading) - Integer.parseInt(reading);
                               tvDisplay.setText(String.valueOf(diff));
                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
               }
           }
       });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) parent.getAdapter();
        if(adapter == adapterYear)
        {
            year = parent.getItemAtPosition(position).toString();
        }
        else if(adapter == adapterMonth)
        {
            month = parent.getItemAtPosition(position).toString();
        }
        adapter.notifyDataSetChanged();

        //Log.d("year","year is  " + year);
        //Log.d("month","month is  " + month);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
