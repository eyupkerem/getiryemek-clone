package com.example.getiryemek_clone.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final  JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer:: disable)
                .authorizeHttpRequests( x->
                        x.requestMatchers("/api/admin/generateToken").permitAll()
                                .requestMatchers("/api/costumer/generateToken").permitAll()
                                .requestMatchers("/api/restaurant-admin/generateToken").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/costumer").permitAll()
                                .requestMatchers("/swagger-ui/index.html#/").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                //.requestMatchers("/api/food").hasAuthority("RESTAURANT_ADMIN")
                                // .requestMatchers(HttpMethod.POST,"/api/admin").permitAll()
                                //.requestMatchers(HttpMethod.POST,"/api/costumer").permitAll()
                                //.requestMatchers(HttpMethod.GET,"/api/restaurant-admin").hasAuthority("ADMIN")
                                //.requestMatchers(HttpMethod.GET,"/api/restaurant").hasAuthority("USER")
                                //.requestMatchers(HttpMethod.GET,"/api/restaurant").hasRole("USER")
                                //.requestMatchers(HttpMethod.GET,"/api/restaurant").authenticated()
                                //.anyRequest().permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement( x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                .build();
    }




    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


}
