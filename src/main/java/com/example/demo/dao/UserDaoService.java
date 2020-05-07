package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(), "test1", "701010-1111111"));
        users.add(new User(2, "Alice", new Date(), "test2", "455110-2222222"));
        users.add(new User(3, "Elena", new Date(), "test3", "722312-3333333"));

    }

    public List<User> getUserList() {
        return users;
    }

    public User getUser(Integer id) {
        for (User user : users) {
            if (id.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }
}

