package com.example.demo.framework.VerifyCertificate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "internships")
public class VerifyCertificateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500, message = "Profile photo URL cannot exceed 500 characters")
    @Column(name = "profile_photo", length = 500)
    private String profilePhoto;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    // UPDATED: 10-digit phone number for India
    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter a valid 10-digit phone number")
    @Size(max = 10, message = "Phone number must be exactly 10 digits")
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Size(max = 500, message = "GitHub link cannot exceed 500 characters")
    @Column(name = "github_link", length = 500)
    private String githubLink;

    @Size(max = 500, message = "LinkedIn link cannot exceed 500 characters")
    @Column(name = "linkedin_link", length = 500)
    private String linkedinLink;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotBlank(message = "Internship profile is required")
    @Size(max = 100, message = "Internship profile cannot exceed 100 characters")
    @Column(name = "internship_profile", nullable = false, length = 100)
    private String internshipProfile;

    @NotBlank(message = "Internship status is required")
    @Size(max = 50, message = "Internship status cannot exceed 50 characters")
    @Column(name = "internship_status", nullable = false, length = 50)
    private String internshipStatus;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    @Size(max = 500, message = "Project link cannot exceed 500 characters")
    @Column(name = "project_link", length = 500)
    private String projectLink;

    @Size(max = 50, message = "Project status cannot exceed 50 characters")
    @Column(name = "project_status", length = 50)
    private String projectStatus;

    @Size(max = 500, message = "Certificate link cannot exceed 500 characters")
    @Column(name = "certificate_link", length = 500)
    private String certificateLink;

    @Size(max = 50, message = "Certificate ID cannot exceed 50 characters")
    @Column(name = "certificate_id", length = 50)
    private String certificateId;

    // Constructors
    public VerifyCertificateEntity() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProfilePhoto() { return profilePhoto; }
    public void setProfilePhoto(String profilePhoto) { this.profilePhoto = profilePhoto; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getGithubLink() { return githubLink; }
    public void setGithubLink(String githubLink) { this.githubLink = githubLink; }

    public String getLinkedinLink() { return linkedinLink; }
    public void setLinkedinLink(String linkedinLink) { this.linkedinLink = linkedinLink; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getInternshipProfile() { return internshipProfile; }
    public void setInternshipProfile(String internshipProfile) { this.internshipProfile = internshipProfile; }

    public String getInternshipStatus() { return internshipStatus; }
    public void setInternshipStatus(String internshipStatus) { this.internshipStatus = internshipStatus; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public String getProjectLink() { return projectLink; }
    public void setProjectLink(String projectLink) { this.projectLink = projectLink; }

    public String getProjectStatus() { return projectStatus; }
    public void setProjectStatus(String projectStatus) { this.projectStatus = projectStatus; }

    public String getCertificateLink() { return certificateLink; }
    public void setCertificateLink(String certificateLink) { this.certificateLink = certificateLink; }

    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }
}