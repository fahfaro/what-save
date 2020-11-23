package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class AudioFragmentAdapter extends RecyclerView.Adapter<AudioFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<AudioModel> audioModels;
    private DBHelper dbHelper;
    private static final String DIR_NAME_AUDIO = "audio";

    public AudioFragmentAdapter(Context context, List<AudioModel> audioModels) {
        this.context = context;
        this.audioModels = audioModels;
    }

    @NonNull
    @NotNull
    @Override
    public AudioFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AudioFragmentAdapter.MyViewHolder holder, int position) {
        String name = audioModels.get(position).getName();
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
                long idfordelete = audioModels.get(position).getId();
                title = dbHelper.getAudioName(idfordelete);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("audio", title);
                context.startActivity(intent);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title;
                long idfordelete = audioModels.get(position).getId();
                title = dbHelper.getAudioName(idfordelete);
                File path = context.getDir(DIR_NAME_AUDIO, Context.MODE_PRIVATE);
                File file = new File(path, title);
                Uri path1 = FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", file);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("audio/*");
                context.startActivity(Intent.createChooser(shareIntent, null));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }

    public long getPostion(int position) {
        return audioModels.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_audio_name);
            mView = itemView;
        }
    }
}
