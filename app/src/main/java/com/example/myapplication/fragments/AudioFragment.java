package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.activities.AudioPlayer;
import com.example.myapplication.helper.Constants;
import com.example.myapplication.adapters.AudioFragmentAdapter;
import com.example.myapplication.models.AudioModel;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.ClickInterface;
import com.example.myapplication.data.DBHelper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AudioFragment extends Fragment implements ClickInterface {
    private DBHelper dbHelper;
    List<AudioModel> audioModels;

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
        RecyclerView recyclerView = view.findViewById(R.id.audio_recycler);
        dbHelper = new DBHelper(view.getContext());

        audioModels = dbHelper.getAudioDataSql();
        AudioFragmentAdapter audioFragmentAdapter = new AudioFragmentAdapter(audioModels, this);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        recyclerView.setAdapter(audioFragmentAdapter);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.HORIZONTAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped
//                    (@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder,
//                     int direction) {
//                dbHelper.deleteSelectedAudio(audioFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
//            }
//
//        });
//
//        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(int position) {
        String title;
        long idfordelete = audioModels.get(position).getId();
        title = dbHelper.getAudioName(idfordelete);
        File path = Objects.requireNonNull(getContext()).getDir(Constants.DIR_NAME_AUDIO, Context.MODE_PRIVATE);
        File file = new File(path, title);
        Intent intent = new Intent(getContext(), AudioPlayer.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        String title;
        long idfordelete = audioModels.get(position).getId();
        title = dbHelper.getAudioName(idfordelete);
        File path = Objects.requireNonNull(getContext()).getDir(Constants.DIR_NAME_AUDIO, Context.MODE_PRIVATE);
        File file = new File(path, title);
        Uri path1 = FileProvider.getUriForFile(getContext(), "com.example.myapplication.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("audio/*");
        startActivity(Intent.createChooser(shareIntent, null));
    }

    private ArrayList<File> findMusicFiles(File file) {
        ArrayList<File> musicfileobject = new ArrayList<>();
        File[] files = file.listFiles();

        for (File currentFiles : files) {

            if (currentFiles.isDirectory() && !currentFiles.isHidden()) {
                musicfileobject.addAll(findMusicFiles(currentFiles));
            } else {
                if (currentFiles.getName().endsWith(".mp3") || currentFiles.getName().endsWith(".mp4a") || currentFiles.getName().endsWith(".wav")) {
                    musicfileobject.add(currentFiles);
                }
            }
        }

        return musicfileobject;
    }
}