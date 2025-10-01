package com.example.demo.framework.VerifyCertificate.repo;


import com.example.demo.framework.VerifyCertificate.model.VerifyCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerifyCertificateRepo extends JpaRepository<VerifyCertificateEntity, Long> {
    // Existing methods
    List<VerifyCertificateEntity> findByInternshipStatus(String status);
    List<VerifyCertificateEntity> findByNameContainingIgnoreCase(String name);

    // NEW: Secure search methods
    Optional<VerifyCertificateEntity> findByEmailIgnoreCase(String email);
    Optional<VerifyCertificateEntity> findByCertificateIdIgnoreCase(String certificateId);
    Optional<VerifyCertificateEntity> findByPhoneNumber(String phoneNumber);

    // Additional search methods for flexibility
    List<VerifyCertificateEntity> findByEmailContainingIgnoreCase(String email);
}