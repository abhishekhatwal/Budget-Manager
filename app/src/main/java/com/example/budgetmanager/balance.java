package com.example.budgetmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class balance extends AppCompatActivity {

    TextView headingshowbalance,totalbudget,totalspending;
    RecyclerView recyclerviewtotal,recyclerviewcredit;
    balance_adapter adapterbal;
    expense_adapter adapterexp;
    ArrayList<model> list1,list2;

    DatabaseReference budgetref,expenseref,personalref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        headingshowbalance=findViewById(R.id.headingshowbalance);
        totalbudget=findViewById(R.id.totalbudget);
        totalspending=findViewById(R.id.totalspending);
        recyclerviewtotal=findViewById(R.id.recyclerviewtotal);
        recyclerviewcredit=findViewById(R.id.recyclerviewcredit);

        recyclerviewtotal.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewcredit.setLayoutManager(new LinearLayoutManager(this));

        list1=new ArrayList<>();
        list2=new ArrayList<>();

        adapterbal=new balance_adapter(balance.this,list1);
        adapterexp=new expense_adapter(balance.this,list2);


        budgetref= FirebaseDatabase.getInstance().getReference().child("budget");
        expenseref= FirebaseDatabase.getInstance().getReference().child("expenses");
        personalref=FirebaseDatabase.getInstance().getReference().child("balance");

        budgetref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    model data=ds.getValue(model.class);
                    list1.add(data);
                }
                recyclerviewtotal.setAdapter(adapterbal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        expenseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    model data=ds.getValue(model.class);
                    list2.add(data);
                }
                recyclerviewcredit.setAdapter(adapterexp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        personalref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tbudget= snapshot.child("totalbudget").getValue().toString();
                String texpense=  snapshot.child("totalexpense").getValue().toString();
                int total=Integer.parseInt(tbudget)-Integer.parseInt(texpense);
                totalbudget.setText(String.valueOf("total budget $"+tbudget));
                totalspending.setText(String.valueOf("total spending $"+texpense));
                headingshowbalance.setText(String.valueOf("balance $"+total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}