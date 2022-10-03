package com.example.inmygn;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnMap;
    Button btnArraylist;

    Button btnPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap= findViewById(R.id.btnMap);
        btnPager= findViewById(R.id.btnPager);
        btnArraylist= findViewById(R.id.btnArraylist);
        btnArraylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),newTableSeason.class);
                startActivity(intent);
            }
        });
        btnPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ViewpagerActivity.class);
                startActivity(intent);
            }
        });


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoadingActivity.class);
                startActivity(intent);
            }
        });

    }

}
