package com.example.demo.framework.login.repository;

import com.example.demo.framework.login.model.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepo extends JpaRepository<LoginEntity,Integer> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    LoginEntity findByUsername(String username);

}
