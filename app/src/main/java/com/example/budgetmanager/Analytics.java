package com.example.budgetmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Analytics extends AppCompatActivity {

    CardView todayanalytics,weekanalytics,monthanalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        todayanalytics=findViewById(R.id.todayanalytics);
        weekanalytics=findViewById(R.id.weekanalytics);
        monthanalytics=findViewById(R.id.monthanalytics);

        todayanalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Analytics.this,todayanalytics_activity.class);
                startActivity(intent);
            }
        });

         weekanalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Analytics.this,weekanalytics_activity.class);
                 startActivity(intent);
            }
        });

        monthanalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Analytics.this,monthanalytics_activity.class);
                 startActivity(intent);
            }
        });

      
    }
}