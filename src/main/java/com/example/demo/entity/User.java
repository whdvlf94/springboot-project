package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//lombok 기능
@Data   // setter and getter, toString, hashCode, equals 를 선언하지 않고도 사용할 수 있도록하는 어노테이션
@AllArgsConstructor // Override Constructor 기능을 제공하는 어노테이션
@NoArgsConstructor  // Default Constructor
//@JsonIgnoreProperties(value = {"password", "ssn"})
@JsonFilter("UserInfo")
public class User {

    private Integer id;
    private String name;
    private Date joinDate;

//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String ssn;




}
