package com.example.dokuapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.dokuapp.Fragments.AnasayfaFragment;
import com.example.dokuapp.Fragments.HesabimFragment;
import com.example.dokuapp.Fragments.Sepet.AdresFragment;
import com.example.dokuapp.Fragments.Sepet.SepetimFragment;
import com.example.dokuapp.Fragments.SiparislerimFragment;
import com.example.dokuapp.Fragments.UrunlerFragment;
import com.example.dokuapp.Login.GirisYapActivity;
import com.example.dokuapp.Urun.Urun;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class DokuAnasayfaActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doku_anasayfa);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnasayfaFragment()).commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);



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