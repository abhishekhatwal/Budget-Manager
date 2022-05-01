package com.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class todayanalytics_activity extends AppCompatActivity {

    DatabaseReference expensesref;
    AnyChartView anychartview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todayanalytics_activity);

        anychartview=findViewById(R.id.todayanychartview);
        expensesref= FirebaseDatabase.getInstance().getReference().child("expenses");

        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal=Calendar.getInstance();
        String date=dateFormat.format(cal.getTime());

        Query query=expensesref.orderByChild("date").equalTo(date);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totaltransport = 0,totalfood = 0,totalshopping = 0,totalhealth = 0;
                int totalhouse = 0,totaleducation = 0,totalother = 0,totalentertainment = 0;

                if (snapshot.exists() && snapshot.getChildrenCount()>0) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        model data = snap.getValue(model.class);
                        String item=data.getItem();
                        if(item.equals("Transport"))
                        totaltransport += Integer.parseInt(data.getAmount());
                        else if(item.equals("Food"))
                            totalfood+=Integer.parseInt(data.getAmount());
                        else if(item.equals("shopping"))
                            totalshopping+=Integer.parseInt(data.getAmount());
                        else if(item.equals("health"))
                            totalhealth+=Integer.parseInt(data.getAmount());
                        else if(item.equals("education"))
                            totaleducation+=Integer.parseInt(data.getAmount());
                        else if(item.equals("house"))
                            totalhouse+=Integer.parseInt(data.getAmount());
                        else if(item.equals("other"))
                            totalother+=Integer.parseInt(data.getAmount());
                        else if(item.equals("entertainment"))
                            totalentertainment+=Integer.parseInt(data.getAmount());

                    }
                    Pie pie = AnyChart.pie();
                    ArrayList<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Transport", totaltransport));
                    data.add(new ValueDataEntry("Food", totalfood));
                    data.add(new ValueDataEntry("shopping", totalshopping));
                    data.add(new ValueDataEntry("entertainment", totalentertainment));
                    data.add(new ValueDataEntry("education", totaleducation));
                    data.add(new ValueDataEntry("health", totalhealth));
                    data.add(new ValueDataEntry("house", totalhouse));
                    data.add(new ValueDataEntry("other", totalother));

                    pie.data(data);

                    pie.title("Today Analytics");

                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Items Spent On")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    anychartview.setChart(pie);
                }
                else
                {
                    Pie pie = AnyChart.pie();
                    ArrayList<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Today", 100));
                    pie.data(data);

                    pie.title("Today Analytics");

                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Items Spent On")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    anychartview.setChart(pie);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}