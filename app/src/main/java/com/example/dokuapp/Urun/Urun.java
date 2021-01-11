package com.example.dokuapp.Urun;

public class Urun  {

    private String ürünFiyati;
    private String ürünAdi;
    private String ürünTahminiKargo;
    private String ürünAciklama;
    private String ürünSatisTuru;
    private String ürünResim;

    public Urun(){

    }

    public Urun(String ürünAdi, String ürünFiyati , String ürünTahminiKargo, String ürünAciklama, String ürünSatisTuru, String ürünResim) {
        this.ürünFiyati = ürünFiyati;
        this.ürünAdi = ürünAdi;
        this.ürünTahminiKargo = ürünTahminiKargo;
        this.ürünAciklama = ürünAciklama;
        this.ürünSatisTuru = ürünSatisTuru;
        this.ürünResim = ürünResim;

    }
    public String getÜrünFiyati() {
        return ürünFiyati;
    }
    public String getÜrünAdi() {
        return ürünAdi;
    }
    public String getÜrünTahminiKargo() {
        return ürünTahminiKargo;
    }
    public String getÜrünAciklama() { return ürünAciklama; }
    public String getÜrünSatisTuru() { return ürünSatisTuru; }
    public String getÜrünResim() { return ürünResim; }
}
