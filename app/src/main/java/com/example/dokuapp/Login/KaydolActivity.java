package com.example.dokuapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dokuapp.DokuAnasayfaActivity;
import com.example.dokuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class KaydolActivity extends AppCompatActivity {
    private EditText kullaniciAdSoyad, kullaniciEposta, kullaniciSifre;
    private Button kaydolbutton;
    FirebaseAuth yetki;
    DocumentReference yol;
    ProgressDialog pd;
    HashMap<String, Object> hashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);

        kullaniciAdSoyad = findViewById(R.id.xml_edittext_kaydol_ad);
        kullaniciEposta = findViewById(R.id.xml_edittext_kaydol_email);
        kullaniciSifre = findViewById(R.id.xml_edittext_kaydol_sifre);
        kaydolbutton = findViewById(R.id.xml_btn_kaydol_2);

        yetki = FirebaseAuth.getInstance();

        kaydolbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_kullaniciAdSoyad = kullaniciAdSoyad.getText().toString();
                String str_kullaniciEposta = kullaniciEposta.getText().toString();
                String str_kullaniciSifre = kullaniciSifre.getText().toString();


                pd = new ProgressDialog(KaydolActivity.this);
                pd.setMessage("Lütfen Bekleyin...");
                pd.show();

                if(TextUtils.isEmpty(str_kullaniciAdSoyad)||TextUtils.isEmpty(str_kullaniciEposta)||TextUtils.isEmpty(str_kullaniciSifre)){
                    Toast.makeText(KaydolActivity.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                }
                else if(str_kullaniciSifre.length() < 8){
                    Toast.makeText(KaydolActivity.this, "Şifre uzunluğu 8 karakterden kısa olamaz.", Toast.LENGTH_SHORT).show();
                }
                else{
                    KullaniciKaydi(str_kullaniciAdSoyad, str_kullaniciEposta, str_kullaniciSifre);
                }
            }
        });
    }

    private void KullaniciKaydi(final String str_kullaniciAdSoyad, final String str_kullaniciEposta, final String str_kullaniciSifre) {
        yetki.createUserWithEmailAndPassword(str_kullaniciEposta, str_kullaniciSifre).addOnCompleteListener(KaydolActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fireBaseKullanici = yetki.getCurrentUser();

                    String kullaniciID = fireBaseKullanici.getUid();

                    yol = FirebaseFirestore.getInstance().collection("Kullanıcılar").document(kullaniciID);


                    hashMap.put("Kullanıcı Adı ve Soyadı", str_kullaniciAdSoyad);
                    hashMap.put("Kullanıcı E-posta", str_kullaniciEposta);
                    hashMap.put("Kullanıcı Şifre", str_kullaniciSifre);
                    hashMap.put("Kullanıcı Id", FirebaseAuth.getInstance().getCurrentUser().getUid());

                    yol.set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(KaydolActivity.this, "Başarılı", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(KaydolActivity.this, DokuAnasayfaActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }

                else{
                    Toast.makeText(KaydolActivity.this, "Başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}