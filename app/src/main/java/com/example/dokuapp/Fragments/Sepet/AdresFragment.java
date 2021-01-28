package com.example.dokuapp.Fragments.Sepet;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokuapp.Adapters.AdresAdapter;
import com.example.dokuapp.Values.AdresBilgiler;
import com.example.dokuapp.Activities.Adresklemedialog;
import com.example.dokuapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class AdresFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String kullaniciId = firebaseAuth.getCurrentUser().getUid();
    AdresAdapter adresAdapter;
    Bundle bundle = new Bundle();
    ProgressDialog pd;


    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_adres, container, false);
        pd = new ProgressDialog(view.getContext());
        final CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Kullanıcılar").
                document(kullaniciId).collection("Adresler");

        Button devam = view.findViewById(R.id.btndevam);
        Button adresekle = view.findViewById(R.id.adresekle);
        Button adressil = view.findViewById(R.id.adressil);
        TextView total = view.findViewById(R.id.sepettoplamfiyat);

        final String sepettoplmafiyat = getArguments().getString("total");
        total.setText(sepettoplmafiyat);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        Query query = FirebaseFirestore.getInstance()
                .collection("Kullanıcılar").document(kullaniciId)
                .collection("Adresler").orderBy("AdresBasligi", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<AdresBilgiler> options = new FirestoreRecyclerOptions.Builder<AdresBilgiler>()
                .setQuery(query, AdresBilgiler.class)
                .build();
        adresAdapter = new AdresAdapter(options);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adresAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adresAdapter.deleteItem(viewHolder.getAdapterPosition());
            }

            @Override
            public float getSwipeEscapeVelocity(float defaultValue) {
                return defaultValue * 10;
            }
        }).attachToRecyclerView(recyclerView);

        devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot.isEmpty()){
                            Toast.makeText(getContext(), "Adres bilgisi giriniz.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            collectionReference.whereEqualTo("durum", true).
                                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){

                                        bundle.putString("AdresBilgisi-AdiSoyadi", String.valueOf(documentSnapshot.get("AdiSoyadi")));
                                        bundle.putString("Adres-Adres", String.valueOf(documentSnapshot.get("Adres")));
                                        bundle.putString("Adres-AdresBasligi", String.valueOf(documentSnapshot.get("AdresBasligi")));
                                        bundle.putString("Adres-IlIlce", String.valueOf(documentSnapshot.get("IlIlce")));
                                        bundle.putString("Adres-telefonno", String.valueOf(documentSnapshot.get("Telefonno")));
                                    }
                                    Fragment fragment = new OdemeFragment();
                                    bundle.putString("total", sepettoplmafiyat);
                                    fragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

                                }
                            });
                        }

                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                        }
                    }
                });
            }
        });

        adresekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

        adressil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                collectionReference.document(adresAdapter.getSeciliAdres()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                        }
                        else{
                            Toast.makeText(getContext(), "Bir şeyler ters gitti", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
    public void openDialog(){
        Adresklemedialog adreeklemedialog = new Adresklemedialog();
        adreeklemedialog.show(getFragmentManager(), "adresdialog");
    }

    @Override
    public void onStart() {
        super.onStart();
        adresAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adresAdapter.stopListening();
    }
}