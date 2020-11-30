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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.activities.VideoPlayer;
import com.example.myapplication.helper.Constants;
import com.example.myapplication.interfaces.ClickInterface;
import com.example.myapplication.data.DBHelper;
import com.example.myapplication.R;
import com.example.myapplication.adapters.VideoFragmentAdapter;
import com.example.myapplication.models.VideoModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class VideoFragment extends Fragment implements ClickInterface {
    private VideoFragmentAdapter videoFragmentAdapter;
    private DBHelper dbHelper;
    private List<VideoModel> videoModels;
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
        RecyclerView recyclerView = view.findViewById(R.id.vid_recycler);
        dbHelper = new DBHelper(view.getContext());

        videoModels = dbHelper.getVideoDataSql();
        videoFragmentAdapter = new VideoFragmentAdapter(view.getContext(), videoModels, this);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        recyclerView.setAdapter(videoFragmentAdapter);
        videoFragmentAdapter.updateAdaterInsert(videoModels);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.HORIZONTAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

//    String deletedVideoName = null, deletedVideoTItle = null;
//    long deletedVideoId;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped
                (@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, int direction) {

            switch (direction) {
                case ItemTouchHelper.LEFT:
//                    deletedVideoId = videoModels.get(position).getId();
//                    deletedVideoName = videoModels.get(position).getName();
//                    deletedVideoTItle = videoModels.get(position).getTitle();
                    Toast.makeText(getContext(), "Left", Toast.LENGTH_SHORT).show();
//                    dbHelper.deleteSelectedVideo(videoFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
                    videoFragmentAdapter.notifyDataSetChanged();
//                    Snackbar.make(recyclerView,deletedVideoName, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            videoModels.add(position,dbHelper.insertVideoData(deletedVideoId,deletedVideoName,deletedVideoTItle));
//                        }
//                    }).show();
//                    videoFragmentAdapter.notifyDataSetChanged();
                    break;
                case ItemTouchHelper.RIGHT:
                    Toast.makeText(getContext(), "Rigth", Toast.LENGTH_SHORT).show();
                    videoFragmentAdapter.notifyDataSetChanged();
                    break;
            }
        }

//        @Override
//        public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.teal_200))
//                    .addSwipeLeftActionIcon(R.drawable.ic_bin)
//                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.teal_700))
//                    .addSwipeRightActionIcon(R.drawable.ic_detail)
//                    .create()
//                    .decorate();
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
    };

    @Override
    public void onItemClick(int position) {
        String title;
        long idfordelete = videoModels.get(position).getId();
        title = dbHelper.getVideoName(idfordelete);
        Intent intent = new Intent(getContext(), VideoPlayer.class);
        intent.putExtra("video", title);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        String title;
        long idfordelete = videoModels.get(position).getId();
        title = dbHelper.getVideoName(idfordelete);
        File path = Objects.requireNonNull(getContext()).getDir(Constants.DIR_NAME_VIDEO, Context.MODE_PRIVATE);
        File file = new File(path, title);
        Uri path1 = FileProvider.getUriForFile(getContext(), "com.example.myapplication.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("video/*");
        startActivity(Intent.createChooser(shareIntent, null));
    }
}