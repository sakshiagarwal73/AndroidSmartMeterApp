package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

public class change_password extends AppCompatActivity {

    EditText current , newpass, confirm;
    Button btn;
    Intent is;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        is=getIntent(); // receive the intent from first activity
        Bundle extras = is.getExtras();
        final String consumerid =extras.getString("user");
        final String userpassword = extras.getString("password");

        Log.d("username is", "consumer id" + consumerid);
        Log.d("password is", "password" + userpassword);

        current = findViewById(R.id.current);
        newpass = findViewById(R.id.newpass);
        confirm = findViewById(R.id.confirm);
         btn = findViewById(R.id.btn);
        mDatabase = FirebaseDatabase.getInstance().getReference();//////mistake

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currpass = current.getText().toString();
                String newpassword = newpass.getText().toString(); ////mistake
                String confirmpass = confirm.getText().toString();///mistake
                SHAExample sha = new SHAExample();
                String hashpass = "";
                try {
                     hashpass = sha.getPassword(currpass);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                    if(hashpass.equals(userpassword) == false)
                    {
                        Toast.makeText(getApplicationContext(),"Wrong Current Password",Toast.LENGTH_LONG).show();
                        Log.d("inside", "onClick:  not authorised");
                    }
                    else
                    {
                        if(newpassword.equals(confirmpass)==false)
                        {
                            Toast.makeText(getApplicationContext(),"Passwords dont match,enter again",Toast.LENGTH_LONG).show();
                            Log.d("inside", "onClick: not equal");
                        }
                        else
                        {
                            Log.d("inside", "onClick: else running");
                            String newhash = "";
                            try {
                                newhash = sha.getPassword(newpassword);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            final String finalNewhash = newhash;
                            Log.d("inside ","new hash" + finalNewhash);
                            mDatabase.child("Consumer_Details").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.d("inside ","inside on data change" );
                                    Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                                    DataSnapshot item = null;
                                    while(items.hasNext())
                                    {
                                        Log.d("inside", "onDataChange: while datasnapshot");
                                        item = items.next();
                                        String id = item.child("Consumer_Id").getValue().toString();
                                        if(id.equals(consumerid))
                                        {
                                            //Log.d("inside if","change password" + String.valueOf(item) + "\t id:- " + id);
                                           // Log.d("value of key", "onDataChange: "+ mDatabase.child("Consumer_Details").child(String.valueOf(item)).child("Password").getKey());
                                           //mDatabase.child("finalproject-745b4/Consumer_Details/" + item.getKey());
                                            Log.d("valueOfSnap", "onDataChange:"+ item.getValue().toString());
                                            item.child("Password").getRef().setValue(finalNewhash);
                                            Toast.makeText(getApplicationContext(),"Password updated successfully",Toast.LENGTH_LONG).show();
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



            }
        });
    }
}
