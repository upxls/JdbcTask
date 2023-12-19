package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.User;
import com.example.demo.openapi.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final List<UserDao> userDaoList;

    public UserService(List<UserDao> userDaoList) {
        this.userDaoList = userDaoList;
    }

    public List<UserDto> findAllUsers() {
        List<User> allUsers = userDaoList.stream()
                .flatMap(userDao -> userDao.findAll().stream())
                .collect(Collectors.toList());

        return allUsers.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setUsername(user.getUsername());
                    userDto.setName(user.getName());
                    userDto.setSurname(user.getSurname());
                    return userDto;
                })
                .collect(Collectors.toList());
    }
}
