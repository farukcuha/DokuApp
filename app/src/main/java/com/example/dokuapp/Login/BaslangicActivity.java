package com.example.dokuapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dokuapp.DokuAnasayfaActivity;
import com.example.dokuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class BaslangicActivity extends AppCompatActivity {

    private Button btn_girisyap_1, btn_kaydol_1, btn_bizkimiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            btn_girisyap_1 = findViewById(R.id.xml_btn_girisyap_1);
            btn_kaydol_1 = findViewById(R.id.xml_btn_kaydol_1);
            btn_bizkimiz = findViewById(R.id.xml_btn_bizkimiz);

            btn_girisyap_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BaslangicActivity.this, GirisYapActivity.class));
                }
            });

            btn_kaydol_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BaslangicActivity.this, KaydolActivity.class));
                }
            });

            btn_bizkimiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BaslangicActivity.this, BizKimizActivity.class));
                }
            });
        }




}


