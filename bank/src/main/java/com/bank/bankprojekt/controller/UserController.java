package com.bank.bankprojekt.controller;


import com.bank.bankprojekt.dao.BankDAO;
import com.bank.bankprojekt.dao.SzamlaDAO;
import com.bank.bankprojekt.dao.UserDAO;
import com.bank.bankprojekt.model.Bank;
import com.bank.bankprojekt.model.Lakcim;
import com.bank.bankprojekt.model.Szamla;
import com.bank.bankprojekt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private SzamlaDAO szamlaDAO;

  @Autowired
  private BankDAO bankDAO;

  private String hazszam_error;
  private String jelszo_error;
  private String jelszo_hossz_error;
  private String delete_confirm;
  private String edit_success;
  private String user_exists;
  private String register_success;
  private String feltoltes_success;
  static private String utalas_success;

  private List<String> formSave = new ArrayList<>();

  private void validation_reset() {
    formSave.clear();
    hazszam_error = "";
    jelszo_error = "";
    jelszo_hossz_error = "";
    delete_confirm = "";
    edit_success = "";
    user_exists = "";
    register_success = "";
    feltoltes_success="";
    utalas_success="";
  }

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("register_success", register_success);
    return "login";
  }


  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("register_success", register_success);
    register_success = "";
    return "index";
  }






  @GetMapping("/bank")
  public String bank(Model model) throws SQLException {

    model.addAttribute("hazszam_error", hazszam_error);
    model.addAttribute("jelszo_error", jelszo_error);
    model.addAttribute("jelszo_hossz_error", jelszo_hossz_error);
    model.addAttribute("delete_confirm", delete_confirm);
    model.addAttribute("formSave", formSave);
    model.addAttribute("edit_success", edit_success);

    validation_reset();


    String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
    Integer ugyfelazonosito= userDAO.getugyfelByUgyfelazonosito(currentUser);
    User user = userDAO.getUserByEmail(ugyfelazonosito);
    model.addAttribute("user", user);

      Szamla szamla=szamlaDAO.getUserbySzamla(ugyfelazonosito);
      model.addAttribute("szamla", szamla);
    Bank bank = bankDAO.getUserbyBank(ugyfelazonosito);
    model.addAttribute("bank", bank);
    Lakcim lakcim = bankDAO.getUserbyLakcim(currentUser);
    model.addAttribute("lakcim",lakcim);


    return "bank";
  }
@GetMapping("/index")
public String index(){
    return "index";
}

  @GetMapping("/register")
  public String register(Model model) {
    if (formSave.isEmpty() || formSave.size() == 11) {
      for (int i = 0; i < 10; ++i) {
        formSave.add("");
      }
    }

    model.addAttribute("hazszam_error", hazszam_error);
    model.addAttribute("jelszo_error", jelszo_error);
    model.addAttribute("jelszo_hossz_error", jelszo_hossz_error);
    model.addAttribute("formSave", formSave);
    model.addAttribute("user_exists", user_exists);
    model.addAttribute("edit_success", edit_success);



    List<Bank> bankList=bankDAO.bankList();
    model.addAttribute("bankList", bankList);
    return "register";

  }
  @PostMapping("/modifyUgyfel")
  public String modify(@RequestParam("anyjaNeve") String anyjaNeve, @RequestParam("password") String password, @RequestParam("password") String password_confirm, @RequestParam("szemelyi") String szemelyi, @RequestParam("telefonszam") String telefonszam, @RequestParam("nev") String nev, @RequestParam("varos") String varos, @RequestParam("utca") String utca, @RequestParam("hazszam") int hazszam){
    String email2 = SecurityContextHolder.getContext().getAuthentication().getName();

    formSave.add(anyjaNeve);
    formSave.add(szemelyi);
    formSave.add(telefonszam);
    formSave.add(varos);
    formSave.add(utca);
    formSave.add(String.valueOf(hazszam));
    formSave.add(nev);
    formSave.add(password);
    if (hazszam < 1) {
      hazszam_error = "Nem megfelelő házszámformátum!";
    }
    if (password.length() < 5) {
      jelszo_hossz_error = "Legalább 5 karakter hosszú jelszó szükséges!";
    }
    Integer ugyfelazonosito=userDAO.getugyfelByUgyfelazonosito(email2);
    userDAO.updateUser(ugyfelazonosito, nev,password,szemelyi,telefonszam,anyjaNeve);
    Integer lakcimid=userDAO.getLakcimid(ugyfelazonosito);
    userDAO.updateLakcim(varos, utca, hazszam, lakcimid);
    edit_success = "Sikeres módosítás!";
    return "redirect:/bank";
  }

  @PostMapping(value = "/registerugyfel")

    public String registerUser(@RequestParam("anyjaNeve") String anyjaNeve, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("password") String password_confirm, @RequestParam("szemelyi") String szemelyi, @RequestParam("telefonszam") String telefonszam, @RequestParam("nev") String nev, @RequestParam("varos") String varos, @RequestParam("utca") String utca, @RequestParam("hazszam") int hazszam, @RequestParam("banknev") String banknev) {
    validation_reset();
    if (userDAO.userExists(email)) {
      user_exists = "A felhasználó már létezik!";
    }
    if (hazszam < 1) {
      hazszam_error = "Nem megfelelő házszámformátum!";
    }
    if (!Objects.equals(password, password_confirm)) {
      jelszo_error = "Nem egyeznek a jelszavak!";
    }
    if (password.length() < 5) {
      jelszo_hossz_error = "Legalább 5 karakter hosszú jelszó szükséges!";
    }
    if (!jelszo_error.isEmpty() || !hazszam_error.isEmpty() || !jelszo_hossz_error.isEmpty() || !user_exists.isEmpty()) {
      formSave.add(email);
      formSave.add(anyjaNeve);
      formSave.add(szemelyi);
      formSave.add(telefonszam);
      formSave.add(varos);
      formSave.add(utca);
      formSave.add(String.valueOf(hazszam));
      formSave.add(nev);
      formSave.add(password);
      formSave.add(banknev);
      return "redirect:/register";
    }
    User user = new User(anyjaNeve, email, password, szemelyi, telefonszam, nev, "ROLE_USER");
    Lakcim lakcim= new Lakcim(varos, utca, hazszam);
    userDAO.insertUser(user, lakcim, banknev);
    userDAO.lakcimidValtoztatas(email);
    register_success = "Sikeres Regisztráció! Jelentkezzen be!";
    return "redirect:/login";
  }

  @PostMapping(value="/deleteuser")
  public String deleteUser() {
    String email=(SecurityContextHolder.getContext().getAuthentication().getName());
    Integer ugyfelazonosito= userDAO.getugyfelByUgyfelazonosito(email);
    Integer lakcimid=userDAO.getLakcimid(ugyfelazonosito);
    userDAO.deleteUser(ugyfelazonosito);
    userDAO.deleteLakcim(lakcimid);
    delete_confirm = "";
    return "redirect:/logout";
  }

  @PostMapping(value="/deleteuser_confirm")
  public String deleteUserConfirm() {
    delete_confirm = "Biztosan törli a fiókot?";
    return "redirect:/bank";
  }

  @PostMapping(value="/deleteuser_reject")
  public String deleteUserReject() {
    delete_confirm = "";
    return "redirect:/bank";
  }

  @PostMapping(value="/fail_login")
  public String handleFailedLogin() {
      
      return "redirect:/login?error";
  }
}