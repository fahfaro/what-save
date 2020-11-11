package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DocumentFragmentAdapter extends RecyclerView.Adapter<DocumentFragmentAdapter.MyViewHolder> {
    private Context context;
    private List<DocumentModel> documentModels;

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
        String name = documentModels.get(position).getName();
        if (name != null) {
            holder.t_name.setText(name);
        }else {
            holder.t_name.setText("Empty");
        }
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

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_document_name);
        }
    }
}
