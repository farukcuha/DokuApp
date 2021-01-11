package com.example.dokuapp.Values;

public class AdresBilgiler {
    private String Adres;
    private String AdresBasligi;
    private String AdiSoyadi;
    private String IlIlce;
    private String Telefonno;


    public AdresBilgiler () {

    }
    public String getAdres() {
        return Adres;
    }

    public String getAdresBasligi() {
        return AdresBasligi;
    }

    public String getAdiSoyadi() {
        return AdiSoyadi;
    }

    public String getIlIlce() {
        return IlIlce;
    }

    public String getTelefonno() {
        return Telefonno;
    }

    public AdresBilgiler(String adres, String adresBasligi, String adiSoyadi, String ilIlce, String telefonno) {
        Adres = adres;
        AdresBasligi = adresBasligi;
        AdiSoyadi = adiSoyadi;
        IlIlce = ilIlce;
        Telefonno = telefonno;
    }
}
