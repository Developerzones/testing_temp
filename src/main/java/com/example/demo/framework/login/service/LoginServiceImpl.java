package com.example.demo.framework.login.service;

import com.example.demo.framework.login.model.LoginEntity;
import com.example.demo.framework.login.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService{

   private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Autowired
    private LoginRepo repo;

    public String register(LoginEntity u) {
        if (repo.existsByUsername(u.getUsername())) return "Username already exists!";
        if (repo.existsByEmail(u.getEmail())) return "Email already registered!";
        if (repo.existsByMobile(u.getMobile())) return "Mobile number already registered!";

       u.setPassword(passwordEncoder.encode(u.getPassword()));

        repo.save(u);
        return null; // success
    }




    public boolean authenticate(String user, String pass){
        LoginEntity u = repo.findByUsername(user);
        if (u == null) return false;

        // Compare raw password with hashed password
        return passwordEncoder.matches(pass, u.getPassword());

    }


}
