package com.example.demo.framework.VerifyCertificate.service;


import com.example.demo.framework.VerifyCertificate.dto.VerifyCertificateDTO;
import com.example.demo.framework.VerifyCertificate.model.VerifyCertificateEntity;
import com.example.demo.framework.VerifyCertificate.repo.VerifyCertificateRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerifyCertificateServiceImpl implements VerifyCertificateService{

    @Autowired
    private VerifyCertificateRepo internshipRepository;


    public VerifyCertificateEntity saveInternship(VerifyCertificateEntity internship) {
        return internshipRepository.save(internship);
    }

    public List<VerifyCertificateEntity> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Optional<VerifyCertificateEntity> getInternshipById(Long id) {
        return internshipRepository.findById(id);
    }

    public void deleteInternship(Long id) {
        internshipRepository.deleteById(id);
    }

    public List<VerifyCertificateEntity> getInternshipsByStatus(String status) {
        return internshipRepository.findByInternshipStatus(status);
    }

    // NEW: Secure search functionality
    public List<VerifyCertificateEntity> searchInternships(VerifyCertificateDTO searchRequest) {
        List<VerifyCertificateEntity> results = new ArrayList<>();

        // Search by email (exact match, case-insensitive)
        if (searchRequest.getEmail() != null && !searchRequest.getEmail().trim().isEmpty()) {
            Optional<VerifyCertificateEntity> internship = internshipRepository.findByEmailIgnoreCase(searchRequest.getEmail().trim());
            internship.ifPresent(results::add);
        }

        // Search by certificate ID (exact match, case-insensitive)
        if (searchRequest.getCertificateId() != null && !searchRequest.getCertificateId().trim().isEmpty()) {
            Optional<VerifyCertificateEntity> internship = internshipRepository.findByCertificateIdIgnoreCase(searchRequest.getCertificateId().trim());
            internship.ifPresent(results::add);
        }

        // Search by phone number (exact match)
        if (searchRequest.getPhoneNumber() != null && !searchRequest.getPhoneNumber().trim().isEmpty()) {
            Optional<VerifyCertificateEntity> internship = internshipRepository.findByPhoneNumber(searchRequest.getPhoneNumber().trim());
            internship.ifPresent(results::add);
        }

        return results;
    }

    // Alternative method for single criterion search (more efficient)
    public Optional<VerifyCertificateEntity> findInternshipByCriteria(VerifyCertificateDTO searchRequest) {
        if (searchRequest.getEmail() != null && !searchRequest.getEmail().trim().isEmpty()) {
            return internshipRepository.findByEmailIgnoreCase(searchRequest.getEmail().trim());
        }

        if (searchRequest.getCertificateId() != null && !searchRequest.getCertificateId().trim().isEmpty()) {
            return internshipRepository.findByCertificateIdIgnoreCase(searchRequest.getCertificateId().trim());
        }

        if (searchRequest.getPhoneNumber() != null && !searchRequest.getPhoneNumber().trim().isEmpty()) {
            return internshipRepository.findByPhoneNumber(searchRequest.getPhoneNumber().trim());
        }

        return Optional.empty();
    }



    //file service

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Path.of(uploadDir);
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(originalFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            // Generate unique filename to avoid conflicts
            String fileExtension = "";
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + originalFileName + ". Please try again!", ex);
        }
    }




    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }



    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("Could not delete file " + fileName, ex);
        }
    }


    public boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif") ||
                        contentType.equals("image/webp")
        );
    }
}