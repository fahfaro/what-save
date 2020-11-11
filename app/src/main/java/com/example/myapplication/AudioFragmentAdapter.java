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

public class AudioFragmentAdapter extends RecyclerView.Adapter<AudioFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<AudioModel> audioModels;

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
        if (name != null) {
            holder.t_name.setText(name);
        }else {
            holder.t_name.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }
    public int getPostion(int position) {
        return audioModels.get(position).getId();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_audio_name);
        }
    }
}
