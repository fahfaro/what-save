package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class AudioFragmentAdapter extends RecyclerView.Adapter<AudioFragmentAdapter.MyViewHolder> {
    private Context context;

    public AudioFragmentAdapter(Context context) {
        this.context = context;
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
        holder.t_name.setText("Audio Fragment");
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_audio_name);
        }
    }
}
