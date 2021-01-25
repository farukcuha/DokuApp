package com.example.dokuapp.Urun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokuapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UrunAyrinti extends AppCompatActivity {
    private ImageView urunResim;
    private Button adetarti, adeteksi, sepeteekle;

    private TextView urunadet, uruntahminikargo, urunfiyat, urunsatisturu, urunadi, urunaciklama;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String kullaniciId;
    private int urunayrintiurunadet = 1;
    private int ksepetUrunToplamFiyat;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    HashMap<String, Object> sepeturun = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ayrinti);
        kullaniciId = firebaseAuth.getCurrentUser().getUid();

        urunResim = findViewById(R.id.xml_urunresim2);
        adetarti = findViewById(R.id.xml_spt_arti);
        adeteksi = findViewById(R.id.xml_spt_eksi);
        sepeteekle = findViewById(R.id.xml_sepetekle);
        urunadet = findViewById(R.id.xml_sepet_adet);
        uruntahminikargo = findViewById(R.id.xml_uruntahminikargo2);
        urunfiyat = findViewById(R.id.xml_urunfiyat2);
        urunsatisturu = findViewById(R.id.xml_urunSatisTuru2);
        urunadi = findViewById(R.id.xml_urunad2);
        urunaciklama = findViewById(R.id.xml_urunAciklama);
        progressBar = findViewById(R.id.progressbarurunayrinti);
        final Intent intent = getIntent();

        uruntahminikargo.setText(intent.getExtras().getString("Uruntahminikargo"));
        urunfiyat.setText(intent.getExtras().getString("Urunfiyati") + " TL");
        urunsatisturu.setText(String.valueOf(intent.getExtras().getString("Urunsatisturu")));
        urunadi.setText(String.valueOf(intent.getExtras().getString("Urunadi")));
        urunaciklama.setText(String.valueOf(intent.getExtras().getString("Urunaciklama")));
        setUpProgressBar(intent.getExtras().getString("Urunresim"), urunResim, progressBar);

        ksepetUrunToplamFiyat = Integer.parseInt(intent.getExtras().getString("Urunfiyati"));

        adetarti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urunayrintiurunadet++;
                if(urunayrintiurunadet < 1){
                    urunayrintiurunadet = 1;
                    urunadet.setText(String.valueOf(urunayrintiurunadet));

                }
                else{
                    urunadet.setText(String.valueOf(urunayrintiurunadet));

                }
                ksepetUrunToplamFiyat = urunayrintiurunadet * Integer.parseInt(intent.getExtras().getString("Urunfiyati"));

            }
        });

        adeteksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urunayrintiurunadet--;
                if(urunayrintiurunadet < 1){
                    urunayrintiurunadet = 1;
                    urunadet.setText(String.valueOf(urunayrintiurunadet));


                }
                else{
                    urunadet.setText(String.valueOf(urunayrintiurunadet));

                }
                ksepetUrunToplamFiyat = urunayrintiurunadet * Integer.parseInt(intent.getExtras().getString("Urunfiyati"));

            }
        });

        sepeteekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(UrunAyrinti.this, "Ürün sepete eklendi", Toast.LENGTH_SHORT).show();

                sepeturun.put("sepetUrunAdi", intent.getExtras().getString("Urunadi"));
                sepeturun.put("sepetUrunResim", intent.getExtras().getString("Urunresim"));
                sepeturun.put("sepetUrunBirimFiyat", intent.getExtras().getString("Urunfiyati"));
                sepeturun.put("sepetUrunAdet", urunayrintiurunadet);
                sepeturun.put("sepetUrunToplamFiyat", ksepetUrunToplamFiyat);
                sepeturun.put("sepetUrunSatisTur", intent.getExtras().get("Urunsatisturu"));

                firestore.collection("Kullanıcılar").document(kullaniciId).collection("Sepet").document((String) sepeturun.get("sepetUrunAdi")).set(sepeturun);
            }
        });
    }

    public void setUpProgressBar(String urunResimUrl, final View urunImage, final View progressbar) {
        Picasso.get().load(urunResimUrl).resize(400,400).centerCrop().into((ImageView) urunImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                if(progressbar != null){
                    progressbar.setVisibility(urunImage.GONE);
                }
            }
            @Override
            public void onError(Exception e) {
            }

        });
    }

}

