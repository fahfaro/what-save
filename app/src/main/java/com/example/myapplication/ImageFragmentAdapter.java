package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageFragmentAdapter extends RecyclerView.Adapter<ImageFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<ImageModel> imageModels;

    public ImageFragmentAdapter(Context context, List<ImageModel> image_Models) {
        this.context = context;
        this.imageModels = image_Models;
    }

    @NonNull
    @NotNull
    @Override
    public ImageFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageFragmentAdapter.MyViewHolder holder, int position) {
        String name = imageModels.get(1).getName();
        if (name != null) {
            holder.t_name.setText(name);
        }else {
            holder.t_name.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_image_name);
        }
    }
}
