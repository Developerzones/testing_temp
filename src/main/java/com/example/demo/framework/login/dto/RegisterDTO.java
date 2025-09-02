package com.example.demo.framework.login.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "Username must not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._]{3,19}$",
            message = "Invalid username format. Must start with letters and be 4–20 characters using letters, digits, '_', or '.'")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number must not be empty")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobile;

    private OffsetDateTime createdAt;

    private String role;

}
