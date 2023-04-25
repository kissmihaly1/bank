package com.bank.bankprojekt.model;

public class Lista1 {

    private String nev;
    private String varos;
    private Long osszeg;

    public Lista1() {

    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Long getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(Long osszeg) {
        this.osszeg = osszeg;
    }

    public Lista1(String nev, String varos, Long osszeg) {
        this.nev = nev;
        this.varos = varos;
        this.osszeg = osszeg;
    }

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }


}
