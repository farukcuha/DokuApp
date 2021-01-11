package com.example.dokuapp.Adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.dokuapp.R;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SepetAdapter extends FirestoreRecyclerAdapter<SepetUrun, SepetAdapter.SepetHolder> {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String kullaniciId = firebaseAuth.getCurrentUser().getUid();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public SepetAdapter(@NonNull FirestoreRecyclerOptions<SepetUrun> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SepetHolder holder, final int position, @NonNull final SepetUrun model) {
        holder.sepetUrunAdi.setText(model.getSepetUrunAdi());



        Glide.with(holder.itemView.getContext()).load(model.getSepetUrunResim()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.pd.setVisibility(holder.sepetUrunResim.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.pd.setVisibility(holder.sepetUrunResim.GONE);
                return false;            }
        }).into(holder.sepetUrunResim);



        holder.sepetUrunAdet.setText(String.valueOf(model.getSepetUrunAdet()));

        holder.sepetUrunToplamFiyati.setText(String.valueOf(model.getSepetUrunToplamFiyat()) + " ₺");

        holder.buttonarti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = model.getSepetUrunAdet();
                i++;
                if(i  == 0){
                    firestore.collection("Kullanıcılar").document(kullaniciId)
                            .collection("Sepet").document(model.getSepetUrunAdi()).delete();
                }
                int k = Integer.parseInt(model.getSepetUrunBirimFiyat()) * i;

                firestore.collection("Kullanıcılar").document(kullaniciId)
                        .collection("Sepet").document(model.getSepetUrunAdi()).update("sepetUrunAdet", i);


                holder.sepetUrunAdet.setText(String.valueOf(i));

                firestore.collection("Kullanıcılar").document(kullaniciId)
                        .collection("Sepet").document(model.getSepetUrunAdi()).update("sepetUrunToplamFiyat", k);

                holder.sepetUrunToplamFiyati.setText(String.valueOf((model.getSepetUrunToplamFiyat())));

            }
        });

        holder.buttoneksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = model.getSepetUrunAdet();
                i--;
                if(i < 1){
                    firestore.collection("Kullanıcılar").document(kullaniciId)
                        .collection("Sepet").document(model.getSepetUrunAdi()).delete();
                        }
                int k = Integer.parseInt(model.getSepetUrunBirimFiyat()) * i;

                firestore.collection("Kullanıcılar").document(kullaniciId)
                        .collection("Sepet").document(model.getSepetUrunAdi()).update("sepetUrunAdet", i);
                holder.sepetUrunAdet.setText(String.valueOf(i));


                firestore.collection("Kullanıcılar").document(kullaniciId)
                        .collection("Sepet").document(model.getSepetUrunAdi()).update("sepetUrunToplamFiyat", k);
                holder.sepetUrunToplamFiyati.setText(String.valueOf(model.getSepetUrunToplamFiyat()) + " ₺");

                holder.sepetUrunToplamFiyati.setText(String.valueOf((model.getSepetUrunToplamFiyat())));

            }
        });
    }
    @NonNull
    @Override
    public SepetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sepetitems, parent, false);
        return new SepetHolder(v);
    }

    public class SepetHolder extends RecyclerView.ViewHolder {
        TextView sepetUrunAdi;
        TextView sepetUrunToplamFiyati;
        ImageView sepetUrunResim;
        TextView sepetUrunAdet;
        Button buttonarti, buttoneksi;
        ProgressBar pd;

        public SepetHolder(@NonNull View itemView) {
            super(itemView);

            sepetUrunAdi = itemView.findViewById(R.id.xml_spt_ad);
            sepetUrunToplamFiyati = itemView.findViewById(R.id.xml_spt_urunfiyat);
            sepetUrunResim = itemView.findViewById(R.id.xml_spt_resim);
            sepetUrunAdet = itemView.findViewById(R.id.xml_sepet_adet);
            buttonarti = itemView.findViewById(R.id.xml_spt_btnarti);
            buttoneksi = itemView.findViewById(R.id.xml_spt_btneksi);
            pd = itemView.findViewById(R.id.progress_sepet);
        }
    }
}
