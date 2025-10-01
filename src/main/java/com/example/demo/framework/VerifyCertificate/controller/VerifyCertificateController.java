package com.example.demo.framework.VerifyCertificate.controller;


import com.example.demo.framework.VerifyCertificate.dto.VerifyCertificateDTO;
import com.example.demo.framework.VerifyCertificate.model.VerifyCertificateEntity;
import com.example.demo.framework.VerifyCertificate.service.VerifyCertificateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/verify-certificate")
public class VerifyCertificateController {

    @Autowired
    private VerifyCertificateService internshipService;

    @Autowired
    private VerifyCertificateService fileStorageService;

    // RESTRICTED: Only for admin use - consider adding @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllInternships() {
        // WARNING: This endpoint exposes all data - restrict access
        List<VerifyCertificateEntity> internships = internshipService.getAllInternships();

        // Add warning in response
        Map<String, Object> response = new HashMap<>();
        response.put("warning", "This endpoint exposes all internship data. Use /search for secure queries.");
        response.put("data", internships);
        response.put("count", internships.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VerifyCertificateEntity> getInternshipById(@PathVariable Long id) {
        return internshipService.getInternshipById(id)
                .map(internship -> ResponseEntity.ok(internship))
                .orElse(ResponseEntity.notFound().build());
    }

    // NEW: Secure search endpoint
    @PostMapping("/search")
    public ResponseEntity<?> searchInternships(@RequestBody @Valid VerifyCertificateDTO searchRequest) {
        try {
            // Validate search criteria
            if (searchRequest.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("At least one search criterion is required (email, certificateId, or phoneNumber)"));
            }


            // Prevent multiple criteria searches for security
            if (searchRequest.hasMultipleCriteria()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Please search with only one criterion at a time"));
            }

            // Perform secure search
            Optional<VerifyCertificateEntity> result = internshipService.findInternshipByCriteria(searchRequest);

            if (result.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "No internship found matching the search criteria");
                response.put("data", null);
                return ResponseEntity.ok(response);
            }

            // Return successful result
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Internship found successfully");
            response.put("data", result.get());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Search failed: " + e.getMessage()));
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createInternship(
            @RequestPart("internship") @Valid VerifyCertificateEntity internship,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) {

        try {
            // Handle file upload
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                if (!fileStorageService.isValidImageFile(profilePhoto)) {
                    return ResponseEntity.badRequest()
                            .body(createErrorResponse("Please upload a valid image file"));
                }

                if (profilePhoto.getSize() > 5 * 1024 * 1024) {
                    return ResponseEntity.badRequest()
                            .body(createErrorResponse("File size must be less than 5MB"));
                }

                String fileName = fileStorageService.storeFile(profilePhoto);
                internship.setProfilePhoto(fileName);
            }


            VerifyCertificateEntity savedInternship = internshipService.saveInternship(internship);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInternship);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error saving internship: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInternship(
            @PathVariable Long id,
            @RequestPart("internship") @Valid VerifyCertificateEntity internship,
            @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) {

        return internshipService.getInternshipById(id)
                .map(existingInternship -> {
                    try {
                        // Update fields
                        existingInternship.setName(internship.getName());
                        existingInternship.setEmail(internship.getEmail());
                        existingInternship.setPhoneNumber(internship.getPhoneNumber());
                        existingInternship.setGithubLink(internship.getGithubLink());
                        existingInternship.setLinkedinLink(internship.getLinkedinLink());
                        existingInternship.setStartDate(internship.getStartDate());
                        existingInternship.setEndDate(internship.getEndDate());
                        existingInternship.setInternshipProfile(internship.getInternshipProfile());
                        existingInternship.setInternshipStatus(internship.getInternshipStatus());
                        existingInternship.setRemark(internship.getRemark());
                        existingInternship.setProjectLink(internship.getProjectLink());
                        existingInternship.setProjectStatus(internship.getProjectStatus());
                        existingInternship.setCertificateLink(internship.getCertificateLink());
                        existingInternship.setCertificateId(internship.getCertificateId());

                        // Handle file upload
                        if (profilePhoto != null && !profilePhoto.isEmpty()) {
                            String fileName = fileStorageService.storeFile(profilePhoto);
                            existingInternship.setProfilePhoto(fileName);
                        }

                        VerifyCertificateEntity updatedInternship = internshipService.saveInternship(existingInternship);
                        return ResponseEntity.ok(updatedInternship);

                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(createErrorResponse("Error updating internship: " + e.getMessage()));
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInternship(@PathVariable Long id) {
        return internshipService.getInternshipById(id)
                .map(internship -> {
                    internshipService.deleteInternship(id);
                    return ResponseEntity.ok(createSuccessResponse("Internship deleted successfully"));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // Could not determine content type
            }

            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }

    private Map<String, Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        return response;
    }



}