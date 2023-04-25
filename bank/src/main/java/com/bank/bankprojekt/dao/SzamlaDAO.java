package com.bank.bankprojekt.dao;
import com.bank.bankprojekt.model.Szamla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class SzamlaDAO extends JdbcDaoSupport{
        @Autowired
        DataSource dataSource;

        @PostConstruct
        private void initialize() {
            setDataSource(dataSource);
        }

        public List<Szamla> listRendelesek(String ugyfelazonosito) {
            String sql = "SELECT * FROM szamla WHERE ugyfelazonosito=?";
            List <Map< String, Object >> rows = getJdbcTemplate().queryForList(sql, ugyfelazonosito);

            List <Szamla> result = new ArrayList<Szamla>();
            for (Map < String, Object > row: rows) {
                Szamla szamla = new Szamla();
                szamla.setSzamlaszam((Integer) row.get("szamlaszam"));
                szamla.setEgyenleg((Integer) row.get("egyenleg"));
                szamla.setUgyfelazonosito((Integer) row.get("ugyfelazonosito"));
                result.add(szamla);
            }

            return result;
        }


    public Szamla getUserbySzamla(Integer ugyfelazonosito) {
        String sql = "SELECT * FROM szamla WHERE ugyfelazonosito=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, ugyfelazonosito);
        List <Szamla> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Szamla user = new Szamla();
            user.setUgyfelazonosito((Integer) row.get("ugyfelazonosito"));
            user.setSzamlaszam((Integer) row.get("szamlaszam"));
            user.setEgyenleg((Integer)row.get("egyenleg"));
            user.setEmail((String) row.get("email"));

            result.add(user);
        }
        return result.get(0);

    }
    public Integer getSzamlaszam(Integer ugyfelazonosito){
        String sql = "SELECT * FROM szamla WHERE ugyfelazonosito=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, ugyfelazonosito);
        Integer ertek= (Integer) rows.get(0).get("szamlaszam");
        return ertek;
    }
    public Integer getEgyenleg(Integer ugyfelazonosito){
        String sql = "SELECT * FROM szamla WHERE ugyfelazonosito=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, ugyfelazonosito);
        Integer ertek= (Integer) rows.get(0).get("egyenleg");
        return ertek;
    }
    public Integer getUgyfelazonositofromszamlaszam(Integer szamlaszam){
        String sql = "SELECT ugyfelazonosito FROM szamla WHERE szamlaszam=?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, szamlaszam);
        Integer ertek= (Integer) rows.get(0).get("ugyfelazonosito");
        return ertek;
    }




    public void setEgyenleg(Integer ugyfelazonosito, int egyenleger){
            String sql3="UPDATE szamla SET egyenleg=?  WHERE ugyfelazonosito=?";
            getJdbcTemplate().update(sql3, egyenleger, ugyfelazonosito);

    }
    public String getEmail(Integer szamlaszam){
        String sql = "SELECT email FROM szamla WHERE szamlaszam=?";
        List<Map<String, Object>> emaill= getJdbcTemplate().queryForList(sql, szamlaszam);
        String emailke="";
        emailke= (String) emaill.get(0).get("email");
        return emailke;
    }

    public void Tranzakcio(Integer utaloszam, Integer celszam, Integer osszeg){
        String sql = "INSERT INTO tranzakcio(utalo_szamlaszam, cel_szamlaszam, datum, osszeg) VALUES (?,?, ?, ?)";
        java.util.Date javaDate = new java.util.Date();
        java.sql.Date mySQLDate = new java.sql.Date(javaDate.getTime());
        getJdbcTemplate().update(sql, utaloszam, celszam, mySQLDate, osszeg );

    }
}
