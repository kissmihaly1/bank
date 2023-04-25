package com.bank.bankprojekt.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class User implements UserDetails {

  private static final long serialVersionUID = 1L;
  private int ugyfelazonosito;
  private String anyjaNeve;
  private String email;
  private String password;
  private String szemelyi;
  private String telefonszam;
  private String nev;
  private String role;


  public User() {}

  public User(String anyjaNeve, String email, String password, String szemelyi, String telefonszam, String nev, String role) {
    this.anyjaNeve = anyjaNeve;
    this.email = email;
    this.password = password;
    this.szemelyi = szemelyi;
    this.telefonszam = telefonszam;
    this.nev = nev;
    this.role=role;
  }

  public User(String anyjaNeve, String email, String password, String szemelyi, String telefonszam, String nev, String varos, String utca, int hazszam) {
    this.ugyfelazonosito = ugyfelazonosito;
    this.anyjaNeve = anyjaNeve;
    this.email = email;
    this.password = password;
    this.szemelyi = szemelyi;
    this.telefonszam = telefonszam;
    this.nev = nev;

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

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getNev() {
    return nev;
  }

  public void setNev(String nev) {
    this.nev = nev;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
            "ugyfelazonosito=" + ugyfelazonosito +
            ", anyjaNeve='" + anyjaNeve + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", szemelyi='" + szemelyi + '\'' +
            ", telefonszam='" + telefonszam + '\'' +
            ", nev='" + nev + '\'' +
            ", role='" + role + '\'' +
            '}';
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection < ? extends GrantedAuthority > getAuthorities() {
    Set < GrantedAuthority > authorities = new HashSet < GrantedAuthority > ();
    authorities.add(new SimpleGrantedAuthority(this.role));
    return authorities;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

}