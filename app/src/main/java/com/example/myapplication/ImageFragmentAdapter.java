package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class ImageFragmentAdapter extends RecyclerView.Adapter<ImageFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<ImageModel> imageModels;
    private DBHelper dbHelper;
    private static final String DIR_NAME_IMAGE = "image";
    private ImageFragment imageFragment;

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
        long idfordelete = imageModels.get(position).getId();
        String name = imageModels.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long idfordelete = imageModels.get(position).getId();
                String title = dbHelper.getImageName(idfordelete);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("image", title);
                context.startActivity(intent);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title;
                long idfordelete = imageModels.get(position).getId();
                title = dbHelper.getImageName(idfordelete);
                File path = context.getDir(DIR_NAME_IMAGE, Context.MODE_PRIVATE);
                File file = new File(path, title);
                Uri path1 = FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", file);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("image/*");
                context.startActivity(Intent.createChooser(shareIntent, null));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public long getPostion(int position) {
        return imageModels.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        CheckBox checkBox;
        View mView;
        ImageFragment imageFragment;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull @NotNull View itemView/*, ImageFragment imageFragment*/) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_image_name);
            checkBox = itemView.findViewById(R.id.checkbox);
            this.imageFragment = imageFragment;
            mView = itemView;
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
