package com.example.dokuapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dokuapp.R;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SiparişAyrintiAdapter extends FirestoreRecyclerAdapter<SepetUrun, SiparişAyrintiAdapter.SiparisAyrintiHolder> {


    public SiparişAyrintiAdapter(@NonNull FirestoreRecyclerOptions<SepetUrun> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SiparisAyrintiHolder holder, int position, @NonNull SepetUrun model) {
        Glide.with(holder.itemView.getContext()).load(model.getSepetUrunResim()).centerCrop().into(holder.resim);
        holder.isim.setText(model.getSepetUrunAdi());
        holder.fiyat.setText(model.getSepetUrunBirimFiyat() + " ₺");
        holder.adet.setText(String.valueOf(model.getSepetUrunAdet()));

        Log.d("xsacddvfbf", model.getSepetUrunAdi());

    }

    @NonNull
    @Override
    public SiparisAyrintiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.odemeozetitems, parent, false);
        return new SiparisAyrintiHolder(v);
    }

    public class SiparisAyrintiHolder extends RecyclerView.ViewHolder {
        private TextView fiyat, adet, isim;
        private ImageView resim;

        public SiparisAyrintiHolder(@NonNull View itemView) {
            super(itemView);

            fiyat = itemView.findViewById(R.id.xml_spt_urunfiyat);
            adet = itemView.findViewById(R.id.xml_sepet_adet);
            isim = itemView.findViewById(R.id.xml_spt_ad);
            resim = itemView.findViewById(R.id.xml_spt_resim);

        }
    }


}
