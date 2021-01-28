package com.example.dokuapp.Fragments;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dokuapp.R;
import com.example.dokuapp.Values.Urun;
import com.example.dokuapp.Adapters.UrunAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;


public class UrunlerFragment extends Fragment{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference documentReference = db.collection("Ürünler");
    private RecyclerView recyclerView;
    private View view;
    private UrunAdapter adapter;
    private TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_urunler, container, false);
        emptyText = view.findViewById(R.id.emptytext);
        setUpRecyclerView(view, emptyText);
        return view;
    }

    public void setUpProgressBar(String urunResimUrl, final View urunImage, final View progressbar) {
        Picasso.get().load(urunResimUrl).resize(200,200).centerCrop().into((ImageView) urunImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                if(progressbar != null){
                    progressbar.setVisibility(urunImage.GONE);
                }
            }
            @Override
            public void onError(Exception e) {

            }

        });
    }

    private void  setUpRecyclerView(View v, TextView emptyText){
        Query query = documentReference.orderBy("ürünAdi", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Urun> options = new FirestoreRecyclerOptions.Builder<Urun>()
                .setQuery(query, Urun.class)
                .build();

        adapter = new UrunAdapter(options, emptyText);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView = v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}


