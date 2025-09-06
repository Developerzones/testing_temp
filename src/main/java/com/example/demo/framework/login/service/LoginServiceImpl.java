package com.example.demo.framework.login.service;

import com.example.demo.framework.login.model.LoginEntity;
import com.example.demo.framework.login.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.security.SecureRandom;

import java.time.LocalDateTime;
import java.util.UUID;



@Service
public class LoginServiceImpl implements LoginService{

   private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    @Autowired
    private LoginRepo repo;

    @Autowired
    private JavaMailSender mailSender;

    private final SecureRandom random = new SecureRandom();




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




    /*
    * For Login Service Impl
    *
    */


    public boolean existsByEmail(String email) {
        return repo.findByEmail(email) != null;
    }

    public boolean verifyUser(String loginId, String pass) {
        LoginEntity u = repo.findByUsername(loginId);
        if (u == null) u = repo.findByEmail(loginId);
        if (u == null) return false;
        return passwordEncoder.matches(pass, u.getPassword());
    }


    public boolean verifyOtp(String email, String suppliedOtp) {
        LoginEntity u = repo.findByEmail(email);
        if (u == null || u.getOtp() == null || !u.getOtp().equals(suppliedOtp)) {
            return false;
        }
        u.setOtp(null);
        repo.save(u);
        return true;
    }

    public String generateAndSendOtp(String email) {
        
        LoginEntity u = repo.findByEmail(email);
        if (u == null) return null;

        String otp = String.format("%06d", random.nextInt(1_000_000));
        System.out.println("before mail Send Success otp is "+otp);

        u.setOtp(otp);
        repo.save(u);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Your One-Time Login Code");
        msg.setText("Your login OTP is: " + otp);
        mailSender.send(msg);

        System.out.println("After mail Send Success otp is "+otp);
        return otp;
    }






    /*
     * Service for reset Password
     * for token Generation
     *
     */


    public void generateResetToken(String email) {
        LoginEntity user = repo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

        repo.save(user);

        // Send email
        sendResetEmail(user.getEmail(), token);
    }


    private void sendResetEmail(String toEmail, String token) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8080/api/reset-password/confirm?token=" + token;
        String body = "Click the following link to reset your password:\n" + resetLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("shivamdubeyworklaptop@gmail.com");

        mailSender.send(message);
    }

    public String resetPasswordWithToken(String token, String newPassword, String confirmPassword) {
        LoginEntity user = repo.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return "Token has expired";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "Passwords do not match";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiry(null);
        repo.save(user);

        return "Password has been successfully reset";
    }


}
