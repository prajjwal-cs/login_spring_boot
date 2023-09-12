package com.example.login_spring_boot.service;

import com.example.login_spring_boot.model.User;

import java.util.List;

public interface UserService {
    public void saveUser(User user);
    public List<Object> isUserPresent(User user);
}
