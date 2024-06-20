package com.travelbnb.config;

import com.travelbnb.entity.AppUser;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Configuration
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;


    public JWTRequestFilter(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String tokenHeader =  request.getHeader("Authorization");

       if(tokenHeader!=null && tokenHeader.startsWith("Bearer "))
       {
            //System.out.println(tokenHeader);
            String toke = tokenHeader.substring(8, tokenHeader.length() -1);
            System.out.println(toke);
            String username= jwtService.getUserName(toke);
            System.out.println(username);
           Optional<AppUser> opUserName = appUserRepository.findByUsername(username);
           if(opUserName.isPresent())  {
               AppUser appUser = opUserName.get();
               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUser,null, Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
           }
       }
        filterChain.doFilter(request,response);
    }
}
