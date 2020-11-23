package com.example.myapplication;

import android.media.audiofx.AcousticEchoCanceler;
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

import java.util.ArrayList;
import java.util.List;


public class ImageFragment extends Fragment {
    private DBHelper dbHelper;
    private List<ImageModel> imageModels;
    private RecyclerView recyclerView;
    private ImageFragmentAdapter imageFragmentAdapter;
    //    private ArrayList<ImageModel> selectionList = new ArrayList<>();
    private int counter = 0;
    public boolean isActionMode = false;

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
        recyclerView = view.findViewById(R.id.img_recycler);
        dbHelper = new DBHelper(view.getContext());

        imageModels = dbHelper.getImageDataSql();
        imageFragmentAdapter = new ImageFragmentAdapter(view.getContext(), imageModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(imageFragmentAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @org.jetbrains.annotations.NotNull RecyclerView recyclerView, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder, @NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped
                    (@NonNull @org.jetbrains.annotations.NotNull RecyclerView.ViewHolder viewHolder,
                     int direction) {
                dbHelper.deleteSelectedImage(imageFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

//    public void startSelection(int index) {
//        if(isActionMode){
//            isActionMode = true;
//            selectionList.add(imageModels.get(index));
//            counter++;
//
//        }
//    }
}