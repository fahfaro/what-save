package com.example.whatsave.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsave.activities.AudioPlayer;
import com.example.whatsave.helper.Constants;
import com.example.whatsave.adapters.AudioFragmentAdapter;
import com.example.whatsave.models.AudioModel;
import com.example.whatsave.R;
import com.example.whatsave.interfaces.ClickInterface;
import com.example.whatsave.data.DBHelper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AudioFragment extends Fragment implements ClickInterface {
    private DBHelper dbHelper;
    private List<AudioModel> audioModels;
    private AudioFragmentAdapter audioFragmentAdapter;

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
        audioFragmentAdapter = new AudioFragmentAdapter(audioModels, this);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        recyclerView.setAdapter(audioFragmentAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped
                    (@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder,
                     int direction) {
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        long idfordelete = audioModels.get(viewHolder.getAdapterPosition()).getId();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder1.setTitle("Are you sure to delete?");
                        builder1.setPositiveButton("OK", (dialog1, which) -> {
                            dbHelper.deleteSelectedAudio(idfordelete);
                            audioFragmentAdapter.notifyDataSetChanged();
                        }).setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                audioFragmentAdapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog dialog1 = builder1.create();
                        dialog1.show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        //                        update the name of Audio file on left to Right Swipe

                        final String[] name = {null};
                        long id = audioModels.get(viewHolder.getAdapterPosition()).getId();
                        String title = audioModels.get(viewHolder.getAdapterPosition()).getTitle();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder.setTitle("Name");
                        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                        builder.setView(customLayout);
                        EditText editText1 = customLayout.findViewById(R.id.editText1);
                        builder.setPositiveButton("OK", (dialog, which) -> {
                            name[0] = editText1.getText().toString();
                            if (name[0].compareTo("") == 0) {
                                Toast.makeText(getContext(), "missing", Toast.LENGTH_SHORT).show();
                            } else {
                                dbHelper.updateAudio(id, name[0], title);
                                audioFragmentAdapter.notifyDataSetChanged();
                            }
                        }).setCancelable(false);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                }
            }

        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        audioFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), AudioPlayer.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        String title;
        long idfordelete = audioModels.get(position).getId();
        title = dbHelper.getAudioName(idfordelete);
        File path = Objects.requireNonNull(getContext()).getDir(Constants.DIR_NAME_AUDIO, Context.MODE_PRIVATE);
        File file = new File(path, title);
        Uri path1 = FileProvider.getUriForFile(getContext(), "com.example.whatsave.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("audio/*");
        startActivity(Intent.createChooser(shareIntent, null));
    }
}