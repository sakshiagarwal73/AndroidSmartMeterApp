package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptionsDisplay extends AppCompatActivity {

    Intent is;
    Intent is1;
    Button btnAccSet,btnTips,btnDetails,btnConsum;
    TextView tvAcc,tvTips,tvDetails,tvCons;
    Intent is2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_display);

        is=getIntent(); // receive the intent from first activity
        Bundle extras = is.getExtras();
        final String consumerid =extras.getString("user");
        final String userpassword = extras.getString("password");

        btnAccSet = findViewById(R.id.btnAccSet);
        btnTips = findViewById(R.id.btnTips);
        btnDetails = findViewById(R.id.btnDetails);
        btnConsum = findViewById(R.id.btnConsum);

        tvAcc = findViewById(R.id.tvAcc);
        tvTips = findViewById(R.id.tvTips);
        tvDetails = findViewById(R.id.tvDetails);
        tvCons = findViewById(R.id.tvCons);

       btnTips.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               is1 = new Intent(OptionsDisplay.this,EnergySavingTips.class);
               startActivity(is1);
               finish();
           }
       });

       btnDetails.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               is2 = new Intent(OptionsDisplay.this,MainActivity.class);
               is2.putExtra("user",consumerid);
               startActivity(is2);
               finish();
           }
       });

    }
}
