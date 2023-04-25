package com.bank.bankprojekt.model;

public class Lista2 {

    private String nev;
    private Integer osszeg;
    private Integer egyenleg;
    private String varos;

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Integer getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(Integer osszeg) {
        this.osszeg = osszeg;
    }

    public Integer getEgyenleg() {
        return egyenleg;
    }

    public void setEgyenleg(Integer egyenleg) {
        this.egyenleg = egyenleg;
    }

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public Lista2() {
    }

    public Lista2(String nev, Integer osszeg, Integer egyenleg, String varos) {
        this.nev = nev;
        this.osszeg = osszeg;
        this.egyenleg = egyenleg;
        this.varos = varos;
    }
}
