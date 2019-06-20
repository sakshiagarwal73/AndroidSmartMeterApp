package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference  mDatabase;
    TextView tvConsumer_Id;
    TextView tvPhone;
    TextView tvName;
    TextView tvEmail,tvAdd,tvMetNo;
    Intent is;
    TextView tviddisp,tvnamedisp,tvphonedisp,tvmetdisp,tvemaildisp,tvadddisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is=getIntent(); // receive the intent from first activity
        final String consumerid =is.getStringExtra("user");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Consumer_Details");

        tvConsumer_Id = (TextView)findViewById(R.id.tvConsumer_Id);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        tvName = (TextView)findViewById(R.id.tvName);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        tvAdd = (TextView)findViewById(R.id.tvAdd) ;
        tvMetNo = (TextView)findViewById(R.id.tvMetNo);


        tviddisp = findViewById(R.id.tviddisp);
        tvnamedisp = findViewById(R.id.tvnamedisp);
        tvphonedisp = findViewById(R.id.tvphonedisp);
        tvemaildisp = findViewById(R.id.tvemaildisp);
        tvmetdisp = findViewById(R.id.tvmetdisp);
        tvadddisp = findViewById(R.id.tvadddisp);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext())
                {
                    DataSnapshot item = items.next();
                    String id = item.child("Consumer_Id").getValue().toString();
                    if(id.equals(consumerid)) //// lertitstry it out wait
                    {
                        String Name = item.child("Name").getValue().toString();
                        String phonenumber = item.child("Phone Number").getValue().toString();
                        String email = item.child("E-mail").getValue().toString();
                        String mnum = item.child("Meter_number").getValue().toString();
                        String add = item.child("Address").getValue().toString();

                        tviddisp.setText("Consumer Id");
                        tvnamedisp.setText("Name");
                        tvphonedisp.setText("Phone Number");
                        tvemaildisp.setText("Email Address");
                        tvmetdisp.setText("Meter Number");
                        tvadddisp.setText("Address");

                        tvConsumer_Id.setText( id);
                        tvName.setText( Name);
                        tvPhone.setText( phonenumber);
                        tvEmail.setText( email);
                        tvMetNo.setText( mnum);
                        tvAdd.setText( add);

                        Log.d("User Here", "onDataChange:  " + id);
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
