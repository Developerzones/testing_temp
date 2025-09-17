package com.example.demo.framework.AssignTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTaskDTO {
    private String title;
    private String description;
    private Long employeeId;
    private String priority;
    private LocalDate deadline;
}
