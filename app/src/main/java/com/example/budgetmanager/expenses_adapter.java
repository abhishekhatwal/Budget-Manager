package com.example.budgetmanager;

import android.app.AlertDialog;
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

import java.util.HashMap;
import java.util.Map;

public class expenses_adapter extends FirebaseRecyclerAdapter<model,expenses_adapter.myviewholder> {



    public expenses_adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.itemselct.setText("Item: "+model.getItem());
        holder.itemamt.setText("Amount spend: "+model.getAmount());
        holder.itemnote.setText("note: "+model.getNote());
        holder.itemdate.setText("date: "+model.getDate());

        switch (model.getItem()) {
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

                        FirebaseDatabase.getInstance().getReference().child("expenses").child(getRef(position).getKey()).updateChildren(mp);
                        dialog.dismiss();
                    }
                });

                deletespent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("expenses").child(getRef(position).getKey()).removeValue();
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
        return new expenses_adapter.myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        CardView spentcard;
        TextView itemselct,itemamt,itemnote,itemdate;
        ImageView img;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
            spentcard=itemView.findViewById(R.id.cardviewspent);
            itemselct=itemView.findViewById(R.id.itemselct);
            itemamt=itemView.findViewById(R.id.itemamt);
            itemnote=itemView.findViewById(R.id.itemnote);
            itemdate=itemView.findViewById(R.id.itemdate);
        }
    }
}
