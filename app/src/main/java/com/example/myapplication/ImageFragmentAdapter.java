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

public class ImageFragmentAdapter extends RecyclerView.Adapter<ImageFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<ImageModel> imageModels;
    private DBHelper dbHelper;

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
        dbHelper = new DBHelper(context);
        String name = imageModels.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = dbHelper.getImageName(position);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("Image", title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public int getPostion(int position) {
        return imageModels.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_image_name);
            mView = itemView;
        }
    }
}
