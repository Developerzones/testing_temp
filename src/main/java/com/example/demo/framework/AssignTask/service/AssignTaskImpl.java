package com.example.demo.framework.AssignTask.service;

import com.example.demo.framework.AssignTask.model.AssignTaskEntity;
import com.example.demo.framework.AssignTask.repo.AssignTaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignTaskImpl implements AssignTaskService {

    private final AssignTaskRepo repo;
    public AssignTaskImpl(AssignTaskRepo repo){ this.repo = repo; }

    public AssignTaskEntity createTask(AssignTaskEntity task){ return repo.save(task); }
    public List<AssignTaskEntity> getAllTasks(){ return repo.findAll(); }
    public AssignTaskEntity getTaskById(Long id){ return repo.findById(id).orElse(null); }

    public boolean existsByTitle(String title){ return repo.existsByTitle(title); }
}
