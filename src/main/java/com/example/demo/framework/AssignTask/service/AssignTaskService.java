package com.example.demo.framework.AssignTask.service;

import com.example.demo.framework.AssignTask.model.AssignTaskEntity;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface AssignTaskService {

    public AssignTaskEntity createTask(AssignTaskEntity task);

    public List<AssignTaskEntity> getAllTasks();

    public AssignTaskEntity getTaskById(Long id);
    public boolean existsByTitle(String title);
}
