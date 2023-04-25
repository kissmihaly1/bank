package com.bank.bankprojekt.model;

public class Lakcim {


    private String varos;
    private String utca;
    private int hazszam;
    private int lakcim_id;

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public String getUtca() {
        return utca;
    }

    public void setUtca(String utca) {
        this.utca = utca;
    }

    public int getHazszam() {
        return hazszam;
    }

    public void setHazszam(int hazszam) {
        this.hazszam = hazszam;
    }

    public int getLakcim_id() {
        return lakcim_id;
    }

    public void setLakcim_id(int lakcim_id) {
        this.lakcim_id = lakcim_id;
    }

    public Lakcim() {
    }

    public Lakcim(String varos, String utca, int hazszam, int lakcim_id) {
        this.varos = varos;
        this.utca = utca;
        this.hazszam = hazszam;
        this.lakcim_id = lakcim_id;
    }

    public Lakcim(String varos, String utca, int hazszam) {
        this.varos = varos;
        this.utca = utca;
        this.hazszam = hazszam;
    }

}
