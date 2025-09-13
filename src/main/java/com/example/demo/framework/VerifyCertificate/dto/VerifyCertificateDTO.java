package com.example.demo.framework.VerifyCertificate.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCertificateDTO {

    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Size(max = 50, message = "Certificate ID cannot exceed 50 characters")
    private String certificateId;

    @Size(max = 10, message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    // Utility method to check if search criteria is provided
    public boolean isEmpty() {
        return (email == null || email.trim().isEmpty()) &&
                (certificateId == null || certificateId.trim().isEmpty()) &&
                (phoneNumber == null || phoneNumber.trim().isEmpty());
    }
    public boolean hasMultipleCriteria() {
        int count = 0;
        if (email != null && !email.trim().isEmpty()) count++;
        if (certificateId != null && !certificateId.trim().isEmpty()) count++;
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) count++;
        return count > 1;
    }
}
