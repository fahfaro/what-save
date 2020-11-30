package com.example.myapplication.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.helper.Constants;
import com.example.myapplication.interfaces.ClickInterface;
import com.example.myapplication.data.DBHelper;
import com.example.myapplication.adapters.ImageFragmentAdapter;
import com.example.myapplication.models.ImageModel;
import com.example.myapplication.R;
import com.example.myapplication.activities.ViewItem;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class ImageFragment extends androidx.fragment.app.Fragment implements ClickInterface {
    private DBHelper dbHelper;
    private List<ImageModel> imageModels;
    private ImageFragmentAdapter imageFragmentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.img_recycler);
        dbHelper = new DBHelper(view.getContext());
        swipeRefreshLayout = view.findViewById(R.id.swipe_refreshlayout_image);
        imageModels = dbHelper.getImageDataSql();
        imageFragmentAdapter = new ImageFragmentAdapter(imageModels, this);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        recyclerView.setAdapter(imageFragmentAdapter);
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
//                dbHelper.deleteSelectedImage(imageFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
//            }
//
//        });
//
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(this::refreshStatus);


    }

    private void refreshStatus() {
        imageFragmentAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(int position) {
        long idfordelete = imageModels.get(position).getId();
        String title = dbHelper.getImageName(idfordelete);
        Intent intent = new Intent(getContext(), ViewItem.class);
        intent.putExtra("image", title);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        String title;
        long idfordelete = imageModels.get(position).getId();
        title = dbHelper.getImageName(idfordelete);
        File path = Objects.requireNonNull(getContext()).getDir(Constants.DIR_NAME_IMAGE, MODE_PRIVATE);
        File file = new File(path, title);
        Uri path1 = FileProvider.getUriForFile(getContext(), "com.example.myapplication.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, null));
    }
}