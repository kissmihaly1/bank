package com.bank.bankprojekt.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.bank.bankprojekt.dao.UserDAO;
import com.bank.bankprojekt.model.User;

public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserDAO userDAO;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDAO.getUserByEmail2(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return user;
  }

}