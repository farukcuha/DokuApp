package com.example.dokuapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dokuapp.R;
import com.example.dokuapp.Adapters.SiparisAdapter;
import com.example.dokuapp.Values.SiparisBilgiler;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SiparislerimFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private SiparisAdapter adapter;
    private TextView bosyazi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_siparislerim, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        bosyazi = view.findViewById(R.id.bosyazi);

        Query query = db.collection("Siparişler")
                .whereEqualTo("kullaniciId", auth.getCurrentUser().getUid()).orderBy("odenenTutar", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<SiparisBilgiler> options = new FirestoreRecyclerOptions.Builder<SiparisBilgiler>()
                .setQuery(query, SiparisBilgiler.class)
                .build();
        adapter = new SiparisAdapter(options, bosyazi);
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