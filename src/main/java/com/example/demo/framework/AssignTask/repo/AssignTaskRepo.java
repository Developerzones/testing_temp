package com.example.demo.framework.AssignTask.repo;

import com.example.demo.framework.AssignTask.model.AssignTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignTaskRepo  extends JpaRepository<AssignTaskEntity, Long> {
    boolean existsByTitle(String title);

}
