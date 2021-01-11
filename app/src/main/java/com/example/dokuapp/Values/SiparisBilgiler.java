package com.example.dokuapp.Values;

public class SiparisBilgiler {
    private String siparisNumarasi;
    private String siparisTarihi;
    private String odenenTutar;
    private String siparisDurumu;
    private String kargoTakipNo;

    public SiparisBilgiler(){

    }
    public SiparisBilgiler(String siparisNumarasi, String siparisTarihi, String odenenTutar, String siparisDurumu, String kargoTakipNo) {
        this.siparisNumarasi = siparisNumarasi;
        this.siparisTarihi = siparisTarihi;
        this.odenenTutar = odenenTutar;
        this.siparisDurumu = siparisDurumu;
        this.kargoTakipNo = kargoTakipNo;
    }

    public String getSiparisNumarasi() {
        return siparisNumarasi;
    }

    public String getSiparisTarihi() {
        return siparisTarihi;
    }

    public String getOdenenTutar() {
        return odenenTutar;
    }

    public String getSiparisDurumu() {
        return siparisDurumu;
    }

    public String getKargoTakipNo() {
        return kargoTakipNo;
    }

}
