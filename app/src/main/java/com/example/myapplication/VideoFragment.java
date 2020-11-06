package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VideoFragment extends Fragment {
    private RecyclerView recyclerView;
    private VideoFragmentAdapter videoFragmentAdapter;
    private DBHelper dbHelper;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.vid_recycler);
        dbHelper = new DBHelper(view.getContext());
        List<VideoModel> videoModels = dbHelper.getVideoDataSql();
        videoFragmentAdapter = new VideoFragmentAdapter(view.getContext(),videoModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(videoFragmentAdapter);
        videoFragmentAdapter.updateAdaterInsert(videoModels);
    }
}