package com.MTBTrail.MTBTrail.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails vernon = User.builder()
                .username("vernon")
                .password("{noop}password")
                .roles("VIEWER")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}password")
                .roles("VIEWER", "MANAGER")
                .build();

        UserDetails alex = User.builder()
                .username("alex")
                .password("{noop}password")
                .roles("VIEWER", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(vernon, mary, alex);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/trails").hasRole("VIEWER")
                        .requestMatchers(HttpMethod.GET, "/api/trails/**").hasRole("VIEWER")
                        .requestMatchers(HttpMethod.POST, "/api/trails").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/trails").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/trails/**").hasRole("ADMIN"));

        httpSecurity.httpBasic();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }
}
