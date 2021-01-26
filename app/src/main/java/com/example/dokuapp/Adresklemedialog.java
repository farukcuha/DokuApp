package com.example.dokuapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Adresklemedialog extends AppCompatDialogFragment {

    EditText adresBasligi, adiSoyadi, il, ilce, adres, telefonno;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String kullaniciId = firebaseAuth.getCurrentUser().getUid();

    ProgressBar pd;
    AlertDialog.Builder builder;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.adreseklemedialog, null);

        adresBasligi = view.findViewById(R.id.adresbasligi);
        adiSoyadi = view.findViewById(R.id.adisoyadi);
        il = view.findViewById(R.id.il);
        ilce = view.findViewById(R.id.ilce);
        adres = view.findViewById(R.id.adres);
        telefonno = view.findViewById(R.id.telno);
        pd = view.findViewById(R.id.progress_bar);



        builder.setView(view)
                .setTitle("Yeni Adres")
                .setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setVisibility(View.VISIBLE);

                HashMap<String, Object> hashMap = new HashMap<>();
                String str_adresbasligi = adresBasligi.getText().toString();

                String str_adisoyadi = adiSoyadi.getText().toString();
                String str_il = il.getText().toString();
                String str_ilce = ilce.getText().toString();
                String str_adres = adres.getText().toString();
                String str_telno = telefonno.getText().toString();

                hashMap.put("AdresBasligi", str_adresbasligi);
                hashMap.put("AdiSoyadi", str_adisoyadi);
                hashMap.put("IlIlce", str_il+"/"+str_ilce);
                hashMap.put("Adres", str_adres);
                hashMap.put("Telefonno", str_telno);
                hashMap.put("durum", false);

                if(TextUtils.isEmpty(str_adisoyadi)
                        ||  TextUtils.isEmpty(str_adresbasligi)
                        ||  TextUtils.isEmpty(str_il)
                        ||  TextUtils.isEmpty(str_ilce)
                        ||  TextUtils.isEmpty(str_adres)){
                    Toast.makeText(getActivity(), "Alanları Doldurunuz", Toast.LENGTH_SHORT).show();
                }

                else {
                    db.collection("Kullanıcılar").document(kullaniciId).collection("Adresler").document(str_adresbasligi).set(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.setVisibility(View.GONE);


                                    }
                                    else{
                                        Toast.makeText(getContext(), "Alanları Doldurunuz", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }




            }
        });
        return builder.create();

    }





}




