package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.util.List;

public class VideoFragmentAdapter extends RecyclerView.Adapter<VideoFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<VideoModel> videoModels;
    private DBHelper dbHelper;
    private static final String DIR_NAME_VIDEO = "video";

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
        final String[] name = {videoModels.get(position).getName()};
        dbHelper = new DBHelper(context);
        if (name[0] != null) {
            holder.t_name.setText(name[0]);
        } else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title;
                long idfordelete = videoModels.get(position).getId();
                title = dbHelper.getVideoName(idfordelete);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("video", title);
                context.startActivity(intent);

            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title;
                long idfordelete = videoModels.get(position).getId();
                title = dbHelper.getVideoName(idfordelete);
                File path = context.getDir(DIR_NAME_VIDEO,Context.MODE_PRIVATE);
                File file = new File(path, title);
                Uri path1 = FileProvider.getUriForFile(context,"com.example.myapplication.fileprovider",file);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
                shareIntent.setFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("video/*");
                context.startActivity(Intent.createChooser(shareIntent, null));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModels.size();
    }

    public long getPostion(int position) {
        return videoModels.get(position).getId();
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
