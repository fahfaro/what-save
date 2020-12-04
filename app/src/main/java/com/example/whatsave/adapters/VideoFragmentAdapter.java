package com.example.whatsave.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsave.R;
import com.example.whatsave.models.VideoModel;
import com.example.whatsave.data.DBHelper;
import com.example.whatsave.interfaces.ClickInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class VideoFragmentAdapter extends RecyclerView.Adapter<VideoFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<VideoModel> videoModels;
    private DBHelper dbHelper;
    private final ClickInterface clickInterface;
    private static final String DIR_NAME_VIDEO = "video";

    public VideoFragmentAdapter(Context context, List<VideoModel> videoModels, ClickInterface clickInterface) {
        this.context = context;
        this.videoModels = videoModels;
        this.clickInterface = clickInterface;
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
        String title = videoModels.get(position).getTitle();
        dbHelper = new DBHelper(context);
        File path = context.getDir(DIR_NAME_VIDEO, Context.MODE_PRIVATE);
        File file = new File(path, title);
        Glide.with(context).load(file).thumbnail(0.1f).into(holder.thumbnail);
        if (name[0] != null) {
            holder.t_name.setSelected(true);
            holder.t_name.setText(name[0]);
        } else {
            holder.t_name.setText("Empty");
        }
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
        ImageView thumbnail;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_video_name);
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
