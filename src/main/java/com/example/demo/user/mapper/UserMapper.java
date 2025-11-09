package com.example.demo.user.mapper;

import com.example.demo.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insert(User user);
    User findByUsername(String username);
}
