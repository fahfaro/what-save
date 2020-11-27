package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.helper.Constants;
import com.example.myapplication.models.ImageModel;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.ClickInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ImageFragmentAdapter extends RecyclerView.Adapter<ImageFragmentAdapter.MyViewHolder> {
    private final List<ImageModel> imageModels;
    private final ClickInterface clickInterface;
    public ImageFragmentAdapter( List<ImageModel> image_Models, ClickInterface clickInterface) {
        this.imageModels = image_Models;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public ImageFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageFragmentAdapter.MyViewHolder holder, int position) {
        String name = imageModels.get(position).getName();
        String title = imageModels.get(position).getTitle();
        File path = holder.itemView.getContext().getDir(Constants.DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            Glide.with(holder.itemView.getContext()).load(file).thumbnail().into(holder.thumbnail);
        }else{
            holder.thumbnail.setImageResource(R.drawable.ic_image);
        }
        
        if (name != null) {
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView t_name;
        ImageView thumbnail;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_image_name);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(v -> clickInterface.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                clickInterface.onLongItemClick(getAdapterPosition());
                return true;
            });
        }

    }
}
