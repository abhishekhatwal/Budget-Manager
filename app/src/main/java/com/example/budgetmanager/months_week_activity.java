package com.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Calendar;

public class months_week_activity extends AppCompatActivity {

    TextView budgetheading;
    RecyclerView recyclerView;
    expenses_adapter adapter;

    FloatingActionButton fab;
    DatabaseReference budgetref;
    FirebaseAuth auth;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months_week_activity);

        budgetheading=findViewById(R.id.budgetheading);
        recyclerView=findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);

        auth=FirebaseAuth.getInstance();

        MutableDateTime ob=new MutableDateTime();
        ob.setDate(0);
        DateTime now=new DateTime();
        Weeks weeks=Weeks.weeksBetween(ob,now);
        Months months=Months.monthsBetween(ob,now);

        budgetref= FirebaseDatabase.getInstance().getReference().child("expenses");

        if(getIntent().getExtras()!=null)
        {
            String value=getIntent().getStringExtra("value");
            if(value.equals("week"))
                query = budgetref.orderByChild("weeks").equalTo(weeks.getWeeks());
            else if(value.equals("month"))
                query = budgetref.orderByChild("months").equalTo(months.getMonths());
        }


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalAmount = 0;
                String value=getIntent().getStringExtra("value");
                if (snapshot.exists() && snapshot.getChildrenCount()>0) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        model data = snap.getValue(model.class);
                        totalAmount += Integer.parseInt(data.getAmount());
                        if(value.equals("week")) {
                            String sTotal = String.valueOf("total spending of this week: $" + totalAmount);
                            budgetheading.setText(sTotal);
                        }
                        else if(value.equals("month")) {
                            String sTotal = String.valueOf("total spending of this month: $" + totalAmount);
                            budgetheading.setText(sTotal);
                        }
                    }
                }
                else
                    budgetheading.setText("spending is: $0");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(query, model.class)
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new expenses_adapter(options);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

    }

    public void add()
    {
        AlertDialog.Builder mydialog=new AlertDialog.Builder(months_week_activity.this);
        LayoutInflater inflater=LayoutInflater.from(months_week_activity.this);
        View view=inflater.inflate(R.layout.input,null);
        mydialog.setView(view);

        AlertDialog dialog=mydialog.create();
        dialog.setCancelable(false);

        Spinner itemspinner=view.findViewById(R.id.spinner);
        EditText amount=view.findViewById(R.id.amount);
        EditText note=view.findViewById(R.id.note);
        Button cancel=view.findViewById(R.id.cancel);
        Button save=view.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetamt=amount.getText().toString();
                String budgetnote=note.getText().toString();
                String budgetitm=itemspinner.getSelectedItem().toString();

                if(TextUtils.isEmpty(budgetamt))
                    Toast.makeText(months_week_activity.this,"select budget amount",Toast.LENGTH_SHORT).show();
                if(budgetitm.equals("select item"))
                    Toast.makeText(months_week_activity.this,"select valid item",Toast.LENGTH_SHORT).show();

                else{
                    String id=budgetref.push().getKey();

                    DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal=Calendar.getInstance();
                    String date=dateFormat.format(cal.getTime());

                    MutableDateTime ob=new MutableDateTime();
                    ob.setDate(0);
                    DateTime now=new DateTime();
                    Weeks weeks=Weeks.weeksBetween(ob,now);
                    Months months=Months.monthsBetween(ob,now);

                    model data=new model(budgetitm,budgetnote,budgetamt,date,weeks.getWeeks(),months.getMonths());
                    budgetref.push().setValue(data);

                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }


}