package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VideoFragmentAdapter extends RecyclerView.Adapter<VideoFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<VideoModel> videoModels;
    public static int pos;
    private DBHelper dbHelper;

    public VideoFragmentAdapter(Context context, List<VideoModel> videoModels) {
        this.context = context;
        this.videoModels = videoModels;
    }
    public void updateAdaterInsert(List<VideoModel> videoModelList) {
        this.videoModels = videoModelList;
        notifyItemInserted(videoModelList.size());
    }
    @NonNull
    @NotNull
    @Override
    public VideoFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideoFragmentAdapter.MyViewHolder holder, int position) {
        String name = videoModels.get(position).getName();
        dbHelper = new DBHelper(context);
        if (name != null) {
            holder.t_name.setText(name);
        }else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = dbHelper.getVideoName(position);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("key",title);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView t_name;
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_video_name);
            mView = itemView;
        }
    }
}
