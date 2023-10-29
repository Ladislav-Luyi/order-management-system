package com.customer.ordermanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authConfig) -> {
                            authConfig.requestMatchers("", "/", "/kosik", "/upravaPolozky", "/objednavka/*", "/orders.txt", "/reply-orders.txt", "/static/**", "/custom.css", "/favicon.ico", "/images/back.jpg", "/images/pattern.png", "/podnik")
                                    .permitAll();
                            authConfig.anyRequest().authenticated();
                        }

                )
                .httpBasic(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}