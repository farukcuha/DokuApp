package com.example.dokuapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dokuapp.R;
import com.example.dokuapp.Adapters.SiparisAdapter;
import com.example.dokuapp.Values.SiparisBilgiler;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SiparislerimFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    SiparisAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_siparislerim, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        Query query = db.collection("Siparişler")
                .orderBy("odenenTutar");

        FirestoreRecyclerOptions<SiparisBilgiler> options = new FirestoreRecyclerOptions.Builder<SiparisBilgiler>()
                .setQuery(query, SiparisBilgiler.class)
                .build();
        adapter = new SiparisAdapter(options);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
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