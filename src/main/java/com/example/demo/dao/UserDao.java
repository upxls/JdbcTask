package com.example.demo.dao;

import com.example.demo.model.User;
import lombok.Data;

import java.util.List;

@Data
public abstract class UserDao {
    public abstract List<User> findAll();
}
