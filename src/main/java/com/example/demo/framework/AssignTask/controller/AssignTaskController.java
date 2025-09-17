package com.example.demo.framework.AssignTask.controller;

import com.example.demo.framework.AssignTask.dto.AssignTaskDTO;
import com.example.demo.framework.AssignTask.model.AssignTaskEntity;
import com.example.demo.framework.AssignTask.model.EmployeeEntity;
import com.example.demo.framework.AssignTask.repo.AssignTaskRepo;
import com.example.demo.framework.AssignTask.repo.EmployeeRepository;
import com.example.demo.framework.AssignTask.service.AssignTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
public class AssignTaskController {


    private final AssignTaskService taskService;
    private final AssignTaskRepo taskRepository;
    private final EmployeeRepository employeeRepository;

    public AssignTaskController(AssignTaskService taskService,
                          AssignTaskRepo taskRepository,
                          EmployeeRepository employeeRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    // Create Task (normal creation, not assignment)
    @PostMapping
    public AssignTaskEntity createTask(@RequestBody AssignTaskEntity task) {
        return taskService.createTask(task);
    }

    // Get All Tasks
    @GetMapping
    public List<AssignTaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get Task By ID
    @GetMapping("/{id}")
    public AssignTaskEntity getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // Validate Task Title
    @GetMapping("/validate")
    public Map<String, Boolean> validateTaskTitle(@RequestParam String title) {
        boolean exists = taskService.existsByTitle(title);
        return Collections.singletonMap("exists", exists);
    }

    // Assign Task to Employee
    @PostMapping("/assignTask")
    public ResponseEntity<?> assignTask(@RequestBody AssignTaskDTO taskRequest) {
        // Validation: task title must be unique globally
        if (taskRepository.existsByTitle(taskRequest.getTitle())) {
            return ResponseEntity.badRequest().body("❌ This task title is already assigned to someone!");
        }

        EmployeeEntity employee = employeeRepository.findById(taskRequest.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        AssignTaskEntity task = new AssignTaskEntity();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setDeadline(taskRequest.getDeadline());
        task.setAssignee(employee);

        taskRepository.save(task);
        return ResponseEntity.ok("✅ Task assigned successfully!");
    }


}
