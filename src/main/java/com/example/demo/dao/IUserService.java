package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUserList();

    User getUser(Integer id);

    User create(User createuser);
    User delete(Integer id);
    User update(User updateuser);
}
