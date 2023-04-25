package com.bank.bankprojekt.controller;



import com.bank.bankprojekt.dao.BankDAO;
import com.bank.bankprojekt.dao.SzamlaDAO;
import com.bank.bankprojekt.dao.UserDAO;
import com.bank.bankprojekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


@Controller
public class BankController {

    private List<String> formSave = new ArrayList<>();
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SzamlaDAO szamlaDAO;

    @GetMapping("/bankinsert")
    private String bankinsert(Model model){
        if (formSave.isEmpty() || formSave.size() == 11) {
            for (int i = 0; i < 2; ++i) {
                formSave.add("");
            }
        }
        List<Bank> bankList = bankDAO.bankList();
        model.addAttribute("bankList", bankList);



        return "bankinsert";
    }
@Autowired
private BankDAO bankDAO;
    @PostMapping("/bankhozzadas")
    private String bankhozzadas(@RequestParam("nev") String nev, @RequestParam("image")MultipartFile image) throws SQLException, IOException {

        formSave.add(nev);
        String kep = Base64.getEncoder().encodeToString(image.getBytes());
        Bank bank= new Bank(nev, kep);
        bankDAO.insertBank(bank);
        return "redirect:/bankinsert";
    }

    @GetMapping("/utalasok")
    private String utalasok(Model model){
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer ugyfelazonosito= userDAO.getugyfelByUgyfelazonosito(currentUser);
        Integer szamlaszam=szamlaDAO.getSzamlaszam(ugyfelazonosito);
        List<Utalas> utalasList = bankDAO.listRendelesek(szamlaszam);
        List<Utalas> utalasList2 = bankDAO.listRendelesek2();

        model.addAttribute("utalasList", utalasList);
        model.addAttribute("utalasList2", utalasList2);

        return "utalasok";


    }
    @PostMapping("/utalastorles")
    private String utalastorles(@RequestParam("tranzakcioid") Integer id){
    bankDAO.utalasokTorles(id);

        return "redirect:/utalasok";
    }
    @PostMapping("/banktorles")
    private String bankrtorles(@RequestParam("bank_id") Integer id){
        bankDAO.bankTorles(id);

        return "redirect:/bankinsert";
    }
    @PostMapping("/bankModosit")
    private String modositas(@RequestParam("bank_id") Integer bankid, @RequestParam("banknev") String banknev, @RequestParam("image") MultipartFile image) throws IOException {
        String kep = Base64.getEncoder().encodeToString(image.getBytes());
        bankDAO.bankModosit(banknev,bankid, kep);
        return "redirect:/bankinsert";
    }
    @PostMapping("/lista3torles")
    private String lista3Torles(@RequestParam("ugyfelazonosito") Integer ugyfelazonosito){

        Integer lakcimid=userDAO.getLakcimid(ugyfelazonosito);
        userDAO.deleteLakcim(lakcimid);
        for (int i = 0; i < 1000000; i++) {
            
        }
        bankDAO.lista3Torles(ugyfelazonosito);
        return "redirect:/listak";
    }

    @GetMapping("/listak")
    private String listak(Model model){
        List<Lista1> lista1List=bankDAO.lista1List();
        List<Lista2> lista2List=bankDAO.lista2List();
        List<Lista3> lista3List=bankDAO.lista3List();
        List<Lista3> lista4List=bankDAO.lista4List();

        model.addAttribute("lista1List", lista1List);
        model.addAttribute("lista2List", lista2List);
        model.addAttribute("lista3List", lista3List);
        model.addAttribute("lista4List", lista4List);
        return "listak";

    }

}

