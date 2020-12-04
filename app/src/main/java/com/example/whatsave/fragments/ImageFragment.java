package com.example.whatsave.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsave.activities.ViewImage;
import com.example.whatsave.helper.Constants;
import com.example.whatsave.interfaces.ClickInterface;
import com.example.whatsave.data.DBHelper;
import com.example.whatsave.adapters.ImageFragmentAdapter;
import com.example.whatsave.models.ImageModel;
import com.example.whatsave.R;

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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
                        long idfordelete = imageModels.get(viewHolder.getAdapterPosition()).getId();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder1.setTitle("Are you sure to delete?");
                        builder1.setPositiveButton("OK", (dialog1, which) -> {
                            dbHelper.deleteSelectedImage(idfordelete);
                            imageFragmentAdapter.notifyDataSetChanged();
                        }).setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imageFragmentAdapter.notifyDataSetChanged();
                            }
                        });
                        AlertDialog dialog1 = builder1.create();
                        dialog1.show();
                        break;
                    case ItemTouchHelper.RIGHT:
//                        update the name of Image file on left to Right Swipe

                        final String[] name = {null};
                        long id = imageModels.get(viewHolder.getAdapterPosition()).getId();
                        String title = imageModels.get(viewHolder.getAdapterPosition()).getTitle();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder.setTitle("Name");
                        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                        builder.setView(customLayout);
                        EditText editText1 = customLayout.findViewById(R.id.editText1);
                        builder.setPositiveButton("OK", (dialog, which) -> {
                            name[0] = editText1.getText().toString();
                            if (name[0].compareTo("") == 0) {
                                Toast.makeText(getContext(),
                                        "missing", Toast.LENGTH_SHORT).show();
                            } else {
                                dbHelper.updateImage(id, name[0], title);
                            }
                        }).setCancelable(false);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        imageFragmentAdapter.notifyDataSetChanged();
                        break;
                }
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
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
        Intent intent = new Intent(getContext(), ViewImage.class);
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
        Uri path1 = FileProvider.getUriForFile(getContext(), "com.example.whatsave.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, null));
    }
}