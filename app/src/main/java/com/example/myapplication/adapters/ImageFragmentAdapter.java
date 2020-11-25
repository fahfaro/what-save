package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.ImageModel;
import com.example.myapplication.R;
import com.example.myapplication.data.DBHelper;
import com.example.myapplication.interfaces.ClickInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageFragmentAdapter extends RecyclerView.Adapter<ImageFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<ImageModel> imageModels;
    private DBHelper dbHelper;
    private ClickInterface clickInterface;
    private static final String DIR_NAME_IMAGE = "image";

    public ImageFragmentAdapter(Context context, List<ImageModel> image_Models, ClickInterface clickInterface) {
        this.context = context;
        this.imageModels = image_Models;
        this.clickInterface = clickInterface;
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
        String title = imageModels.get(position).getTitle();
        File path = context.getDir(DIR_NAME_IMAGE, context.MODE_PRIVATE);
        File file = new File(path, title);
        if (file.exists()) {
            Bitmap myBitmap = null;
            Size size = new Size(110, 65);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                try {
                    myBitmap = ThumbnailUtils.createImageThumbnail(file, size,null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            holder.thumbnail.setImageBitmap(myBitmap);
        }else{
            holder.thumbnail.setImageResource(R.drawable.ic_image);
        }

//        Glide.with(context).load(file).thumbnail().into(holder.thumbnail);
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

    public long getPostion(int position) {
        return imageModels.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView t_name;
        ImageView thumbnail;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_image_name);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickInterface.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }

    }
}
