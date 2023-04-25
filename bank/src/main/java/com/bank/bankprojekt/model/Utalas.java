package com.bank.bankprojekt.model;

import java.util.Date;

public class Utalas {
    private int utalo_szamlaszam;
    private int cel_szamlaszam;
    private Date datum;
    private int osszeg;
    private int tranzakcioid;

    public Utalas() {

    }

    @Override
    public String toString() {
        return "Utalas{" +
                "utalo_szamlaszam=" + utalo_szamlaszam +
                ", cel_szamlaszam=" + cel_szamlaszam +
                ", datum=" + datum +
                ", osszeg=" + osszeg +
                ", tranzakcioid=" + tranzakcioid +
                '}';
    }

    public int getUtalo_szamlaszam() {
        return utalo_szamlaszam;
    }

    public void setUtalo_szamlaszam(int utalo_szamlaszam) {
        this.utalo_szamlaszam = utalo_szamlaszam;
    }

    public int getCel_szamlaszam() {
        return cel_szamlaszam;
    }

    public void setCel_szamlaszam(int cel_szamlaszam) {
        this.cel_szamlaszam = cel_szamlaszam;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(int osszeg) {
        this.osszeg = osszeg;
    }

    public int getTranzakcioid() {
        return tranzakcioid;
    }

    public void setTranzakcioid(int tranzakcioid) {
        this.tranzakcioid = tranzakcioid;
    }

    public Utalas(int utalo_szamlaszam, int cel_szamlaszam, Date datum, int osszeg) {
        this.utalo_szamlaszam = utalo_szamlaszam;
        this.cel_szamlaszam = cel_szamlaszam;
        this.datum = datum;
        this.osszeg = osszeg;
    }

    public Utalas(int utalo_szamlaszam, int cel_szamlaszam, Date datum, int osszeg, int tranzakcioid) {
        this.utalo_szamlaszam = utalo_szamlaszam;
        this.cel_szamlaszam = cel_szamlaszam;
        this.datum = datum;
        this.osszeg = osszeg;
        this.tranzakcioid = tranzakcioid;
    }
}
