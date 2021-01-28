package com.example.dokuapp.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokuapp.R;
import com.example.dokuapp.Activities.SiparisAyrinti;
import com.example.dokuapp.Values.SiparisBilgiler;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SiparisAdapter extends FirestoreRecyclerAdapter<SiparisBilgiler, SiparisAdapter.SiparisHolder> {
    private TextView bosyazi;

    public SiparisAdapter(@NonNull FirestoreRecyclerOptions<SiparisBilgiler> options, TextView bosyazi) {
        super(options);
        this.bosyazi = bosyazi;
    }

    @Override
    protected void onBindViewHolder(@NonNull final SiparisHolder holder, final int position, @NonNull final SiparisBilgiler model) {
        holder.siparisNumarasi.setText("Sipariş Numarası: " + model.getSiparisNumarasi());
        holder.siparisTarihi.setText("Sipariş Tarihi: " + model.getSiparisTarihi());
        holder.toplamTutar.setText("Toplam Tutar: " + model.getOdenenTutar());
        holder.kargoDurumu.setText("Sipariş Durumu: " + model.getSiparisDurumu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), SiparisAyrinti.class);
                Bundle bundle = new Bundle();
                bundle.putString("Sipariş Numarası", model.getSiparisNumarasi());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public SiparisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.siparisitem, parent, false);
        if(getSnapshots().isEmpty()){
            bosyazi.setVisibility(View.VISIBLE);
        }
        else {
            bosyazi.setVisibility(View.GONE);
        }
        return new SiparisHolder(view);

    }

    public class SiparisHolder extends RecyclerView.ViewHolder {
        private TextView siparisNumarasi;
        private TextView siparisTarihi;
        private TextView toplamTutar;
        private TextView kargoDurumu;

        public SiparisHolder(@NonNull View itemView) {
            super(itemView);
            siparisNumarasi = itemView.findViewById(R.id.siparisno);
            siparisTarihi = itemView.findViewById(R.id.siparistarihi);
            toplamTutar = itemView.findViewById(R.id.toplamtutar);
            kargoDurumu = itemView.findViewById(R.id.siparisdurumu);


        }
    }


}
