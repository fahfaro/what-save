//package com.example.whatsave;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//
//public class DocumentFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private DocumentFragmentAdapter documentFragmentAdapter;
//    private DBHelper dbHelper;
//
//    public DocumentFragment() {
//    }
//
//    @Nullable
//    @org.jetbrains.annotations.Nullable
//    @Override
//    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_document, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView = view.findViewById(R.id.doc_recycler);
//        dbHelper = new DBHelper(view.getContext());
////        List<DocumentModel> documentModels = dbHelper.getDocumentDataSql();
////        documentFragmentAdapter = new DocumentFragmentAdapter(view.getContext(), documentModels);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        recyclerView.setAdapter(documentFragmentAdapter);
////        documentFragmentAdapter.updateAdaterInsert(documentModels);
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
////                dbHelper.deleteSelectedDocument(documentFragmentAdapter.getPostion(viewHolder.getAdapterPosition()));
//            }
//
//        });
//
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//
//    }
//}