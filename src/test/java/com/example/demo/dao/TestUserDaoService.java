package com.example.demo.dao;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;  //static import

import java.util.List;
import java.util.Optional;

public class TestUserDaoService {

    UserDaoService service = new UserDaoService();

    @Test
    public void  사용자목록() {
        List<User> list = service.getUserList();

        Assertions.assertTrue(list.size() == 3, "초기 사용자는 3명이어야 합니다.");
//      message는 condition이 false인 경우에만 출력된다.

        Assertions.assertEquals(3, list.size(),"초기 사용자는 3명이어야 합니다.");
//      assertEquals(expected, actual(실제 값), message(false인 경우에만 출력))


    }

    @Test
    public void 사용자정보확인() {
        User user = service.getUserList().get(0);
        Assertions.assertTrue(user.getId() == 1);
    }

    @Test
    public void 사용자상세조회() {
        User user = service.getUser(Integer.valueOf(1));
        assertNotNull(user);
        assertTrue(user.getId() == 1);
    }
}

