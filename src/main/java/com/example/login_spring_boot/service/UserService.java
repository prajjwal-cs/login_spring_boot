package com.example.login_spring_boot.service;

import com.example.login_spring_boot.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<Object> isUserPresent(User user);
}
