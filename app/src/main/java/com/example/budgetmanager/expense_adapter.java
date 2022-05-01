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

public class expense_adapter extends RecyclerView.Adapter<expense_adapter.myviewholder> {

   Context context;
    ArrayList<model> list2;

    public expense_adapter(Context context, ArrayList<model> list2) {
        this.context = context;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fetch,parent,false);
        return new expense_adapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.itemselect.setText("Item: "+list2.get(position).getItem());
        holder.itemamount.setText("Amount spend: "+list2.get(position).getAmount());
        holder.itemnote.setText("note: "+list2.get(position).getNote());
        holder.itemdate.setText("date: "+list2.get(position).getDate());

        switch (list2.get(position).getItem()) {
            case "Transport":
                holder.img.setImageResource(R.drawable.transport);
                break;
            case "Food":
                holder.img.setImageResource(R.drawable.food);
                break;
            case "house":
                holder.img.setImageResource(R.drawable.house);
                break;
            case "entertainment":
                holder.img.setImageResource(R.drawable.entertainment);
                break;
            case "education":
                holder.img.setImageResource(R.drawable.education);
                break;
            case "shopping":
                holder.img.setImageResource(R.drawable.shopping);
                break;
            case "health":
                holder.img.setImageResource(R.drawable.health);
                break;
            case "others":
                holder.img.setImageResource(R.drawable.other);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list2.size();
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
