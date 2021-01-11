package com.example.dokuapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokuapp.Values.AdresBilgiler;
import com.example.dokuapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AdresAdapter extends FirestoreRecyclerAdapter<AdresBilgiler, AdresAdapter.AdresHolder> {

    View view;
    int selectedposition;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth kullanici = FirebaseAuth.getInstance();
    String kullaniciId = kullanici.getCurrentUser().getUid();

    public AdresAdapter(@NonNull FirestoreRecyclerOptions<AdresBilgiler> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AdresAdapter.AdresHolder holder, final int position, @NonNull final AdresBilgiler model) {
        holder.adresadi.setText(model.getAdresBasligi());
        holder.adsoyad.setText(model.getAdiSoyadi());
        holder.adres.setText(model.getAdres());
        holder.ililce.setText(model.getIlIlce());
        holder.telno.setText(model.getTelefonno());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedposition = position;
                notifyDataSetChanged();
            }
        });

        if(selectedposition == position){
            adresSecici(true, model, holder);
        }
        else{
            adresSecici(false, model, holder);
        }
    }

    private void adresSecici(Boolean durum, AdresBilgiler model, AdresAdapter.AdresHolder holder){
        holder.checkBox.setChecked(durum);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("AdiSoyadi", model.getAdiSoyadi());
        hashMap.put("Adres", model.getAdres());
        hashMap.put("AdresBasligi", model.getAdresBasligi());
        hashMap.put("IlIlce", model.getIlIlce());
        hashMap.put("Telefonno", model.getTelefonno());
        hashMap.put("durum", durum);

        db.collection("Kullanıcılar").document(kullaniciId).collection("Adresler").document(model.getAdresBasligi())
                .update(hashMap);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public AdresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adresitem, parent, false);

        return new AdresHolder(view);
    }

    public class AdresHolder extends RecyclerView.ViewHolder {
        private TextView adresadi;
        private TextView adsoyad;
        private TextView adres;
        private TextView ililce;
        private TextView telno;
        private CheckBox checkBox;

        public AdresHolder(@NonNull View itemView) {
            super(itemView);

            adresadi = itemView.findViewById(R.id.adresadi);
            adsoyad = itemView.findViewById(R.id.adsoyad);
            adres = itemView.findViewById(R.id.adres);
            ililce = itemView.findViewById(R.id.ililce);
            telno = itemView.findViewById(R.id.telefonno);
            checkBox = itemView.findViewById(R.id.check);

        }
    }
}
