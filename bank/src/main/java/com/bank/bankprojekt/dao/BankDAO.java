package com.bank.bankprojekt.dao;

import com.bank.bankprojekt.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;


@Repository
public class BankDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    public Bank getUserbyBank(Integer ugyfelazonosito) throws SQLException {
        String sql = "SELECT bank_id FROM szamla WHERE ugyfelazonosito=?";
        List<Map<String, Object>> bankid = getJdbcTemplate().queryForList(sql, ugyfelazonosito);
        Integer jobankid = (Integer) bankid.get(0).get("bank_id");
        String sql2 = "SELECT * FROM bank WHERE bank_id=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql2, jobankid);

        List<Bank> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Bank user = new Bank();
            user.setNev((String) row.get("banknev"));
            user.setBank_id((Integer) row.get("bank_id"));
            user.setImage((String) row.get("image"));
            result.add(user);
        }
        return result.get(0);

    }

    public Integer getBankid(String email) {
        String sql = "SELECT bank_id FROM szamla WHERE email=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, email);
        //String sql2= "SELECT szamlaszam, egyenleg FROM szamla WHERE email=?";
        //List<Map<String, Object>> rows2 = getJdbcTemplate().queryForList(sql2, email);
        Integer ertek = (Integer) rows.get(0).get("bank_id");
        //String sql3="UPDATE szamla SET egyenleg=egyenleg-ertek  WHERE email=?";
//        List<Map<String, Object>> rows3 = getJdbcTemplate().queryForList(sql3, email);
        return ertek;
    }


    public void insertBank(Bank bank) {
        String sql = "INSERT INTO bank(banknev, image) VALUES (?, ?)";
        getJdbcTemplate().update(sql, new Object[]{
                bank.getNev(), bank.getImage()});
    }


    public List<Utalas> listRendelesek(Integer szamlaszam) {
        String sql = "SELECT * FROM tranzakcio WHERE utalo_szamlaszam=? ORDER BY datum DESC";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, szamlaszam);

        List<Utalas> result = new ArrayList<Utalas>();
        for (Map<String, Object> row : rows) {
            Utalas utalas = new Utalas();
            utalas.setUtalo_szamlaszam((Integer) row.get("utalo_szamlaszam"));
            utalas.setCel_szamlaszam((Integer) row.get("cel_szamlaszam"));
            utalas.setDatum(((Date) row.get("datum")));
            utalas.setOsszeg((Integer) row.get("osszeg"));
            utalas.setTranzakcioid((Integer) row.get("tranzakcioid"));
            result.add(utalas);
        }

        return result;
    }
    public List<Utalas> listRendelesek2() {
        String sql = "SELECT * FROM tranzakcio ORDER BY datum DESC, tranzakcio.utalo_szamlaszam;";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Utalas> result = new ArrayList<Utalas>();
        for (Map<String, Object> row : rows) {
            Utalas utalas = new Utalas();
            utalas.setUtalo_szamlaszam((Integer) row.get("utalo_szamlaszam"));
            utalas.setCel_szamlaszam((Integer) row.get("cel_szamlaszam"));
            utalas.setDatum(((Date) row.get("datum")));
            utalas.setOsszeg((Integer) row.get("osszeg"));
            utalas.setTranzakcioid((Integer) row.get("tranzakcioid"));
            result.add(utalas);
        }

        return result;
    }



    public void utalasokTorles(Integer id) {
        String sql = "DELETE FROM tranzakcio WHERE tranzakcioid=?";
        getJdbcTemplate().update(sql, id);
    }
    public void bankTorles(Integer id) {
        String sql = "DELETE FROM bank WHERE bank_id=?";
        getJdbcTemplate().update(sql, id);
    }

    public void lista3Torles(Integer id) {
        String sql = "DELETE FROM users WHERE ugyfelazonosito=?";
        getJdbcTemplate().update(sql, id);
    }



    public List<Lakcim> lakcimList(){
        String sql= "SELECT lakcim.lakcimid, lakcim.varos, lakcim.utca, lakcim.hazszam FROM lakcim, users WHERE lakcim.lakcimid=users.lakcimid AND lakcim.varos=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, "Kecskemét");
        List<Lakcim> result = new ArrayList<Lakcim>();
        for (Map<String, Object> row : rows) {
            Lakcim lakcim = new Lakcim();
            lakcim.setLakcim_id((Integer) row.get("lakcimid"));
            lakcim.setHazszam((Integer) row.get("hazszam"));
            lakcim.setUtca((String) row.get("utca"));
            lakcim.setVaros((String) row.get("varos"));
            result.add(lakcim);
        }

        return result;

    }
    public List<String> lakcimList2(){
        String sql= "SELECT lakcim.lakcimid, lakcim.varos, lakcim.utca, lakcim.hazszam FROM lakcim, users WHERE lakcim.lakcimid=users.lakcimid AND lakcim.varos=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, "Kecskemét");
        List<String> result = new ArrayList<String>();
        for (Map<String, Object> row : rows) {
            String[] string = new String[3];
            string[0]= (String) row.get("varos");
            string[1]=(String) row.get("nev");
            string[2]=(String) row.get("osszeg");
        }

        return result;

    }

    public List<User> userList(){
        String sql= "SELECT * FROM users, lakcim WHERE lakcim.lakcimid=users.lakcimid AND lakcim.varos=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, "Kecskemét");
        List < User > result = new ArrayList < User > ();
        for (Map < String, Object > row: rows) {
            User user = new User();
            user.setUgyfelazonosito((Integer) row.get("ugyfelazonosito"));
            user.setEmail((String) row.get("email"));
            user.setNev((String) row.get("nev"));
            user.setAnyjaNeve((String) row.get("anyjaNeve"));
            user.setSzemelyi((String) row.get("szemelyiszam"));
            user.setTelefonszam((String) row.get("telefonszam"));
            user.setRole((String) row.get("role"));
            user.setPassword((String) row.get("password"));

            result.add(user);
        }

        return result;

    }
    public List<Szamla> szamlaList() {
        String sql = "SELECT DISTINCT szamla.ugyfelazonosito, szamla.szamlaszam,szamla.egyenleg, szamla.email FROM tranzakcio, lakcim, szamla, users WHERE szamla.ugyfelazonosito=users.ugyfelazonosito AND lakcim.lakcimid=users.lakcimid AND lakcim.varos=? AND tranzakcio.cel_szamlaszam=? ";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, "Kecskemét", 1000000001);

        List <Szamla> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Szamla user = new Szamla();
            user.setUgyfelazonosito((Integer) row.get("ugyfelazonosito"));
            user.setSzamlaszam((Integer) row.get("szamlaszam"));
            user.setEgyenleg((Integer)row.get("egyenleg"));

            result.add(user);
        }
        return result;
    }
    public List<Lista1> lista1List() {
        String sql="SELECT DISTINCT COUNT(*), users.nev, lakcim.varos FROM tranzakcio, lakcim, szamla, users WHERE szamla.ugyfelazonosito=users.ugyfelazonosito AND lakcim.lakcimid=users.lakcimid AND lakcim.varos=? AND tranzakcio.cel_szamlaszam=? AND tranzakcio.utalo_szamlaszam=szamla.szamlaszam GROUP BY users.email ORDER BY users.nev";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, "Kecskemét", 1000000001);

        List <Lista1> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Lista1 user = new Lista1();
            user.setNev((String) row.get("nev"));
            user.setVaros((String) row.get("varos"));
            user.setOsszeg((Long) row.get("COUNT(*)"));
            result.add(user);
        }
        return result;
    }

    public List<Lista2> lista2List() {
        String sql="SELECT MAX(tranzakcio.osszeg), users.nev, szamla.egyenleg, lakcim.varos FROM tranzakcio,lakcim,szamla,users WHERE szamla.ugyfelazonosito=users.ugyfelazonosito AND lakcim.lakcimid=users.lakcimid AND tranzakcio.utalo_szamlaszam=szamla.szamlaszam GROUP BY users.nev ORDER BY MAX(tranzakcio.osszeg)";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List <Lista2> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Lista2 user = new Lista2();
            user.setNev((String) row.get("nev"));
            user.setOsszeg((Integer) row.get("MAX(tranzakcio.osszeg)"));
            user.setVaros((String) row.get("varos"));
            user.setEgyenleg((Integer) row.get("egyenleg"));
            result.add(user);
        }
        return result;
    }
    public List<Lista3> lista3List() {
        String sql="SELECT DISTINCT users.ugyfelazonosito, users.email, users.telefonszam, users.nev, szamla.egyenleg, lakcim.varos, lakcim.utca, lakcim.hazszam, szamla.szamlaszam, bank.banknev FROM lakcim,szamla,users,bank WHERE szamla.ugyfelazonosito=users.ugyfelazonosito AND bank.bank_id=szamla.bank_id AND lakcim.lakcimid=users.lakcimid";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List <Lista3> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Lista3 user = new Lista3();
            user.setNev((String) row.get("nev"));
            user.setEmail((String) row.get("email"));
            user.setTelefonszam((String) row.get("telefonszam"));
            user.setEgyenleg((Integer) row.get("egyenleg"));
            user.setHazszam((Integer) row.get("hazszam"));
            user.setUtca((String) row.get("utca"));
            user.setVaros((String) row.get("varos"));
            user.setBanknev((String) row.get("banknev"));
            user.setSzamlaszam((Integer) row.get("szamlaszam"));
            user.setUgyfelazonosito((Integer) row.get("ugyfelazonosito"));

            result.add(user);
        }
        return result;
    }
    public List<Lista3> lista4List() {
        String sql="SELECT szamla.szamlaszam, users.nev, users.email FROM szamla, users WHERE users.ugyfelazonosito=szamla.ugyfelazonosito AND szamla.szamlaszam NOT IN (SELECT tranzakcio.cel_szamlaszam FROM tranzakcio ORDER BY szamla.szamlaszam)";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List <Lista3> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Lista3 user = new Lista3();
            user.setSzamlaszam((Integer) row.get("szamlaszam"));
            user.setEmail((String) row.get("email"));
            user.setNev((String) row.get("nev"));
            result.add(user);
        }
        return result;

    }

    public List<Bank>bankList(){
        String sql= "SELECT * FROM bank";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        List<Bank> result = new ArrayList<Bank>();
        for (Map<String, Object> row : rows) {
            Bank bank = new Bank();
            bank.setNev((String) row.get("banknev"));
            bank.setBank_id((Integer) row.get("bank_id"));
            bank.setImage((String) row.get("image"));
            result.add(bank);
        }


        return result;
    }

    public void bankModosit(String banknev, Integer bank_id, String image){
        String sql= "UPDATE bank SET banknev='"+banknev+"', image='"+image+"' WHERE bank_id=?";
        getJdbcTemplate().update(sql, bank_id);
    }


    public Lakcim getUserbyLakcim(String email) {
        String sql= "SELECT lakcim.lakcimid, lakcim.varos, lakcim.utca, lakcim.hazszam, users.email FROM lakcim, users WHERE lakcim.lakcimid=users.lakcimid AND email=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, email);


        List<Lakcim> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Lakcim lakcim = new Lakcim();
            lakcim.setHazszam((Integer) row.get("hazszam"));
            lakcim.setUtca((String) row.get("utca"));
            lakcim.setVaros((String) row.get("varos"));
            lakcim.setLakcim_id((Integer) row.get("lakcimid"));
            result.add(lakcim);
        }
        return result.get(0);

    }


}