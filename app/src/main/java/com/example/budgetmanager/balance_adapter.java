package com.example.budgetmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class balance_adapter extends RecyclerView.Adapter<balance_adapter.myviewholder> {

    Context context;
    ArrayList<model> list1;

    public balance_adapter(Context context, ArrayList<model> list1) {
        this.context = context;
        this.list1 = list1;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.fetch,parent,false);
       return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
       holder.itemselect.setText("Item: "+list1.get(position).getItem());
        holder.itemamount.setText("Budget Amount: "+list1.get(position).getAmount());
        holder.itemnote.setText("note: "+list1.get(position).getNote());
        holder.itemdate.setText("date: "+list1.get(position).getDate());

        switch (list1.get(position).getItem()) {
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
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView itemselect,itemamount,itemdate,itemnote;
        ImageView img;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            itemselect=itemView.findViewById(R.id.itemselct);
            itemamount=itemView.findViewById(R.id.itemamt);
            itemdate=itemView.findViewById(R.id.itemdate);
            itemnote=itemView.findViewById(R.id.itemnote);
        }
    }
}
