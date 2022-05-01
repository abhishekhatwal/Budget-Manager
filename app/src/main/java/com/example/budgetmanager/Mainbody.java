package com.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Mainbody extends AppCompatActivity {

    CardView budetcardview,todaycardview,weekcardview,monthcardview,balancecardview,analyticscardview;
    TextView budgetval,todayval,weekval,monthval,balanceval;
    DatabaseReference budgetRef, expensesRef, personalRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainbody);

        budetcardview=findViewById(R.id.budgetcardview);
        todaycardview=findViewById(R.id.todaycardview);
        weekcardview=findViewById(R.id.weekcardview);
        monthcardview=findViewById(R.id.monthcardview);
        balancecardview=findViewById(R.id.balancecardview);
        analyticscardview=findViewById(R.id.analyticscardview);

        budgetval=findViewById(R.id.budgetval);
        todayval=findViewById(R.id.todayval);
        weekval=findViewById(R.id.weekval);
        monthval=findViewById(R.id.monthval);
        balanceval=findViewById(R.id.balanceval);

        budetcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mainbody.this,budget_activity.class);
                startActivity(intent);
            }
        });

        todaycardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mainbody.this,today_activity.class);
                startActivity(intent);
            }
        });

        weekcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mainbody.this,months_week_activity.class);
                intent.putExtra("value","week");
                startActivity(intent);
            }
        });

        monthcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mainbody.this,months_week_activity.class);
                intent.putExtra("value","month");
                startActivity(intent);
            }
        });

        balancecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mainbody.this,balance.class);
                startActivity(intent);
            }
        });

        analyticscardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mainbody.this,Analytics.class);
                startActivity(intent);
            }
        });


        budgetRef = FirebaseDatabase.getInstance().getReference("budget");
        expensesRef = FirebaseDatabase.getInstance().getReference("expenses");
        personalRef=FirebaseDatabase.getInstance().getReference().child("balance");

        showbudgetval();
        showtodayval();
        showweekval();
        showmonthval();
        showbalanceval();

    }

    private void showbudgetval() {
       budgetRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               int totalAmount=0;
               if (snapshot.exists() && snapshot.getChildrenCount()>0){
                   for (DataSnapshot snap :  snapshot.getChildren())
                   {
                       model data = snap.getValue(model.class);
                       totalAmount += Integer.parseInt(data.getAmount());
                       String sTotal = String.valueOf("$"+ totalAmount);
                       budgetval.setText(sTotal);
                   }
               }
               else
                   budgetval.setText("$0");
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    private void showtodayval()
    {
        DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal=Calendar.getInstance();
        String date=dateFormat.format(cal.getTime());
        Query query = expensesRef.orderByChild("date").equalTo(date);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalAmount = 0;
                if (snapshot.exists() && snapshot.getChildrenCount()>0) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        model data = snap.getValue(model.class);
                        totalAmount += Integer.parseInt(data.getAmount());
                        String sTotal = String.valueOf("$" + totalAmount);
                        todayval.setText(sTotal);
                    }
                }
                else
                    todayval.setText("$0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showweekval()
    {
        MutableDateTime ob=new MutableDateTime();
        ob.setDate(0);
        DateTime now=new DateTime();
        Weeks weeks=Weeks.weeksBetween(ob,now);
        Query query = expensesRef.orderByChild("weeks").equalTo(weeks.getWeeks());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalAmount = 0;

                if (snapshot.exists() && snapshot.getChildrenCount()>0) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        model data = snap.getValue(model.class);
                        totalAmount += Integer.parseInt(data.getAmount());
                        String sTotal = String.valueOf("$" + totalAmount);
                        weekval.setText(sTotal);
                    }
                }
                else
                    weekval.setText("$0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void showmonthval()
    {
        MutableDateTime ob=new MutableDateTime();
        ob.setDate(0);
        DateTime now=new DateTime();
        Weeks weeks=Weeks.weeksBetween(ob,now);
        Months months=Months.monthsBetween(ob,now);
       Query query = expensesRef.orderByChild("months").equalTo(months.getMonths());

       query.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               int totalAmount = 0;
               if (snapshot.exists() && snapshot.getChildrenCount()>0) {
                   for (DataSnapshot snap : snapshot.getChildren()) {
                       model data = snap.getValue(model.class);
                       totalAmount += Integer.parseInt(data.getAmount());
                       String sTotal = String.valueOf("$" + totalAmount);
                       monthval.setText(sTotal);
                   }
               }
               else
                   monthval.setText("$0");
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    private void showbalanceval()
    {

        budgetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalbudgetamount = 0;
                for (DataSnapshot snap: snapshot.getChildren()) {
                    model data = snap.getValue(model.class);
                    totalbudgetamount += Integer.parseInt(data.getAmount());
                }
                personalRef.child("totalbudget").setValue(totalbudgetamount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalexpenseamount = 0;
                for (DataSnapshot snap: snapshot.getChildren()) {
                    model data = snap.getValue(model.class);
                    totalexpenseamount += Integer.parseInt(data.getAmount());
                }
                personalRef.child("totalexpense").setValue(totalexpenseamount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       personalRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               String tbudget= snapshot.child("totalbudget").getValue().toString();
               String texpense=  snapshot.child("totalexpense").getValue().toString();
               int total=Integer.parseInt(tbudget)-Integer.parseInt(texpense);
               if(total!=0) {
                   balanceval.setText("$"+String.valueOf(total));
               }
               else
                   balanceval.setText("$0");
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });



    }


}