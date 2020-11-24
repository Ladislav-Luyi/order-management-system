package com.customer.ordermanagementsystem.pojos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

/*
https://github.com/spring-projects/spring-security-javaconfig/blob/master/samples/preauth/src/main/java/org/springframework/security/samples/config/SecurityConfig.java
https://stackoverflow.com/questions/22767205/spring-security-exclude-url-patterns-in-security-annotation-configurartion
https://stackoverflow.com/questions/37285016/what-is-username-and-password-when-starting-spring-boot-with-tomcat
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

                http.httpBasic()
                        .and()
                        .authorizeRequests().antMatchers("/otvorene").authenticated()
                        .and()
                        .csrf().disable();




    }

}
