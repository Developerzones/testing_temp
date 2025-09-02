package com.example.demo.framework.login.service;

import com.example.demo.framework.login.model.LoginEntity;

public interface LoginService {


    public String register(LoginEntity u) ;

    public boolean authenticate(String user, String pass) ;


    }
