package com.example.getiryemek_clone.security;

import com.example.getiryemek_clone.entity.enums.Role;

import com.example.getiryemek_clone.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        String autHeader = request.getHeader("Authorization");
        String token = null ;
        String email = null;
        Role role = null;

        if (autHeader != null ) {
            if (autHeader.length() > 7) {
                token = autHeader.substring(7).trim();
                System.out.println("TOKEN " + token);
                email = jwtService.extractEmail(token);
                role = jwtService.extractRole(token);

            } else {
                System.out.println("Authorization header is too short");
            }
        } else {
            System.out.println("Authorization header is missing or invalid");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CostumUserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
            if (jwtService.validateToken(token , userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request , response);
    }
}
