package com.example.demo.framework.login.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String loginType;
    private String loginId;
    private String password;
    private String otp;


}
