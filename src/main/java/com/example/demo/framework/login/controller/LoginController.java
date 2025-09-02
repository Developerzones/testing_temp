package com.example.demo.framework.login.controller;


import com.example.demo.framework.login.dto.RegisterDTO;
import com.example.demo.framework.login.model.LoginEntity;
import com.example.demo.framework.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Auth")
public class LoginController {

@Autowired
    private LoginService svc;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO d) {
        LoginEntity u = new LoginEntity();
        u.setUsername(d.getUsername());
        u.setPassword(d.getPassword()); // Will be encoded in service
        u.setEmail(d.getEmail());
        u.setMobile(d.getMobile());
        u.setRole("USER");
        String error = svc.register(u);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }

        return ResponseEntity.ok(Map.of("message", "Your account has been registered successfully!"));
    }






}
