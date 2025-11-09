package com.example.demo.user.service;

import com.example.demo.user.domain.User;
import com.example.demo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    // 会員登録
    public void join(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userMapper.insert(user);
    }

    // ユーザー
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
