package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AudioFragment extends Fragment {
    private RecyclerView recyclerView;
    private AudioFragmentAdapter audioFragmentAdapter;
    private DBHelper dbHelper;

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.audio_recycler);
        dbHelper = new DBHelper(view.getContext());
        List<AudioModel> audioModels = dbHelper.getAudioDataSql();
        audioFragmentAdapter = new AudioFragmentAdapter(view.getContext(), audioModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(audioFragmentAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped
                    (@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder,
                     int direction) {
                dbHelper.deleteSelectedAudio(audioFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}