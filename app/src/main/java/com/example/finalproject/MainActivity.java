package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    Intent is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        is=getIntent(); // receive the intent from first activity
        String consumerid =is.getStringExtra("user");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Consumer_Details");

        tvConsumer_Id = (TextView)findViewById(R.id.tvConsumer_Id);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        tvName = (TextView)findViewById(R.id.tvName);
        final String user = "";

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext())
                {
                    DataSnapshot item = items.next();
                    String id = item.child("Consumer_Id").getValue().toString();
                    if(id.equals(user))
                    {
                        String Name = item.child("Name").getValue().toString();
                        String phonenumber = item.child("Phone Number").getValue().toString();
                        tvConsumer_Id.setText("Consumer Id: " + id);
                        tvName.setText("Name: " + Name);
                        tvPhone.setText("Phone Number:" + phonenumber);
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
