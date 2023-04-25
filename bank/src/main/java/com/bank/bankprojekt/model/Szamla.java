package com.bank.bankprojekt.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Szamla implements Serializable {
    Integer szamlaszam;
    Integer egyenleg;
    Integer ugyfelazonosito;
    String email;

    public Szamla(Integer szamlaszam, Integer egyenleg, Integer ugyfelazonosito, String email) {
        this.szamlaszam = szamlaszam;
        this.egyenleg = egyenleg;
        this.ugyfelazonosito = ugyfelazonosito;
        this.email = email;
    }

    public Szamla(Integer szamlaszam, Integer egyenleg) {
        this.szamlaszam = szamlaszam;
        this.egyenleg = egyenleg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Szamla(Integer szamlaszam, Integer egyenleg, Integer ugyfelazonosito) {
        this.szamlaszam = szamlaszam;
        this.egyenleg = egyenleg;
        this.ugyfelazonosito = ugyfelazonosito;
    }
    public Szamla(){}

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

    public Integer getUgyfelazonosito() {
        return ugyfelazonosito;
    }

    public void setUgyfelazonosito(Integer ugyfelazonosito) {
        this.ugyfelazonosito = ugyfelazonosito;
    }
}
