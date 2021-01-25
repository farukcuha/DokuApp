package com.example.dokuapp.Fragments.Sepet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokuapp.Fragments.SiparislerimFragment;
import com.example.dokuapp.Adapters.OnayOzetSepetAdapter;
import com.example.dokuapp.R;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class OnayFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String kullaniciId = firebaseAuth.getCurrentUser().getUid();
    OnayOzetSepetAdapter adapter;
    LinearLayoutManager manager;
    TextView total;
    Button siparisibaslat;
    Calendar calendar;
    SimpleDateFormat tarih, saat;
    String str_tarih, str_saat;
    SimpleDateFormat siparissaat, siparistarih;
    String id_saat, id_tarih;
    String siparisid;
    TextView adresadi, adsoyad, adres, ililce, telefonno;

    HashMap<String, Object> map = new HashMap<>();

    ProgressDialog siparisOlusturma;
    AlertDialog siparisUyari;

    @ServerTimestamp
    Date time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onay, container, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Eminmisiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                siparisOlusturma.show();
                siparisOlusutur();

            }
        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        siparisUyari = builder.create();

        siparisOlusturma = new ProgressDialog(getContext());
        siparisOlusturma.setMessage("Siparişiniz OLuşturuluyor...");

        recyclerView = view.findViewById(R.id.recyclerview);
        total = view.findViewById(R.id.sepettoplamfiyat);
        siparisibaslat = view.findViewById(R.id.siparisbaslat);

        adresadi = view.findViewById(R.id.adresadii);
        adsoyad = view.findViewById(R.id.adsoyad);
        adres = view.findViewById(R.id.adres);
        ililce = view.findViewById(R.id.ililce);
        telefonno = view.findViewById(R.id.telefonno);
        String str_total = String.valueOf(getArguments().get("total"));

        total.setText(str_total);

        adresadi.setText(String.valueOf(getArguments().getString("Adres-AdresBasligi")));
        adsoyad.setText(String.valueOf(getArguments().getString("AdresBilgisi-AdiSoyadi")));
        adres.setText(String.valueOf(getArguments().getString("Adres-Adres")));
        ililce.setText(String.valueOf(getArguments().getString("Adres-IlIlce")));
        telefonno.setText(String.valueOf(getArguments().getString("Adres-telefonno")));

        setuprecyclerview(view);
        siparisibaslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siparisUyari.show();
            }
        });
        return view;

    }

    private void setuprecyclerview(View view) {
        Query query = db.collection("Kullanıcılar").document(kullaniciId)
                .collection("Sepet").orderBy("sepetUrunAdi", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<SepetUrun> options = new FirestoreRecyclerOptions.Builder<SepetUrun>()
                .setQuery(query, SepetUrun.class)
                .build();

        adapter = new OnayOzetSepetAdapter(options);

        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    private void siparisOlusutur(){
        calendar = Calendar.getInstance();

        tarih = new SimpleDateFormat("dd.MM.yyyy");
        saat = new SimpleDateFormat("HH:mm");
        str_saat = saat.format(calendar.getTime());
        str_tarih = tarih.format(calendar.getTime());

        siparissaat = new SimpleDateFormat("ddMMyyyy");
        siparistarih = new SimpleDateFormat("HHmmss");
        id_saat = siparissaat.format(calendar.getTime());
        id_tarih = siparistarih.format(calendar.getTime());

        siparisid = (id_tarih+id_saat);

        Log.d("a", str_saat);
        Log.d("b", str_tarih);



        HashMap<String, Object> adresbilgisi = new HashMap<>();

        adresbilgisi.put("Adres Başlığı", getArguments().getString("Adres-AdresBasligi"));
        adresbilgisi.put("Ad Soyad", getArguments().getString("AdresBilgisi-AdiSoyadi"));
        adresbilgisi.put("Adres", getArguments().getString("Adres-Adres"));
        adresbilgisi.put("İlİlçe", getArguments().getString("Adres-IlIlce"));
        adresbilgisi.put("Telefon no", getArguments().getString("Adres-telefonno"));

        db.collection("Siparişler").document(siparisid)
                .collection("Adres").document("adres")
                .set(adresbilgisi);


       db.collection("Kullanıcılar").document(kullaniciId)
                .collection("Sepet").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {

               for(QueryDocumentSnapshot sp : task.getResult()){
                   map.putAll(sp.getData());
                   db.collection("Siparişler").document(siparisid)

                           .collection("Ürünler").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentReference> task) {

                           Log.d("cd", String.valueOf(map));
                       }
                   });
               }
           }
       });

       HashMap<String, Object> hashMap = new HashMap<>();

       hashMap.put("siparisNumarasi", (id_tarih+id_saat));
       hashMap.put("siparisTarihi", str_tarih + " - " + str_saat);
       hashMap.put("kargoFirma", null);
       hashMap.put("odenenTutar", getArguments().get("total"));
       hashMap.put("siparisDurumu", "Yeni Sipariş");
       hashMap.put("kargoTakipNo", "123564795");
       hashMap.put("kullaniciId", kullaniciId);
       hashMap.put("tamamlandimi", false);

       db.collection("Siparişler").document(siparisid).set(hashMap)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                       if(task.isSuccessful()){
                           Log.d("a", "Siparişiniz Oluşturuldu");

                           getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SiparislerimFragment()).commit();
                           Toast.makeText(getContext(), "Siparişiniz Oluşturuldu", Toast.LENGTH_LONG).show();
                           siparisOlusturma.dismiss();
                           siparisUyari.dismiss();

                       }

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