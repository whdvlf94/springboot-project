package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService implements IUserService {
    private static List<User> users = new ArrayList<>();

    //    private static int userCount = 3;
    static {
        users.add(new User(1, "Kenneth", new Date(), "test1", "701010-1111111"));
        users.add(new User(2, "Alice", new Date(), "test2", "455110-2222222"));
        users.add(new User(3, "Elena", new Date(), "test3", "722312-3333333"));

    }

    @Override
    public List<User> getUserList() {
        return users;
    }

    @Override
    public User getUser(Integer id) {
        for (User user : users) {
            if (id.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User create(User createuser) {
        if (createuser.getId() == null) {
            //list id 값의 중복을 막기 위해 마지막 인덱스 아이디 값 + 1으로 설정
            createuser.setId(users.get(users.size() - 1).getId()+1);
        }
        createuser.setJoinDate(new Date());
        users.add(createuser);
        return createuser;
    }

    @Override
    public User update(User updateuser) {
        //get 메서드는 index를 받기 때문에, Id 값에서 1을 빼주어야 한다.
        User user = users.get(updateuser.getId()-1);
        user.setName(updateuser.getName());
        user.setSsn(updateuser.getSsn());
        user.setPassword(updateuser.getPassword());
        user.setJoinDate(new Date());

        return user;
    }

    @Override
    public User delete(Integer id) {
        Iterator<User> itUseres = users.iterator();
        //List : 순차적인 데이터 지원
        //set : 순차적인 데이터 지원, 중복 불가
        //map(key, value) : 순차적인 데이터 지원 안함, 중복 불가
        while (itUseres.hasNext()) {
            User user = itUseres.next();
            if (user.getId() == id) {
                itUseres.remove();
                return user;
            }
        }
        return null;
    }

}

