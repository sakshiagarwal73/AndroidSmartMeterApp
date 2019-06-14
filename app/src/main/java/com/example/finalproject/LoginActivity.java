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

       final Boolean[] ispresent = {false};
        final String[] user = new String[1];
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 user[0] = mTextUsername.getText().toString();
                final String pass = mTextPassword.getText().toString();


                mDatabase.child("Consumer_Details").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("Inside Data Write", "onDataChange: success " );

                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();

                        Log.d("Inside Data Write", "onDataChange: " + items.toString());

                        DataSnapshot item = null;
                        while(items.hasNext())
                        {
                             item = items.next();
                            String id = item.child("Consumer_Id").getValue().toString();
                            if(id.equals(user[0]))
                            {
                                Log.d("Inside Data Write", "onDataChange:  user found" + user[0]);
                                //Toast.makeText(getApplicationContext(),"Username exist",Toast.LENGTH_LONG).show();
                                ispresent[0] = true;
                                break;
                            }

                        }

                        if(!ispresent[0])
                        {
                            Toast.makeText(getApplicationContext(),"Username doesn't exist",Toast.LENGTH_LONG).show();
                            Log.d("Inside Data Write", "onDataChange: not found ");
                        }

                        else
                        {
                            String userpassword = item.child("Password").getValue().toString();
                            SHAExample sha = new SHAExample();
                            try {
                                 String hash = sha.getPassword(pass);
                                Log.d("Inside Data Write", "hash Pwd: " + hash + " " + hash);
                                 if(userpassword.equals(hash)) //change back to hash later
                                 {
                                     ispresent[0] = true;
                                     Log.d("Inside Data Write", "onDataChange: pwd match ");
                                     //Toast.makeText(LoginActivity.this, "pwd match", Toast.LENGTH_SHORT).show();
                                     Intent i = new Intent(LoginActivity.this,change_password.class);

                                     Bundle extras  = new Bundle();
                                     extras.putString("user",user[0]);
                                     extras.putString("password",userpassword);
                                     i.putExtras(extras);
                                     //i.putExtra("user",user[0]);*/
                                     startActivity(i);
                                     finish();
                                 }
                                 else
                                 {

                                     if(ispresent[0])
                                     {
                                         Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_LONG).show();
                                     }
                                     ispresent[0] = false;
                                     Log.d("Inside Data Write", "onDataChange: wrong pwd");
                                 }
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Log.d("Inside Data Write", "onCancelled: ");

                    }
                }); // username exists and wrong password is coming

            }
        });
       /* if(ispresent[0])
        {
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            i.putExtra("user", user[0]);
            startActivity(i); // going to mainactivity
        }*/
    }
}
