package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

@RestController
//@Slf4j
//@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDaoService service;


    @GetMapping("/users")
    public MappingJacksonValue getUserList() {


        List<User> list = service.getUserList();

        List<EntityModel<User>> result = new ArrayList<>();
        //users 에서 각 user 마다 link를 설정하기 위해서는
        //List<EntityModel<User>> 타입의 빈 List 를 추가해야 한다.


        //HATEOAS link 설정 작업
        for (User user : list) {
            EntityModel<User> model = new EntityModel<>(user);
            //User entity에 대해 각각의 EntityModel 객체를 생성한다.
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
            //HATEOAS 메서드를 이용해 각 user 마다의 link를 생성
            model.add(linkTo.withRel("user-detail"));
            //link name
            result.add(model);
            //HATEOAS 가 적용된 model을 result List에 추가한다.
        }

        //user 정보 filter 작업
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(result);
        // 새롭게 추가한 List에 HATEOAS가 적용된 model들이 포함되어 있다.
        mapping.setFilters(provider);

        return mapping;
    }

    @GetMapping("/users/{id}")
    public MappingJacksonValue getUser(@PathVariable Integer id) {
        User user = service.getUser(id);

        if (user == null) {
            throw new UserNotFoundException("id:" + id + " is not exist");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);

        return mapping;
    }

    @PostMapping("/users")
    public MappingJacksonValue create(@RequestBody User createuser) {
        User user = service.create(createuser);

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
        model.add(linkTo.withRel("user-detail"));


        //Filter
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(model);
        mapping.setFilters(provider);

        return mapping;

    }

    @PutMapping("/users/{id}")
    public MappingJacksonValue update(@PathVariable Integer id , @RequestBody User updateuser) {
        //@PathVariable을 이용하여 updateuser 내에 id 값을 삽입해 준다.
        updateuser.setId(id);
        User user = service.update(updateuser);

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
        model.add(linkTo.withRel("user-detail"));


        //Filter
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(model);
        mapping.setFilters(provider);

        return mapping;
    }

    @DeleteMapping("/users/{id}")
    public MappingJacksonValue delete(@PathVariable Integer id) {
        User user = service.delete(id);

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
        model.add(linkTo.withRel("user-detail"));


        //Filter
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(model);
        mapping.setFilters(provider);

        return mapping;
    }

    @GetMapping("/admin/users/{id}")
    public MappingJacksonValue getUserByAdmin(@PathVariable Integer id) {
        //데이터 형이 다른 경우 value를 통해 작성 가능

        User user = service.getUser(id);

        if (user == null) {
            throw new UserNotFoundException("id:" + id + " is not exist");
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);

        return mapping;

    }

    // spring-boot 2.1
    @GetMapping("/hateoas/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable Integer id) {
        User user = service.getUser(id);

        if (user == null) {
            throw new UserNotFoundException("id:" + id + " is not exist");
        }
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserList());
        model.add(linkTo.withRel("all-users"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider provider = new SimpleFilterProvider()
                .addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(model);
        mapping.setFilters(provider);

        return mapping;
    }
}
