package com.example.demo.framework.AssignTask.repo;

import com.example.demo.framework.AssignTask.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<EmployeeEntity, Long> {}
