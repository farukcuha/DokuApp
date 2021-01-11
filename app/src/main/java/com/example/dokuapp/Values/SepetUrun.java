package com.example.dokuapp.Values;

public class SepetUrun{
    private String sepetUrunAdi;
    private String sepetUrunResim;
    private String sepetUrunBirimFiyat;
    private int sepetUrunAdet;
    private int sepetUrunToplamFiyat;

    public SepetUrun(){

    }

    public SepetUrun(String sepetUrunAdi, String sepetUrunResim, String sepetUrunBirimFiyat, int sepetUrunAdet, int sepetUrunToplamFiyat) {
        this.sepetUrunAdi = sepetUrunAdi;
        this.sepetUrunResim = sepetUrunResim;
        this.sepetUrunBirimFiyat = sepetUrunBirimFiyat;
        this.sepetUrunAdet = sepetUrunAdet;
        this.sepetUrunToplamFiyat = sepetUrunToplamFiyat;

    }

    public String getSepetUrunAdi() {
        return sepetUrunAdi;
    }

    public String getSepetUrunResim() {
        return sepetUrunResim;
    }

    public String getSepetUrunBirimFiyat() { return sepetUrunBirimFiyat; }

    public int getSepetUrunAdet() { return sepetUrunAdet; }

    public int getSepetUrunToplamFiyat() { return sepetUrunToplamFiyat; }
}
