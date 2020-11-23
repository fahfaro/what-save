package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class PdfFragmentAdapter extends RecyclerView.Adapter<PdfFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<PdfModel> pdf_models;
    private DBHelper dbHelper;
    private static final String DIR_NAME_PDF = "pdf";

    public PdfFragmentAdapter(Context context, List<PdfModel> pdf_models) {
        this.context = context;
        this.pdf_models = pdf_models;
    }

    public void updateAdaterInsert(List<PdfModel> pdfModelList) {
        this.pdf_models = pdfModelList;
        notifyItemInserted(pdfModelList.size());
    }

    @NonNull
    @NotNull
    @Override
    public PdfFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PdfFragmentAdapter.MyViewHolder holder, int position) {
        dbHelper = new DBHelper(context);
        String name = pdf_models.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long idfordelete = pdf_models.get(position).getId();
                String title = dbHelper.getPdfName(idfordelete);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("pdf", title);
                context.startActivity(intent);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title;
                long idfordelete = pdf_models.get(position).getId();
                title = dbHelper.getPdfName(idfordelete);
                File path = context.getDir(DIR_NAME_PDF, Context.MODE_PRIVATE);
                File file = new File(path, title);
                Uri path1 = FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", file);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("application/pdf");
                context.startActivity(Intent.createChooser(shareIntent, null));
                return false;
            }
        });
    }

    public long getPostion(int position) {
        return pdf_models.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return pdf_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_location_name);
            mView = itemView;
        }
    }
}
