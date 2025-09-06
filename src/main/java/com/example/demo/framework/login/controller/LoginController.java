package com.example.demo.framework.login.controller;


import com.example.demo.framework.login.dto.LoginDTO;
import com.example.demo.framework.login.dto.RegisterDTO;
import com.example.demo.framework.login.model.LoginEntity;
import com.example.demo.framework.login.service.LoginService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Auth")
public class LoginController {

@Autowired
    private LoginService svc;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /*
     * Register APIs
     * for Creating Users
     *
     */
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



/*
*
* Login API
* for Auth Verification
*
*/


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO d) {
        if (d.getLoginType() == null || d.getLoginType().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("loginType must be 'password' or 'OTP'");
        }

        if (d.getLoginId() == null || d.getLoginId().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("loginId (username or email) must not be empty");
        }

        if ("OTP".equalsIgnoreCase(d.getLoginType())) {
            if (d.getOtp() == null || d.getOtp().isBlank()) {
                if (!svc.existsByEmail(d.getLoginId())) {
                    return ResponseEntity
                            .status(401)
                            .body("Invalid email for OTP");
                }

                svc.generateAndSendOtp(d.getLoginId());
                return ResponseEntity.ok("OTP has been sent to your email successfully.");
            }

            if (!d.getOtp().matches("\\d{6}")) {
                return ResponseEntity
                        .badRequest()
                        .body("OTP must be a 6‑digit number");
            }
            if (svc.verifyOtp(d.getLoginId(), d.getOtp())) {
                return ResponseEntity.ok("Successfully logged in via OTP");
            } else {
                return ResponseEntity
                        .status(401)
                        .body("Invalid OTP");
            }
        }

        if ("password".equalsIgnoreCase(d.getLoginType())) {
            if (d.getPassword() == null || d.getPassword().isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .body("Password must not be empty");
            }
            boolean valid = svc.verifyUser(d.getLoginId(), d.getPassword());
            if (valid) {
                return ResponseEntity.ok("Successfully logged in");
            } else {
                return ResponseEntity
                        .status(401)
                        .body("Invalid credentials");
            }
        }

        return ResponseEntity
                .badRequest()
                .body("Unsupported loginType: must be 'password' or 'OTP'");
    }



    /*
     * Reset Password API to get Email Link of Reset
     * for Auth Verification
     *
     */




    @PostMapping("/request-reset")
    public ResponseEntity<String> requestReset(@RequestParam String email) {

        logger.info("We get email "+ email);
        svc.generateResetToken(email);
        logger.info("reset Service is Completed");

        return ResponseEntity.ok( "Reset link sent to email");
    }



    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmReset(@RequestParam String token,
                                               @RequestParam String newPassword,
                                               @RequestParam String confirmPassword) {
        String response = svc.resetPasswordWithToken(token, newPassword, confirmPassword);
        return ResponseEntity.ok(response);
    }




}
