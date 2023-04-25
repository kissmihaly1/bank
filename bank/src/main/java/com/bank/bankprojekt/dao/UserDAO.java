package com.bank.bankprojekt.dao;

import com.bank.bankprojekt.model.Lakcim;
import com.bank.bankprojekt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO extends JdbcDaoSupport {

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Autowired
  DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  public void insertUser(User user, Lakcim lakcim, String banknev) {




    String sql = "INSERT INTO users(email, nev, szemelyiszam, anyjaNeve, telefonszam, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
    getJdbcTemplate().update(sql, new Object[] {
      user.getEmail(), user.getNev(), user.getSzemelyi(), user.getAnyjaNeve(), user.getTelefonszam(), passwordEncoder.encode(user.getPassword()), user.getRole()});
   //SZAMLA
    String sql5="INSERT INTO lakcim(varos, utca, hazszam) VALUES(?,?,?)";
    getJdbcTemplate().update(sql5, new Object[] {
            lakcim.getVaros(), lakcim.getUtca(), lakcim.getHazszam()});


    String sql2="INSERT INTO szamla(egyenleg, ugyfelazonosito, bank_id) VALUES(?,?,?)";
    String email= user.getEmail();
    String sql3="SELECT ugyfelazonosito FROM users WHERE email=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql3, user.getEmail());
    Integer szama= (Integer) rows.get(0).get("ugyfelazonosito");
    String sql4="SELECT bank_id FROM bank WHERE banknev=?";
    List < Map < String, Object >> rows2 = getJdbcTemplate().queryForList(sql4, banknev);
    Integer bank_id=(Integer) rows2.get(0).get("bank_id") ;
    getJdbcTemplate().update(sql2,0, szama, bank_id);

//LAKCIM
  }
  public void lakcimidValtoztatas(String email) {
    String sql = "SELECT MAX(lakcimid) FROM lakcim";
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
    Integer lakcimid2 = (Integer) rows.get(0).get("MAX(lakcimid)");
    String sql2 = "UPDATE users SET lakcimid=? WHERE email=?";
    getJdbcTemplate().update(sql2, lakcimid2, email);
  }



  public User getUserByEmail(Integer ugyfelazonosito) {
    String sql = "SELECT * FROM users WHERE ugyfelazonosito=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql, ugyfelazonosito);

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

    return result.get(0);
  }


  public User getUserByEmail2(String email) {
    String sql = "SELECT * FROM users WHERE email=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql, email);

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

    return result.get(0);
  }

  public boolean userExists(String email) {
    String sql = "SELECT * FROM users WHERE email=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql, email);
    return rows.size() > 0;
  }

  public void deleteUser(Integer ugyfelazonosito) {
    String sql2 = "SELECT users.lakcimid FROM users WHERE ugyfelazonosito=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql2, ugyfelazonosito);
    String sql = "DELETE FROM users WHERE ugyfelazonosito=?";
    getJdbcTemplate().update(sql, ugyfelazonosito);
  }
  public void updateUser(Integer ugyfelazonosito, String nev, String password, String szemelyi, String telefonszam, String anyjaNeve) {
    String sql = "UPDATE users SET nev='" + nev + "',password='" + passwordEncoder.encode(password) + "', szemelyiszam='" + szemelyi + "', telefonszam='" + telefonszam + "', anyjaNeve='" + anyjaNeve + "' WHERE ugyfelazonosito=?";
    getJdbcTemplate().update(sql, ugyfelazonosito);
  }

  public Integer getugyfelByUgyfelazonosito(String email){
    String sql = "SELECT * FROM users WHERE email=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql, email);
    return (Integer) rows.get(0).get("ugyfelazonosito");
  }


  public void updateLakcim(String varos, String utca, Integer hazszam, Integer lakcimid){
    String sql= "UPDATE lakcim SET varos='"+varos+"', utca='"+utca+"', hazszam='"+hazszam+"' WHERE lakcimid=?";
    getJdbcTemplate().update(sql, lakcimid);
  }

  public Integer getLakcimid(Integer ugyfelazonosito){
    String sql = "SELECT lakcimid FROM users WHERE ugyfelazonosito=?";
    List < Map < String, Object >> rows = getJdbcTemplate().queryForList(sql, ugyfelazonosito);
    return (Integer) rows.get(0).get("lakcimid");
  }

  public void deleteLakcim(Integer lakcimid){
    String sql="DELETE FROM lakcim WHERE lakcimid=?";
    getJdbcTemplate().update(sql, lakcimid);
  }
}