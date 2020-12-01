package com.example.whatsave.fragments;

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

import com.example.whatsave.helper.Constants;
import com.example.whatsave.interfaces.ClickInterface;
import com.example.whatsave.data.DBHelper;
import com.example.whatsave.adapters.PdfFragmentAdapter;
import com.example.whatsave.models.PdfModel;
import com.example.whatsave.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class PdfFragment extends Fragment implements ClickInterface {
    private DBHelper dbHelper;
    private List<PdfModel> pdfModels;
    public PdfFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return (inflater.inflate(R.layout.fragment_pdf, container, false));
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.pdf_recycler);
        dbHelper = new DBHelper(view.getContext());

        pdfModels = dbHelper.getPdfDataSql();
        PdfFragmentAdapter pdfFragmentAdapter = new PdfFragmentAdapter(pdfModels, this);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),4));
        recyclerView.setAdapter(pdfFragmentAdapter);
        pdfFragmentAdapter.updateAdaterInsert(pdfModels);
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
//                dbHelper.deleteSelectedPdf(pdfFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
//            }
//
//        });
//
//        itemTouchHelper.attachToRecyclerView(recyclerView);

    }


    @Override
    public void onItemClick(int position) {
//        long idfordelete = pdfModels.get(position).getId();
//        String title = dbHelper.getPdfName(idfordelete);
//        Intent intent = new Intent(getContext(), ViewItem.class);
//        intent.putExtra("pdf", title);
//        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        String title;
        long idfordelete = pdfModels.get(position).getId();
        title = dbHelper.getPdfName(idfordelete);
        File path = Objects.requireNonNull(getContext()).getDir(Constants.DIR_NAME_PDF, Context.MODE_PRIVATE);
        File file = new File(path, title);
        Uri path1 = FileProvider.getUriForFile(getContext(), "com.example.whatsave.fileprovider", file);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("application/pdf");
        startActivity(Intent.createChooser(shareIntent, null));
    }
}