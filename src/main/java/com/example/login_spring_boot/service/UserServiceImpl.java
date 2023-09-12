package com.example.login_spring_boot.service;

import com.example.login_spring_boot.model.Role;
import com.example.login_spring_boot.model.User;
import com.example.login_spring_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //  user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public List<Object> isUserPresent(User user) {
        boolean userExist = false;
        String message = null;
        Optional<User> existingUserEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserEmail.isPresent()) {
            userExist = true;
            message = "Email Already Present!";
        }
        Optional<User> existingUserMobile = userRepository.findByMobile(user.getMobile());
        if (existingUserMobile.isPresent()) {
            userExist = true;
            message = "Mobile Number Already Present!";
        }
        if (existingUserMobile.isPresent() && existingUserEmail.isPresent()) {
            message = "Email and Mobile Number both Already Present!";
        }
        System.out.println("existingUserEmail.isPresent() - " +
                existingUserEmail.isPresent() + "existingUserMobile.isPresent() - "
                + existingUserMobile.isPresent());
        return Arrays.asList(userExist, message);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", email)
                )
        );
    }

}
