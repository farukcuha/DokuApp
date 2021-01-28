package com.example.dokuapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dokuapp.Activities.DokuAnasayfaActivity;
import com.example.dokuapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.app.PendingIntent.getActivity;


public class GirisYapActivity extends AppCompatActivity {
    private EditText girisyapemail, girisyapsifre;
    private Button girisyapbutton;
    private FirebaseAuth girisYetkisi;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

            girisyapemail = findViewById(R.id.xml_edittext_girisyap_email);
            girisyapsifre = findViewById(R.id.xml_edittext_girisyap_sifre);
            girisyapbutton = findViewById(R.id.xml_btn_girisyap_2);

            girisYetkisi = FirebaseAuth.getInstance();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(GirisYapActivity.this);
            final SharedPreferences.Editor editor = sharedPreferences.edit();

            girisyapbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String str_giris_email = girisyapemail.getText().toString();
                    final String str_giris_sifre = girisyapsifre.getText().toString();

                    pd = new ProgressDialog(GirisYapActivity.this);
                    pd.setMessage("Giriş Yapılıyor");
                    pd.show();

                    if(TextUtils.isEmpty(str_giris_email)||TextUtils.isEmpty(str_giris_sifre)){
                        Toast.makeText(GirisYapActivity.this, "Lütfen alanları doldurun", Toast.LENGTH_SHORT).show();
                    }



                    girisYetkisi.signInWithEmailAndPassword(str_giris_email,str_giris_sifre)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();

                                        editor.putString("email", str_giris_email);
                                        editor.putString("sifre", str_giris_sifre);
                                        editor.commit();

                                        Intent intent = new Intent(GirisYapActivity.this, DokuAnasayfaActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        pd.dismiss();
                                        Toast.makeText(GirisYapActivity.this, "Girmedi", Toast.LENGTH_SHORT).show();


                                    }

                                }
                            });
                }
            });

    }
}