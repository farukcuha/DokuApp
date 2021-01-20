package com.example.dokuapp.Fragments.Sepet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokuapp.R;
import com.example.dokuapp.Adapters.SepetAdapter;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SepetimFragment extends Fragment {


    private SepetAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String kullaniciId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference collectionReference = db.collection("Kullanıcılar").document(kullaniciId).collection("Sepet");
    private RecyclerView recyclerView;
    private TextView sepetfiyattoplam;
    public Button btndevam;
    private int total = 0;




    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sepetimragment, container, false);
        final TextView sepetfiyattoplam = view.findViewById(R.id.sepettoplamfiyat);

        Query query1 = db.collection("Kullanıcılar").document(kullaniciId)
                .collection("Sepet").orderBy("sepetUrunToplamFiyat");

        query1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(QueryDocumentSnapshot documentSnapshot : value){
                    total += Integer.parseInt(String.valueOf(documentSnapshot.get("sepetUrunToplamFiyat")));
                    sepetfiyattoplam.setText(total + " ₺");
                }
                total = 0;
            }
        });
        setuUpRecyclerView(view);
        return view;
    }
    public void setuUpRecyclerView(View v){
        Query query = collectionReference.orderBy("sepetUrunAdi", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<SepetUrun> options = new FirestoreRecyclerOptions.Builder<SepetUrun>()
                .setQuery(query, SepetUrun.class)
                .build();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter  = new SepetAdapter(options);

        recyclerView = v.findViewById(R.id.sepet_recyclerview);
        sepetfiyattoplam = v.findViewById(R.id.sepettoplamfiyat);
        btndevam = v.findViewById(R.id.btndevam);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        btndevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        QuerySnapshot snapshot = task.getResult();
                        if(snapshot.isEmpty()){
                            Toast.makeText(getContext(), "Sepete ürün ekleyiniz", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Fragment fragment = new AdresFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("total", sepetfiyattoplam.getText().toString());
                            fragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        }
                    }
                });
            }
        });
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