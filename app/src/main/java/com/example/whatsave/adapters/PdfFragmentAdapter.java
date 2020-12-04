package com.example.whatsave.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsave.models.PdfModel;
import com.example.whatsave.R;
import com.example.whatsave.interfaces.ClickInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PdfFragmentAdapter extends RecyclerView.Adapter<PdfFragmentAdapter.MyViewHolder> {
    private List<PdfModel> pdf_models;
    private ClickInterface clickInterface;

    public PdfFragmentAdapter( List<PdfModel> pdf_models, ClickInterface clickInterface) {
        this.pdf_models = pdf_models;
        this.clickInterface = clickInterface;
    }

    public void updateAdaterInsert(List<PdfModel> pdfModelList) {
        this.pdf_models = pdfModelList;
        notifyItemInserted(pdfModelList.size());
    }

    @NonNull
    @NotNull
    @Override
    public PdfFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PdfFragmentAdapter.MyViewHolder holder, int position) {
        String name = pdf_models.get(position).getName();
        if (name != null) {
            holder.t_name.setSelected(true);
            holder.t_name.setText(name);
        } else {
            holder.t_name.setText("Empty");
        }
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
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            t_name = itemView.findViewById(R.id.tv_pdf_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickInterface.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
