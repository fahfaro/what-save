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

import java.util.List;

public class LocationFragmentAdapter extends RecyclerView.Adapter<LocationFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<LocationModel> location_models;

    public LocationFragmentAdapter(Context context, List<LocationModel> location_models) {
        this.context = context;
        this.location_models = location_models;
    }
    public void updateAdaterInsert(List<LocationModel> locationModelList) {
        this.location_models = locationModelList;
        notifyItemInserted(locationModelList.size());
    }
    @NonNull
    @NotNull
    @Override
    public LocationFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LocationFragmentAdapter.MyViewHolder holder, int position) {
        String name = location_models.get(position).getName();
        String title = location_models.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        }else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItem.class);
                context.startActivity(intent);
            }
        });
    }
    public int getPostion(int position) {
        return location_models.get(position).getId();
    }
    @Override
    public int getItemCount() {
        return location_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_location_name);
            mView = itemView;
        }
    }
}
