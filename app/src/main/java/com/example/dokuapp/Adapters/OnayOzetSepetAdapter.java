package com.example.dokuapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokuapp.R;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;


public class OnayOzetSepetAdapter extends FirestoreRecyclerAdapter<SepetUrun, OnayOzetSepetAdapter.OnayOzetSepetHolder > {



    public OnayOzetSepetAdapter(@NonNull FirestoreRecyclerOptions<SepetUrun> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final OnayOzetSepetHolder holder, int position, @NonNull SepetUrun model) {

        holder.sepetUrunAdi.setText(model.getSepetUrunAdi());
        holder.sepetUrunAdet.setText(model.getSepetUrunAdet() + " Adet");
        Picasso.get().load(model.getSepetUrunResim()).into((holder.sepetUrunResim), new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                if(holder.pd != null){
                    holder.pd.setVisibility(holder.sepetUrunResim.GONE);

                }
            }
            @Override
            public void onError(Exception e) {

            }

        });
        holder.sepetUrunToplamFiyati.setText(String.valueOf(model.getSepetUrunToplamFiyat()) + " ₺");


    }

    @NonNull
    @Override
    public OnayOzetSepetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.odemeozetitems, parent, false);
        return new OnayOzetSepetHolder(view);

    }

    public class OnayOzetSepetHolder extends RecyclerView.ViewHolder {
        TextView sepetUrunAdi;
        TextView sepetUrunToplamFiyati;
        ImageView sepetUrunResim;
        TextView sepetUrunAdet;
        ProgressBar pd;
        public OnayOzetSepetHolder(@NonNull View itemView) {
            super(itemView);
            sepetUrunAdi = itemView.findViewById(R.id.xml_spt_ad);
            sepetUrunToplamFiyati = itemView.findViewById(R.id.xml_spt_urunfiyat);
            sepetUrunResim = itemView.findViewById(R.id.xml_spt_resim);
            sepetUrunAdet = itemView.findViewById(R.id.xml_sepet_adet);
            pd = itemView.findViewById(R.id.progress_sepet);
        }
    }
}
