package com.example.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoctionFragment extends Fragment {
    private static final String IMAGE_NAME = "5066";
    private RecyclerView recyclerView;
    private LocationFragmentAdapter locationFragmentAdapter;
    private DBHelper dbHelper;
    private static final String DIR_NAME_IMAGE = "Images";
    private ImageView mImageView;

    public LoctionFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_location, container, false));
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        recyclerView = view.findViewById(R.id.loc_recycler);
//        dbHelper = new DBHelper(view.getContext());
//        List<LocationModel> locationModels = dbHelper.getDataSql();
//        locationFragmentAdapter = new LocationFragmentAdapter(view.getContext(), locationModels);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        recyclerView.setAdapter(locationFragmentAdapter);
        mImageView = view.findViewById(R.id.myImageView);
//        ContextWrapper cw = new ContextWrapper(view.getContext());
//        File path = getContext().getDir(DIR_NAME_IMAGE, Context.MODE_PRIVATE);
//        File file = new File(path, IMAGE_NAME + ".JPEG");
//        mImageView.setImageDrawable(Drawable.createFromPath(file.toString()));
//        Bitmap image = null;
//        File path = getContext().getDir(DIR_NAME_IMAGE, Context.MODE_PRIVATE);
//        File file = new File(path, IMAGE_NAME + ".JPEG");
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(file);
//            image = BitmapFactory.decodeStream(inputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.d("TAG", String.valueOf(e));
//        }
//        mImageView.setImageBitmap(image);
    }

    private void readImage(View view) {
//        Bitmap image = null;
//        File path = getContext().getDir(DIR_NAME_IMAGE, Context.MODE_PRIVATE);
//        File file = new File(path, IMAGE_NAME);
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(file);
//            image = BitmapFactory.decodeStream(inputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        mImageView.setImageBitmap(image);
    }
}