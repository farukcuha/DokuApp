package com.example.dokuapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.example.dokuapp.Fragments.AnasayfaFragment;
import com.example.dokuapp.Fragments.HesabimFragment;
import com.example.dokuapp.Fragments.Sepet.SepetimFragment;
import com.example.dokuapp.Fragments.SiparislerimFragment;
import com.example.dokuapp.Fragments.UrunlerFragment;
import com.example.dokuapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DokuAnasayfaActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doku_anasayfa);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnasayfaFragment()).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DokuAnasayfaActivity.this);
        String s = sharedPreferences.getString("email", "sad");
        String s2 = sharedPreferences.getString("sifre", "sol");
        Log.d("a", s + s2);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.ürünleri_item:
                    selectedFragment = new UrunlerFragment();
                    break;
                case R.id.sepetim_item:
                    selectedFragment = new SepetimFragment();
                    break;
                case R.id.dokuanasayfa_item:
                    selectedFragment = new AnasayfaFragment();
                    break;
                case R.id.siparislerim_item:
                    selectedFragment = new SiparislerimFragment();
                    break;
                case R.id.hesabim_item:
                    selectedFragment = new HesabimFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

            return true;
        }
    };


}