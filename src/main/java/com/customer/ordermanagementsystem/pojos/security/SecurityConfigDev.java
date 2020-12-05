package com.customer.ordermanagementsystem.pojos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfigDev extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .and()
                .authorizeRequests().antMatchers("/otvorene").authenticated()
                .and()
                .authorizeRequests().antMatchers("/db/**").permitAll()
                .and()
                .csrf().disable();


        http.headers().frameOptions().disable(); 
        }

    }



