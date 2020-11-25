package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.AudioModel;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.ClickInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AudioFragmentAdapter extends RecyclerView.Adapter<AudioFragmentAdapter.MyViewHolder> {
    private final List<AudioModel> audioModels;
    private final ClickInterface clickInterface;

    public AudioFragmentAdapter(List<AudioModel> audioModels, ClickInterface clickInterface) {
        this.audioModels = audioModels;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @NotNull
    @Override
    public AudioFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_view, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull AudioFragmentAdapter.MyViewHolder holder, int position) {
        String name = audioModels.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        return audioModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        ImageView thumbnail;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_audio_name);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(v -> clickInterface.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                clickInterface.onLongItemClick(getAdapterPosition());
                return true;
            });
        }
    }
}
