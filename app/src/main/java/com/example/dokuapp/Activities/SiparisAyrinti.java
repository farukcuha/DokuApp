package com.example.dokuapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dokuapp.Adapters.SiparişAyrintiAdapter;
import com.example.dokuapp.R;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SiparisAyrinti extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SiparişAyrintiAdapter adapter;
    private TextView siparisDurumu, siparisTutari, siparisTarihi, siparisNo, kargoNo, kargoFirma;
    private TextView adresBaslik, adresAdSoyad, adres, adresIlIlce, adresTelno;
    private ProgressDialog progressDialog;
    private LinearLayout kargo1, kargo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ayrinti);

        progressDialog = new ProgressDialog(SiparisAyrinti.this);
        progressDialog.setMessage("Yükleniyor...");

        String siparisId = getIntent().getExtras().getString("Sipariş Numarası");

        idPairs();
        setUpRecyclerView(siparisId);
        siparisSorgula(siparisId);

    }
    private void adresSorgula(String siparisId) {
        FirebaseFirestore.getInstance().collection("Siparişler").document(siparisId)
                .collection("Adres").document("adres")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot sp = task.getResult();

                    adresBaslik.setText(String.valueOf(sp.get("Adres Başlığı")));
                    adresAdSoyad.setText(String.valueOf(sp.get("Ad Soyad")));
                    adres.setText(String.valueOf(sp.get("Adres")));
                    adresIlIlce.setText(String.valueOf(sp.get("İlİlçe")));
                    adresTelno.setText(String.valueOf(sp.get("Telefon no")));
                    progressDialog.dismiss();



                }

            }
        });
    }

    private void siparisSorgula(final String siparisId) {
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("Siparişler").document(siparisId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot sp = task.getResult();
                    if(String.valueOf(sp.get("siparisDurumu")).equals("Kargoya Verildi")){
                        kargo1.setVisibility(View.VISIBLE);
                        kargo2.setVisibility(View.VISIBLE);
                        siparisDurumu.setText(String.valueOf(sp.get("siparisDurumu")));
                        siparisTutari.setText(String.valueOf(sp.get("odenenTutar")));
                        siparisTarihi.setText(String.valueOf(sp.get("siparisTarihi")));
                        siparisNo.setText(String.valueOf(sp.get("siparisNumarasi")));
                        kargoNo.setText(String.valueOf(sp.get("kargoTakipNo")));
                        kargoFirma.setText(String.valueOf(sp.get("kargoFirma")));

                        adresSorgula(siparisId);
                    }
                    else {
                        siparisDurumu.setText(String.valueOf(sp.get("siparisDurumu")));
                        siparisTutari.setText(String.valueOf(sp.get("odenenTutar")));
                        siparisTarihi.setText(String.valueOf(sp.get("siparisTarihi")));
                        siparisNo.setText(String.valueOf(sp.get("siparisNumarasi")));
                        adresSorgula(siparisId);
                    }



                }

            }
        });
    }

    private void setUpRecyclerView(String siparisId) {
        Query query = FirebaseFirestore.getInstance().collection("Siparişler").document(siparisId)
                .collection("Ürünler").orderBy("sepetUrunBirimFiyat", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<SepetUrun> options = new FirestoreRecyclerOptions.Builder<SepetUrun>()
                .setQuery(query, SepetUrun.class)
                .build();

        adapter = new SiparişAyrintiAdapter(options);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }

    private void idPairs() {
        recyclerView = findViewById(R.id.recyclerview);

        siparisDurumu = findViewById(R.id.siparisdurumu);
        siparisTutari = findViewById(R.id.fiyat);
        siparisTarihi = findViewById(R.id.tarih);
        siparisNo = findViewById(R.id.siparisno);
        kargoNo = findViewById(R.id.kargotakipno);
        kargoFirma = findViewById(R.id.kargoFirma);

        kargo1 = findViewById(R.id.kargolayout1);
        kargo2 = findViewById(R.id.kargolayout2);

        adresBaslik = findViewById(R.id.adresadii);
        adresAdSoyad = findViewById(R.id.adsoyad);
        adres = findViewById(R.id.adres);
        adresIlIlce = findViewById(R.id.ililce);
        adresTelno = findViewById(R.id.telefonno);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}