package com.example.budgetmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class budget_adapter extends FirebaseRecyclerAdapter<model,budget_adapter.myviewholder> {



    public budget_adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull model model) {

        holder.itemselct.setText("Item: "+model.getItem());
        holder.itemamt.setText("Budget Amount: $"+model.getAmount());
        holder.itemnote.setText("note: "+model.getNote());
        holder.itemdate.setText("date: "+model.getDate());


        switch (model.getItem()) {
            case "others":
                holder.img.setImageResource(R.drawable.other);
                break;
            case "salary":
                holder.img.setImageResource(R.drawable.salary);
                break;
            case "savings":
                holder.img.setImageResource(R.drawable.savings);
                break;
        }


        holder.spentcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder myDialog= new AlertDialog.Builder(holder.itemamt.getContext());
                LayoutInflater inflater = LayoutInflater.from(holder.itemamt.getContext());
                View mView = inflater.inflate(R.layout.update_delete, null);

                myDialog.setView(mView);
                AlertDialog dialog = myDialog.create();

                 EditText mAmount = mView.findViewById(R.id.updateamount);
                 EditText mNotes = mView.findViewById(R.id.updatenote);
                Button deletespent=mView.findViewById(R.id.deletespent);
                Button updatespent=mView.findViewById(R.id.updatespent);

                mAmount.setText(model.getAmount());
                mNotes.setText(model.getNote());

                dialog.show();

               updatespent.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       String budgetamt=mAmount.getText().toString();
                       String budgetnote=mNotes.getText().toString();

                      Map<String,Object> mp=new HashMap<>();
                      mp.put("amount",budgetamt);
                      mp.put("note",budgetnote);

                       FirebaseDatabase.getInstance().getReference().child("budget").child(getRef(position).getKey()).updateChildren(mp);
                       dialog.dismiss();
                   }
               });

               deletespent.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       FirebaseDatabase.getInstance().getReference().child("budget").child(getRef(position).getKey()).removeValue();
                       dialog.dismiss();
                   }
               });



            }
        });


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        CardView spentcard;
        TextView itemselct,itemamt,itemnote,itemdate;
        ImageView img;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            spentcard=itemView.findViewById(R.id.cardviewspent);
            itemselct=itemView.findViewById(R.id.itemselct);
            itemamt=itemView.findViewById(R.id.itemamt);
            itemnote=itemView.findViewById(R.id.itemnote);
            itemdate=itemView.findViewById(R.id.itemdate);
            img=itemView.findViewById(R.id.img);
        }
    }
}
