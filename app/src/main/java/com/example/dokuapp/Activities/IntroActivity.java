package com.example.dokuapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.dokuapp.Login.BaslangicActivity;
import com.example.dokuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {
    private ProgressDialog pd;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(IntroActivity.this);
        pd = new ProgressDialog(IntroActivity.this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (sharedPreferences.getString("email", "qwert") != "qwert" && sharedPreferences.getString("sifre", "asdfg") != "asdfg"){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(sharedPreferences.getString("email",
                            "qwert"), sharedPreferences.getString("sifre", "asdfg")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Intent intent = new Intent(IntroActivity.this, DokuAnasayfaActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {
                    Intent intent = new Intent(IntroActivity.this, BaslangicActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }


            }
        }, 3000);


    }
}