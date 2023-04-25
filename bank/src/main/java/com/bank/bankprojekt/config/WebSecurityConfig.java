package com.bank.bankprojekt.config;

import com.bank.bankprojekt.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
            .antMatchers("/static/images/kep.png").permitAll()
            .antMatchers("/bankinsert/**").access("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
      .antMatchers("/profil/**").access("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
      .antMatchers("/utalas/**").access("hasAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
            .antMatchers("/utalasmasnak/**").access("hasAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
            .antMatchers("/utalasok/**").access("hasAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
            .antMatchers("/bank/**").access("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
            .antMatchers("/index/**").access("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
            .antMatchers("/penzfeltoltes/**").access("hasAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
            .antMatchers("/listak/**").access("hasAuthority('ROLE_ADMIN')")
            .anyRequest().permitAll()
      .and()
      .formLogin().loginPage("/login")
      .permitAll()
      .defaultSuccessUrl("/index")
      .failureForwardUrl("/fail_login")
      .and()
      .logout().permitAll()
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
      .logoutSuccessUrl("/");
  }

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {}

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomUserDetailsService();
  }

}