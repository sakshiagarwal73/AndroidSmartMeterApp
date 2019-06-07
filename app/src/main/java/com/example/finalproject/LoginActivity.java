package com.example.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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



public class LoginActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword ;
    Button mButtonLogin;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextUsername = findViewById(R.id.edittext_username);
        mTextPassword = findViewById(R.id.edittext_password);
        mButtonLogin =  findViewById(R.id.button_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String[] user = new String[1];
       final Boolean[] ispresent = {false};

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 user[0] = mTextUsername.getText().toString();
                final String pass = mTextPassword.getText().toString();

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();

                        DataSnapshot item = null;
                        while(items.hasNext())
                        {
                             item = items.next();
                            String id = item.child("Consumer_Id").getValue().toString();
                            if(id.equals(user[0]))
                            {
                                ispresent[0] = true;
                                break;
                            }

                        }

                        if(ispresent[0])
                        {
                            Toast.makeText(getApplicationContext(),"Username doesn't exist",Toast.LENGTH_LONG).show();
                        }

                        else
                        {
                            String userpassword = item.child("Password").getValue().toString();
                            SHAExample sha = new SHAExample();
                            try {
                                 String hash = sha.getPassword(pass);
                                 if(userpassword.equals(hash))
                                 {
                                     ispresent[0] = true;
                                 }
                                 else
                                 {
                                     ispresent[0] = false;
                                     Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                                 }
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        if(ispresent[0])
        {
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            i.putExtra("user", user[0]);
            startActivity(i);
        }
    }
}
