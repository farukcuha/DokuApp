package com.example.dokuapp.Fragments.Sepet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import static android.content.Context.CLIPBOARD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class OdemeFragment extends Fragment {

    TextView total;
    Button button;
    TextView iban;
    Button devam;
    ProgressBar pb;
    Bundle bundle = new Bundle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_odeme, container, false);

        total = view.findViewById(R.id.sepettoplamfiyat);
        button = view.findViewById(R.id.kopyala);
        iban = view.findViewById(R.id.iban);
        devam = view.findViewById(R.id.btndevam);
        pb = view.findViewById(R.id.progress_bar);

        final String ibanno = iban.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("iban", ibanno);
                manager.setPrimaryClip(clipData);

                Toast.makeText(getContext(), "Panoya Kopyalandı", Toast.LENGTH_SHORT).show();
            }
        });

        final String sepettoplamfiyat = getArguments().getString("total");
        total.setText(sepettoplamfiyat);
        String adsoyad = getArguments().getString("AdresBilgisi-AdiSoyadi");
        String adres = getArguments().getString("Adres-Adres");
        String adresbasligi = getArguments().getString("Adres-AdresBasligi");
        String ililce = getArguments().getString("Adres-IlIlce");
        String telefonno = getArguments().getString("Adres-telefonno");


        bundle.putAll(getArguments());


        Log.d("x", adsoyad);
        Log.d("y", adres);
        Log.d("z", adresbasligi);
        Log.d("t", ililce);
        Log.d("a", telefonno);

        Log.d("b", sepettoplamfiyat);

        devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);

                Fragment fragment = new OnayFragment();
                bundle.putString("total", total.getText().toString());
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        return view;

    }



}