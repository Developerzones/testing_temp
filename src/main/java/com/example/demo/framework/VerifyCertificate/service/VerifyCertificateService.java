package com.example.demo.framework.VerifyCertificate.service;

import com.example.demo.framework.VerifyCertificate.dto.VerifyCertificateDTO;
import com.example.demo.framework.VerifyCertificate.model.VerifyCertificateEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface VerifyCertificateService {

    public VerifyCertificateEntity saveInternship(VerifyCertificateEntity internship);

    public List<VerifyCertificateEntity> getAllInternships();

    public Optional<VerifyCertificateEntity> getInternshipById(Long id);

    public void deleteInternship(Long id);

    public List<VerifyCertificateEntity> getInternshipsByStatus(String status);

    public List<VerifyCertificateEntity> searchInternships(VerifyCertificateDTO searchRequest);

    public Optional<VerifyCertificateEntity> findInternshipByCriteria(VerifyCertificateDTO searchRequest);

    public String storeFile(MultipartFile file);
    public boolean isValidImageFile(MultipartFile file);
    public void deleteFile(String fileName);
    public Resource loadFileAsResource(String fileName) ;

    }
