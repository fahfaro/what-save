package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MainAdapter.MyViewHolder holder, int position) {
        holder.t_name.setText("Hamza");
        holder.t_date.setText("2/10/2002");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsContact.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name, t_date;
        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.contact_name);
            t_date = itemView.findViewById(R.id.date_stamp);
            mView = itemView;
        }
    }
}
