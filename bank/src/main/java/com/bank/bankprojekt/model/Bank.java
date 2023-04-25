package com.bank.bankprojekt.model;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

public class Bank {
    private int bank_id;
    private String nev;
    private String image;

    public Bank(int bank_id, String nev, String image) {
        this.bank_id = bank_id;
        this.nev = nev;
        this.image = image;
    }

    public Bank(String nev, String image) {
        this.nev = nev;
        this.image = image;
    }

    public Bank() {
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

    @Override
    public String toString() {
        return "Bank{" +
                "bank_id=" + bank_id +
                ", nev='" + nev + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
