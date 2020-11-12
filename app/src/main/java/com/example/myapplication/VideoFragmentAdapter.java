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

public class VideoFragmentAdapter extends RecyclerView.Adapter<VideoFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<VideoModel> videoModels;
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
        } else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title;
                title = dbHelper.getVideoName(position);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("Video", title);
                context.startActivity(intent);

            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                context.startActivity(shareIntent);
                return false;
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
