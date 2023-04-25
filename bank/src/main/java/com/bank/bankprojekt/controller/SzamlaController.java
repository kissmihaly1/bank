package com.bank.bankprojekt.controller;



import com.bank.bankprojekt.dao.SzamlaDAO;
import com.bank.bankprojekt.dao.UserDAO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class SzamlaController {

    private String utalas_success;
    private String feltoltes_success;
    private String utalas_fail;
    private String feltoltes_fail;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SzamlaDAO szamlaDAO;


    private void validation_reset() {
        utalas_success="";
        feltoltes_success="";
        utalas_fail="";
        feltoltes_fail="";
    }


    @PostMapping("/utalasmasnak")
    public String bank(@RequestParam(value = "ugyfelazonosito", required = false) Integer ugyfelazonosito,@RequestParam("osszeg") Integer osszeg, @RequestParam("celszamla") Integer celszamla, @RequestParam(value = "email", required = false) String email, Model model) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer ugyfelazonositoutalo= userDAO.getugyfelByUgyfelazonosito(currentUser);
        Integer ugyfelazonositocel2= szamlaDAO.getUgyfelazonositofromszamlaszam(celszamla);
        Integer utaloegyenleg = szamlaDAO.getEgyenleg(ugyfelazonositoutalo);

        if (osszeg > 0&&osszeg<=utaloegyenleg&&(celszamla>=1000000000&&celszamla<=2000000001)) {

            utaloegyenleg = utaloegyenleg - osszeg;
            szamlaDAO.setEgyenleg(ugyfelazonositoutalo, utaloegyenleg);
            Integer celegyenleg = szamlaDAO.getEgyenleg(ugyfelazonositocel2);
            Integer utaloszam = szamlaDAO.getSzamlaszam(ugyfelazonositoutalo);
            celegyenleg += osszeg;
            szamlaDAO.setEgyenleg(ugyfelazonositocel2, celegyenleg);
            szamlaDAO.Tranzakcio(utaloszam, celszamla, osszeg);
            utalas_success="Sikeres utalás!";
            return "redirect:/utalas";
        }
        else {
            utalas_fail="Sikertelen utalás, helyes adatokat adjon meg!";
            return "redirect:/utalas";
        }
    }

    @GetMapping("/utalas")
    public String utalas(Model model){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Integer ugyfelazonosito= userDAO.getugyfelByUgyfelazonosito(currentUser);
        Szamla szamla=szamlaDAO.getUserbySzamla(ugyfelazonosito);
        model.addAttribute("szamla", szamla);
        model.addAttribute("utalas_success", utalas_success);
        model.addAttribute("utalas_fail", utalas_fail);
        validation_reset();

        return "utalas";
    }


    @PostMapping("/penzfeltoltes")
    public String penzfeltoltes(@RequestParam("osszeg") Integer osszeg, Model model){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer ugyfelazonosito = userDAO.getugyfelByUgyfelazonosito(currentUser);
        Integer alapegyenleg= szamlaDAO.getEgyenleg(ugyfelazonosito);
        if (osszeg>0&&osszeg<2000000000) {
            Integer modosultegyenleg = alapegyenleg + osszeg;
            szamlaDAO.setEgyenleg(ugyfelazonosito, modosultegyenleg);
            feltoltes_success = "Sikeres pénzfeltöltés!";
            return "redirect:/feltoltes";
        }
        else {
            feltoltes_fail="Sikertelen feltöltés, ellenőrízze az adatokat!";
            return "redirect:/feltoltes";

        }
    }
    @GetMapping("/feltoltes")
    public String feltoltes(Model model){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer ugyfelazonosito= userDAO.getugyfelByUgyfelazonosito(currentUser);
        User user = userDAO.getUserByEmail(ugyfelazonosito);
        model.addAttribute("user", user);
        Szamla szamla=szamlaDAO.getUserbySzamla(ugyfelazonosito);
        model.addAttribute("szamla", szamla);
        model.addAttribute("feltoltes_success", feltoltes_success);
        model.addAttribute("feltoltas_fail", feltoltes_fail);
        validation_reset();

        return "feltoltes";
    }


}
