package com.bank.bankprojekt.model;

import java.util.Date;

public class Lista3 {

    //bank
    private int bank_id;
    private String banknev;
    private String image;
    //szamla
    Integer szamlaszam;
    Integer egyenleg;
    //user
    private int ugyfelazonosito;
    private String anyjaNeve;
    private String email;
    private String szemelyi;
    private String telefonszam;

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

    private String nev;
    private String role;
    //lakcim
    private String varos;
    private String utca;
    private int hazszam;
    private int lakcim_id;
    //utalas

    private int tranzakcioid;


    public Lista3() {
    }

    public Lista3(int bank_id, String banknev, String image, Integer szamlaszam, Integer egyenleg, int ugyfelazonosito, String anyjaNeve, String email, String szemelyi, String telefonszam, String nev, String role, int tranzakcioid) {

        this.bank_id = bank_id;
        this.banknev = banknev;
        this.image = image;
        this.szamlaszam = szamlaszam;
        this.egyenleg = egyenleg;
        this.ugyfelazonosito = ugyfelazonosito;
        this.anyjaNeve = anyjaNeve;
        this.email = email;
        this.szemelyi = szemelyi;
        this.telefonszam = telefonszam;
        this.nev = nev;
        this.role = role;

        this.tranzakcioid = tranzakcioid;
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSzamlaszam() {
        return szamlaszam;
    }

    public void setSzamlaszam(Integer szamlaszam) {
        this.szamlaszam = szamlaszam;
    }

    public Integer getEgyenleg() {
        return egyenleg;
    }

    public void setEgyenleg(Integer egyenleg) {
        this.egyenleg = egyenleg;
    }

    public int getUgyfelazonosito() {
        return ugyfelazonosito;
    }

    public void setUgyfelazonosito(int ugyfelazonosito) {
        this.ugyfelazonosito = ugyfelazonosito;
    }

    public String getAnyjaNeve() {
        return anyjaNeve;
    }

    public void setAnyjaNeve(String anyjaNeve) {
        this.anyjaNeve = anyjaNeve;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSzemelyi() {
        return szemelyi;
    }

    public void setSzemelyi(String szemelyi) {
        this.szemelyi = szemelyi;
    }

    public String getTelefonszam() {
        return telefonszam;
    }

    public void setTelefonszam(String telefonszam) {
        this.telefonszam = telefonszam;
    }

    public String getUser_nev() {
        return nev;
    }

    public String getBanknev() {
        return banknev;
    }

    public void setBanknev(String banknev) {
        this.banknev = banknev;
    }

    public void setUser_nev(String user_nev) {
        this.nev = user_nev;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public int getTranzakcioid() {
        return tranzakcioid;
    }

    public void setTranzakcioid(int tranzakcioid) {
        this.tranzakcioid = tranzakcioid;
    }
}

