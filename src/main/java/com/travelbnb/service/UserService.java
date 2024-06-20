package com.travelbnb.service;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;
    private final JWTService jwsService;

    public UserService(AppUserRepository appUserRepository, JWTService jwsService) {
        this.appUserRepository = appUserRepository;
        this.jwsService = jwsService;
    }

    public AppUser createUser(AppUser user){

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
        AppUser createdUser = appUserRepository.save(user);
        return createdUser;
    }

    public String verifyLogin(LoginDto loginDto) {
     Optional<AppUser> opUserName =  appUserRepository.findByUsername(loginDto.getUsername());
     if(opUserName.isPresent()) {
         AppUser appUser = opUserName.get();
         if( BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())){
                String token =  jwsService.generateToken(appUser);
                return token;
         }
     }
     return null;
    }
}
