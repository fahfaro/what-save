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
import java.util.SplittableRandom;

public class DocumentFragmentAdapter extends RecyclerView.Adapter<DocumentFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<DocumentModel> documentModels;
    private DBHelper dbHelper;
    private static final String DIR_NAME_DOCUMENT = "document";

    public DocumentFragmentAdapter(Context context, List<DocumentModel> documentModels) {
        this.context = context;
        this.documentModels = documentModels;
    }

    public void updateAdaterInsert(List<DocumentModel> documentModelList) {
        this.documentModels = documentModelList;
        notifyItemInserted(documentModelList.size());
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.document_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DocumentFragmentAdapter.MyViewHolder holder, int position) {
        dbHelper = new DBHelper(context);
        String name = documentModels.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = dbHelper.getDocumnetName(position);
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("document", title);
                context.startActivity(intent);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title;
                title = dbHelper.getDocumnetName(position);
                File path = context.getDir(DIR_NAME_DOCUMENT, Context.MODE_PRIVATE);
                File file = new File(path, title);
                Uri path1 = FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", file);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, path1);
                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("document/*");
                context.startActivity(Intent.createChooser(shareIntent, null));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return documentModels.size();
    }

    public int getPostion(int position) {
        return documentModels.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t_name;
        View mView;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_document_name);
            mView = itemView;
        }
    }
}
