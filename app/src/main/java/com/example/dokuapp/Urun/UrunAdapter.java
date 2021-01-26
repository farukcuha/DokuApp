package com.example.dokuapp.Urun;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokuapp.DokuAnasayfaActivity;
import com.example.dokuapp.Fragments.UrunlerFragment;
import com.example.dokuapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class UrunAdapter extends FirestoreRecyclerAdapter<Urun, UrunAdapter.UrunHolder>{
    UrunlerFragment urunlerFragment = new UrunlerFragment();
    private TextView emptyText;
    public UrunAdapter(@NonNull FirestoreRecyclerOptions<Urun> options, TextView emptyText) {
        super(options);
        this.emptyText = emptyText;
    }

    @Override
    public void onBindViewHolder(@NonNull final UrunHolder holder, final int position, @NonNull final Urun model) {
        holder.textViewUrunTahminiKargo.setText(model.getÜrünTahminiKargo());
        holder.textViewUrunAdi.setText(model.getÜrünAdi());
        holder.textViewUrunFiyati.setText(model.getÜrünFiyati() + " ₺");
        holder.textViewUrunSatisTuru.setText(model.getÜrünSatisTuru());

        urunlerFragment.setUpProgressBar(model.getÜrünResim(), holder.imageViewUrunResim, holder.imageProgressBar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UrunAyrinti.class);
                Bundle bundle = new Bundle();
                bundle.putString("Uruntahminikargo", model.getÜrünTahminiKargo());
                bundle.putString("Urunadi", model.getÜrünAdi());
                bundle.putString("Urunfiyati", model.getÜrünFiyati());
                bundle.putString("Urunsatisturu", model.getÜrünSatisTuru());
                bundle.putString("Urunaciklama", model.getÜrünAciklama());
                bundle.putString("Urunresim", model.getÜrünResim());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
    public UrunHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.urunitems, parent,false);
        UrunHolder urunHolder = new UrunHolder(v);
        if(getSnapshots().isEmpty()){
            emptyText.setVisibility(View.VISIBLE);
        }
        else {
            emptyText.setVisibility(View.GONE);
        }
        return urunHolder;
    }
    public static class UrunHolder extends RecyclerView.ViewHolder {
        TextView textViewUrunFiyati;
        TextView textViewUrunAdi;
        TextView textViewUrunTahminiKargo;
        TextView textViewUrunSatisTuru;
        ImageView imageViewUrunResim;
        ProgressBar imageProgressBar;
        public UrunHolder(@NonNull View itemView) {
            super(itemView);
            textViewUrunFiyati = itemView.findViewById(R.id.xml_urunfiyati);
            textViewUrunAdi= itemView.findViewById(R.id.xml_urunadi);
            textViewUrunTahminiKargo = itemView.findViewById(R.id.xml_uruntahminikargo);
            textViewUrunSatisTuru = itemView.findViewById(R.id.xml_urunsatisturu);
            imageViewUrunResim = itemView.findViewById(R.id.xml_urunresim);
            imageProgressBar = itemView.findViewById(R.id.imageprogressBar);
        }
    }
}
