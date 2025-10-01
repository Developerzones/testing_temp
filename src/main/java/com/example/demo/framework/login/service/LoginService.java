package com.example.demo.framework.login.service;

import com.example.demo.framework.login.model.LoginEntity;
import org.springframework.http.ResponseEntity;

public interface LoginService {

// It is for Register API
    public String register(LoginEntity u) ;

    public boolean authenticate(String user, String pass) ;

//    It is for Login API

    public boolean existsByEmail(String email);

    public boolean verifyUser(String loginId, String pass);

    public boolean verifyOtp(String email, String suppliedOtp);

    public String generateAndSendOtp(String email);



    // It is of Reset Password

    public void generateResetToken(String email) ;

    public String resetPasswordWithToken(String token, String newPassword, String confirmPassword);


    }
