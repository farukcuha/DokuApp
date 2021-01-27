package com.example.dokuapp.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokuapp.Login.BaslangicActivity;
import com.example.dokuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;


public class HesabimFragment extends Fragment {
    private TextView cikisYap, eMail, hesabiSil, kullaniciAd, sifre;
    private String kullaniciId;
    private DocumentReference reference;
    private ProgressDialog pd;
    private ImageView sifrebtn, emailbtn;
    AlertDialog.Builder builder;
    private HashMap<String, Object> hashMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hesabim, container, false);

        cikisYap = view.findViewById(R.id.textView6);
        hesabiSil = view.findViewById(R.id.hesabisil);
        eMail = view.findViewById(R.id.emaill);
        kullaniciAd = view.findViewById(R.id.kullaniciAd);
        sifrebtn = view.findViewById(R.id.sifrebtn);
        emailbtn = view.findViewById(R.id.emailbtn);
        sifre = view.findViewById(R.id.sifre);

        pd = new ProgressDialog(getContext());
        pd.setCancelable(false);

        kullaniciId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference = FirebaseFirestore.getInstance().collection("Kullanıcılar").document(kullaniciId);
        builder = new AlertDialog.Builder(getContext());


        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BaslangicActivity.class));

            }
        });

        hesabiSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    builder.setMessage("Hesabınızı Kalıcı Silmek İstediğinize Emin misini?")
                        .setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pd.setMessage("Hesabınız Siliniyor...");
                                pd.show();
                                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        startActivity(new Intent(getContext(), BaslangicActivity.class));
                                                        pd.dismiss();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                builder.create().show();

            }
        });
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                eMail.setText(String.valueOf(value.get("email")));
                kullaniciAd.setText(String.valueOf(value.get("adSoyad")));
                sifre.setText(String.valueOf(value.get("sifre")));
                pd.dismiss();
            }
        });

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.emaildegisitirmedialog, null);
                final EditText email = view1.findViewById(R.id.yeniemail);
                pd.setMessage("Yükleniyor...");

                builder.setView(view1)
                        .setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pd.show();
                                FirebaseAuth.getInstance().getCurrentUser().updateEmail(email.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                hashMap = new HashMap<>();
                                                hashMap.put("email", email.getText().toString());
                                                reference.set(hashMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            pd.dismiss();
                                                        }
                                                    }
                                                });
                                            }
                                        });

                            }
                        })
                        .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();

                builder.show();
            }
        });

        sifrebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.sifredegistirmedialog, null);
                final EditText eskisifre = view1.findViewById(R.id.eskisifre);
                final EditText yenisifre1 = view1.findViewById(R.id.yenisifre1);
                final EditText yenisifre2 = view1.findViewById(R.id.yenisifre2);

                pd.setMessage("Yükleniyor");

                builder.setView(view1)
                        .setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String str_eskisifre = eskisifre.getText().toString();
                                final String str_yenisifre1 = yenisifre1.getText().toString();
                                final String str_yenisifre2 = yenisifre2.getText().toString();
                                pd.show();
                                if((TextUtils.isEmpty(eskisifre.getText().toString()) | TextUtils.isEmpty(yenisifre1.getText().toString()) | TextUtils.isEmpty(yenisifre2.getText().toString()))){
                                    Toast.makeText(getContext(), "Alanların Hepsini Doldurunuz.", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    dialog.dismiss();
                                }

                                else if (str_eskisifre.length() < 8 | str_yenisifre1.length() < 8 | str_yenisifre2.length() < 8){
                                    Toast.makeText(getContext(), "Şifre Uzunluğu 8 Karakterden Kısa Olamaz.", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    dialog.dismiss();
                                }
                                else if (!str_yenisifre1.equals(str_yenisifre2)){
                                    Toast.makeText(getContext(), "Şifreleri Farklı Girildi.", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                    dialog.dismiss();
                                }
                                else {
                                    if(eskisifre.getText().toString().equals(sifre.getText().toString())){
                                            FirebaseAuth.getInstance().getCurrentUser().updatePassword(str_yenisifre2)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                hashMap = new HashMap<>();
                                                                hashMap.put("sifre", str_yenisifre2);
                                                                reference.set(hashMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful()){
                                                                            pd.dismiss();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Eski Şifrenizi Hatalı Girdiniz.", Toast.LENGTH_SHORT).show();
                                            pd.dismiss();
                                        }



                                }

                            }
                        })
                        .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();

                builder.show();

            }
        });


        return view;




    }
}