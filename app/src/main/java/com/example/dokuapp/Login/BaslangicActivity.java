package com.example.dokuapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.dokuapp.R;

public class BaslangicActivity extends AppCompatActivity {

    private Button btn_girisyap_1, btn_kaydol_1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BaslangicActivity.this);
        editor = sharedPreferences.edit();

            btn_girisyap_1 = findViewById(R.id.xml_btn_girisyap_1);
            btn_kaydol_1 = findViewById(R.id.xml_btn_kaydol_1);
            pd = new ProgressDialog(BaslangicActivity.this);
            pd.setMessage("Yükleniyor...");
            pd.setCancelable(false);

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
            
    }
}


